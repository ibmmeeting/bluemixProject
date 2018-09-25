package com.ibmMeeting.excel.util;


import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;


/**
 * 작성자 : 최문정
 * 내용 : 예약기록(History Table)과 예약내역(ReservationTable) 엑셀 파일로 저장
 * @author ADMINIBM
 *
 */
@Component("excelXlsxView")
public class ExcelXlsxView extends AbstractXlsxView {
	private Workbook workbook;
	private Map<String, Object> map;
	private HttpServletResponse response;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> map, 
												Workbook workbook,
												HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		this.workbook = workbook;
		this.map = map;
		this.response = response;
		
		
		String deleteDate = map.get("dDate").toString();
		
		
		ArrayList<HashMap<String, Object>> historyResult = (ArrayList<HashMap<String, Object>>) map.get("historyTable");
		ArrayList<HashMap<String, Object>> ReservationResult = (ArrayList<HashMap<String, Object>>) map.get("reservTable");
		
		
		//예약기록(History Table) Sheet 생성
		Sheet sheet = workbook.createSheet("예약기록");
		Row row = null;
		int rowCount = 0;
		int cellCount = 0;
		
		sheet.setColumnWidth(1, 256*20);
		sheet.setColumnWidth(2, 256*20);
		sheet.setColumnWidth(3, 256*12);
		sheet.setColumnWidth(4, 256*12);
		sheet.setColumnWidth(5, 256*12);
		sheet.setColumnWidth(6, 256*20);
		sheet.setColumnWidth(7, 256*12);
		sheet.setColumnWidth(8, 256*20);
		sheet.setColumnWidth(9, 256*20);
		sheet.setColumnWidth(10, 256*15);
		sheet.setColumnWidth(11, 256*20);
		sheet.setColumnWidth(12, 256*15);
		sheet.setColumnWidth(13, 256*15);
		sheet.setColumnWidth(14, 256*15);
		sheet.setColumnWidth(15, 256*15);
		
		// 제목 Cell 생성
		row = sheet.createRow(rowCount++);

		row.createCell(cellCount++).setCellValue("HistoryNo");
		row.createCell(cellCount++).setCellValue("회의제목");
		row.createCell(cellCount++).setCellValue("회의날짜");
		row.createCell(cellCount++).setCellValue("회의시작시간");
		row.createCell(cellCount++).setCellValue("회의종료시간");
		row.createCell(cellCount++).setCellValue("총회의시간");
		row.createCell(cellCount++).setCellValue("회의실");
		row.createCell(cellCount++).setCellValue("예약자 이름");
		row.createCell(cellCount++).setCellValue("예약자 핸드폰 번호");
		row.createCell(cellCount++).setCellValue("예약자 이메일");
		row.createCell(cellCount++).setCellValue("예약 비밀번호");
		row.createCell(cellCount++).setCellValue("회의 등록/수정일");
		row.createCell(cellCount++).setCellValue("예약상태");
		row.createCell(cellCount++).setCellValue("반복예약기간");
		row.createCell(cellCount++).setCellValue("반복주기설정");
		row.createCell(cellCount++).setCellValue("반복Seq");
		
		// 데이터 Cell 생성
		for (int i = 0 ; i < historyResult.size() ; i++) {
			row = sheet.createRow(rowCount++);
			cellCount = 0;
			
			//cell에 데이터 입력
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_NO").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_RSV_TITLE").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_DATE").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_START_TIME").toString().substring(0, 5));
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_END_TIME").toString().substring(0, 5));
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_TOTAL_TIME").toString().substring(0, 5));
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("CONF_NM").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_RSV_MEM_NM").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_RSV_MEM_PN").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_RSV_MEM_EM").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_DEL_PWD").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_REG_DATE").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_RSV_STATE").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_REPEAT_PERIOD").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_SETTING").toString());
			row.createCell(cellCount++).setCellValue(historyResult.get(i).get("HST_REPEAT_NO").toString());

		}
		
		
			//예약기록(History Table) Sheet 생성
			Sheet sheet2 = workbook.createSheet("예약내역");
			Row row2 = null;
			int rowCount2 = 0;
			int cellCount2 = 0;
			
			sheet2.setColumnWidth(1, 256*30);
			sheet2.setColumnWidth(2, 256*20);
			sheet2.setColumnWidth(3, 256*12);
			sheet2.setColumnWidth(4, 256*12);
			sheet2.setColumnWidth(5, 256*12);
			sheet2.setColumnWidth(6, 256*20);
			sheet2.setColumnWidth(7, 256*12);
			sheet2.setColumnWidth(8, 256*20);
			sheet2.setColumnWidth(9, 256*20);
			sheet2.setColumnWidth(10, 256*15);
			sheet2.setColumnWidth(11, 256*15);
			sheet2.setColumnWidth(12, 256*15);
			sheet2.setColumnWidth(13, 256*15);
			sheet2.setColumnWidth(14, 256*15);
			sheet2.setColumnWidth(15, 256*15);
			sheet2.setColumnWidth(16, 256*15);
	
			
			// 제목 Cell 생성
			row2 = sheet2.createRow(rowCount2++);

			row2.createCell(cellCount2++).setCellValue("ReservNo");
			row2.createCell(cellCount2++).setCellValue("회의제목");
			row2.createCell(cellCount2++).setCellValue("회의날짜");
			row2.createCell(cellCount2++).setCellValue("회의시작시간");
			row2.createCell(cellCount2++).setCellValue("회의종료시간");
			row2.createCell(cellCount2++).setCellValue("총회의시간");
			row2.createCell(cellCount2++).setCellValue("회의실");
			row2.createCell(cellCount2++).setCellValue("예약자 이름");
			row2.createCell(cellCount2++).setCellValue("예약자 핸드폰 번호");
			row2.createCell(cellCount2++).setCellValue("예약자 이메일");
			row2.createCell(cellCount2++).setCellValue("예약 비밀번호");
			row2.createCell(cellCount2++).setCellValue("회의 등록일");
			row2.createCell(cellCount2++).setCellValue("예약승인여부");
			row2.createCell(cellCount2++).setCellValue("이메일 발송 여부");
			row2.createCell(cellCount2++).setCellValue("반복예약기간");
			row2.createCell(cellCount2++).setCellValue("반복주기설정");
			row2.createCell(cellCount2++).setCellValue("반복Seq");
			
			// 데이터 Cell 생성
			for (int i = 0 ; i < ReservationResult.size() ; i++) {
				row2 = sheet2.createRow(rowCount2++);
				cellCount2 = 0;
				
				//cell에 데이터 입력
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_NO").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_TITLE").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_DATE").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_START_TIME").toString().substring(0, 5));
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_END_TIME").toString().substring(0, 5));
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_TOTAL_TIME").toString().substring(0, 5));
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("CONF_NM").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_MEM_NM").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_MEM_PN").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_MEM_EM").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_DEL_PWD").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_REG_DATE").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_CONFIRM_STATE").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_EMAIL_CHECK").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_REPEAT_PERIOD").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_SETTING").toString());
				row2.createCell(cellCount2++).setCellValue(ReservationResult.get(i).get("RSV_REPEAT_NO").toString());

			}
		
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyMMdd", Locale.KOREA);
		Date date = new Date();
		String storeDate = dateformat.format(date);
		
		response.setContentType("Application/Msexcel");
		response.setHeader("Content-Disposition", "attachment; filename=abcd-excel.xls");

		String filePathAndName = "C:/("+storeDate+")"+deleteDate+"이전기록.xlsx";
		
		FileOutputStream fileout = new FileOutputStream(filePathAndName);
		workbook.write(fileout);

	}

}
