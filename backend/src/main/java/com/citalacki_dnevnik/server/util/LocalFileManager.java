package com.citalacki_dnevnik.server.util;

import com.citalacki_dnevnik.server.config.error.BadRequestException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor = @__(@Autowired), access = AccessLevel.PRIVATE)
@Service
@Slf4j
public class LocalFileManager {


    @Value("${document.logo.path}")
    public String LOGO_FILES_PATH;

    @Value("${document.user-image.path}")
    public String USER_PROFILE_IMAGE_FILES_PATH;


    public String saveFileToSystem(byte[] data, String filePath)  {
        String systemIdentifier = UUID.randomUUID().toString();

        Path path = Paths.get(filePath + "/" + systemIdentifier);
        try {
            Files.write(path, data, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            log.error("Error uploading file", e);
            throw new BadRequestException("FILE_UPLOAD_ERROR");
        }
        return systemIdentifier;
    }

    public byte[] downloadFile(String folder, String fileSystemIdentifer) {
        Path folderPath = Paths.get(folder);
        Path path = folderPath.resolve(fileSystemIdentifer);
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
            //throw new BadRequestException(CAN_NOT_READ_FILE);
        }
        return bytes;
    }


}
