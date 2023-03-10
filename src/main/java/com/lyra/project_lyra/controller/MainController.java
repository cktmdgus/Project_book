package com.lyra.project_lyra.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lyra.project_lyra.repository.member.MemberInfoRepository;
import com.lyra.project_lyra.service.interfaces.BookService;
import com.lyra.project_lyra.service.interfaces.CombineService;
import com.lyra.project_lyra.service.interfaces.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/main")
@Log4j2
@RequiredArgsConstructor
public class MainController {
	private final BookService bookService;
	private final MemberService memberService;
	private final MemberInfoRepository repository;
	private final CombineService combineService;

	@GetMapping("/main")
	public ModelAndView getMainList(Model model, @RequestParam(value = "name", required=false) String loginUser) throws Exception{
		String username;
		if (loginUser == null) {
			username = "user";
		}else {
			username = loginUser;			
		}	
		
		String[] categoryOne = memberService.getCategory(username);
		
		model.addAttribute("username", username);
		model.addAttribute("bookResult", categoryDBtoView(username));
		model.addAttribute("result1", bookService.getCategoryList(categoryOne[0], combineService.bookLikeList(username), combineService.bookKeepList(username)));
		model.addAttribute("result2", bookService.getCategoryList(categoryOne[1], combineService.bookLikeList(username), combineService.bookKeepList(username)));
		model.addAttribute("bookLikeList", bookService.getLikeList(combineService.bookLikeList(username), combineService.bookKeepList(username)));
		model.addAttribute("bookUpdateList", bookService.getUpdateList(combineService.bookLikeList(username), combineService.bookKeepList(username)));
		
		ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/main/main");
        return modelAndView;
	}
	
	@PostMapping("/main")
	public String getMainList(Authentication authentication) throws Exception{		
		String username = (String)authentication.getPrincipal();
		
		return username;
	}
	
	@PostMapping("/categoryInsert")
	public void setCategoryInsert(Model model, @RequestBody Map<String,Object> data, Authentication authentication) {
		String category = (String)data.get("category");
		String url = (String)data.get("url");
		
		log.info("category : " + category);
		
		String categoryEntity = categoryViewtoDB(category);
		String username = (String)authentication.getPrincipal();
		
		log.info("categoryEntity : " + categoryEntity);
		log.info(username);
		
		memberService.categoryInsert(username, categoryEntity);
		log.info("category Insert Success");
	}
	
	 @GetMapping("/genrepage")
    public ModelAndView gernepage(Model model, @RequestParam(value = "name", required=false) String loginUser) {
    	String username;
		log.info(loginUser);
		if (loginUser == null) {
			username = "user";
		}else {
			username = loginUser;			
		}
		
		model.addAttribute("list", bookService.getList(combineService.bookLikeList(username), combineService.bookKeepList(username)));
    	
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/main/genrepage");
        return modelAndView;
    }
    
    @PostMapping("/genrepage")
	public String getCategory(Authentication authentication) throws Exception{
		String username = (String)authentication.getPrincipal();
		
		return username;
	}
    
    @GetMapping("/bookflip")
    public ModelAndView getBookflip(Model model,@RequestParam(value = "name", required=false) String loginUser,@RequestParam(value = "num", required=false) Long bookNum) {
    	String username;
    	Long bookNums;
    	
		if (loginUser == null) {
			username = "user";
		}else {
			username = loginUser;			
		}	
		
		if (bookNum == null) {
			bookNums = 1L;
		}else {
			bookNums = bookNum;			
		}	
		
		model.addAttribute("username", username);
		model.addAttribute("bookNum", bookNums);
		
        log.info("/main/bookflip");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/main/bookflip");
        return modelAndView;
    }
    
    @PostMapping("/bookflip")
	public String getBookflip(Authentication authentication, @RequestBody Map<String,Object> data) throws Exception{
		String username = (String)authentication.getPrincipal();

		return username;
	}
    
    @PostMapping("/pageInsert")
	public void setPageInsert(@RequestBody Map<String,Object> data, Authentication authentication) {
		String bookNum = (String)data.get("bookNums");
		String bookPage = (String)data.get("bookPage");
		String username = (String)authentication.getPrincipal();
		
		Long lbookNum = Long.parseLong(bookNum); 
		Long lbookPage = Long.parseLong(bookPage); 
		
		log.info("bookNum : " + bookNum);
		log.info("bookPage : " + bookPage);
		log.info("username : " + username);

		combineService.bookPageSave(username, lbookNum, lbookPage);
	}
    
    @PostMapping("/modal")
	public Long modalShow(@RequestBody Map<String,Object> data, Authentication authentication) {
		String bookNum = (String)data.get("bookNums");
		
		Long lbookNum = Long.parseLong(bookNum); 
		
		log.info("bookNum : " + bookNum);
		
		return lbookNum;
	}

	// Entity -> DTO
	public String[] categoryDBtoView(String username) {
		String[] categoryName = memberService.getCategory(username);

		for (int i = 0; i < categoryName.length; i++) {
			switch (categoryName[i]) {
				case "1":
					categoryName[i] = "#??????";
					break;
				case "2":
					categoryName[i] = "#?????????";
					break;
				case "3":
					categoryName[i] = "#??????";
					break;
				case "4":
					categoryName[i] = "#?????????";
					break;
				case "5":
					categoryName[i] = "#??????";
					break;
				case "6":
					categoryName[i] = "#??????";
					break;
				case "7":
					categoryName[i] = "#??????";
					break;
				case "8":
					categoryName[i] = "#??????";
					break;
				case "9":
					categoryName[i] = "#??????";
					break;
				case "10":
					categoryName[i] = "#??????";
					break;
				case "11":
					categoryName[i] = "#??????";
					break;
				case "12":
					categoryName[i] = "#??????";
					break;
				default:
					categoryName[i] = "#????????? ?????? ??????";
					break;
			}
		}
		
		return categoryName;
	}
	
	public String categoryViewtoDB(String category) {
		String categoryName = null;
		
		switch (category) {
			case "#??????":
				categoryName = "1";
				break;
			case "#?????????":
				categoryName = "2";
				break;
			case "#??????":
				categoryName = "3";
				break;
			case "#?????????":
				categoryName = "4";
				break;
			case "#??????":
				categoryName = "5";
				break;
			case "#??????":
				categoryName = "6";
				break;
			case "#??????":
				categoryName = "7";
				break;
			case "#??????":
				categoryName = "8";
				break;
			case "#??????":
				categoryName = "9";
				break;
			case "#??????":
				categoryName = "10";
				break;
			case "#??????":
				categoryName = "11";
				break;
			case "#??????":
				categoryName = "12";
				break;
			default:
				categoryName = "";
				break;
		}		
		return categoryName;
	}
}
