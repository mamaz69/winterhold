package com.winterhold.service;

import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.dto.category.CategoryDetailDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.dto.utility.DropdownDTO;

import java.util.List;

public interface CategoryService {
    public List<CategoryGridDTO> getCategoryGrid(Integer page, String name);
    public Long getTotalPages(String name);

    public String saveCategory(InsertCategoryDTO dto);
    public UpdateCategoryDTO getUpdateCategory(String name);

    public void updateCategory(UpdateCategoryDTO dto);
    public Boolean deleteCategory(String name);
    public Long dependentBook(String name);

    public CategoryGridDTO getCategoryHeader(String name);
    public List<BookGridDTO> getDetailBooks(Integer page, String name,String title,String authorName);
    public Long getTotalDetailPages(String name,String title,String authorName);

    public String saveBook(InsertBookDTO dto);
    public UpdateBookDTO getUpdateBook(String code);
    public void updateBook(UpdateBookDTO dto);
    public void deleteBook(String code);
    public List<DropdownDTO> getAuthorDropdown();

    public Boolean checkExistingCategory(String name);
    public Boolean checkExistingCode(String code);

    public CategoryDetailDTO getCategoryWithBooks(String name);
}
