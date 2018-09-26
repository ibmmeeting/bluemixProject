package com.ibmMeeting.Controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibmMeeting.Constant.ConstantCode;
import com.ibmMeeting.Service.HistoryService;


@Controller
@RequestMapping("/AdminBoarding")
public class AdminHistoryController {
	
	@Autowired
	HistoryService historyService;
	/* 
	 * 작성자 : 고창환
	 * 가예약목록리스트
	 */
	@RequestMapping("/ReservationHistory")
	public ModelAndView reservationHistory(HttpServletRequest request, HttpSession session){
		
		ModelAndView reservationHistory = new ModelAndView();
		reservationHistory.addObject("fakeReservation",historyService.fakeReservation());
		reservationHistory.setViewName("/admin/admin_reservationHistory");
		
		
		return reservationHistory;
	}
	/* 
	 * 작성자 : 고창환
	 * 가예약 승인기능
	 */
	@ResponseBody
	@RequestMapping("/ReservationUpdate")
	public Integer reservationUpdate(final HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, MessagingException, ParseException{
		Thread thread = new Thread(new Runnable(){

			public void run() {
				
				try {
					historyService.reservationUpdate(request);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		thread.run();
		
		return ConstantCode.SUCCESS;
		
	}
	/* 
	 * 작성자 : 고창환
	 * 가예약 반려기능
	 */
	@ResponseBody
	@RequestMapping("/ReservationDelete")
	public Integer reservationDelete(final HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, MessagingException, ParseException{
		
		Thread thread = new Thread(new Runnable(){

			public void run() {
				try {
					historyService.reservationDelete(request);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		thread.run();
		
		return ConstantCode.SUCCESS;
		
	}
	/* 
	 * 작성자 : 고창환
	 * 예약관리 필터링
	 */
	@RequestMapping("/Filtering")
	public ModelAndView filteringPage(HttpServletRequest request) {
		ModelAndView showList = new ModelAndView();
		
		
		showList.addObject("fakeReservation", historyService.selectResult(request));
		showList.setViewName("/admin/admin_reservationHistory");
		
		
		return showList;
	}
	
	/*@ResponseBody
	@RequestMapping("/LookInside")
	public ModelAndView lookInside(HttpServletRequest request){
		
		
		ModelAndView lookInside = new ModelAndView();
		
		lookInside.addObject("repeatReservationInside",historyService.lookInside(request));
		return lookInside;
	}*/
	/* 
	 * 작성자 : 고창환
	 * 예약관리-반복예약 상세보기 내용
	 */
	@ResponseBody
	@RequestMapping("/LookInside")
	public ArrayList<HashMap<String,Object>> lookInside(HttpServletRequest request){
		
		ArrayList<HashMap<String,Object>> lookInside = historyService.lookInside(request);
		return lookInside;
	}

}
