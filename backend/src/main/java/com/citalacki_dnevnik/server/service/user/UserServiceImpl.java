package com.citalacki_dnevnik.server.service.user;

import com.citalacki_dnevnik.server.config.Constants;
import com.citalacki_dnevnik.server.config.error.BadRequestException;
import com.citalacki_dnevnik.server.event.listener.NewUserMailEvent;
import com.citalacki_dnevnik.server.event.listener.TestEvent;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.auth.AuthResponse;
import com.citalacki_dnevnik.server.model.dto.user.*;
import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.model.user.UserGroup;
import com.citalacki_dnevnik.server.model.user.UserPasswordResetToken;
import com.citalacki_dnevnik.server.model.user.enums.UserType;
import com.citalacki_dnevnik.server.repository.user.UserGroupRepository;
import com.citalacki_dnevnik.server.repository.user.UserPasswordResetTokenRepository;
import com.citalacki_dnevnik.server.repository.user.UserRepository;
import com.citalacki_dnevnik.server.config.error.ErrorMessageConstants;
import com.citalacki_dnevnik.server.util.LocalFileManager;
import com.citalacki_dnevnik.server.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.*;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final JwtUtil jwtUtil;
    private final UserPasswordResetTokenRepository userPasswordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserGroupRepository userGroupRepository;
    private final LocalFileManager localFileManager;

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findByEntityStatus(EntityStatus.REGULAR)
                .stream()
                .map(u -> convertToDTO(u))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User userForUpdate = userRepository.findByIdAndEntityStatus(id, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
        userForUpdate.setFirstName(userDTO.getFirstName());
        userForUpdate.setLastName(userDTO.getLastName());
        userForUpdate.setCity(userDTO.getCity());
        userForUpdate.setBirthDate(userDTO.getBirthDate());
        userForUpdate.setPhone(userDTO.getPhone());
        userForUpdate.setImageUrl(userDTO.getImageUrl());

        User updatedUser = userRepository.save(userForUpdate);
        return convertToDTO(updatedUser);
    }

    @Override
    @Transactional
    public UserDTO deleteUser(Long userId) {
        User user = userRepository.findByIdAndEntityStatus(userId, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
        user.setEntityStatus(EntityStatus.DELETED);
        return convertToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUser(Long userId) {
        User user = userRepository.findByIdAndEntityStatus(userId, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
        return convertToDTO(user);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsernameAndEntityStatus(username, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(ErrorMessageConstants.USER_NOT_FOUND_BY_USERNAME));
    }

    @Override
    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUserType(user.getUserType());
        dto.setCity(user.getCity());
        dto.setBirthDate(user.getBirthDate());
        dto.setPhone(user.getPhone());
        dto.setImageUrl(user.getImageUrl());
        dto.setUserGroups(user.getUserGroups());
        return dto;
    }

    @Override
    public User convertFromDTO(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setUserType(dto.getUserType());
        user.setCity(dto.getCity());
        user.setBirthDate(dto.getBirthDate());
        user.setPhone(dto.getPhone());
        user.setImageUrl(dto.getImageUrl());
        user.setUserGroups(dto.getUserGroups());
        return user;
    }

    public void sendMail() {
        eventPublisher.publishEvent(TestEvent.builder().build());
    }

    @Override
    public AuthResponse generateAuthResponse(String username) {
        AuthResponse authResponse = new AuthResponse();
        User user = userRepository.findByUsernameAndEntityStatus(username, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(ErrorMessageConstants.USER_NOT_FOUND_BY_USERNAME));
        String token = jwtUtil.generateToken(username);
        authResponse.setUser(convertToAuthDTO(user));
        authResponse.setToken(token);
        return authResponse;
    }

    @Override
    @Transactional
    public void sendCredentialsEmail(List<Long> usersId) {
        List<User> users= userRepository.findAllById(usersId);
        for(User user : users){
            sendPasswordForNewUser(user);
        }
    }

    @Transactional
    private void sendPasswordForNewUser(User user){
        if (user.getEmail().isEmpty())
            throw new BadRequestException("EMAIL_IS_MISSING");
        UserPasswordResetToken token = new UserPasswordResetToken();
        token.setUser(user);
        token.setEntityStatus(EntityStatus.REGULAR);
        token.setToken(UUID.randomUUID().toString());
        token.setCreatedBy(getCurrentUser().getUsername());
        token.setCreatedDate(Instant.now());
        token = userPasswordResetTokenRepository.save(token);
        NewUserMailEvent mailEvent = NewUserMailEvent.builder()
                .user(user)
                .token(token.getToken())
                .build();
        eventPublisher.publishEvent(mailEvent);
    }

    @Override
    @Transactional
    public void sendLinkForResetPassword(String email) {
        User user = userRepository.findByEmailAndEntityStatus(email, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND_BY_EMAIL));
        UserPasswordResetToken token = new UserPasswordResetToken();
        token.setUser(user);
        token.setEntityStatus(EntityStatus.REGULAR);
        token.setToken(UUID.randomUUID().toString());
        token = userPasswordResetTokenRepository.save(token);
        NewUserMailEvent mailEvent = NewUserMailEvent.builder()
                .user(user)
                .token(token.getToken())
                .build();
        eventPublisher.publishEvent(mailEvent);
    }


    @Override
    public UserPasswordResetToken validatePasswordResetToken(String token) {
        final UserPasswordResetToken passToken = userPasswordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("TOKEN_NOT_FOUND"));
        if (isTokenExpired(passToken)) {
            throw new BadRequestException("TOKEN_EXPIRED");
        }
        return passToken;
    }

    @Override
    @Transactional
    public UserDTO resetPassword(PasswordDTO passwordDTO) {
        UserPasswordResetToken validatedToken = validatePasswordResetToken(passwordDTO.getToken());
        User user = validatedToken.getUser();

        user.setPassword(passwordEncoder.encode(String.valueOf(passwordDTO.getNewPassword())));
        User updatedUser = userRepository.save(user);
        validatedToken.setUsed(true);
        userPasswordResetTokenRepository.save(validatedToken);
//        userSearchRepository.save(UserEDTO.convertToEDTO(user));
        return convertToDTO(updatedUser);
    }


    @Override
    @Transactional
    public UserDTO updatePassword(Long userId, PasswordUpdateDTO passwordUpdateDTO) {
        User user = userRepository.findByIdAndEntityStatus(userId, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        if(!passwordEncoder.matches(passwordUpdateDTO.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Trenutna sifra nije tacna");
        }
        if(passwordUpdateDTO.getNewPassword().length() < 6) {
            throw new BadRequestException("Lozinka mora imati najmanje 6 karaktera");
        }
        if(!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmPassword())) {
            throw new BadRequestException("Lozinke se ne poklapaju");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    @Override
    @Transactional
    public UserDTO uploadProfileImage(Long id, MultipartFile profileImage) {
        User user = userRepository.findByIdAndEntityStatus(id, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
        try {
            user.setImageUrl(saveFileToSystem(profileImage.getBytes()));
            this.userRepository.save(user);
            return this.convertToDTO(user);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException(FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public byte[] getProfileImage(Long id) {
        User user = userRepository.findByIdAndEntityStatus(id, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        String identifier = user.getImageUrl();
//        if (Variables.springEnv.equals("dev") || Variables.springEnv.equals("docker-local")) {
        if(identifier != null) {
            return localFileManager.downloadFile(localFileManager.USER_PROFILE_IMAGE_FILES_PATH, identifier);
        }else {
            return null;
        }

//        } else {
//            return awsBucketManager.downloadFile(identifier, awsBucketManager.MAINTENANCE_IMAGE_FILES_PATH);
//        }
    }

    @Override
    @Transactional
    public UserDTO register(UserRegisterDTO userRegisterDTO) {
        validateUserRegister(userRegisterDTO);
        User newUser = new User();
        newUser.setUsername(userRegisterDTO.getUsername());
        newUser.setEmail(userRegisterDTO.getEmail());
        newUser.setFirstName(userRegisterDTO.getFirstName());
        newUser.setLastName(userRegisterDTO.getLastName());
        newUser.setCity(userRegisterDTO.getCity());
        newUser.setPhone(userRegisterDTO.getPhone());
        newUser.setBirthDate(userRegisterDTO.getBirthDate());
        newUser.setEntityStatus(EntityStatus.REGULAR);
        newUser.setPassword(new BCryptPasswordEncoder().encode(userRegisterDTO.getPassword()));
        newUser.setUserType(UserType.USER);
        List<UserGroup> userGroups = new ArrayList<>();
        userGroups.add(userGroupRepository.findById(Constants.USER_GROUP_ID).get());
        newUser.setUserGroups(userGroups);

        return convertToDTO(userRepository.save(newUser));
    }


    private  String saveFileToSystem(byte[] data) {
//        if (Variables.springEnv.equals("dev") || Variables.springEnv.equals("docker-local")) {
        return localFileManager.saveFileToSystem(data, localFileManager.USER_PROFILE_IMAGE_FILES_PATH);
//        } else {
//            return awsBucketManager.saveFileToSystem(data, awsBucketManager.LOGO_FILES_PATH);
//        }
    }

    private boolean isTokenExpired(UserPasswordResetToken passToken){
        return passToken.getCreatedDate().plusSeconds(2592000).isBefore(Instant.now()) || passToken.isUsed();
    }

    private AuthUserDTO convertToAuthDTO(User user) {
        AuthUserDTO dto = new AuthUserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUserType(user.getUserType());
        dto.setImageUrl(user.getImageUrl());
        dto.setUserGroups(user.getUserGroups());
        return dto;
    }

    private void validateUserRegister(UserRegisterDTO userRegisterDTO) {
        Optional<User> byUsername = userRepository.findByUsernameAndEntityStatus(userRegisterDTO.getUsername(), EntityStatus.REGULAR);
        if(byUsername.isPresent()){
            throw new BadRequestException("Korisnicko ime: " + userRegisterDTO.getUsername() + " se vec koristi");
        }
        Optional<User> byEmail = userRepository.findByEmailAndEntityStatus(userRegisterDTO.getEmail(), EntityStatus.REGULAR);
        if(byEmail.isPresent()){
            throw new BadRequestException("Email: " + userRegisterDTO.getEmail() + " se vec koristi");
        }
        if(userRegisterDTO.getFirstName() != null & userRegisterDTO.getLastName() != null & userRegisterDTO.getUsername() != null
            & userRegisterDTO.getEmail() != null & userRegisterDTO.getPassword() != null) {
            if(userRegisterDTO.getFirstName().isEmpty() | userRegisterDTO.getLastName().isEmpty() | userRegisterDTO.getUsername().isEmpty()
                    | userRegisterDTO.getEmail().isEmpty() | userRegisterDTO.getPassword().isEmpty()) {
                throw new BadRequestException("Morate popuniti sva obavezna polja");
            }
            if(!userRegisterDTO.getEmail().contains("@") | !userRegisterDTO.getEmail().contains(".com")) {
                throw new BadRequestException("Email mora biti pravilno napisan");
            }
            if(userRegisterDTO.getPassword().length() < 6) {
                throw new BadRequestException("Lozinka mora imati najmanje 6 karaktera");
            }
            if(!userRegisterDTO.getPassword().equals(userRegisterDTO.getRepeatPassword())) {
                throw new BadRequestException(PASSWORD_DOESNT_MATCH);
            }
        } else {
            throw new BadRequestException("Morate popuniti sva obavezna polja");
        }


    }

}
