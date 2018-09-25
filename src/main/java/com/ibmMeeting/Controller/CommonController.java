package com.ibmMeeting.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibmMeeting.Service.CommonService;

// 공통 컨트롤러
@Controller
@RequestMapping("/Common")
public class CommonController {
	
	@Autowired
	CommonService commonService;
	
	/**
	 * 작성자 : 박성준
	 * 세션 확인
	 */
	@ResponseBody
	@RequestMapping("/SessionCheck")
	public String sessionCheck(HttpSession session){
		return commonService.sessionCheck(session);
	}
	
	/**
	 * 작성자 : 박성준
	 * 로그아웃
	 */
	@RequestMapping("/Logout")
	public String logout(HttpSession session){
		commonService.logout(session);
		return "index/index";
	}
	
}


