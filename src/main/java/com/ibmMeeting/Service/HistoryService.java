package com.ibmMeeting.Service;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibmMeeting.Constant.ConstantCode;
import com.ibmMeeting.Dao.BoardingDao;
import com.ibmMeeting.Dao.HistoryDao;
import com.ibmMeeting.Dao.MeetingRoomDao;
import com.ibmMeeting.Dao.ReservationDao;
import com.ibmMeeting.VO.History;
import com.ibmMeeting.VO.Reservation;

@Service
public class HistoryService {
   
   @Autowired
   HistoryDao historyDao;
   
   @Autowired
   MeetingRoomDao meetingRoomDao;
   
   @Autowired
   ReservationDao reservationDao;
   
   @Autowired
   BoardingDao boardingDao;
   
   @Autowired
   CommonService commonService;
   
   
   

   /**
    * 작성자 : 박세연
    * 예약기록에 등록하기 - 등록/수정/삭제
    * @param reservation
    * @param hstState
    */
   public void insertHistory(Reservation reservation, String hstState){

      History history = new History();
      Date date = new Date();
      
      //만약 key값을 이용해서 사이트명을 구분한다면 바뀌어야 할 코드
      history.setHstRsvComp(ConstantCode.COMPANY_NAME);
      
      history.setHstRsvTitle(reservation.getRsvTitle());
      history.setHstDate(reservation.getRsvDate());
      history.setHstTotalTime(reservation.getRsvTotalTime());
      history.setHstStartTime(reservation.getRsvStartTime());
      history.setHstEndTime(reservation.getRsvEndTime());
      history.setHstRsvMemNm(reservation.getRsvMemNm());
      history.setHstRsvMemPn(reservation.getRsvMemPn());
      history.setHstRsvMemEm(reservation.getRsvMemEm());
      history.setHstDelPwd(reservation.getRsvDelPwd());
      history.setHstRegDate(date);
      history.setHstConfNo(reservation.getRsvConfNo());
      history.setHstRepeatPeriod("N");
      history.setHstSetting("0");
      history.setHstRsvState(hstState); // RESERVE/MODIFY/DELETE
      history.setHstRepeatNo(0);
      history.setHstDescription("");
      
      historyDao.registHistory(history);
      
   }
   
