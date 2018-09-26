package com.ibmMeeting.Dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.ibmMeeting.VO.History;

@Mapper
public interface HistoryDao {

	/**
	 * 작성자 : 박세연
	 * 예약기록에 등록하기 - 등록/수정/삭제
	 * @param history
	 */
	void registHistory(History history);
	
	/**
	 * 작성자 : 최문정
	 * 내용 : 이전날짜의히스토리 삭제
	 * @param deleteDate
	 */
	void deleteHistoryByDate(String deleteDate);
	
	/**
	 * 작성자 : 최문정
	 * 내용 : 이전 날짜 이전의 기록 저장(HISTORY TABLE)
	 * @param dateInfo
	 * @return
	 */
	ArrayList<HashMap<String, Object>> selectAllHistoryByDate(String deleteDate);
	

	
	
	ArrayList<HashMap<String,Object>> fakeReservation();
	void reservationUpdate(HashMap<String,Object> reservationUpdate);
	void reservationUpdateAndInsert(HashMap<String,Object> reservationUpdate);

	void reservationDelete(HashMap<String, Object> reservationDelete);
	void reservationDeleteAndInsert(HashMap<String,Object> reservationDeleteAndInsert);
	ArrayList<HashMap<String,Object>> repeatReservationInside(Integer repeatNo);
	ArrayList<HashMap<String,Object>> repeatReservationHead();

	ArrayList<HashMap<String,Object>> overtimeReservation();


	HashMap<String,Object> getHistoryAndUserInfo(Integer hstNo);
	void historyNoShowCancel(Integer hstNo);

}
