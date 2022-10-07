package com.winterhold.service;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CategoryRepository;
import com.winterhold.dto.book.BookGridDTO;
import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.dto.category.CategoryDetailDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Book;
import com.winterhold.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private final Integer ROWS_IN_PAGE = 10;

    @Override
    public List<CategoryGridDTO> getCategoryGrid(Integer page, String name) {
        Pageable pagination = PageRequest.of(page - 1, ROWS_IN_PAGE, Sort.by("name"));
        List<CategoryGridDTO> grid = categoryRepository.findAll(name, pagination);
        for (CategoryGridDTO rows : grid) {
            Long totalBooks = bookRepository.countByCategoryName(rows.getName());
            rows.setTotalBooks(totalBooks);
        }
        return grid;
    }

    @Override
    public Long getTotalPages(String name) {
        double totalData = (double) categoryRepository.count(name);
        long totalPage = (long) (Math.ceil(totalData / (double) ROWS_IN_PAGE));
        return totalPage;
    }

    @Override
    public String saveCategory(InsertCategoryDTO dto) {
        Category entity = new Category(dto.getName(), dto.getFloor(), dto.getIsle(), dto.getBay());
        Category newCategory = categoryRepository.save(entity);
        return newCategory.getName();
    }

    @Override
    public UpdateCategoryDTO getUpdateCategory(String name) {
        Optional<Category> nullableEntity = categoryRepository.findById(name);
        Category entity = nullableEntity.get();
        UpdateCategoryDTO dto = new UpdateCategoryDTO(entity.getName(),
                entity.getFloor(),
                entity.getIsle(),
                entity.getBay());
        return dto;
    }

    @Override
    public void updateCategory(UpdateCategoryDTO dto) {
        Category entity = new Category(dto.getName(),
                dto.getFloor(),
                dto.getIsle(),
                dto.getBay());
        categoryRepository.save(entity);
    }

    @Override
    public Boolean deleteCategory(String name) {
        Long totalDependentBooks = dependentBook(name);
        if (totalDependentBooks == 0) {
            categoryRepository.deleteById(name);
            return true;
        }
        return false;
    }

    @Override
    public Long dependentBook(String name) {
        Long totalDependentBooks = bookRepository.countByCategoryName(name);
        return totalDependentBooks;
    }

    @Override
    public CategoryGridDTO getCategoryHeader(String name) {
        Category entity =categoryRepository.findById(name).get();
        CategoryGridDTO header = new CategoryGridDTO(entity.getName(), entity.getFloor(), entity.getIsle(), entity.getBay());
        return header;
    }

    @Override
    public List<BookGridDTO> getDetailBooks(Integer page, String name,String title,String authorName) {
        Pageable pagination = PageRequest.of(page - 1, ROWS_IN_PAGE);
        List<BookGridDTO> details=bookRepository.findAllByCategoryName(name,title,authorName,pagination);
        for (BookGridDTO rows : details) {
            String borrowed = "Available";
            if (rows.getIsBorrowed()==true){
                borrowed = "Borrowed";
            }
            rows.setBorrowed(borrowed);
        }
        return details;
    }

    @Override
    public Long getTotalDetailPages(String name,String title,String authorName) {
        double totalData = (double) bookRepository.countByCategoryName(name,title,authorName);
        long totalPage = (long) (Math.ceil(totalData / (double) ROWS_IN_PAGE));
        return totalPage;
    }

    @Override
    public String saveBook(InsertBookDTO dto) {
        Book entity = new Book(dto.getCode(),
                dto.getTitle(),
                dto.getCategoryName(),
                dto.getAuthorId(),
                dto.getIsBorrowed(),
                dto.getSummary(),
                dto.getReleaseDate(),
                dto.getTotalPage());
        Book newBook = bookRepository.save(entity);
        return newBook.getCode();
    }

    @Override
    public UpdateBookDTO getUpdateBook(String code) {
        Optional<Book> entityNullable = bookRepository.findById(code);
        Book entity = entityNullable.get();
        UpdateBookDTO orderDTO = new UpdateBookDTO(entity.getCode(),
                entity.getTitle(),
                entity.getCategoryName(),
                entity.getAuthorId(),
                entity.getIsBorrowed(),
                entity.getSummary(),
                entity.getReleaseDate(),
                entity.getTotalPage());
        return orderDTO;
    }

    @Override
    public void updateBook(UpdateBookDTO dto) {
        Book entity = new Book(dto.getCode(),
                dto.getTitle(),
                dto.getCategoryName(),
                dto.getAuthorId(),
                dto.getIsBorrowed(),
                dto.getSummary(),
                dto.getReleaseDate(),
                dto.getTotalPage());
        bookRepository.save(entity);
    }

    @Override
    public void deleteBook(String code) {
        bookRepository.deleteById(code);
    }

    @Override
    public List<DropdownDTO> getAuthorDropdown() {
        return authorRepository.findAllOrderByName();
    }

    @Override
    public Boolean checkExistingCategory(String name) {
        Long sameCategory = categoryRepository.countCategory(name);
        if (sameCategory>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkExistingCode(String code) {
        Long sameCode = bookRepository.countBook(code);
        if (sameCode>0){
            return true;
        }
        return false;
    }

    @Override
    public CategoryDetailDTO getCategoryWithBooks(String name) {
        CategoryGridDTO category = getCategoryHeader(name);
        List<BookGridDTO> books = bookRepository.findAllByCategoryName(name);
        CategoryDetailDTO detail = new CategoryDetailDTO(category,books);
        return detail;
    }
}
