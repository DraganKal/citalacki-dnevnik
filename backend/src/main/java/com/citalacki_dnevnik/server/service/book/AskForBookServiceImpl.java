package com.citalacki_dnevnik.server.service.book;

import com.citalacki_dnevnik.server.config.error.BadRequestException;
import com.citalacki_dnevnik.server.model.EntityStatus;
import com.citalacki_dnevnik.server.model.book.AskForBook;
import com.citalacki_dnevnik.server.model.dto.book.AskForBookDTO;
import com.citalacki_dnevnik.server.model.user.User;
import com.citalacki_dnevnik.server.repository.book.AskForBookRepository;
import com.citalacki_dnevnik.server.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.citalacki_dnevnik.server.config.error.ErrorMessageConstants.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AskForBookServiceImpl implements AskForBookService {

    private final AskForBookRepository askForBookRepository;
    private final UserRepository userRepository;

    @Override
    public Page<AskForBookDTO> findAllNotDonePageable(Pageable pageable) {
        return askForBookRepository.findAllByDone(false, pageable).map(this::convertToDTO);
    }

    @Override
    public AskForBookDTO getOne(Long id) {
        AskForBook askForBook = askForBookRepository.findByIdAndEntityStatus(id, EntityStatus.REGULAR)
                .orElseThrow(() -> new BadRequestException(ASK_FOR_BOOK_NOT_FOUND));
        return convertToDTO(askForBook);
    }

    @Override
    @Transactional
    public AskForBookDTO create(AskForBookDTO askForBookDTO) {
        AskForBook newAskForBook = new AskForBook();
        newAskForBook = convertFromDTO(askForBookDTO);
        newAskForBook.setDone(false);
        newAskForBook.setEntityStatus(EntityStatus.REGULAR);

        AskForBook saved = askForBookRepository.save(newAskForBook);
        return convertToDTO(saved);
    }

    @Override
    public AskForBook convertFromDTO(AskForBookDTO askForBookDTO) {
        AskForBook askForBook = new AskForBook();
        askForBook.setId(askForBookDTO.getId());
        askForBook.setName(askForBookDTO.getName());
        askForBook.setAuthor(askForBookDTO.getAuthor());
        askForBook.setDone(askForBookDTO.isDone());

        if(askForBookDTO.getUserId() != null) {
            User user = userRepository.findByIdAndEntityStatus(askForBookDTO.getUserId(), EntityStatus.REGULAR)
                    .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
            askForBook.setUser(user);
        }

        return askForBook;
    }

    @Override
    public AskForBookDTO convertToDTO(AskForBook askForBook) {
        AskForBookDTO dto = new AskForBookDTO();
        dto.setId(askForBook.getId());
        dto.setName(askForBook.getName());
        dto.setAuthor(askForBook.getAuthor());
        dto.setDone(askForBook.isDone());

        if(askForBook.getUser() != null) {
            dto.setUserId(askForBook.getUser().getId());
            dto.setUser(askForBook.getUser().getUsername());
        }

        return dto;
    }


}
