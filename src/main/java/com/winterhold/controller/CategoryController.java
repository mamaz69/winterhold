package com.winterhold.controller;

import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model) {
        List<CategoryGridDTO> grid = service.getCategoryGrid(page, name);
        Long totalPages = service.getTotalPages(name);
        page = (totalPages > 0) ? page : 0;
        model.addAttribute("grid", grid);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);
        return "/category/category-index";
    }

    @GetMapping("/insertForm")
    public String insertForm(@RequestParam(required = false) String name, Model model) {
        InsertCategoryDTO dto = new InsertCategoryDTO();
        model.addAttribute("category", dto);
        model.addAttribute("actionType", "insert");
        return "category/category-insert-form";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam(required = true) String name, Model model) {
        UpdateCategoryDTO dto = service.getUpdateCategory(name);
        model.addAttribute("category", dto);
        model.addAttribute("actionType", "update");
        return "category/category-update-form";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("category") InsertCategoryDTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            String actionType = "insert";
            model.addAttribute("actionType", actionType);
            return "category/category-insert-form";
        } else {
            service.saveCategory(dto);
            return "redirect:/category/index";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("category") UpdateCategoryDTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            String actionType = "update";
            model.addAttribute("actionType", actionType);
            return "category/category-update-form";
        } else {
            service.updateCategory(dto);
            return "redirect:/category/index";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String name, Model model) {
        Boolean successDelete = service.deleteCategory(name);
        if (successDelete) {
            return "redirect:/category/index";
        }
        Long totalDependentBooks = service.dependentBook(name);
        model.addAttribute("dependencies", totalDependentBooks);
        return "category/category-delete";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(required = true) String name,
                         @RequestParam(defaultValue = "") String title,
                         @RequestParam(defaultValue = "") String authorName,
                         Model model) {
        var detailGrid = service.getDetailBooks(page, name, title, authorName);
        var header = service.getCategoryHeader(name);
        var totalPages = service.getTotalDetailPages(name, title, authorName);
        page = (totalPages > 0) ? page : 0;
        model.addAttribute("grid", detailGrid);
        model.addAttribute("header", header);
        model.addAttribute("title", title);
        model.addAttribute("authorName", authorName);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        return "category/category-detail";
    }

    @GetMapping("/insertDetailForm")
    public String insertDetailForm(@RequestParam(required = false) String name,
                                   Model model) {
        List<DropdownDTO> authorDropdown = service.getAuthorDropdown();//dropdown
        model.addAttribute("authorDropdown", authorDropdown);//atribut untuk dipanggil di html
        String actionType = "insert";
        InsertBookDTO dto = new InsertBookDTO();//insert detail semua
        dto.setCategoryName(name);//ngeset category name
        model.addAttribute("book", dto);//atribut yang digunakan html(berupa field field di class DTO), biar connect ke database
        model.addAttribute("actionType", actionType);
        return "category/category-detail-insert-form";
    }

    @GetMapping("/updateDetailForm")
    public String updateDetailForm(@RequestParam(required = true) String code,
                                   Model model) {
        List<DropdownDTO> authorDropdown = service.getAuthorDropdown();//dropdown
        model.addAttribute("authorDropdown", authorDropdown);//atribut untuk dipanggil di html
        String actionType = "update";
        UpdateBookDTO dto = service.getUpdateBook(code);//insert detail semua
        model.addAttribute("book", dto);//atribut yang digunakan html(berupa field field di class DTO), biar connect ke database
        model.addAttribute("actionType", actionType);
        return "category/category-detail-update-form";
    }

    @PostMapping("/insertDetail")
    public String insertDetail(@Valid @ModelAttribute("book") InsertBookDTO dto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        List<DropdownDTO> authorDropdown = service.getAuthorDropdown();
        model.addAttribute("authorDropdown", authorDropdown);
        String actionType = "insert";
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionType", actionType);
            return "category/category-detail-insert-form";
        } else {
            service.saveBook(dto);
            redirectAttributes.addAttribute("name", dto.getCategoryName());
            return "redirect:/category/detail";
        }
    }

    @PostMapping("/updateDetail")
    public String updateDetail(@Valid @ModelAttribute("book") UpdateBookDTO dto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        List<DropdownDTO> authorDropdown = service.getAuthorDropdown();
        model.addAttribute("authorDropdown", authorDropdown);
        String actionType = "update";
        if (bindingResult.hasErrors()) {
            model.addAttribute("actionType", actionType);
            return "category/category-detail-update-form";
        } else {
            service.updateBook(dto);
            redirectAttributes.addAttribute("name", dto.getCategoryName());//
            return "redirect:/category/detail";
        }
    }

    @GetMapping("/deleteDetail")
    public String deleteDetail(@RequestParam(required = true) String code, Model model, RedirectAttributes redirectAttributes) {
        String categoryName = service.getUpdateBook(code).getCategoryName();
        service.deleteBook(code);
        redirectAttributes.addAttribute("name", categoryName);
        return "redirect:/category/detail";
    }
}
