package pl.k4t.ideas100.category.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.k4t.ideas100.category.domain.model.Category;
import pl.k4t.ideas100.category.service.CategoryService;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryApiController {

    private final CategoryService categoryService;

    public CategoryApiController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping
    Page<Category> getCategories(Pageable pageable){

        return categoryService.getCategories(pageable);
    }

    @GetMapping("{id}")
    Category getCategory(@PathVariable UUID id){

        return  categoryService.getCategory(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Category createCategory(@RequestBody Category category){

        return categoryService.createCategory(category);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Category updateCategory(@PathVariable UUID id, @RequestBody Category category){
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategorie(@PathVariable UUID id){
        categoryService.deleteCategory(id);
    }
}
