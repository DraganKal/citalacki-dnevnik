package com.citalacki_dnevnik.server.controller;

import com.citalacki_dnevnik.server.model.dto.book.BookDTO;
import com.citalacki_dnevnik.server.model.dto.user.*;
import com.citalacki_dnevnik.server.model.dto.userBook.UserBookDTO;
import com.citalacki_dnevnik.server.service.user.UserNotificationService;
import com.citalacki_dnevnik.server.service.user.UserService;
import com.citalacki_dnevnik.server.service.userBook.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 Basic user CRUD controller. It's basically a test CRUD
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final UserNotificationService userNotificationService;
    private final UserBookService userBookService;

//    @GetMapping(value = "/{id}")
//    @PreAuthorize("hasPermission('user', 'read')")
    @GetMapping(value = "/details/{id}")
    public ResponseEntity<UserDTO> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO,
                                              @PathVariable Long id) {
        return new ResponseEntity<>(userService.updateUser(id, userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        return null;
    }

    @GetMapping(value = "/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return new ResponseEntity<>(userService.convertToDTO(userService.getCurrentUser()), HttpStatus.OK);
    }

    @PostMapping("/send-credentials-to-all")
    @PreAuthorize("hasPermission('employee', 'read')")
    public ResponseEntity<Void> sendCredentialsEmailToAll(@RequestBody List<Long> usersId) {
        userService.sendCredentialsEmail(usersId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/send-reset-password-email")
    public ResponseEntity<Void> sendLinkForResetPassword(@RequestBody ResetPasswordEmailDTO resetPasswordEmailDTO) {
        userService.sendLinkForResetPassword(resetPasswordEmailDTO.getEmail());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "/email")
    public ResponseEntity<Void> sendEmail() {
        userService.sendMail();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}/profile-image", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> uploadProfileImage(@PathVariable("id") Long id, @RequestPart(value = "image") MultipartFile image) {
        return new ResponseEntity<>(userService.uploadProfileImage(id, image), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/profile-image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getProfileImage(@PathVariable("id") Long id) {
        byte[] image = userService.getProfileImage(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Void> validateToken(@RequestBody String token) {
        userService.validatePasswordResetToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody PasswordDTO passwordDTO) {
        UserDTO updated = userService.resetPassword(passwordDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/update-password")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        UserDTO updatedUser = userService.updatePassword(id, passwordUpdateDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return new ResponseEntity<>(userService.register(userRegisterDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}/notifications")
    public ResponseEntity<List<UserNotificationDTO>> getAllNotifications(@PathVariable Long id) {
        return new ResponseEntity<>(userNotificationService.getAllPresentNotificationsByUserId(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/notification/{notificationId}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserNotificationDTO> deleteUserNotification(@PathVariable(name = "id") Long id,
                                                                      @PathVariable(name = "notificationId") Long notificationId) {
        UserNotificationDTO deletedNotification = userNotificationService.deleteById(id, notificationId);
        return new ResponseEntity<>(deletedNotification, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/notifications", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserNotificationDTO>> deleteAllUserNotifications(@PathVariable(name = "id") Long id) {
        List<UserNotificationDTO> deletedNotifications = userNotificationService.deleteAllByUserId(id);
        return new ResponseEntity<>(deletedNotifications, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/read-notifications")
    public ResponseEntity<Page<UserNotificationDTO>> getAllReadedNotificationsByUserIdPageable(@PathVariable() Long id,
                                                                                               Pageable pageable) {
        return new ResponseEntity<>(userNotificationService.getAllReadedByUserIdPageable(id, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/books-in-shelf")
    public ResponseEntity<List<UserBookDTO>> getAllUserBooks(@PathVariable() Long id) {
        return new ResponseEntity<>(userBookService.getAllUserBooksByUserId(id), HttpStatus.OK);
    }

}
