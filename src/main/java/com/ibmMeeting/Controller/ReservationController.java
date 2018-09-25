package com.ibmMeeting.Controller;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ibmMeeting.Constant.ConstantCode;
import com.ibmMeeting.Service.HistoryService;
import com.ibmMeeting.Service.MemberService;
import com.ibmMeeting.Service.ReservationService;
import com.ibmMeeting.VO.Reservation;
import com.ibmMeeting.Validation.RegisterReservationValidator;

@Controller
@RequestMapping("/Reservation")
public class ReservationController {

	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	MemberService memberService;
	
	/**
	 * 작성자 : 박세연
	 * 예약된 이벤트들 달력으로 조회
	 * @return
	 */
	@RequestMapping("/GetReservation")
	@ResponseBody
	public List<Reservation> getReservation(){
		
		List<Reservation> list =  reservationService.getReservation();
		
		return list;
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약된 이벤트 클릭시 상세 조회
	 * @return
	 */
	@RequestMapping("/GetReservationInfo")
	@ResponseBody
	public List<Reservation> getReservationInfo(@RequestParam int no){
		
		List<Reservation> list = reservationService.getReservationInfo(no);
		
		return list;
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약 등록하기
	 * @param reservation
	 * @param emailCheckValue
	 * @return
	 * @throws MessagingException 
	 * @throws ParseException 
	 */
	@RequestMapping("/RegistReservation")
	public ModelAndView registReservation(@ModelAttribute final Reservation reservation, @RequestParam final String emailCheckValue, BindingResult errors, ModelMap map, RedirectAttributes redirectAttr) throws MessagingException, ParseException{

		//validation
		RegisterReservationValidator valid = new RegisterReservationValidator();
		valid.validate(reservation, errors);

		if(errors.hasErrors()){
			map.addAttribute("rsvInfo", reservation);
			return new ModelAndView("/index/index");
		}
			
		//insert
		Thread thread = new Thread(new Runnable(){

			public void run() {
				try {
					reservationService.registReservation(reservation, emailCheckValue);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		thread.start();

		if(reservation.getRsvConfirmState().equals("Y")){
			String hstState = "RESERVE";
			historyService.insertHistory(reservation, hstState);
		}
		
		//이미 등록된 회원이라면 -- count(*)
		int exist = memberService.checkExistMemInfo(reservation.getRsvMemPn());
		if(exist > 0){
			//등록된 회원의 전화번호가 같을때, 회원정보 변경
			memberService.modifyMember(reservation);
		}else{
			//등록되어 있지 않다면 새로 insert
			memberService.registMember(reservation);
		}
		
		String to = "";
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		to = transFormat.format(reservation.getRsvDate());

		redirectAttr.addFlashAttribute("registRsvDate", to);
		
		return new ModelAndView("redirect:/");

	}
	
	/**
	 * 작성자 : 박세연
	 * 비밀번호 확인위해 등록된 예약No의 비밀번호 가져오기
	 * @param rsvNo
	 * @return
	 */
	@RequestMapping("/GetPwd")
	@ResponseBody
	public String getPwd(@RequestParam int rsvNo){
		
		String pwd = reservationService.getPwd(rsvNo);
		
		return pwd;
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약 수정하기
	 * @param reservation
	 * @param emailCheckValue
	 * @return
	 */
	@RequestMapping("/ModifyReservation")
	@ResponseBody
	public String modifyRsv(@ModelAttribute Reservation reservation, @RequestParam String emailCheckValue){
		
		//예약 변경
		reservationService.modifyReservation(reservation, emailCheckValue);
		
		String hstState = "MODIFY";
		historyService.insertHistory(reservation, hstState);
		
		//이미 등록된 회원이라면 -- count(*)
		int exist = memberService.checkExistMemInfo(reservation.getRsvMemPn());
		if(exist > 0){
			//등록된 회원의 전화번호가 같을때, 회원정보 변경
			memberService.modifyMember(reservation);
		}else{
			//등록되어 있지 않다면 새로 insert
			memberService.registMember(reservation);
		}
		
		return ConstantCode.SUCCESS_STRING;
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약 삭제하기
	 * @param reservation
	 * @return
	 */
	@RequestMapping("/DeleteReservation")
	@ResponseBody
	public String deleteRsv(@ModelAttribute Reservation reservation){
		
		reservationService.deleteReservation(reservation.getRsvNo());
		String hstState = "DELETE";
		historyService.insertHistory(reservation, hstState);
		
		return ConstantCode.SUCCESS_STRING;
	}
	
	/**
	 * 작성자 : 박세연
	 * 드래그 앤 드롭을 통한 예약 수정
	 * @param rsvNo
	 * @param rsvConfNo
	 * @param rsvStartTime
	 * @param rsvEndTime
	 * @return
	 */
	@RequestMapping("/ModifyRsvByDrop")
	@ResponseBody
	public String modifyRsvByDrop(@RequestParam int rsvNo, @RequestParam int rsvConfNo, @RequestParam Time rsvStartTime, @RequestParam Time rsvEndTime, @RequestParam Time rsvTotalTime){
		
		reservationService.modifyRsvByDrop(rsvNo, rsvConfNo, rsvStartTime, rsvEndTime, rsvTotalTime);
		//history
		List<Reservation> rsv = reservationService.getReservationInfo(rsvNo);
		Reservation reservation = new Reservation();
		
		for(Reservation r : rsv){
			reservation = r;
		}

		reservation.setRsvConfNo(rsvConfNo);
		reservation.setRsvStartTime(rsvStartTime);
		reservation.setRsvEndTime(rsvEndTime);
		reservation.setRsvTotalTime(rsvTotalTime);
		
		String hstState = "MODIFY";
		historyService.insertHistory(reservation, hstState);
		
		return ConstantCode.SUCCESS_STRING;
	}
	
	/**
	 * 작성자 : 박세연
	 * 회의실 독점 방지
	 * @param rsvTitle
	 * @param rsvMemPn
	 * @return
	 */
	@RequestMapping("/PreventMonopoly")
	@ResponseBody
	public String preventMonopoly(@RequestParam String rsvTitle, @RequestParam String rsvMemPn, @RequestParam String rsvDate, @RequestParam String rsvTotalTime, @RequestParam int rsvNo){
	
		String over = reservationService.preventMonopoly(rsvTitle, rsvMemPn, rsvDate, rsvTotalTime, rsvNo);
		
		return over;
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약 최대 시간 설정값 가져오기
	 * @return
	 */
	@RequestMapping("/GetMaxTime")
	@ResponseBody
	public String getMaxTime(){
		
		int max = ConstantCode.RESERVATION_MAX_MONOPOLY;
		String maxTime = Integer.toString(max);
		
		return maxTime;
	}
	
	/**
	 * 작성자 : 박세연
	 * 이미 예약된 시작&종료시간 select option hide 시키기
	 * @param rsvDate
	 * @param rsvConfNo
	 * @return
	 */
	@RequestMapping("/ControlStartTime")
	@ResponseBody
	public List<Reservation> controlStartTime(@RequestParam String rsvDate, @RequestParam int rsvConfNo){
		
		List<Reservation> list = reservationService.controlStartTime(rsvDate, rsvConfNo);
		
		return list;
	}
	
	/**
	 * 작성자 : 박세연
	 * 이미 예약된 시간선택 후 등록/수정 진행시, return false시키기 위한 메소드
	 * @param rsvNo
	 * @param rsvDate
	 * @param rsvConfNo
	 * @param rsvStartTime
	 * @param rsvEndTime
	 * @return
	 */
	@RequestMapping("/ChkRsvedTime")
	@ResponseBody
	public String chkRsvedTime(@RequestParam(value="rsvNo", defaultValue="0") int rsvNo, @RequestParam String rsvDate, @RequestParam int rsvConfNo, @RequestParam Time rsvStartTime, @RequestParam Time rsvEndTime){
		
		int count = reservationService.chkRsvedTime(rsvNo, rsvDate, rsvConfNo, rsvStartTime, rsvEndTime);
		
		if(count == 0){
			return "able";
		}		
	
		return "disable";
	}
	
	/**
	 * 작성자 : 박세연
	 * resize를 통해 예약 수정하기
	 * @param rsvNo
	 * @param rsvStartTime
	 * @param rsvEndTime
	 * @param rsvTotalTime
	 * @param rsvConfirmState
	 * @return
	 */
	@RequestMapping("/ModifyRsvByResize")
	@ResponseBody
	public String modifyRsvByResize(@RequestParam int rsvNo, @RequestParam Time rsvStartTime, @RequestParam Time rsvEndTime, @RequestParam Time rsvTotalTime, @RequestParam String rsvConfirmState){
		
		reservationService.modifyRsvByResize(rsvNo, rsvStartTime, rsvEndTime, rsvTotalTime, rsvConfirmState);
		//history
		List<Reservation> rsv = reservationService.getReservationInfo(rsvNo);
		Reservation reservation = new Reservation();
		
		for(Reservation r : rsv){
			reservation = r;
		}

		reservation.setRsvStartTime(rsvStartTime);
		reservation.setRsvEndTime(rsvEndTime);
		reservation.setRsvTotalTime(rsvTotalTime);
		
		String hstState = "MODIFY";
		historyService.insertHistory(reservation, hstState);
		
		return ConstantCode.SUCCESS_STRING;
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약 가능 최대 월 수 설정값 가져오기
	 * @return
	 */
	@RequestMapping("/GetRsvMaxMonth")
	@ResponseBody
	public String getRsvMaxMonth(){
		
		int max = ConstantCode.RESERVATION_MAX_MONTH;
		String maxTime = Integer.toString(max);
		
		return maxTime;
	}
	
	/**
	 * 작성자 : 박세연
	 * 검색페이지에서 하이퍼링크 타고 그 예약 날짜의 달력으로 돌아가기
	 * @param rsvNo
	 * @param map
	 * @return
	 */
	@RequestMapping("/SearchToCalendar")
	public String searchToCalendar(@RequestParam int rsvNo, ModelMap map){
		
		List<Reservation> list = reservationService.getReservationInfo(rsvNo);
		
		String to = "";
		for(Reservation rsv : list){
			
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			to = transFormat.format(rsv.getRsvDate());
		}
	
		map.addAttribute("rsvInfo", list);
		map.addAttribute("fromSearch", "T");
		map.addAttribute("rsvDateFromSearch", to);
		
		return "/index/index";
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약내역의 예약자 전화번호(PK) 가져오기
	 * @param rsvNo
	 * @return
	 */
	@RequestMapping("/GetRsvedMemPn")
	@ResponseBody
	public String getRsvedMemPn(@RequestParam int rsvNo){
		
		String pn = reservationService.getRsvedMemPn(rsvNo);
		
		return pn;
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약자에게 예약 비밀번호 전송
	 * @param rsvNo
	 * @return
	 * @throws MessagingException 
	 */
	@RequestMapping("/SendEmail")
	@ResponseBody
	public String sendEmail(@RequestParam int rsvNo) throws MessagingException{
		
		reservationService.emailFind(rsvNo);
		
		return ConstantCode.SUCCESS_STRING;
	}
	
	/**
	 * 작성자 : 박세연
	 * 가예약 승인된 예약은 관리자만 수정 가능
	 * @param rsvNo
	 * @return
	 */
	@RequestMapping("/GetRsvConfirmStateVal")
	@ResponseBody
	public String getRsvConfirmStateVal(@RequestParam int rsvNo){
		
		String state = reservationService.getRsvConfirmStateVal(rsvNo);
		
		return state;
	}
	
	/**
	 * 작성자 : 박세연
	 * 등록된 예약 위로 마우스 위치시, 예약자 내역 tooltip에 띄우기
	 * @param rsvNo
	 * @return
	 */
	@RequestMapping("/ShowInfoByTooltip")
	@ResponseBody
	public List<Reservation> showInfoByTooltip(@RequestParam int rsvNo){
		
		List<Reservation> list = reservationService.showInfoByTooltip(rsvNo);
		
		return list;
	}
	
	/**
	 * 작성자 : 박세연
	 * 등록된 예약의 제목 가져오기
	 * @param rsvNo
	 * @return
	 */
	@RequestMapping("/GetRsvedTitle")
	@ResponseBody
	public String getRsvedTitle(@RequestParam int rsvNo){
		
		System.out.println("제목 가지러 가자");
		String title = reservationService.getRsvedTitle(rsvNo);
		
		return title;
	}
	
}
