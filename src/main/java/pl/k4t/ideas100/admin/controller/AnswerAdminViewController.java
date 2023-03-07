package pl.k4t.ideas100.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.k4t.ideas100.common.dto.Message;
import pl.k4t.ideas100.question.domain.model.Answer;
import pl.k4t.ideas100.question.domain.repository.AnswerRepository;
import pl.k4t.ideas100.service.AnswerService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static pl.k4t.ideas100.common.controller.ControllerUtils.paging;

@Controller
@RequestMapping("/admin/answers")
@Slf4j
public class AnswerAdminViewController {
    private final AnswerRepository answerRepository;

    private final AnswerService answerService;

    public AnswerAdminViewController(AnswerService answerService,
                                     AnswerRepository answerRepository) {

        this.answerService = answerService;
        this.answerRepository = answerRepository;
    }

    @GetMapping
    public String indexView(
            @RequestParam(name = "s", required = false) String search,
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), field);
        List<Answer> answersPage = answerService.findAllAnswers();
        String reverseSort = null;
        if ("asc".equals(direction)) {
            reverseSort = "desc";
        } else {
            reverseSort = "asc";
        }

        model.addAttribute("answers", answerService.findAllAnswers());
        model.addAttribute("answersPage", answersPage);
        model.addAttribute("search", search);
        model.addAttribute("reverseSort", reverseSort);

        paging(model, answerRepository.findAll(pageable));

        return "admin/answer/index";
    }

    @GetMapping("{id}")
    public String editView(Model model, @PathVariable UUID id) {
        model.addAttribute("answer", answerService.getAnswer(id));

        return "admin/answer/edit";
    }

    @PostMapping("{id}")
    public String edit(
            @PathVariable UUID id,
            @Valid @ModelAttribute("answer") Answer answer,
            BindingResult bindingResult,
            RedirectAttributes ra,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("answer", answer);
            model.addAttribute("message", Message.error("Save error"));
            return "admin/answer/edit";
        }
        try {
            answerService.updateAnswer(id, answer);
            ra.addFlashAttribute("message", Message.info("Answer saved"));
        } catch (Exception e) {
            model.addAttribute("answer", answer);
            model.addAttribute("message", Message.error("Unknown save error"));
            return "admin/answer/edit";
        }

        return "redirect:/admin/answers";
    }

    @GetMapping("{id}/delete")
    public String deleteView(@PathVariable UUID id, RedirectAttributes ra) {
        try {
            answerService.deleteAnswer(id);
            ra.addFlashAttribute("message", Message.info("Answer deleted"));
        } catch (Exception e) {
            log.error("Error on Answer.delete", e);
            ra.addFlashAttribute("message", Message.error("Unknown delete error"));
            return "redirect:/admin/answers";
        }
        return "redirect:/admin/answers";
    }
}
