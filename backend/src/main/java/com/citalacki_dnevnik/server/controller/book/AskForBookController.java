package com.citalacki_dnevnik.server.controller.book;

import com.citalacki_dnevnik.server.model.dto.book.AskForBookDTO;
import com.citalacki_dnevnik.server.service.book.AskForBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ask-for-book")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AskForBookController {

    private final AskForBookService askForBookService;

    @PostMapping
    public ResponseEntity<AskForBookDTO> create(@RequestBody AskForBookDTO askForBookDTO) {
        return new ResponseEntity<>(askForBookService.create(askForBookDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<AskForBookDTO>> findAllNotDonePageable(Pageable pageable) {
        return new ResponseEntity<>(askForBookService.findAllNotDonePageable(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AskForBookDTO> findOne(@PathVariable Long id) {
        return new ResponseEntity<>(askForBookService.getOne(id), HttpStatus.OK);
    }


}