   /* 
    * 작성자 : 고창환
    * 가예약 리스트
    */
   public ArrayList<HashMap<String,Object>> fakeReservation() {
      // TODO Auto-generated method stub
      return historyDao.fakeReservation();
   }
   /* 
    * 작성자 : 고창환
    * 가예약 승인 기능(승인 전 히스토리테이블 인서트)
    */
   public String reservationUpdate(HttpServletRequest request) throws MessagingException, ParseException {
      // TODO Auto-generated method stub
      
      String reservationNumber=request.getParameter("reservationSeq");
      String repeatNo=request.getParameter("repeatNo");
      
      HashMap<String,Object> reservationUpdateAndInsert = new HashMap<String,Object>();
      
      reservationUpdateAndInsert.put("reservationNo", reservationNumber);
      reservationUpdateAndInsert.put("repeatNo",repeatNo);

      historyDao.reservationUpdateAndInsert(reservationUpdateAndInsert);
      
      HashMap<String,Object> reservationUpdate = new HashMap<String,Object>();
      
      reservationUpdate.put("reservationNo", reservationNumber);
      reservationUpdate.put("repeatNo",repeatNo);
      
      historyDao.reservationUpdate(reservationUpdate);
      
      
      /*
       * 가예약 및 반복예약 승인 메일
       * 작성자 : 박성준
       */
      
      Integer reservationNo = Integer.parseInt(reservationNumber);
      
      HashMap<String,Object> reservationAndUserInfo = boardingDao.reservationAndUserInfo(reservationNo);
      String rsvEmail = (String) reservationAndUserInfo.get("RSV_MEM_EM");
      String rsvMemNm = (String) reservationAndUserInfo.get("RSV_MEM_NM");
      String rsvTitle = (String) reservationAndUserInfo.get("RSV_TITLE");
      Time rsvStartTime = (Time) reservationAndUserInfo.get("RSV_START_TIME");
      Time rsvEndTime = (Time) reservationAndUserInfo.get("RSV_END_TIME");
      Integer rsvConfNo = (Integer) reservationAndUserInfo.get("RSV_CONF_NO");
      String rsvConfNm = commonService.confName(rsvConfNo);
      
      String rsvStartTimeFormat = commonService.timeToString(rsvStartTime);
      String rsvEndTimeFormat = commonService.timeToString(rsvEndTime);
      
      String rsvStartTimeChange = rsvStartTimeFormat.substring(0,5);
      String rsvEndTimeChange = rsvEndTimeFormat.substring(0,5);
      HashMap<String,Object> dateInfo = reservationDao.firstDayAndLastDay(reservationNo);
      Date firstDay = (Date) dateInfo.get("firstDay");
      String firstDayString = commonService.DateToString(firstDay);  
      String firstDayOfTheWeek = commonService.dayOfTheWeek(firstDayString);
      Date lastDay = (Date) dateInfo.get("lastDay");
      String lastDayString = commonService.DateToString(lastDay); 
      String lastDayOfTheWeek = commonService.dayOfTheWeek(lastDayString);
      
      
      HashMap<String,Object> dateInfo2 = reservationDao.getDay(reservationNo);
      Date reservedDay = (Date) dateInfo2.get("rsv_date");//에러
      String reservedDayString = commonService.DateToString(reservedDay);  
      String reservedDayOfTheWeek = commonService.dayOfTheWeek(reservedDayString);
      
      String dateInfoString;
      String subject;
      if((Integer) reservationAndUserInfo.get("RSV_REPEAT_NO")==0) {  
        dateInfoString = reservedDay + " (" + reservedDayOfTheWeek;
         subject = "[회의실 예약 승인] " + rsvTitle + " (" + reservedDayString + "(" + reservedDayOfTheWeek + ") " + rsvStartTimeChange + " - "  + rsvEndTimeChange   + "), " + rsvConfNm;
      } else {
         dateInfoString = firstDay + " (" + firstDayOfTheWeek +") ~ " + lastDay + "(" + lastDayOfTheWeek; 
         subject = "[회의실 예약 승인] " + rsvTitle + " (" + firstDayString + "(" + firstDayOfTheWeek + ") " + rsvStartTimeChange + " - " + lastDayString + "(" + lastDayOfTheWeek + ") " + rsvEndTimeChange   + "), " + rsvConfNm;
      }
      
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
            "   <img src=\"https://i.imgur.com/rOpAzMk.png\">\r\n </a>" + 
            "   <br>\r\n" + 
            "   <hr size=\"2\" noshade>\r\n" + 
            "   <p>안녕하세요</p> \r\n" + 
            "   <p>"+"[회의실 예약 승인] " + rsvMemNm+ "님의 " +rsvTitle + " 회의가 승인됐습니다"+ 
            "   <table style=\"text-align: center;border: 1px solid black;border-collapse: collapse;\">\r\n" + 
            "      <tr>\r\n" + 
            "         <td style=\"width: 200px;font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 제목 </td>\r\n" + 
            "         <td style=\"width: 400px;border: 1px solid black;border-collapse: collapse;\">"+rsvTitle+"</td>\r\n" + 
            "      </tr>\r\n" + 
            "      \r\n" + 
            "      <tr>\r\n" + 
            "         <td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 일자 </td>\r\n" + 
            "         <td style=\"border: 1px solid black;border-collapse: collapse;\">" + dateInfoString + ')' + "</td>\r\n" + 
            "      </tr>\r\n" + 
            "      \r\n" + 
            "      <tr>\r\n" + 
            "         <td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 시간 </td>\r\n" + 
            "         <td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvStartTimeChange + " - " + rsvEndTimeChange + "</td>\r\n" + 
            "      </tr>\r\n" + 
            "      \r\n" + 
            "      <tr>\r\n" + 
            "         <td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 장소 </td>\r\n" + 
            "         <td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvConfNm + "</td>\r\n" + 
            "      </tr>\r\n" + 
            "\r\n" + 
            "\r\n" + 
            "   </table>\r\n" + 
            "   \r\n" + 
            "   </div>\r\n" + 
            "</body>\r\n" + 
            "</html>";
      
      
      commonService.sendEmail(rsvEmail, subject, content);
      
      return "success";
      
   }
   /* 
    * 작성자 : 고창환
    * 가예약 반려 기능(반려전 히스토리 인서트)
    */
   public String reservationDelete(HttpServletRequest request) throws MessagingException, ParseException {
      // TODO Auto-generated method stub
      String reservationNumber=request.getParameter("reservationSeq");
      String repeatNo=request.getParameter("repeatNo");
      
      HashMap<String,Object> reservationDeleteAndInsert = new HashMap<String,Object>();
      
      reservationDeleteAndInsert.put("reservationNo", reservationNumber);
      reservationDeleteAndInsert.put("repeatNo",repeatNo);

      
      
      HashMap<String,Object> reservationDelete = new HashMap<String,Object>();
      
      reservationDelete.put("reservationNo", reservationNumber);
      reservationDelete.put("repeatNo",repeatNo);
      
      
      
      /*
       * 가예약 및 반복예약 거절 메일
       * 작성자 : 박성준
       */
      
Integer reservationNo = Integer.parseInt(reservationNumber);
      
      HashMap<String,Object> reservationAndUserInfo = boardingDao.reservationAndUserInfo(reservationNo);
      String rsvEmail = (String) reservationAndUserInfo.get("RSV_MEM_EM");
      String rsvMemNm = (String) reservationAndUserInfo.get("RSV_MEM_NM");
      String rsvTitle = (String) reservationAndUserInfo.get("RSV_TITLE");
      Time rsvStartTime = (Time) reservationAndUserInfo.get("RSV_START_TIME");
      Time rsvEndTime = (Time) reservationAndUserInfo.get("RSV_END_TIME");
      Integer rsvConfNo = (Integer) reservationAndUserInfo.get("RSV_CONF_NO");
      String rsvConfNm = commonService.confName(rsvConfNo);
      
      String rsvStartTimeFormat = commonService.timeToString(rsvStartTime);
      String rsvEndTimeFormat = commonService.timeToString(rsvEndTime);
      
      String rsvStartTimeChange = rsvStartTimeFormat.substring(0,5);
      String rsvEndTimeChange = rsvEndTimeFormat.substring(0,5);
      HashMap<String,Object> dateInfo = reservationDao.firstDayAndLastDay(reservationNo);
      Date firstDay = (Date) dateInfo.get("firstDay");
      String firstDayString = commonService.DateToString(firstDay);
      String firstDayOfTheWeek = commonService.dayOfTheWeek(firstDayString);
      Date lastDay = (Date) dateInfo.get("lastDay");
      String lastDayString = commonService.DateToString(lastDay);
      String lastDayOfTheWeek = commonService.dayOfTheWeek(lastDayString);
      
 
      
      HashMap<String,Object> dateInfo2 = reservationDao.getDay(reservationNo);
      Date reservedDay = (Date) dateInfo2.get("firstDay");//에러
      String reservedDayString = commonService.DateToString(reservedDay);  
      String reservedDayOfTheWeek = commonService.dayOfTheWeek(reservedDayString);
      
      String dateInfoString;
      String subject;
      if((Integer) reservationAndUserInfo.get("RSV_REPEAT_NO")==0) {  
        dateInfoString = reservedDay + " (" + reservedDayOfTheWeek;
         subject = "[회의실 예약 반려] " + rsvTitle + " (" + reservedDayString + "(" + reservedDayOfTheWeek + ") " + rsvStartTimeChange + " - "  + rsvEndTimeChange   + "), " + rsvConfNm;
      } else {
         dateInfoString = firstDay + " (" + firstDayOfTheWeek +") ~ " + lastDay + "(" + lastDayOfTheWeek; 
         subject = "[회의실 예약 반려] " + rsvTitle + " (" + firstDayString + "(" + firstDayOfTheWeek + ") " + rsvStartTimeChange + " - " + lastDayString + "(" + lastDayOfTheWeek + ") " + rsvEndTimeChange   + "), " + rsvConfNm;
      }
      
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
            "   <img src=\"https://i.imgur.com/rOpAzMk.png\">\r\n </a>" + 
            "   <br>\r\n" + 
            "   <hr size=\"2\" noshade>\r\n" + 
            "   <p>안녕하세요</p> \r\n" + 
            "   <p>"+"[회의실 예약 반려] " + rsvMemNm+ "님의 " +rsvTitle + " 회의가 반려됐습니다"+ 
            "   <table style=\"text-align: center;border: 1px solid black;border-collapse: collapse;\">\r\n" + 
            "      <tr>\r\n" + 
            "         <td style=\"width: 200px;font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 제목 </td>\r\n" + 
            "         <td style=\"width: 400px;border: 1px solid black;border-collapse: collapse;\">"+rsvTitle+"</td>\r\n" + 
            "      </tr>\r\n" + 
            "      \r\n" + 
            "      <tr>\r\n" + 
            "         <td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 일자 </td>\r\n" + 
            "         <td style=\"border: 1px solid black;border-collapse: collapse;\">" + dateInfoString + ')' + "</td>\r\n" + 
            "      </tr>\r\n" + 
            "      \r\n" + 
            "      <tr>\r\n" + 
            "         <td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 시간 </td>\r\n" + 
            "         <td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvStartTimeChange + " - " + rsvEndTimeChange + "</td>\r\n" + 
            "      </tr>\r\n" + 
            "      \r\n" + 
            "      <tr>\r\n" + 
            "         <td style=\"font-weight: bold;border: 1px solid black;border-collapse: collapse;\">회의 장소 </td>\r\n" + 
            "         <td style=\"border: 1px solid black;border-collapse: collapse;\">" + rsvConfNm + "</td>\r\n" + 
            "      </tr>\r\n" + 
            "\r\n" + 
            "\r\n" + 
            "   </table>\r\n" + 
            "   \r\n" + 
            "   </div>\r\n" + 
            "</body>\r\n" + 
            "</html>";
      
      
      commonService.sendEmail(rsvEmail, subject, content);
      
      historyDao.reservationDelete(reservationDelete);
      historyDao.reservationDeleteAndInsert(reservationDeleteAndInsert);
      
      return "success";

   }
   
