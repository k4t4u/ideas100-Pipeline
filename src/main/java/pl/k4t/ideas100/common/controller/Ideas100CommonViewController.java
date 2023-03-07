package pl.k4t.ideas100.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import pl.k4t.ideas100.category.service.CategoryService;

public abstract class Ideas100CommonViewController {

	@Autowired
	protected CategoryService categoryService;

	protected void addGlobalAttributes(Model model) {
		model.addAttribute("categoriesTop", categoryService.getCategories(
				PageRequest.of(0, 10, Sort.by("name").ascending())
		));
	}
}
