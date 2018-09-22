package com.ibmMeeting.Service;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibmMeeting.Constant.ConstantCode;
import com.ibmMeeting.Dao.AdminDao;
import com.ibmMeeting.Dao.MeetingRoomDao;
import com.ibmMeeting.Dao.ReservationDao;
import com.ibmMeeting.VO.Reservation;

@Service
public class ReservationService {

	@Autowired
	ReservationDao reservationDao;
	
	@Autowired
	MeetingRoomDao meetingRoomDao;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	AdminDao adminDao;
	
	/**
	 * 작성자 : 박세연
	 * 등록된 예약내역 간단 조회
	 * @return
	 */
	public List<Reservation> getReservation(){
		
		List<Reservation> list = reservationDao.getReservation();
		
		return list;
	}
	
	/**
	 * 작성자 : 박세연
	 * 등록된 예약내역 상세 조회
	 * @param no
	 * @return
	 */
	public List<Reservation> getReservationInfo(int no){
		
		List<Reservation> list = reservationDao.getReservationInfo(no);
		
		return list;
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약 등록
	 * @param reservation
	 * @param emailCheckValue
	 * @throws MessagingException 
	 * @throws ParseException 
	 */
	public void registReservation(Reservation reservation, String emailCheckValue) throws MessagingException, ParseException{
		
		
		System.out.println("::::::::::::::::::::: cont :::::::::::");
		
		
		//객체안 값 설정
		Date date = new Date();
		reservation.setRsvNo(0);
		reservation.setRsvRegDate(date);
		reservation.setRsvEmailCheck(emailCheckValue);
		reservation.setRsvRepeatPeriod("N");
		reservation.setRsvSetting("0");
		reservation.setRsvRepeatNo(0);
		reservation.setRsvDescription("");
	
		//만약 key값을 이용해서 사이트명을 구분한다면 바뀌어야 할 코드
		reservation.setRsvComp(ConstantCode.COMPANY_NAME);
		
		
		String rsvMemNm = reservation.getRsvMemNm();
		String rsvTitle = reservation.getRsvTitle();
		Time rsvStartTime = reservation.getRsvStartTime();
		Time rsvEndTime = reservation.getRsvEndTime();
		String rsvConfNm = commonService.confName(reservation.getRsvConfNo());
		Date rsvDate = reservation.getRsvDate();
		String rsvDateString = commonService.DateToString(rsvDate);
		String rsvDateOfTheWeek = commonService.dayOfTheWeek(rsvDateString);
		String rsvStartTimeChange = commonService.timeToString(rsvStartTime).substring(0,5);
		String rsvEndTimeChange = commonService.timeToString(rsvEndTime).substring(0,5);
		
		String email = reservation.getRsvMemEm();
		
		String subject;
		String content;
		
		if(reservation.getRsvConfirmState().equals("N")){
			
			email = adminDao.getAdminEmail();
			
			subject = "[회의실 가예약] " + rsvTitle + " (" + rsvDateString + "(" + rsvDateOfTheWeek + ") " + rsvStartTimeChange + " - " + rsvDateString + "(" + rsvDateOfTheWeek + ")" + rsvEndTimeChange	+ "), " + rsvConfNm;
			
			content = "<html>\r\n" + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
					+ "<head>\r\n" + "\r\n" + "\r\n" + "</head>\r\n" + "<body>\r\n" + "\r\n"
					+ "<div class=\"container\" style=\"display: block!important;max-width: 600px!important;margin: 0 auto!important;clear: both!important;\">\r\n"
					+ "   <a href=\"http://bluemixb.mybluemix.net/\">"
					+ "	<img src=\"https://i.imgur.com/rOpAzMk.png\">\r\n </a>" + "	<br>\r\n"
					+ "	<hr size=\"2\" noshade>\r\n" + "	<p>안녕하세요</p> \r\n" + "	"
					+ "<p>" + rsvMemNm + "님의 회의실 예약이 아래와 같이 가예약 신청됐습니다."
					+ "	<table style=\"text-align: center;border: 1px solid black;border-collapse: collapse;\">\r\n"
					+ "		<tr>\r\n"
					+ "			<td style=\"width: 200px;font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 제목 </td>\r\n"
					+ "			<td style=\"width: 400px;border: 1px solid black;border-collapse: collapse;\">" + rsvTitle
					+ "</td>\r\n" + "		</tr>\r\n" + "		\r\n" + "		<tr>\r\n"
					+ "			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 일자 </td>\r\n"
					+ "			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvDateString + "(" + rsvDateOfTheWeek + ")"  + "</td>\r\n" + "		</tr>\r\n"
					+ "		\r\n" + "		<tr>\r\n"
					+ "			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 시간 </td>\r\n"
					+ "			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvStartTimeChange + " - " + rsvEndTimeChange + "</td>\r\n" + "		</tr>\r\n" + "		\r\n" + "		<tr>\r\n"
					+ "			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 장소 </td>\r\n"
					+ "			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvConfNm
					+ "</td>\r\n" + "		</tr>\r\n" + "\r\n" + "\r\n" + "	</table>\r\n" + "	\r\n" + "	</div>\r\n"
					+ "</body>\r\n" + "</html>";
			commonService.sendEmail(email, subject, content);
		}
		else {
			
			subject = "[회의실 예약] " + rsvTitle + " (" + rsvDateString + "(" + rsvDateOfTheWeek + ") " + rsvStartTimeChange + " - " + rsvDateString + "(" + rsvDateOfTheWeek + ") " + rsvEndTimeChange	+ "), " + rsvConfNm;
			
			content = "<html>\r\n" + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
					+ "<head>\r\n" + "\r\n" + "\r\n" + "</head>\r\n" + "<body>\r\n" + "\r\n"
					+ "<div class=\"container\" style=\"display: block!important;max-width: 600px!important;margin: 0 auto!important;clear: both!important;\">\r\n"
					+ "   <a href=\"http://bluemixb.mybluemix.net/\">"
					+ "	<img src=\"https://i.imgur.com/rOpAzMk.png\">\r\n </a>" + "	<br>\r\n"
					+ "	<hr size=\"2\" noshade>\r\n" + "	<p>안녕하세요</p> \r\n" + "	"
					+ "<p>" + rsvMemNm + "님의 회의실 예약이 아래와 같이 완료되었습니다."
					+ "	<table style=\"text-align: center;border: 1px solid black;border-collapse: collapse;\">\r\n"
					+ "		<tr>\r\n"
					+ "			<td style=\"width: 200px;font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 제목 </td>\r\n"
					+ "			<td style=\"width: 400px;border: 1px solid black;border-collapse: collapse;\">" + rsvTitle
					+ "</td>\r\n" + "		</tr>\r\n" + "		\r\n" + "		<tr>\r\n"
					+ "			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 일자 </td>\r\n"
					+ "			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvDateString + "(" + rsvDateOfTheWeek + ")"  + "</td>\r\n" + "		</tr>\r\n"
					+ "		\r\n" + "		<tr>\r\n"
					+ "			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 시간 </td>\r\n"
					+ "			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvStartTimeChange + " - " + rsvEndTimeChange + "</td>\r\n" + "		</tr>\r\n" + "		\r\n" + "		<tr>\r\n"
					+ "			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 장소 </td>\r\n"
					+ "			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvConfNm
					+ "</td>\r\n" + "		</tr>\r\n" + "\r\n" + "\r\n" + "	</table>\r\n" + "	\r\n" + "	</div>\r\n"
					+ "</body>\r\n" + "</html>";
			
			commonService.sendEmail(email, subject, content);
			
		}
		
		//insert
		reservationDao.registReservation(reservation);
		
	}
	
	/**
	 * 작성자 : 박세연
	 * 비밀번호 확인을 위해 등록된 각 예약NO의 비밀번호 가져오기
	 * @param rsvNo	
	 * @return
	 */
	public String getPwd(int rsvNo){
		
		String pwd = reservationDao.getPwd(rsvNo);
		
		return pwd;
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약 수정하기
	 * @param reservation
	 * @param emailCheckValue
	 */
	public void modifyReservation(Reservation reservation, String emailCheckValue) throws MessagingException, ParseException{
		
		//객체안 값 설정
		reservation.setRsvEmailCheck(emailCheckValue);

		if(reservation.getRsvConfirmState().equals("N")){
			makeAndSendAdminEmail(reservation);
		}
		
		//modify
		reservationDao.modifyReservation(reservation);
				
	}
	
	private void makeAndSendAdminEmail(Reservation reservation)throws MessagingException, ParseException {
		String rsvMemNm = reservation.getRsvMemNm();
		String rsvTitle = reservation.getRsvTitle();
		Time rsvStartTime = reservation.getRsvStartTime();
		Time rsvEndTime = reservation.getRsvEndTime();
		String rsvConfNm = commonService.confName(reservation.getRsvConfNo());
		Date rsvDate = reservation.getRsvDate();
		String rsvDateString = commonService.DateToString(rsvDate);
		String rsvDateOfTheWeek = commonService.dayOfTheWeek(rsvDateString);
		String rsvStartTimeChange = commonService.timeToString(rsvStartTime).substring(0,5);
		String rsvEndTimeChange = commonService.timeToString(rsvEndTime).substring(0,5);
		
		String email = reservation.getRsvMemEm();
		
		String subject;
		String content;
		
		if(reservation.getRsvConfirmState().equals("N")){
			
			email = adminDao.getAdminEmail();
			
			subject = "[회의실 가예약] " + rsvTitle + " (" + rsvDateString + "(" + rsvDateOfTheWeek + ") " + rsvStartTimeChange + " - " + rsvDateString + "(" + rsvDateOfTheWeek + ")" + rsvEndTimeChange	+ "), " + rsvConfNm;
			
			content = "<html>\r\n" + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
					+ "<head>\r\n" + "\r\n" + "\r\n" + "</head>\r\n" + "<body>\r\n" + "\r\n"
					+ "<div class=\"container\" style=\"display: block!important;max-width: 600px!important;margin: 0 auto!important;clear: both!important;\">\r\n"
					+ "   <a href=\"http://bluemixb.mybluemix.net/\">"
					+ "	<img src=\"https://i.imgur.com/rOpAzMk.png\">\r\n </a>" + "	<br>\r\n"
					+ "	<hr size=\"2\" noshade>\r\n" + "	<p>안녕하세요</p> \r\n" + "	"
					+ "<p>" + rsvMemNm + "님의 회의실 예약이 아래와 같이 가예약 신청됐습니다."
					+ "	<table style=\"text-align: center;border: 1px solid black;border-collapse: collapse;\">\r\n"
					+ "		<tr>\r\n"
					+ "			<td style=\"width: 200px;font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 제목 </td>\r\n"
					+ "			<td style=\"width: 400px;border: 1px solid black;border-collapse: collapse;\">" + rsvTitle
					+ "</td>\r\n" + "		</tr>\r\n" + "		\r\n" + "		<tr>\r\n"
					+ "			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 일자 </td>\r\n"
					+ "			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvDateString + "(" + rsvDateOfTheWeek + ")"  + "</td>\r\n" + "		</tr>\r\n"
					+ "		\r\n" + "		<tr>\r\n"
					+ "			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 시간 </td>\r\n"
					+ "			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvStartTimeChange + " - " + rsvEndTimeChange + "</td>\r\n" + "		</tr>\r\n" + "		\r\n" + "		<tr>\r\n"
					+ "			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 장소 </td>\r\n"
					+ "			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvConfNm
					+ "</td>\r\n" + "		</tr>\r\n" + "\r\n" + "\r\n" + "	</table>\r\n" + "	\r\n" + "	</div>\r\n"
					+ "</body>\r\n" + "</html>";
			commonService.sendEmail(email, subject, content);
		}
	}
	
	
	/**
	 * 작성자 : 박세연
	 * 예약 삭제하기
	 * @param rsvNo
	 */
	public void deleteReservation(int rsvNo){
		
		reservationDao.deleteReservation(rsvNo);
	}
	
	
	
	/**
	 * 작성자 : 최문정
	 * 내용 : 관리자 예약내역 중 입력날짜 이후의 값을 삭제
	 * @param request
	 * @return
	 */
	public Integer deleteReservationByDate(HttpServletRequest request) {
		
		String deleteDate = request.getParameter("deleteDate");
		
		reservationDao.deleteReservationByDate(deleteDate);

		
		return ConstantCode.SUCCESS;
	}
	
	/**
	 * 작성자 : 박세연
	 * 드래그 앤 드롭을 통해 예약 수정하기
	 * @param rsvNo
	 * @param rsvConfNo
	 * @param rsvStartTime
	 * @param rsvEndTime
	 */
	public void modifyRsvByDrop(int rsvNo, int rsvConfNo, Time rsvStartTime, Time rsvEndTime, Time rsvTotalTime){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rsvNo", rsvNo);
		map.put("rsvConfNo", rsvConfNo);
		map.put("rsvStartTime", rsvStartTime);
		map.put("rsvEndTime", rsvEndTime);
		map.put("rsvTotalTime", rsvTotalTime);
		
		reservationDao.modifyRsvByDrop(map);
	}
	
	/**
	 * 작성자 : 박세연
	 * 회의실 독점 방지를 위해 한 주의 예약내역 개수세기
	 * @param rsvTitle
	 * @param rsvMemPn
	 * @return
	 */
	public String preventMonopoly(String rsvTitle, String rsvMemPn, String rsvDate, String rsvTotalTime, int rsvNo){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rsvTitle", rsvTitle);
		map.put("rsvMemPn", rsvMemPn);
		map.put("rsvDate", rsvDate);

		List<Reservation> list = reservationDao.preventMonopoly(map);
		int total = 0;
		for(Reservation r : list){ //이미 DB에 등록된 예약들의 총 시간 구하기
			String totalTime = r.getRsvTotalTime().toString();

			if(r.getRsvNo() != rsvNo){ //수정 중일 때, 이미 등록된 시간과 현재 수정 중 총 소요시간을 두번 합산하지 않기 위해서
				total = total + Integer.parseInt(totalTime.substring(0,2)) * 60 + Integer.parseInt(totalTime.substring(3,5));
			}
		}
		
		total = total + Integer.parseInt(rsvTotalTime.substring(0,1)) * 60 + Integer.parseInt(rsvTotalTime.substring(2,4));
		
		int max = ConstantCode.RESERVATION_MAX_MONOPOLY * 60 * 3;
		
		String over = "";
		if(total >= max){ //한 주에 독점방지 시간 이상일 때, 5시간(설정값) * 3
			over = "T";
		}else{ //아닐 때
			over = "F";
		}
		
		return over;
	}
	
	
	/**
	 * 작성자 : 박세연
	 * 이미 예약된 시작&종료시간 select option disabled 시키기
	 * @param rsvDate
	 * @param rsvConfNo
	 * @return
	 */
	public List<Reservation> controlStartTime(String rsvDate, int rsvConfNo){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rsvDate", rsvDate);
		map.put("rsvConfNo", rsvConfNo);
		List<Reservation> rsvList = reservationDao.controlStartTime(map);

		return rsvList;
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
	public int chkRsvedTime(int rsvNo, String rsvDate, int rsvConfNo, Time rsvStartTime, Time rsvEndTime){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rsvNo", rsvNo);
		map.put("rsvDate", rsvDate);
		map.put("rsvConfNo", rsvConfNo);
		map.put("rsvStartTime", rsvStartTime);
		map.put("rsvEndTime", rsvEndTime);
		int count = reservationDao.chkRsvedTime(map);
		
		return count;
	}
	
	// ParkSungJun - reservationList
	public ArrayList<HashMap<String,Object>> allReservationList(HttpServletRequest request){
		
		String wantDate;
		if(request.getParameter("datepicker")!=null){
			wantDate=request.getParameter("datepicker");
		}
		else{
			wantDate = commonService.nowTime();
		}
		
		return reservationDao.allReservationList(wantDate);
		
	}
	
	/**
	 * 작성자 : 박세연
	 * resize 통해 예약 수정하기
	 * @param rsvNo
	 * @param rsvStartTime
	 * @param rsvEndTime
	 * @param rsvTotalTime
	 * @param rsvConfirmState
	 */
	public void modifyRsvByResize(int rsvNo, Time rsvStartTime, Time rsvEndTime, Time rsvTotalTime, String rsvConfirmState){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rsvNo", rsvNo);
		map.put("rsvStartTime", rsvStartTime);
		map.put("rsvEndTime", rsvEndTime);
		map.put("rsvTotalTime", rsvTotalTime);
		map.put("rsvConfirmState", rsvConfirmState);
		
		reservationDao.modifyRsvByResize(map);
	}
	
	/**
	 * 작성자 : 박세연
	 * 예약내역의 예약자 전화번호(PK) 가져오기
	 * @param rsvNo
	 * @return
	 */
	public String getRsvedMemPn(int rsvNo){
	
		String pn = reservationDao.getRsvedMemPn(rsvNo);
		
		return pn;
	}
	
	
	/**
	 * 작성자 : 박성준
	 * 예약 번호를 통해 예약내역의 정보를 받아와 사용자에게 비밀번호를 전송
	 * @throws MessagingException 
	 */
	public void emailFind(int rsvNo) throws MessagingException{
		
		List<Reservation> reservationInfo = reservationDao.getReservationInfo(rsvNo);
		
		String rsvMemberEmail = reservationInfo.get(ConstantCode.ZERO).getRsvMemEm();
		String memberName = reservationInfo.get(ConstantCode.ZERO).getRsvMemNm();
		String reservationTitle = reservationInfo.get(ConstantCode.ZERO).getRsvTitle();
		String reservationPw = reservationInfo.get(ConstantCode.ZERO).getRsvDelPwd();
		
		
		
		String subject = "[비밀번호 확인]" + memberName + "님의"  + reservationTitle + " 회의 비밀번호를 확인해주세요 ";
		
		String content = "<html>\r\n" + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "<head>\r\n" + "\r\n" + "\r\n" + "</head>\r\n" + "<body>\r\n" + "\r\n"
				+ "<div class=\"container\" style=\"display: block!important;max-width: 600px!important;margin: 0 auto!important;clear: both!important;\">\r\n"
				+ "   <a href=\"http://bluemixb.mybluemix.net/\">"
				+ "	<img src=\"https://i.imgur.com/rOpAzMk.png\">\r\n </a>" + "	<br>\r\n"
				+ "	<hr size=\"2\" noshade>\r\n" + "	<p>안녕하세요</p> \r\n" + "	"
				+ "<p>" + memberName + "님의 비밀번호는 다음과 같습니다."
				+ "	<table style=\"text-align: center;border: 1px solid black;border-collapse: collapse;\">\r\n"
				+ "		<tr>\r\n"
				+ "			<td style=\"width: 200px;font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 제목 </td>\r\n"
				+ "			<td style=\"width: 400px;border: 1px solid black;border-collapse: collapse;\">" + reservationTitle
				+ "</td>\r\n" + "		</tr>\r\n" + "		\r\n" + "		<tr>\r\n"
				+ "			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">비밀번호 </td>\r\n"
				+ "			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + reservationPw + "</td>\r\n" + "		</tr>\r\n"
				+ "		\r\n" + "		<tr>\r\n"
				+ "</body>\r\n" + "</html>";
		
		commonService.sendEmail(rsvMemberEmail, subject, content);
		
		
	}
	
	/**
	 * 작성자 : 박세연
	 * 가예약 승인된 예약은 관리자만 수정 가능
	 * @param rsvNo
	 * @return
	 */
	public String getRsvConfirmStateVal(int rsvNo){
	
		String state = reservationDao.getRsvConfirmStateVal(rsvNo);
		
		return state;
	}
	
	/**
	 * 작성자 : 박세연
	 * 등록된 예약 위로 마우스 위치시, 예약자 내역 tooltip에 띄우기
	 * @param rsvNo
	 * @return
	 */
	public List<Reservation> showInfoByTooltip(int rsvNo){
		
		List<Reservation> list = reservationDao.showInfoByTooltip(rsvNo);
		
		return list;
	}
	
	/**
	 * 작성자 : 박세연
	 * 등록된 예약의 제목 가져오기
	 * @param rsvNo
	 * @return
	 */
	public String getRsvedTitle(int rsvNo){
		
		String title = reservationDao.getRsvedTitle(rsvNo);
		
		return title;
	}
	
}
