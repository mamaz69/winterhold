package com.winterhold.rest;

import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.InsertAuthorDTO;
import com.winterhold.dto.author.UpdateAuthorDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.entity.Author;
import com.winterhold.service.AuthorService;
import com.winterhold.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorRestController {

    @Autowired
    private AuthorService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name){
        List<AuthorGridDTO> dtos = service.getAuthorGrid(page, name);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping(value = "/totalPage")
    public ResponseEntity<Object> getTotalPage(@RequestParam(defaultValue = "") String name){
        Long totalPage = service.getTotalPages(name);
        return ResponseEntity.status(HttpStatus.OK).body(totalPage);
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody InsertAuthorDTO dto, BindingResult bindingResult){
        Long respondId = service.saveAuthor(dto);
        dto.setId(respondId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PutMapping
    public ResponseEntity<Object> put(@RequestBody UpdateAuthorDTO dto, BindingResult bindingResult) {
        service.updateAuthor(dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestParam(required = true) Long id){
        Boolean success = service.deleteAuthor(id);
        if (success){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(success);
        }
        Long dependentBook = service.dependentBook(id);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dependentBook);
    }

    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<Object> detail(@RequestParam(required = true) Long id){
        var detail= service.getAuthorWithBooks(id);
        return ResponseEntity.status(HttpStatus.OK).body(detail);
    }
}
