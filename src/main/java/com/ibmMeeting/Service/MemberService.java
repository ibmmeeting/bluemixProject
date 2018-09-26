package com.ibmMeeting.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibmMeeting.Constant.ConstantCode;
import com.ibmMeeting.Dao.MemberDao;
import com.ibmMeeting.VO.Member;
import com.ibmMeeting.VO.Reservation;

@Service
public class MemberService {

	@Autowired
	MemberDao memberDao;
	

	/**
	 * 작성자 : 최문정
	 * 자동완성 기능
	 * @param memPn
	 * @return
	 */
	public void getMemInfoByPn(String memPn, HttpServletResponse response){
		
		List<Member> list = memberDao.getMemInfoByPn(memPn);
		
		//출력 List 결과를 JSON에 입력
		JSONArray jarr = new JSONArray();
		try{
			for(int i=0 ; i < list.size() ; i++) {
				JSONObject sOjbt = new JSONObject();
				
				//JSON에 입력
				sOjbt.put("pn", list.get(i).getMemPn());
				sOjbt.put("nm", list.get(i).getMemName());
				sOjbt.put("em", list.get(i).getMemEm());

				jarr.put(sOjbt);
			}
		}catch (JSONException e) {
			e.printStackTrace();
			System.out.println("JSON변환에러");
		}
		

		//PrintWriter로 출력
		try {
			PrintWriter result = response.getWriter();
			result.print(jarr);
			result.flush();
			result.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("PrintWriter 에러");
		}

		
	}
	
	
	/**
	 * 작성자 : 박세연
	 * 회원 등록하기
	 * @param reservation
	 */
	public void registMember(Reservation reservation){
		
		Member member =  new Member();
		Date date = new Date();
		
		member.setMemName(reservation.getRsvMemNm());
		member.setMemPn(reservation.getRsvMemPn());
		member.setMemEm(reservation.getRsvMemEm());
		member.setMemComp(ConstantCode.COMPANY_NAME);
		member.setMemRegDate(date);
		member.setMemState("정상");
		member.setCountWarn(ConstantCode.ZERO);
		member.setMemBanday("N");
		
		memberDao.registMember(member);
		
	}
	
	/**
	 * 작성자 : 박세연
	 * 등록된 회원인지 확인
	 * @param memPn
	 * @return
	 */
	public int checkExistMemInfo(String memPn){
		
		int exist = memberDao.checkExistMemInfo(memPn);
		
		return exist;
	}
	
	/**
	 * 작성자 : 박세연
	 * 회원정보 수정하기
	 * @param reservation
	 */
	public void modifyMember(Reservation reservation){
	
		Member member =  new Member();
		
		member.setMemName(reservation.getRsvMemNm());
		member.setMemPn(reservation.getRsvMemPn());
		member.setMemEm(reservation.getRsvMemEm());
		
		memberDao.modifyMember(member);
	}
	
	/**
	 * 작성자 : 박세연
	 * 관리자로 로그인시, tooltip에 회원상태(정상/차단)과 noshow 카운트 수 추가하여 보여주기
	 * @param memPn
	 * @return
	 */
	public List<Member> memInfoByToolTip(String memPn){
		
		List<Member> list = memberDao.memInfoByToolTip(memPn);
		
		return list;
	}
}
