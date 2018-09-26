package com.ibmMeeting.Service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ibmMeeting.Constant.ConstantCode;
import com.ibmMeeting.Dao.BoardingDao;
import com.ibmMeeting.Dao.HistoryDao;
import com.ibmMeeting.Dao.ReservationDao;
import com.ibmMeeting.Dao.SettingDao;
import com.ibmMeeting.VO.Reservation;

@Service
public class AdminNoShowService {
	
	@Autowired
	ReservationDao reservationDao;
	
	@Autowired
	BoardingDao boardingDao;
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	SettingDao settingDao;
	
	@Autowired
	HistoryDao historyDao;
	
	@Autowired
	public JavaMailSender emailSender;
	
	/* 
	 * 작성자 : 박성준
	 * No-Show 버튼을 눌렀을 경우
	 * 유저Sequence넘버를 통해 noShowBan
	 */

	public Integer noShowSubmit(HttpServletRequest request) throws MessagingException, ParseException{
		
		// 회의번호를 통해 회의정보를 가져옴
		Integer rsvNo = Integer.parseInt(request.getParameter("rsvNo"));
		Reservation rsvInformation = reservationDao.getReservationInfo(rsvNo).get(ConstantCode.ZERO);
		
		// 관리자가 설정한 noShowBanDay값을 가져옴
		HashMap<String,Object> settingValue = settingDao.settingLoad();
		Integer noShowBanDay = (Integer)settingValue.get("SET_NO_SHOW_BAN_DAY");
		
		// noShowBanDay만큼 오늘 날짜에 더하고 포맷 변경
		Calendar getCalendar = Calendar.getInstance();
		getCalendar.add(Calendar.DATE, noShowBanDay);
		DateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		String afterBanDayString = transFormat.format(getCalendar.getTime());
		
		// memberInformation에 값을 저장하여 이에 해당되는 noShow Count 증가
		HashMap<String,Object> memberInformation = new HashMap<String,Object>();
		memberInformation.put("memberName", rsvInformation.getRsvMemNm());
		memberInformation.put("memberPhone", rsvInformation.getRsvMemPn());
		memberInformation.put("nowDate", afterBanDayString);
		
		boardingDao.noShowCountPlusInManage(memberInformation);
		
		String rsvMemNm = rsvInformation.getRsvMemNm();
		String rsvTitle = rsvInformation.getRsvTitle();
		Time rsvStartTime = rsvInformation.getRsvStartTime();
		Time rsvEndTime = rsvInformation.getRsvEndTime();
		String rsvConfNm = commonService.confName(rsvInformation.getRsvConfNo());
		Date rsvDate = rsvInformation.getRsvDate();
		String rsvDateString = commonService.DateToString(rsvDate);
		String rsvDateOfTheWeek = commonService.dayOfTheWeek(rsvDateString);
		String rsvStartTimeChange = commonService.timeToString(rsvStartTime).substring(0,5);
		String rsvEndTimeChange = commonService.timeToString(rsvEndTime).substring(0,5);
		
		String subject = "[회의 No-Show] " + rsvInformation.getRsvMemNm() + "님의 " + rsvTitle + "회의가 No-Show 됐습니다." ;

		String content = "<html>\r\n" + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
				+ "<head>\r\n" + "\r\n" + "\r\n" + "</head>\r\n" + "<body>\r\n" + "\r\n"
				+ "<div class=\"container\" style=\"display: block!important;max-width: 600px!important;margin: 0 auto!important;clear: both!important;\">\r\n"
				+ "   <a href=\"http://bluemixb.mybluemix.net/\">"
				+ "	<img src=\"https://i.imgur.com/rOpAzMk.png\">\r\n </a>" + "	<br>\r\n"
				+ "	<hr size=\"2\" noshade>\r\n" + "	<p>안녕하세요</p> \r\n" + "	"
				+ "<p>" + rsvMemNm + "님의 회의가 No-Show 됐습니다."
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
		// 이메일전송
		
		commonService.sendEmail(rsvInformation.getRsvMemEm(), subject, content);
		
		HashMap<String,Object> settingLoad = settingDao.settingLoad();
		Integer limitNoShowCount = (Integer)settingLoad.get("SET_NO_SHOW_COUNT");
		

		if(boardingDao.noShowUserCount(memberInformation)==limitNoShowCount){
			boardingDao.memberBan(memberInformation);
			
			subject = "[NoShow 초과]" + rsvInformation.getRsvMemNm()+ "님 No-Show 카운트 설정값을 초과하여 차단됩니다.\n";
			
			content = "<html>\r\n" + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n"
			+ "<head>\r\n" + "\r\n" + "\r\n" + "</head>\r\n" + "<body>\r\n" + "\r\n"
			+ "<div class=\"container\" style=\"display: block!important;max-width: 600px!important;margin: 0 auto!important;clear: both!important;\">\r\n"
			+ "   <a href=\"http://bluemixb.mybluemix.net/\">"
			+ "	<img src=\"https://i.imgur.com/rOpAzMk.png\">\r\n </a>" + "	<br>\r\n"
			+ "	<hr size=\"2\" noshade>\r\n" + "	<p>안녕하세요</p> \r\n" + "	"
			+ "<p>" +"안녕하세요 " + rsvMemNm + "님 현재 회원님의 NoShow 횟수가 많아 회의실 예약 시스템에서 차단됩니다.</p>"
			+ "<p> 차단기간 : " + afterBanDayString + "</p>" 
			+ "<p> 문의는 해당메일로 답장 부탁드립니다.</p>";
		}
		
		commonService.sendEmail(rsvInformation.getRsvMemEm(), subject, content);
		
		// history Update
		String hstState = "NOSHOW";
		historyService.insertHistory(rsvInformation, hstState);
		
		// 예약내역에서 삭제
		reservationDao.deleteReservation(rsvNo);
		
		return ConstantCode.SUCCESS;
	}
	
	
	/* 
	 * 작성자 : 박성준
	 * 사용자별 노쇼 리스트 출력
	 * 유저 이름과 핸드폰 번호를 통해 사용자 정보를 업데이트
	 */
	public ArrayList<HashMap<String,Object>> noShowDetail(HttpServletRequest request){
		
		// 사용자 이름과 핸드폰 번호 받아옴
		String userName = request.getParameter("detailMemberName");
		String userPhone = request.getParameter("detailMemberPhone");
		
		// 오늘 날짜를 받아옴
		Calendar today = Calendar.getInstance();
		String todayYear = Integer.toString(today.get(Calendar.YEAR));
		String todayMonth = Integer.toString(today.get(Calendar.MONTH)+1);
		
		// 오늘 날짜의 첫달과 마지막 날짜를 입력
		String todayMonthFirstDay = todayYear + "/" + todayMonth+ "/" + 01;
		String todayMonthLastDay = todayYear + "/" + todayMonth+ "/" + 31;
		
		// 위의 정보를 가져와 현재 달의 noShowList 정보 가져옴
		HashMap<String,Object> noShowDetailInformation = new HashMap<String,Object>();
		noShowDetailInformation.put("userName",userName);
		noShowDetailInformation.put("userPhone",userPhone);
		noShowDetailInformation.put("todayMonthFirstDay",todayMonthFirstDay);
		noShowDetailInformation.put("todayMonthLastDay",todayMonthLastDay);
		
		return boardingDao.noShowDetail(noShowDetailInformation);
	}
	
