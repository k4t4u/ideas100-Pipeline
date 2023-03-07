package pl.k4t.ideas100.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.k4t.ideas100.Ideas100Configuration;
import pl.k4t.ideas100.common.controller.Ideas100CommonViewController;
import pl.k4t.ideas100.question.domain.model.Question;
import pl.k4t.ideas100.service.AnswerService;
import pl.k4t.ideas100.service.QuestionService;

import java.util.UUID;

import static pl.k4t.ideas100.common.controller.ControllerUtils.paging;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionViewController extends Ideas100CommonViewController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final Ideas100Configuration ideas100Configuration;

    @GetMapping
    public String indexView(Model model){
        model.addAttribute("questions", questionService.getQuestions());
        addGlobalAttributes(model);

        return "question/index";
    }

    @GetMapping("{id}")
    public String singleView(Model model, @PathVariable UUID id){
        model.addAttribute("question", questionService.getQuestion(id));
        model.addAttribute("answers", answerService.getAnswers(id));
        addGlobalAttributes(model);

        return "question/single";
    }

    @GetMapping("add")
    public String addView(Model model){
        model.addAttribute("question", new Question());

        return "question/add";
    }

    @PostMapping
    public String addQuestion(Model model, Question question){
        questionService.createQuestion(question);
        model.addAttribute("questions", questionService.getQuestions());
        addGlobalAttributes(model);

        return "redirect:/questions";
    }

    @GetMapping("hot")
    public String hotView(
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model
    ){
        PageRequest pageRequest = PageRequest.of(page - 1, ideas100Configuration.getPagingPageSize());

        Page<Question> questionsPage = questionService.findHot(pageRequest);

        model.addAttribute("questionsPage", questionsPage);
        paging(model, questionsPage);
        addGlobalAttributes(model);

        return "question/index";
    }

    @GetMapping("unanswered")
    public String unansweredView(
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model
    ){
        PageRequest pageRequest = PageRequest.of(page - 1, ideas100Configuration.getPagingPageSize());

        Page<Question> questionsPage = questionService.findUnanswered(pageRequest);

        model.addAttribute("questionsPage", questionsPage);
        paging(model, questionsPage);
        addGlobalAttributes(model);

        return "question/index";
    }
}
