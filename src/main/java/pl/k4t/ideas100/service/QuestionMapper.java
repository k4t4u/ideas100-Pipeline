package pl.k4t.ideas100.service;

import org.springframework.stereotype.Component;
import pl.k4t.ideas100.question.domain.model.Question;
import pl.k4t.ideas100.question.dto.QuestionDto;

@Component
public class QuestionMapper {

	public QuestionDto map(Question question) {
		QuestionDto questionDto = new QuestionDto();
		questionDto.setId(question.getId());
		questionDto.setName(question.getName());
		questionDto.setCategoryId(question.getCategory().getId());
		questionDto.setCategoryName(question.getCategory().getName());
		questionDto.setAnswers(question.getAnswers() == null ? 0 : question.getAnswers().size());
		questionDto.setCreated(question.getCreated());

		return questionDto;
	}
}