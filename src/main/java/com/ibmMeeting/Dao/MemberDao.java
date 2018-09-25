package com.ibmMeeting.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ibmMeeting.VO.Member;

/**
 * @author 박세연
 */
@Mapper
public interface MemberDao {
	
	List<Member> getMemInfoByPn(String memPn);
	
	void registMember(Member member);
	
	int checkExistMemInfo(String memPn);
	
	void modifyMember(Member member);
	
	List<Member> memInfoByToolTip(String memPn);
}
