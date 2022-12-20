package com.citalacki_dnevnik.server.service.user;

import com.citalacki_dnevnik.server.model.auth.AuthResponse;
import com.citalacki_dnevnik.server.model.dto.user.PasswordDTO;
import com.citalacki_dnevnik.server.model.dto.user.PasswordUpdateDTO;
import com.citalacki_dnevnik.server.model.dto.user.UserDTO;
import com.citalacki_dnevnik.server.model.dto.user.UserRegisterDTO;
import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.model.user.UserPasswordResetToken;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User getCurrentUser();

    UserDTO convertToDTO(User user);

    User convertFromDTO(UserDTO dto);

    UserDTO updateUser(Long id, UserDTO userDTO);

    UserDTO deleteUser(Long userId);

    UserDTO getUser(Long userId);

    List<UserDTO> findAllUsers();

    void sendMail();

    AuthResponse generateAuthResponse(String username);

    void   sendCredentialsEmail(List<Long> usersId);

    UserPasswordResetToken validatePasswordResetToken(String token);

    UserDTO resetPassword(PasswordDTO passwordDTO);

    UserDTO updatePassword(Long userId, PasswordUpdateDTO passwordUpdateDTO);

    void sendLinkForResetPassword(String email);

    UserDTO uploadProfileImage(Long id, MultipartFile profileImage);

    byte[] getProfileImage(Long id);

    UserDTO register(UserRegisterDTO userRegisterDTO);

}
