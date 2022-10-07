package com.winterhold.controller;

import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.InsertAuthorDTO;
import com.winterhold.dto.author.UpdateAuthorDTO;
import com.winterhold.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {


    @Autowired
    private AuthorService service;

    @GetMapping("/homePage")
    public String home(){
            return "/author/home-page";
    }

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        List<AuthorGridDTO> grid = service.getAuthorGrid(page, name);
        Long totalPages = service.getTotalPages(name);
        page = (totalPages > 0) ? page : 0;
        model.addAttribute("grid", grid);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);
        return "/author/author-index";
    }

    @GetMapping("/insertForm")
    public String insertForm(@RequestParam(required = false) Long id, Model model) {
            InsertAuthorDTO dto = new InsertAuthorDTO();
            model.addAttribute("author", dto);
            model.addAttribute("actionType", "insert");
            return "author/author-insert-form";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam(required = true) Long id, Model model) {
            UpdateAuthorDTO dto = service.getUpdateAuthor(id);
            model.addAttribute("author", dto);
            model.addAttribute("actionType", "update");
            return "author/author-update-form";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("author") InsertAuthorDTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            String actionType = "insert";
            model.addAttribute("actionType", actionType);
            return "author/author-insert-form";
        } else {
            service.saveAuthor(dto);
            return "redirect:/author/index";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("author") UpdateAuthorDTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            String actionType = "update";
            model.addAttribute("actionType", actionType);
            return "author/author-update-form";
        } else {
            service.updateAuthor(dto);
            return "redirect:/author/index";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model) {
        Boolean successDelete = service.deleteAuthor(id);
        if (successDelete) {
            return "redirect:/author/index";
        }
        Long totalDependentBooks = service.dependentBook(id);
        model.addAttribute("dependencies", totalDependentBooks);
        return "author/author-delete";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(required = true) Long id,
                         Model model){
        var detailGrid= service.getDetailBooks(page,id);
        var header = service.getAuthorHeader(id);
        var totalPages = service.getTotalDetailPages(id);
        page = (totalPages > 0) ? page : 0;
        model.addAttribute("grid", detailGrid);
        model.addAttribute("header",header);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        return "author/author-detail";
    }
}
