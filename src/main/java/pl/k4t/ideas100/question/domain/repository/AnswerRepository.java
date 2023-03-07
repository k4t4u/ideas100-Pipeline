package pl.k4t.ideas100.question.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.k4t.ideas100.question.domain.model.Answer;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {

	List<Answer> findByQuestionId(UUID questionId);

	Page<Answer> findAllByQuestionId(UUID id, Pageable pageable);

	Page<Answer> findAllByQuestionIdAndNameContainingIgnoreCase(UUID id, String name, Pageable pageable);
}
