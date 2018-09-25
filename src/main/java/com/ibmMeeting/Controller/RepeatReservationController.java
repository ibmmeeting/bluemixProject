package com.ibmMeeting.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibmMeeting.Service.MeetingRoomManagementService;
import com.ibmMeeting.Service.RepeatReservationService;


@Controller
@RequestMapping("/RepeatReservation")
public class RepeatReservationController {
	
	@Autowired
	MeetingRoomManagementService meetingRoomManagementService;
	
	@Autowired
	RepeatReservationService repeatReservationService;
	
	/**
	 * 작성자 : 박성준
	 * 반복예약페이지 출력
	 */
	
	@RequestMapping("/")
	public ModelAndView repeatIndex(){
		ModelAndView repeatReservationIndex = new ModelAndView();
		ArrayList<HashMap<String,Object>> meetingRoomList = meetingRoomManagementService.meetingRoomList();
		repeatReservationIndex.addObject("meetingRoomList",meetingRoomList);
		repeatReservationIndex.setViewName("repeatReservation/repeat_request");
		
		return repeatReservationIndex;
	}
	
	/**
	 * 작성자 : 박성준
	 * 반복예약 가능여부 확인
	 */
	@ResponseBody
	@RequestMapping("/AvailableCheck")
	public ArrayList<ArrayList<String>> availableCheck(HttpServletRequest request) throws ParseException{
		return repeatReservationService.reservationAvailableCheck(request);
	}
	
	/**
	 * 작성자 : 박성준
	 * 반복예약 제출
	 * @throws MessagingException 
	 */
	@RequestMapping("/RepeatReservationSubmit")
	public String repeatReservationSubmit(HttpServletRequest request) throws ParseException, MessagingException{
		repeatReservationService.repeatReservationSubmit(request);
		return "/index/index";
	}
}
