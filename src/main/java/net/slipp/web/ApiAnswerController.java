package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;
import net.slipp.utils.HttpSessionUtils;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@PostMapping("")
	public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)){
			return null;
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(loginUser, question, contents);
		question.addAnswer();
		
		return answerRepository.save(answer);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)){
			return "login"; 
		}
		
		Answer answer = answerRepository.findOne(id);
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		
		if(!answer.isSameWriter(loginUser)) {
			return "permission";
		}
		
		answerRepository.delete(id);
		
		Question question = questionRepository.findOne(questionId);
		question.deleteAnswer();
		questionRepository.save(question);
		return "success";
	}
}
