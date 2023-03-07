package pl.k4t.ideas100.question.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.k4t.ideas100.common.dto.StatisticsDto;
import pl.k4t.ideas100.question.domain.model.Question;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

	List<Question> findAllByCategoryId(UUID id);

	Page<Question> findAllByCategoryId(UUID id, Pageable pageable);

	Page<Question> findAllByCategoryIdAndNameContainingIgnoreCase(UUID id, String name, Pageable pageable);

	List<Question> findAllByNameContainingIgnoreCase(String name);

	Page<Question> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

	@Query("from Question q order by q.answers.size desc")
	Page<Question> findHot(Pageable pageable);

	@Query("from Question q where q.answers.size = 0")
	Page<Question> findUnanswered(Pageable pageable);

	@Query(
			value = "select * from questions q where upper(q.name) like upper('%' || :query || '%') ",
			countQuery = "select count(*) from questions q where upper(q.name) like upper('%' || :query || '%') ",
			nativeQuery = true
	)
	Page<Question> findByQuery(String query, Pageable pageable);

	@Query(value = "select * from questions q order by random() limit :limit", nativeQuery = true)
	List<Question> findRandomQuestions(int limit);

	@Query(value = "select new pl.k4t.ideas100.common.dto.StatisticsDto(count(q) as numQuestions, count(a) as numAnswers) from Question q join q.answers a where q.category = :category")
	StatisticsDto statistics(@Param("category") String category);
}