   /**
    * 작성자  : 최문정
    * 내용 : 관리자 예약내역 페이지, 입력한 날짜 이전의 날짜를 모두 삭제
    * @param request
    * @return
    */
   public Integer deleteHistoryByDate(HttpServletRequest request) {
      
      String deleteDate = request.getParameter("deleteDate");
      
      historyDao.deleteHistoryByDate(deleteDate);
      return ConstantCode.SUCCESS;
   }
   
   
   /**
    * 작성자 : 최문정
    * 내용 : History Table과 Reservation Table의 내용을 Controller에 Map 형태로 전달
    * @param request
    * @param response
    * @param dDate
    */
   public HashMap<String, Object> reservHistoryToExcel(HttpServletRequest request, HttpServletResponse response, String dDate) {
      
      ArrayList<HashMap<String,Object>> historyResult = historyDao.selectAllHistoryByDate(dDate);
      ArrayList<HashMap<String,Object>> reservationResult =  reservationDao.selectAllReservationByDate(dDate);
      
      HashMap<String, Object> map =  new HashMap<String, Object>();
      
      //엑셀에 저장할 내용 map에 저장
      map.put("historyTable", historyResult);
      map.put("reservTable", reservationResult);
      map.put("dDate", dDate );
      
      return map;

   }
   
