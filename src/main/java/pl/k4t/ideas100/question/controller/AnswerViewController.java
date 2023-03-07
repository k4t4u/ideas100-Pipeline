package pl.k4t.ideas100.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.k4t.ideas100.Ideas100Configuration;
import pl.k4t.ideas100.common.controller.Ideas100CommonViewController;
import pl.k4t.ideas100.question.domain.model.Answer;
import pl.k4t.ideas100.service.AnswerService;
import pl.k4t.ideas100.service.QuestionService;

import java.util.UUID;

@Controller
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerViewController extends Ideas100CommonViewController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    private final Ideas100Configuration ideas100Configuration;

    @GetMapping
    public String indexView(Model model){
        model.addAttribute("answers", answerService.getAnswer(null));
        addGlobalAttributes(model);

        return "answer/index";
    }

    @GetMapping("{id}")
    public String singleView(Model model, @PathVariable UUID id){
        model.addAttribute("answers", answerService.getAnswers(id));
        model.addAttribute("question", questionService.getQuestion(id));
        addGlobalAttributes(model);

        return "answer/single";
    }

    @GetMapping("add")
    public String addView(Model model){
        model.addAttribute("answer", new Answer());

        return "answer/add";
    }

    @PostMapping
    public String add(UUID questionId, Answer answer){
        answerService.createAnswer(questionId, answer);

        return "redirect:/answers";
    }
}
