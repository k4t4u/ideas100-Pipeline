package pl.k4t.ideas100.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.k4t.ideas100.question.domain.model.Answer;
import pl.k4t.ideas100.question.domain.model.Question;
import pl.k4t.ideas100.question.domain.repository.AnswerRepository;
import pl.k4t.ideas100.question.domain.repository.QuestionRepository;

import java.util.List;
import java.util.UUID;

    @Service
    @RequiredArgsConstructor
    public class AnswerService {

        private final AnswerRepository answerRepository;

        private final QuestionRepository questionRepository;

        private final AnswerMapper answerMapper;


        @Transactional(readOnly = true)
        public List<Answer> getAnswers(UUID questionId) {

            return answerRepository.findByQuestionId(questionId);
        }

        @Transactional(readOnly = true)
        public Page<Answer> findAllByQuestionId(UUID id, String search, Pageable pageable) {
            if(search==null){
                return answerRepository.findAllByQuestionId(id, pageable);
            }
            return answerRepository.findAllByQuestionIdAndNameContainingIgnoreCase(id, search, pageable);
        }

        @Transactional(readOnly = true)
        public List<Answer> findAllAnswers() {
            return answerRepository.findAll();
        }

        @Transactional(readOnly = true)
        public Answer getAnswer(UUID id) {

            return answerRepository.getById(id);
        }

        @Transactional
        public Answer createAnswer(UUID questionId, Answer answerRequest) {
            Answer answer = new Answer();
            answer.setName(answerRequest.getName());

            Question question = questionRepository.getById(questionId);
            question.addAnswer(answer);

            answerRepository.save(answer);
            questionRepository.save(question);
            return answer;
        }

        @Transactional
        public Answer updateAnswer(UUID answerId, Answer answerRequest) {
            Answer answer = answerRepository.getById(answerId);
            answer.setName(answerRequest.getName());
            return answerRepository.save(answer);
        }

        @Transactional
        public void deleteAnswer(UUID answerId) {
            answerRepository.deleteById(answerId);
        }

        @Transactional
        public Integer countAnswers() {
            return answerRepository.findAll().size();
        }
    }
