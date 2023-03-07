package pl.k4t.ideas100.question.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.k4t.ideas100.question.domain.model.Answer;
import pl.k4t.ideas100.question.domain.model.Question;
import pl.k4t.ideas100.service.AnswerService;
import pl.k4t.ideas100.service.QuestionService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/questions")
public class QuestionApiController {

    private final QuestionService questionService;

    private final AnswerService answerService;

    @GetMapping
    List<Question> getQuestions(){

        return questionService.getQuestions();
    }

    @GetMapping("{id}")
    Question getQuestion(@PathVariable UUID id) {

        return questionService.getQuestion(id);
    }

    @GetMapping("{id}/answers")
    List<Answer> findAllByQuestionId(@PathVariable UUID id) {

        return answerService.getAnswers(id);
    }
    
    @GetMapping("{question-id}")
    Question getAnswer(@PathVariable("question-id") UUID questionId,
                     @PathVariable("answer-id") UUID answerId){
        return  questionService.getQuestion(questionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Question createQuestion(
            @PathVariable("category-id") UUID categoryId,
            @RequestBody Question question){

        return questionService.createQuestion(question);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Question updateQuestion(
            @PathVariable UUID id,
            @RequestBody Question question) {

        return questionService.updateQuestion(id, question);
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    Answer createAnswer(@PathVariable UUID id, @RequestBody Answer answer) {
        return answerService.createAnswer(id, answer);
    }

    @DeleteMapping("{question-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteQuestion(@PathVariable("question-id") UUID questionId){

        questionService.deleteQuestion(questionId);
    }
}
