package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.slipp.domain.Thumnail;
import net.slipp.domain.ThumnailRepository;
import net.slipp.domain.User;
import net.slipp.utils.HttpSessionUtils;
import net.slipp.utils.UploadFileUtils;

@Controller
@RequestMapping("/thumnail")
public class ThumnailController {
	@Autowired
	private ThumnailRepository thumnailRepository;
	
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("thumnail", thumnailRepository.findAll(new Sort(Direction.DESC,"id")));
		return "/thumnail/list";
	}
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)){
			return "/users/loginForm";
		}
		
		return "/thumnail/form";
	}
	
	@PostMapping("")
	public String create(HttpSession session, String title, String contents, @RequestParam("file") MultipartFile realFile) {
		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		String fileName = null;
		
		if(!realFile.isEmpty()) {
			StringBuffer dbFileName = UploadFileUtils.randomStringFormat(realFile.getOriginalFilename());
			// File Upload
			UploadFileUtils.singleFileUpload(realFile, dbFileName);
			
			fileName = UploadFileUtils.DBPATH + dbFileName;
		}
		
		
		Thumnail newThumnail = new Thumnail(sessionUser, title, contents, fileName);
		thumnailRepository.save(newThumnail);
		
		return "redirect:/thumnail/";
	}
}
