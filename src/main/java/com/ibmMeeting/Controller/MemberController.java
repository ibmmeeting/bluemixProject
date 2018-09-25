package com.ibmMeeting.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibmMeeting.Service.MemberService;
import com.ibmMeeting.VO.Member;

@Controller
@RequestMapping("/Member")
public class MemberController {

	@Autowired
	MemberService memberService;

	/**
	 * 작성자 : 박세연
	 * 전화번호 입력시 회원 이름과 이메일 자동입력을 위한 data 가져오기
	 * @param memPn
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/GetMemInfoByPn")
	public void getMemInfoByPn(@RequestParam String memPn, HttpServletRequest request,
			HttpServletResponse response){
		
		response.setCharacterEncoding("UTF-8");
		memberService.getMemInfoByPn(memPn, response);
		

	}
	
	/**
	 * 작성자 : 박세연
	 * 관리자로 로그인시, tooltip에 회원상태(정상/차단)과 noshow 카운트 수 추가하여 보여주기
	 * @param memPn
	 * @return
	 */
	@RequestMapping("/MemInfoByTooltip")
	@ResponseBody
	public List<Member> memInfoByToolTip(@RequestParam String memPn){
		
		List<Member> list = memberService.memInfoByToolTip(memPn);
		
		return list;
	}
	
	
}
