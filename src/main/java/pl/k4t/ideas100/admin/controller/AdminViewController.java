package pl.k4t.ideas100.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.k4t.ideas100.category.service.CategoryService;
import pl.k4t.ideas100.service.AnswerService;
import pl.k4t.ideas100.service.QuestionService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminViewController {

	private final QuestionService questionService;

	private final AnswerService answerService;

	private final CategoryService categoryService;

	@GetMapping
	public String indexView(Model model) {
		model.addAttribute("countAnswers", answerService.countAnswers());
		model.addAttribute("countCategories", categoryService.countCategories());
		model.addAttribute("countQuestions", questionService.countQuestions());
		return "admin/index";
	}
}