   /* 
    * 작성자 : 고창환
    * 가예약 셀렉트 옵션 별 리스트
    */
   public ArrayList<HashMap<String, Object>> selectResult(HttpServletRequest request) {
      
      ArrayList<HashMap<String, Object>> selectResult = new ArrayList<HashMap<String, Object>>();
      
      if(request.getParameter("searchOption")!=null){
         String selectOpt = request.getParameter("searchOption");
         
         if(selectOpt.equals("allList")) {
            selectResult =  historyDao.fakeReservation();
         }else if(selectOpt.equals("repeatList")) {
            selectResult = historyDao.repeatReservationHead();
         }else if(selectOpt.equals("overtimeList")){
            selectResult = historyDao.overtimeReservation();
         }
      }
      return selectResult;
   }

   /* 
    * 작성자 : 고창환
    * 반복예약 상세내역 조회
    */
   public ArrayList<HashMap<String,Object>> lookInside(HttpServletRequest request) {
      
      ArrayList<HashMap<String, Object>> lookInside = new ArrayList<HashMap<String, Object>>();

      
      Integer repeatNo=Integer.parseInt(request.getParameter("repeatNo"));
      
      
      lookInside = historyDao.repeatReservationInside(repeatNo);
      
      
      return lookInside;
      
   }
   
}