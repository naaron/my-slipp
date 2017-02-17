package net.slipp.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
public class Question extends AbstractEntity {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	@JsonProperty
	private User writer;
	
	@JsonProperty
	private String title;
	
	@Lob
	@JsonProperty
	private String contents;
	
	@JsonProperty
	private Integer countOfAnswer = 0;
	
	@OneToMany(mappedBy = "question")
	@OrderBy("id ASC")
	private List<Answer> answer;
	
	public Question() {}
	public Question(User writer, String title, String contents) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		
	}
	
	/*
	 * 2017.02.10
	 * 댓글 갯수 증가
	 * author : aron
	 */
	public void addAnswer() {
		this.countOfAnswer += 1;
	}
	
	/*
	 * 2017.02.10
	 * 댓글 갯수 감소
	 * author : aron
	 */
	public void deleteAnswer() {
		this.countOfAnswer -= 1;
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
		
	}
	
	public boolean isSameWriter(User loginUser) {
		return this.writer.equals(loginUser);
	}
	
}