	/* 
	 * 작성자 : 박성준
	 * 노쇼 취소
	 * 히스토리 넘버를 통해 NOSHOW 취소
	 */
	public Integer noShowReservationCancel(HttpServletRequest request){
		
		// 히스토리 넘버 가져옴
		Integer hstNo = Integer.parseInt(request.getParameter("hstNo"));
		
		// 업데이트 해야할 히스토리 정보와 사용자 정보, 그리고 셋팅값 가져옴
		HashMap<String,Object> historyAndUserInfo = historyDao.getHistoryAndUserInfo(hstNo);
		HashMap<String,Object> settingLoad = settingDao.settingLoad();
		HashMap<String,Object> updateInfo = new HashMap<String,Object>();
		
		Integer userNoShowLimit = (Integer)settingLoad.get("SET_NO_SHOW_COUNT");
		Integer userNoShowCount = (Integer)historyAndUserInfo.get("COUNT_WARN");
		
		// 노쇼카운트 수가 셋팅값보다 같거나 적을때는 정상 상태로
		if(userNoShowCount<=userNoShowLimit){
			updateInfo.put("MEM_BANDAY", ConstantCode.NOT);
			updateInfo.put("MEM_STATE", ConstantCode.NORMAL);
		}
		// 노쇼카운트가 셋팅값보다 많을경우 유지
		else{
			updateInfo.put("MEM_BANDAY", historyAndUserInfo.get("MEM_BANDAY"));
			updateInfo.put("MEM_STATE", historyAndUserInfo.get("MEM_STATE"));
		}
		
		updateInfo.put("COUNT_WARN", userNoShowCount - ConstantCode.ONE);
		updateInfo.put("MEM_PN", historyAndUserInfo.get("MEM_PN"));
		
		// 히스토리 업데이트
		// 노쇼 값 1 마이너스
		historyDao.historyNoShowCancel(hstNo);
		boardingDao.noShowCountMinusInReservation(updateInfo);
		
		return ConstantCode.SUCCESS;
	}
	
}
