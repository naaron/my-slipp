package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;
import net.slipp.utils.HttpSessionUtils;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		return "/qna/form";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		
		return "redirect:/";
	}
	
	@GetMapping("{id}")
	public String show(@PathVariable Long id, Model model) {
		Question viewQuestion = questionRepository.findOne(id);
		model.addAttribute("show", viewQuestion);
		return "/qna/show";
	}
	
	@GetMapping("{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			model.addAttribute("show", question);
			return "/qna/updateForm";
		}catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}
	
	private boolean hasPermission(HttpSession session, Question question) {
		if(!HttpSessionUtils.isLoginUser(session)){
			throw new IllegalStateException("로그인이 필요합니다.");
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!question.isSameWriter(loginUser)) {
			throw new IllegalStateException("자신의 글만 수정, 삭제가 가능합니다.");
		}
		return true;
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, HttpSession session, Model model) {
		try {
			Question question = questionRepository.findOne(id);
			question.update(title,contents);
			questionRepository.save(question);
			return String.format("redirect:/questions/%d",id);
		}catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
		
		
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, HttpSession session, Model model) {
		try {
			Question question = questionRepository.findOne(id);
			hasPermission(session, question);
			questionRepository.delete(id);
			return "redirect:/";
		}catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/user/login";
		}
	}
}
