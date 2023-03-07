package pl.k4t.ideas100.question.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AnswerDto {

	private UUID id;

	private String name;

	private UUID questionId;

	private String questionName;

	private UUID categoryId;

	private String categoryName;

	private LocalDateTime created;
}