package com.ibmMeeting.Dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettingDao {
	
	void settingUpdate(HashMap<String,Object> settingValue); // 셋팅 수정
	HashMap<String,Object> settingLoad(); // 셋팅 목록
}