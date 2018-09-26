package com.ibmMeeting.Dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper 
public interface AdminDao {
	
	Integer loginCheck(HashMap<String,Object> loginInformation); 			// 로그인 체크
	void changeEmail(HashMap<String,Object> adminInformation);				// 이메일 변경
	void changePassword(HashMap<String,Object> loginInformation);			// 패스워드 변경
	String getAdminEmail();		
	// 모든 관리자 이메일 가져오기

}