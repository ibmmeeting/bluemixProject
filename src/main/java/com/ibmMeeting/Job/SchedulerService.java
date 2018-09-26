package com.ibmMeeting.Job;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ibmMeeting.Dao.BoardingDao;
import com.ibmMeeting.Dao.SchedulerDao;
import com.ibmMeeting.Dao.SettingDao;
import com.ibmMeeting.Service.CommonService;

@Component
public class SchedulerService {

	@Autowired
	BoardingDao boardingDao;

	@Autowired
	SchedulerDao schedulerDao;

	@Autowired
	SettingDao settingDao;

	@Autowired
	CommonService commonService;
	
	/* 
	 * 작성자 : 박성준
	 * 매일 12시 5분
	 * noShow 차단 해제 해당자 해제
	 */
	@Scheduled(cron ="0 5 0 * * *")
	public void banReset(){
		String today = commonService.nowTimeDash();
		boardingDao.noShowBanReset(today);
	}

	/* 
	 * 작성자 : 박성준
	 * 매월 1일 0시 0분 0초에 실행한다.
	 * 모든 유저의 noShowCount 초기화
	 */
	@Scheduled(cron = "0 0 0 1 * *")
	public void noShowCountAllInit() {
		boardingDao.noShowAllUserInit();
	}

	/* 
	 * 작성자 : 박성준
	 * 애플리케이션 시작 후 60초 후에 첫 실행, 그 후 매 120초마다 주기적으로 실행
	 * 이메일 보낼 대상자들 선정하여 이메일 전송, 회의 예약 30분전 테스트
	 */
	@Scheduled(initialDelay = 60000, fixedDelay = 120000)
	public void reservationBeforeEmailSend() throws MessagingException, ParseException {

		HashMap<String, Object> settingValue = settingDao.settingLoad();
		Integer emailTime = (Integer) settingValue.get("SET_EMAIL_TIME");

		//현재시간에 이메일 셋팅값을 더함
		Calendar nowTimeCal = Calendar.getInstance();
		nowTimeCal.add(Calendar.MINUTE, emailTime);
		int hour = nowTimeCal.get(Calendar.HOUR_OF_DAY);
		int min = nowTimeCal.get(Calendar.MINUTE);

		String nowDate = commonService.nowTime();
		String nowTime = Integer.toString(hour) + ":" + Integer.toString(min)+ ":00";
		
		HashMap<String, Object> dateInformation = new HashMap<String, Object>();
		dateInformation.put("nowDate", nowDate);
		dateInformation.put("nowTime", nowTime);
		

		// 셋팅값을 더한시간보다 미팅시작시간이 작고 이메일 체크가 돼 있다면
		// 그 reservation 값을 가져옴
		ArrayList<HashMap<String, Object>> emailSendUserInfo = schedulerDao.reservaionEmailInfo(dateInformation);
		
		if (!emailSendUserInfo.isEmpty()) {
			for (int i = 0; i < emailSendUserInfo.size(); i++) {

				Integer rsvNumber = (Integer) emailSendUserInfo.get(i).get("RSV_NO");
				String rsvMemberEmail = (String) emailSendUserInfo.get(i).get("RSV_MEM_EM");
				String rsvConfNm = (String) emailSendUserInfo.get(i).get("CONF_NM");
				Time rsvStartTime =  (Time) emailSendUserInfo.get(i).get("RSV_START_TIME");
				Time rsvEndTime = (Time) emailSendUserInfo.get(i).get("RSV_END_TIME");
				String rsvTitle = (String) emailSendUserInfo.get(i).get("RSV_TITLE");
				String rsvMemNm = (String) emailSendUserInfo.get(i).get("RSV_MEM_NM");
				Date rsvDate = (Date) emailSendUserInfo.get(i).get("RSV_DATE");
				String rsvDateString = commonService.DateToString(rsvDate);
				String rsvDayOfTheWeek = commonService.dayOfTheWeek(rsvDateString);
				
				String rsvStartTimeChange = commonService.timeToString(rsvStartTime);
				String rsvEndTimeChange = commonService.timeToString(rsvEndTime);

				String subject = "[예약 Reminder] "  + rsvTitle + " (" + rsvDateString + "(" + rsvDayOfTheWeek + ") " + rsvStartTimeChange + " - " + rsvEndTimeChange	+ " ), " + rsvConfNm;
				
				String content = 
						"<html>\r\n" + 
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
						"<head>\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"</head>\r\n" + 
						"<body>\r\n" + 
						"\r\n" + 
						"<div class=\"container\" style=\"display: block!important;max-width: 600px!important;margin: 0 auto!important;clear: both!important;\">\r\n" +
						"   <a href=\"http://bluemixb.mybluemix.net/\">" +
						"	<img src=\"https://i.imgur.com/rOpAzMk.png\">\r\n </a>" + 
						"	<br>\r\n" + 
						"	<hr size=\"2\" noshade>\r\n" + 
						"	<p>안녕하세요</p> \r\n" + 
						"	<p>" + rsvMemNm+ "님의 " +rsvTitle + " 회의가 예정되어 있습니다. 참고 바랍니다.</p>\r\n" + 
						"	<table style=\"text-align: center;border: 1px solid black;border-collapse: collapse;\">\r\n" + 
						"		<tr>\r\n" + 
						"			<td style=\"width: 200px;font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 제목 </td>\r\n" + 
						"			<td style=\"width: 400px;border: 1px solid black;border-collapse: collapse;\">"+rsvTitle+"</td>\r\n" + 
						"		</tr>\r\n" + 
						"		\r\n" + 
						"		<tr>\r\n" + 
						"			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 일자 </td>\r\n" + 
						"			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvDate + " (" + rsvDayOfTheWeek + ')' + "</td>\r\n" + 
						"		</tr>\r\n" + 
						"		\r\n" + 
						"		<tr>\r\n" + 
						"			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 시간 </td>\r\n" + 
						"			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvStartTimeChange + " - " + rsvEndTimeChange + "</td>\r\n" + 
						"		</tr>\r\n" + 
						"		\r\n" + 
						"		<tr>\r\n" + 
						"			<td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 장소 </td>\r\n" + 
						"			<td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvConfNm + "</td>\r\n" + 
						"		</tr>\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"	</table>\r\n" + 
						"	\r\n" + 
						"	</div>\r\n" + 
						"</body>\r\n" + 
						"</html>";
				
				
				
				commonService.sendEmail(rsvMemberEmail, subject, content);
				
				schedulerDao.eMailSendStateUpdate(rsvNumber);
			}
		}
	}
}
