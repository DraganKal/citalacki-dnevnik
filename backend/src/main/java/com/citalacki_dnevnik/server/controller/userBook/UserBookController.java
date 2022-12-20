package com.citalacki_dnevnik.server.controller.userBook;

import com.citalacki_dnevnik.server.model.dto.userBook.UserBookDTO;
import com.citalacki_dnevnik.server.service.userBook.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-book")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserBookController {

    private final UserBookService userBookService;

    @PostMapping
    public ResponseEntity<UserBookDTO> create(@RequestBody UserBookDTO userBookDTO) {
        return new ResponseEntity<>(userBookService.create(userBookDTO), HttpStatus.OK);
    }

}
