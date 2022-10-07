package com.winterhold.service;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dao.BookRepository;
import com.winterhold.dto.author.AuthorDetailDTO;
import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.InsertAuthorDTO;
import com.winterhold.dto.author.UpdateAuthorDTO;
import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AuthorServiceImplementation implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private final Integer ROWS_IN_PAGE = 10;

    @Override
    public List<AuthorGridDTO> getAuthorGrid(Integer page, String name) {
        Pageable pagination = PageRequest.of(page - 1, ROWS_IN_PAGE, Sort.by("id"));
        List<AuthorGridDTO> grid = authorRepository.findAll(name, pagination);
        for (AuthorGridDTO rows : grid) {
            String fullName = String.format("%s %s %s", rows.getTitle(), rows.getFirstName(), rows.getLastName());
            rows.setFullName(fullName);
            Long age;
            if (rows.getDeceasedDate() != null) {
                age = ChronoUnit.YEARS.between(rows.getBirthDate(), rows.getDeceasedDate());
            }else{
                age = ChronoUnit.YEARS.between(rows.getBirthDate(), LocalDate.now());
            }
            rows.setAge(age);
            String status = "Alive";
            if (rows.getDeceasedDate() != null) {
                status = "Deceased";
            }
            rows.setStatus(status);
        }
        return grid;
    }

    @Override
    public Long getTotalPages(String name) {
        double totalData = (double) authorRepository.count(name);
        long totalPage = (long) (Math.ceil(totalData / (double) ROWS_IN_PAGE));
        return totalPage;
    }

    @Override
    public Long saveAuthor(InsertAuthorDTO dto) {
        Author entity = new Author(dto.getId(), dto.getTitle(), dto.getFirstName(), dto.getLastName(), dto.getBirthDate(), dto.getDeceasedDate(), dto.getEducation(), dto.getSummary());
        Author newAuthor = authorRepository.save(entity);
        return newAuthor.getId();
    }

    @Override
    public UpdateAuthorDTO getUpdateAuthor(Long id) {
        Optional<Author> nullableEntity = authorRepository.findById(id);
        Author entity = nullableEntity.get();
      UpdateAuthorDTO dto = new UpdateAuthorDTO(entity.getId(),
                entity.getTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getDeceasedDate(),
                entity.getEducation(),
                entity.getSummary());
        return dto;
    }

    @Override
    public void updateAuthor(UpdateAuthorDTO dto) {
        Author entity = new Author(dto.getId(), dto.getTitle(), dto.getFirstName(), dto.getLastName(), dto.getBirthDate(), dto.getDeceasedDate(), dto.getEducation(), dto.getSummary());
        authorRepository.save(entity);
    }

    @Override
    public Boolean deleteAuthor(Long id) {
        Long totalDependentBooks = dependentBook(id);
        if (totalDependentBooks == 0) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Long dependentBook(Long id) {
        Long totalDependentBooks = bookRepository.countByAuthorId(id);
        return totalDependentBooks;    }

    @Override
    public AuthorGridDTO getAuthorHeader(Long id) {
        Author entity =authorRepository.findById(id).get();
        String fullName = String.format("%s %s %s",entity.getTitle(), entity.getFirstName(),entity.getLastName());
        Long age;
        if (entity.getDeceasedDate() != null) {
            age = ChronoUnit.YEARS.between(entity.getBirthDate(), entity.getDeceasedDate());
        }else{
            age = ChronoUnit.YEARS.between(entity.getBirthDate(), LocalDate.now());
        }
        String status = "Alive";
        if (entity.getDeceasedDate() != null) {
            status = "Deceased";
        }

        AuthorGridDTO header = new AuthorGridDTO(entity.getId(),
                entity.getTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                fullName,
                entity.getBirthDate(),
                age,
                entity.getDeceasedDate(),
                status,
                entity.getEducation(),
                entity.getSummary());
        return header;
    }

    @Override
    public List<BookGridDTO> getDetailBooks(Integer page, Long id) {
        Pageable pagination = PageRequest.of(page - 1, ROWS_IN_PAGE);
        List<BookGridDTO> details=bookRepository.findAllByAuthorId(id,pagination);
        for (BookGridDTO rows : details) {
            String borrowed = "Available";
            if (rows.getIsBorrowed()){
                borrowed = "Borrowed";
            }
            rows.setBorrowed(borrowed);
        }
        return details;
    }

    @Override
    public Long getTotalDetailPages(Long id) {
        double totalData = (double) bookRepository.countByAuthorId(id);
        long totalPage = (long) (Math.ceil(totalData / (double) ROWS_IN_PAGE));
        return totalPage;
    }

    @Override
    public AuthorDetailDTO getAuthorWithBooks(Long id) {
        AuthorGridDTO author = getAuthorHeader(id);
        List<BookGridDTO> books = bookRepository.findAllByAuthorId(id);
        AuthorDetailDTO detail = new AuthorDetailDTO(author,books);
        return detail;
    }
}
