package pl.k4t.ideas100.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.k4t.ideas100.category.domain.repository.CategoryRepository;
import pl.k4t.ideas100.question.domain.model.Question;
import pl.k4t.ideas100.question.dto.QuestionDto;
import pl.k4t.ideas100.question.domain.repository.QuestionRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Question> getQuestions() {

        return questionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Question getQuestion(UUID id) {

        return questionRepository.getReferenceById(id);
    }

    @Transactional(readOnly = true)
    public Page<Question> getQuestions(String search, Pageable pageable) {
        if(search==null){
            return questionRepository.findAll(pageable);
        } else {
            return questionRepository.findAllByNameContainingIgnoreCase(search, pageable);
        }
    }
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestions(String search) {
        return questionRepository.findAllByNameContainingIgnoreCase(search)
                .stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public Question createQuestion(Question questionRequest) {
        Question question = new Question();
        question.setName(questionRequest.getName());
        question.setCategory(questionRequest.getCategory());

        return questionRepository.save(question);
    }

    @Transactional
    public Question updateQuestion(UUID id, Question questionRequest) {
        Question question = questionRepository.getReferenceById(id);
        question.setName(questionRequest.getName());

        return questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(UUID id) {

        questionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Question> findAllByCategoryId(UUID id) {

        return questionRepository.findAllByCategoryId(id);
    }

        @Transactional(readOnly = true)
    public Page<Question> findAllByCategoryId(UUID id, Pageable pageable) {
        return findAllByCategoryId(id, null, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Question> findAllByCategoryId(UUID id, String search, Pageable pageable) {
        if(search==null){
            return questionRepository.findAllByCategoryId(id, pageable);
        } else {
            return questionRepository.findAllByCategoryIdAndNameContainingIgnoreCase(id, search, pageable);
        }
    }

    @Transactional(readOnly = true)
    public Page<Question> findHot(Pageable pageable) {

        return questionRepository.findHot(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Question> findUnanswered(Pageable pageable) {

        return questionRepository.findUnanswered(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Question> findByQuery(String query, Pageable pageable) {
        return questionRepository.findByQuery(query, pageable);
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findTop(int limit) {
        return questionRepository.findAll(PageRequest.of(0, limit))
                .get()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findTop(UUID categoryId, int limit) {
        return questionRepository.findAllByCategoryId(categoryId, PageRequest.of(0, limit))
                .stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findRandom(int limit) {
        return questionRepository.findRandomQuestions(limit)
                .stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Integer countQuestions() {
        return questionRepository.findAll().size();
    }
}
