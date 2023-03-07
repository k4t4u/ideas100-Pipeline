package pl.k4t.ideas100.category.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.k4t.ideas100.common.dto.Message;
import pl.k4t.ideas100.category.domain.model.Category;
import pl.k4t.ideas100.category.service.CategoryService;
import pl.k4t.ideas100.question.domain.model.Question;
import pl.k4t.ideas100.service.QuestionService;

import javax.validation.Valid;
import java.util.UUID;

import static pl.k4t.ideas100.common.controller.ControllerUtils.paging;

@Controller
@RequestMapping("/admin/categories")
@Slf4j
public class CategoryAdminViewController {

	private final CategoryService categoryService;

	private final QuestionService questionService;

	public CategoryAdminViewController(CategoryService categoryService, QuestionService questionService) {

		this.categoryService = categoryService;
		this.questionService = questionService;
	}

	@GetMapping
	public String indexView(
			@RequestParam(name = "s", required = false) String search,
			@RequestParam(name = "field", required = false, defaultValue = "id") String field,
			@RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size,
			Model model
	){
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction),field);
		Page<Category> categoriesPage = categoryService.getCategories(search, pageable);
		String reverseSort = null;
		if("asc".equals(direction)){
			reverseSort = "desc";
		} else {
			reverseSort ="asc";
		}

		model.addAttribute("categoriesPage", categoriesPage);
		model.addAttribute("search", search);
		model.addAttribute("reverseSort", reverseSort);

		paging(model, categoriesPage);

		return "admin/category/index";
	}

	@GetMapping("/add")
	public String addView(Model model){
		model.addAttribute("category", new Category());
		return "admin/category/add";
	}

	@PostMapping("/add")
	public String add(
			@Valid @ModelAttribute("category") Category category,
			BindingResult bindingResult,
			RedirectAttributes ra,
			Model model
	){
		if (bindingResult.hasErrors()){
			model.addAttribute("category", category);
			model.addAttribute("message", Message.error("Save error"));
			return "admin/category/add";
		}
		try {
			categoryService.createCategory(category);
			ra.addFlashAttribute("message", Message.info("Category added"));
		}catch (Exception e){
			model.addAttribute("category", category);
			model.addAttribute("message", Message.error("Unknown save error"));
			return "admin/category/add";
		}

		return "redirect:/admin/categories";
	}

	@GetMapping("{id}/add")
	public String addQuestionView(@PathVariable UUID id, Model model) {
		model.addAttribute("category", categoryService.getCategory(id));
		Question question = new Question();
		question.setCategory(categoryService.getCategory(id));
		model.addAttribute("question", question);
		return "admin/question/addQuestion";
	}

	@PostMapping("{id}/add")
	public String addQuestion(@PathVariable UUID id,
							@Valid @ModelAttribute("question") Question question,
							BindingResult bindingResult,
							RedirectAttributes ra,
							Model model) {
		if(bindingResult.hasErrors()){
			model.addAttribute("category", categoryService.getCategory(id));
		}
		try {
			model.addAttribute("question", questionService.createQuestion(question));
			ra.addFlashAttribute("message", Message.info("Question has been added"));
		}catch(Exception e) {
			log.error("Error on answer add", e);
			model.addAttribute("question", question);
			ra.addFlashAttribute("message", Message.info("Unknown error occurred on question add."));
			return "admin/question/addQuestion";
		}
		return "redirect:/admin/categories/{id}";
	}

	@GetMapping("{id}")
	public String editView(Model model, @PathVariable UUID id){
		model.addAttribute("category", categoryService.getCategory(id));

		return "admin/category/edit";
	}

	@PostMapping("{id}")
	public String edit(
			@PathVariable UUID id,
			@Valid @ModelAttribute("category") Category category,
			BindingResult bindingResult,
			RedirectAttributes ra,
			Model model
						){
		if (bindingResult.hasErrors()){
			model.addAttribute("category", category);
			model.addAttribute("message", Message.error("Save error"));
			return "admin/category/edit";
		}
		try {
			categoryService.updateCategory(id, category);
			ra.addFlashAttribute("message", Message.info("Category saved"));
		}catch (Exception e){
			model.addAttribute("category", category);
			model.addAttribute("message", Message.error("Unknown save error"));
			return "admin/category/edit";
		}

		return "redirect:/admin/categories";
	}

	@GetMapping("{id}/delete")
	public String deleteView(@PathVariable UUID id, RedirectAttributes ra) {
		try {
			categoryService.deleteCategory(id);
			ra.addFlashAttribute("message", Message.info("Category deleted"));
		} catch (Exception e) {
			log.error("Error on category.delete", e);
			ra.addFlashAttribute("message", Message.error("Unknown delete error"));
			return "redirect:/admin/categories";
		}
		return "redirect:/admin/categories";

	}
}
