package com.winterhold.service;

import com.winterhold.dto.author.AuthorDetailDTO;
import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.InsertAuthorDTO;
import com.winterhold.dto.author.UpdateAuthorDTO;
import com.winterhold.dto.book.BookGridDTO;

import java.util.List;

public interface AuthorService {

    public List<AuthorGridDTO> getAuthorGrid(Integer page, String name);
    public Long getTotalPages(String name);

    public Long saveAuthor(InsertAuthorDTO dto);
    public UpdateAuthorDTO getUpdateAuthor(Long id);

    public void updateAuthor(UpdateAuthorDTO dto);
    public Boolean deleteAuthor(Long id);
    public Long dependentBook(Long id);

    public AuthorGridDTO getAuthorHeader(Long id);
    public List<BookGridDTO> getDetailBooks(Integer page, Long id);
    public Long getTotalDetailPages(Long id);

    public AuthorDetailDTO getAuthorWithBooks(Long id);
}
