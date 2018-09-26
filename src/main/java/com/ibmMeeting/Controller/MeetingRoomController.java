package com.ibmMeeting.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibmMeeting.Constant.ConstantCode;
import com.ibmMeeting.Service.MeetingRoomManagementService;
import com.ibmMeeting.VO.Conference;


@Controller
@RequestMapping("/MeetingRoom")
public class MeetingRoomController {
	
	@Autowired
	MeetingRoomManagementService meetingRoomManagementService;
	
	
	/**
	 * 작성자 : 박성준
	 * 회의실 관리페이지 출력
	 */
	@RequestMapping("/RoomManagementList")
	public ModelAndView roomManagement(HttpServletRequest request){
		ModelAndView roomManagement = new ModelAndView();
		roomManagement.addObject("meetingRoomList",meetingRoomManagementService.meetingRoomList());
		roomManagement.setViewName("/admin/admin_room_management");
		
		return roomManagement;
	}
	
	/**
	 * 작성자 : 박성준
	 * 회의실 추가
	 */
	@ResponseBody
	@RequestMapping("/MeetingRoomAdd")
	public Integer meetingRoomAdd(HttpServletRequest request){
		meetingRoomManagementService.meetingRoomAdd(request);
		return ConstantCode.SUCCESS;
	}
	
	/**
	 * 작성자 : 박성준
	 * 회의실 수정
	 */
	@ResponseBody
	@RequestMapping("/MeetingRoomUpdate")
	public Integer meetingRoomUpdate(HttpServletRequest request){
		meetingRoomManagementService.meetingRoomUpdate(request);
		return ConstantCode.SUCCESS;
	}
	
	/**
	 * 작성자 : 박성준
	 * 회의실 삭제
	 */
	@ResponseBody
	@RequestMapping("/MeetingRoomDelete")
	public Integer meetingRoomDelete(HttpServletRequest request){
		meetingRoomManagementService.meetingRoomDelete(request);
		return ConstantCode.SUCCESS;
	}
	
	/**
	 * 작성자 : 박세연
	 * 등록된 회의실들 가져오기
	 * @return
	 */
	@RequestMapping("/GetResources")
	@ResponseBody
	public List<Conference> getResources(){
		List<Conference> list = meetingRoomManagementService.getResources();
		
		return list;
	}
	
}


