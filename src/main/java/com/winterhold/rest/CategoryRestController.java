package com.winterhold.rest;

import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name){
        List<CategoryGridDTO> dtos = service.getCategoryGrid(page, name);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody InsertCategoryDTO dto, BindingResult bindingResult){
        String respondId = service.saveCategory(dto);
        dto.setName(respondId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping
    public ResponseEntity<Object> put(@RequestBody UpdateCategoryDTO dto, BindingResult bindingResult) {
        service.updateCategory(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestParam(required = true) String name){
        Boolean success = service.deleteCategory(name);
        if (success){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(success);
        }
        Long dependentBook = service.dependentBook(name);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dependentBook);
    }

    @GetMapping(value = "/detail/{name}")
    public ResponseEntity<Object> detail(@RequestParam(required = true) String name){
        var detail= service.getCategoryWithBooks(name);
        return ResponseEntity.status(HttpStatus.OK).body(detail);
    }

    @PostMapping(value = "/detail")
    public ResponseEntity<Object> postDetail(@RequestBody InsertBookDTO dto,
                                             BindingResult bindingResult){
        String respondId = service.saveBook(dto);
        dto.setCode(respondId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping(value = "/detail")
    public ResponseEntity<Object> putDetail(@RequestBody UpdateBookDTO dto,
                                             BindingResult bindingResult){
        service.updateBook(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping(value = "/detail")
    public ResponseEntity<Object> deleteDetail(@RequestParam(required = true) String code){
       service.deleteBook(code);
        return ResponseEntity.status(HttpStatus.OK).body("Sukses untuk delete buku.");
    }
}
