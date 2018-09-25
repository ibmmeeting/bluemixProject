package com.ibmMeeting.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping(value = {"/","/home.do"})
	public String IndexPage() {
		return "/index/index";
	}
//	
//	@RequestMapping(value = {"/home.do"})
//	public String AmorePage() {
//		
//		return "/index/index";
//		
//	}
}
