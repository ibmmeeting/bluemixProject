<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script src="https://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>
	
<link rel="stylesheet" href="https://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-1.9.1.js"  type="text/javascript"></script>
<script src="https://code.jquery.com/ui/1.10.3/jquery-ui.js" type="text/javascript"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>  

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/bootstrap/js/bootbox.min.js"></script>  

<link rel="stylesheet" type="text/css"	href="/resources/bootstrap/bootswatch.css" />
<link rel="stylesheet/less" type="text/css"	href="/resources/bootstrap/bootswatch.less" />
<link rel="stylesheet/less" type="text/css" href="/resources/bootstrap/variables.less" />
<link href='/resources/fullcalander/fullcalendar.css' rel='stylesheet' />
<link href='/resources/fullcalander/fullcalendar.print.min.css' rel='stylesheet' media='print' />
<link href='/resources/scheduler/scheduler.min.css' rel='stylesheet' />
<script type="text/javascript" src="/resources/fullcalander/moment.min.js"></script>
<script type="text/javascript" src="/resources/fullcalander/fullcalendar.min.js"></script>
<script type="text/javascript" src="/resources/scheduler/scheduler.min.js"></script>
<script type="text/javascript" src="/resources/fullcalander/locale-all.js"></script>
<script type="text/javascript" src="/resources/fullcalander/gcal.js"></script>
<link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

<script type="text/javascript" src="/resources/js/headerLocation.js"></script>  

<link href="https://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" rel="Stylesheet"></link> 
	<script type="text/javascript" src="/resources/EasyAutocomplete-1.3.5/jquery.easy-autocomplete.js"></script>
<script type="text/javascript" src="/resources/EasyAutocomplete-1.3.5/jquery.easy-autocomplete.min.js"></script>
<link rel="stylesheet" type="text/css"	href="/resources/EasyAutocomplete-1.3.5/easy-autocomplete.css" />
<link rel="stylesheet" type="text/css"	href="/resources/EasyAutocomplete-1.3.5/easy-autocomplete.min.css" />
<link rel="stylesheet" type="text/css"	href="/resources/EasyAutocomplete-1.3.5/easy-autocomplete.themes.css" />
<link rel="stylesheet" type="text/css"	href="/resources/EasyAutocomplete-1.3.5/easy-autocomplete.themes.min.css" />
<script type="text/javascript" src="/resources/loadingBar/ajaxLoading.js"></script>   
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">

<style>

 .YongSanText { font-weight:bold; margin-right:auto; font-size:30px; color:#080c42}
 .pickDate {text-align:center; width:165px; cursor:pointer; border:none; font-size:28px}
 .weekOfDay { font-size:22px}
 
 
@media screen and (max-width: 500px) {
      .pickDate {text-align:center; width:130px; cursor:pointer; border:none; font-size:20px; padding-top:0px}
       .yongSanDiv { display:none}
       .pickDiv {padding-top:0px; margin-bottom:-15px}
        .weekOfDay { font-size:18px}
}
 

</style>

<html>
<head>
<title>IBM 회의실 예약시스템</title>
</head>
	<body id="htmlBody">
		<jsp:include page="../headerAndFooter/header.jsp"></jsp:include>		

		<div class="container">
		
			<div class="col-lg-12" style="background-color:white;">

				<div class="col-lg-5 yongSanDiv" style="padding-top:20px">
					<span class="YongSanText"> IBM YongSan Office </span>
				</div>
				
				<div class="col-lg-7 pickDiv" style="padding-top:20px">
					<table id="pickDate" style="text-align:right; margin-left:auto">
					<tr><td style="border:none" >
						<i class="fa fa-chevron-left" id="prev" style="cursor:pointer; padding: 0px 7px 0px 0px"></i>
					</td><td style="border:none">
						<span class="fa-stack fa-1x">
				    		<i class="fa fa-calendar-o fa-stack-2x"></i>
				    		<strong class="fa-stack-1x calendar-text" style="font-size:12px; cursor:pointer" id="todayDate"></strong>
				 	  	</span>
						<input type="text" id="date" name="date" maxlength=45 class="pickDate" >
						<span id="day" class="weekOfDay"></span>
					</td>
					<td style="border:none">
					<i class="fa fa-chevron-right" id="next" style="cursor:pointer;  padding: 0px 0px 0px 7px"></i>
					</td></tr>
					</table>
				</div>
				
			</div>	
			<div id="calendar"></div><br>
			
			<form action="/Reservation/RegistReservation" method="post" id="submitForm">
				
					<div class="jumbotron">
				<div class="col-lg-12">
					<div class="form-group col-lg-3">
					  <label class="control-label">날짜</label>
					  <div>
					  <div class="input-group">
					    <span class="input-group-addon input-sm"><i class="fa fa-calendar-check-o"></i></span>
					    <input class="form-control input-sm" type="text" id="rsvDate" name="rsvDate" maxlength=45>
					  </div>
					  <span class="error"><form:errors path="reservation.rsvDate" delimiter="<br>"/></span>
					  <div id="dateChkMsg" style="display:none"></div>
					  </div>
					</div>
					
					<div class="form-group col-lg-3">
					  <label class="control-label">시작 시간</label>
					  <div class="input-group">
					    <span class="input-group-addon input-sm"><i class="fa fa-clock-o"> </i></span> 
 		 		 		<select id="rsvStartTime" name="rsvStartTime" class="form-control input-sm">
							<option value="09:00:00">오전 09:00</option>
							<option value="09:30:00">오전 09:30</option>
							<option value="10:00:00">오전 10:00</option>
							<option value="10:30:00">오전 10:30</option>
							<option value="11:00:00">오전 11:00</option>
							<option value="11:30:00">오전 11:30</option>
							<option value="12:00:00">오후 12:00</option>
							<option value="12:30:00">오후 12:30</option>
							<option value="13:00:00">오후 01:00</option>
							<option value="13:30:00">오후 01:30</option>
							<option value="14:00:00">오후 02:00</option>
							<option value="14:30:00">오후 02:30</option>
							<option value="15:00:00">오후 03:00</option>
							<option value="15:30:00">오후 03:30</option>
							<option value="16:00:00">오후 04:00</option>
							<option value="16:30:00">오후 04:30</option>
							<option value="17:00:00">오후 05:00</option>
							<option value="17:30:00">오후 05:30</option>
						</select>   
					  </div>
					</div>
					
					<div class="form-group col-lg-3">
					  <label class="control-label">종료 시간</label>
					  <div class="input-group">
					    <span class="input-group-addon input-sm"><i class="fa fa-clock-o"> </i></span>
					    <select id="rsvEndTime" name="rsvEndTime" class="form-control input-sm">
							<option value="09:30:00">오전 09:30</option>
							<option value="10:00:00">오전 10:00</option>
							<option value="10:30:00">오전 10:30</option>
							<option value="11:00:00">오전 11:00</option>
							<option value="11:30:00">오전 11:30</option>
							<option value="12:00:00">오후 12:00</option>
							<option value="12:30:00">오후 12:30</option>
							<option value="13:00:00">오후 01:00</option>
							<option value="13:30:00">오후 01:30</option>
							<option value="14:00:00">오후 02:00</option>
							<option value="14:30:00">오후 02:30</option>
							<option value="15:00:00">오후 03:00</option>
							<option value="15:30:00">오후 03:30</option>
							<option value="16:00:00">오후 04:00</option>
							<option value="16:30:00">오후 04:30</option>
							<option value="17:00:00">오후 05:00</option>
							<option value="17:30:00">오후 05:30</option>
							<option value="18:00:00">오후 06:00</option>
						</select> 
					  </div>
					</div>
					
					<div class="form-group col-lg-3">
					  <label class="control-label">색상</label>
					  <div class="input-group">
					    <span class="input-group-addon input-sm"><i class="fa fa-eye"></i></span>
					    <select id="rsvColor" name="rsvColor" class="form-control input-sm">
					    	<option value="red">빨강</option>
					    	<option value="orange">주황</option>
					    	<option value="green">초록</option>
					    	<option value="blue" selected>파랑</option>
					    	<option value="navy">남색</option>
					    	<option value="skyblue">하늘</option>
					    </select>
					  </div>
					</div>
				</div>
			
				<div class="col-lg-12">
					<div class="form-group col-lg-4">
					  <label class="control-label">회의실</label>
					  <div class="input-group">
					    <span class="input-group-addon input-sm"><i class="fa fa-laptop"></i></span>
					 	<select id="rsvConfNo" name="rsvConfNo" class="form-control input-sm">
					    </select>
					  </div>
					  <div id="confChkMsg" style="display:none"></div>
					</div>
					
					<div class="form-group col-lg-4">
					  <label class="control-label">회의 제목</label>
					  <div>
					  <div class="input-group">
					    <span class="input-group-addon input-sm"><i class="fa fa-comments"></i></span>
					    <input class="form-control input-sm" type="text" id="rsvTitle" name="rsvTitle" maxlength=45 value="${param.rsvTitle }" placeholder="회의제목을 입력하세요" onKeyPress="if(event.keyCode == 13) return false;">
					  </div>
					  <span class="error" id="errorTitle"><form:errors path="reservation.rsvTitle" delimiter="<br>"/></span>
					  <div id="confTitleChkMsg" style="display:none"></div>
					  </div>
					</div>
					
					<div class="form-group col-lg-4">
					  <label class="control-label">비밀번호</label>
					  <div>
					  <div class="input-group">
					    <span class="input-group-addon input-sm"><i class="fa fa-key"></i></span>
					    <input style="font-family:arial" class="form-control input-sm" type="password" id="rsvDelPwd" name="rsvDelPwd" maxlength=45 value="${param.rsvDelPwd }" placeholder="비밀번호를 입력하세요" onkeypress="caps_lock(event); if(event.keyCode == 13) return false;">
					  </div>
						 <span class="error" id="errorPwd"><form:errors path="reservation.rsvDelPwd" delimiter="<br>"/></span>
						<div id="pwdChkMsg" style="display:none"></div>
						</div>
					</div>
				</div>
				
				<div class="col-lg-12">
					<div class="form-group col-lg-4">
					  <label class="control-label">전화번호</label>
					  <div>
					  <div class="input-group">
					    <span class="input-group-addon input-sm"><i class="fa fa-phone"></i></span>
					    <input class="form-control input-sm" type="text" id="rsvMemPn" name="rsvMemPn" maxlength=13 value="${param.rsvMemPn }" placeholder="전화번호를 입력하세요" onkeyup="inputDash()" onKeyPress="if(event.keyCode == 13) return false;">
					  </div>
					  <span class="error" id="errorPn"><form:errors path="reservation.rsvMemPn" delimiter="<br>"/></span>
					  <div id="telChkMsg" style="display:none"></div>
					  </div>
					</div>
					
					<div class="form-group col-lg-4">
					  <label class="control-label">이름</label>
					  <div>
					  <div class="input-group">
					    <span class="input-group-addon input-sm"><i class="fa fa-user-plus"></i></span>
					    <input class="form-control input-sm" type="text" id="rsvMemNm" name="rsvMemNm" maxlength=45 value="${param.rsvMemNm }" placeholder="이름을 입력하세요" onKeyPress="if(event.keyCode == 13) return false;">
					  </div>
					  <span class="error" id="errorName"><form:errors path="reservation.rsvMemNm" delimiter="<br>"/></span>
					  <div id="memNmChkMsg" style="display:none"></div>
					  </div>
					</div>
					
					<div class="form-group col-lg-4">
						<div style="text-align:left; display:inline"><label class="control-label">이메일</label></div>
					    <div style=" text-align:right; display:inline">
					    	<label><span class="control-label" style="width:250px; color:#8d9193; font-size:12px; text-align:right">　　30분 전 Reminder 메일수신</span>
					 	    <input type="checkbox" id="rsvEmailCheck" name="rsvEmailCheck" checked>
					   		<input type="hidden" id="emailCheckValue" name="emailCheckValue">
					  		</label>
					    </div>
					  <div>
					  <div class="input-group">
					    <span class="input-group-addon input-sm"><i class="fa fa-envelope"></i></span>
					    <input class="form-control input-sm" type="text" id="rsvMemEm" name="rsvMemEm" maxlength=45 value="${param.rsvMemEm }" placeholder="이메일을 입력하세요" onKeyPress="if(event.keyCode == 13) return false;">
					  </div>
					   <span class="error" id="errorEmail"><form:errors path="reservation.rsvMemEm" delimiter="<br>"/></span>
					  <div id="memEmChkMsg" style="display:none"></div>
					  </div>
					</div>
				</div>
				
				<div class="col-lg-12">
					<div class="form-group col-lg-11" style="text-align:right; margin-top:10px">
					  
					</div>
					<div id="submit" class="form-group col-lg-1" style=text-align:right>	
					<button type="submit" class="btn btn-primary" style="float:right;" onclick="return checkForm()">등록</button>
					</div>
				</div>
					
 			 
				<div id="modify" style="display:none;">
					<button type="button" id="deleteBtn" class="btn btn-primary" style="float:right; margin-right:30px; margin-left:0px; margin-top:-20px">삭제</button>
           		    <button type="button" id="modifyBtn" class="btn btn-primary" style="float:right; margin-right:30px; margin-left:0px; margin-top:-20px">수정</button>
				</div>
						
 				<input type="hidden" id="rsvTotalTime" name="rsvTotalTime"> 
				<input type="hidden" id="rsvConfirmState" name="rsvConfirmState" value="Y">
		 		<input type="hidden" id="rsvNo" name="rsvNo" value="0">

				<input type="hidden" id="monopolyCount" name="monopolyCount">
				&nbsp;
					</div>
			</form>
			</div>
		
		<input type="hidden" id="fromSearch" name="fromSearch" value="${requestScope.fromSearch }">
		<input type="hidden" id="rsvDateFromSearch" name="rsvDateFromSearch" value="${requestScope.rsvDateFromSearch}">  

		<input type="hidden" id="registRsvDate" name="registRsvDate" value="${requestScope.registRsvDate}">  

		<div id="eventContent" title="Event Details" >
		<br>
		    <div id="eventInfo"></div><br>
		    <div id="eventLink" style="display:none;"><small><i><ins style="cursor:pointer">이메일로 비밀번호 전송받기</ins></i></small><div id="detailInfo" style='display:none;'></div></div>
		</div>
		

		<jsp:include page="../headerAndFooter/footer.jsp"></jsp:include>
		
		 
	</body>
</html>

<script>

//전화번호 입력시 자동 (-) 입력
function inputDash(){
 	var pn = $("#rsvMemPn").val();
	pn = pn.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3"); 
	return pn;
}

//패스워드 입력시 capsLock 설정 확인
function caps_lock(e) {
    var keyCode = 0;
    var shiftKey = false;
    keyCode = e.keyCode;
    shiftKey = e.shiftKey;
    if (((keyCode >= 65 && keyCode <= 90) && !shiftKey) || ((keyCode >= 97 && keyCode <= 122) && shiftKey)) {
    	$("#pwdChkMsg").empty();
    	$("#pwdChkMsg").append("CapsLock이 켜져있습니다.");
        $("#pwdChkMsg").show();
    } else {
    	$("#pwdChkMsg").empty();
    }
}

function emptyErrorMsg(){
	
	//error 메세지 입력되어있다면 지우기
	$("#errorEmail").empty();
	$("#errorTitle").empty();
	$("#errorPwd").empty();
	$("#errorPn").empty();
	$("#errorName").empty();
	
	$("#dateChkMsg").empty();
	$("#confChkMsg").empty();
	$("#confTitleChkMsg").empty();
	$("#pwdChkMsg").empty();
	$("#telChkMsg").empty();
	$("#memNmChkMsg").empty();
	$("#memEmChkMsg").empty();
	
}

//예약하기위한 slot 선택시 발생 이벤트
function regRsv(date, resourceObj){

	$("#rsvNo").val(0);
	$("#submit").show();
	$("#modify").hide();
	
	//이미 입력되어있는 textbox내의 값들 지우기
	$("#rsvTitle").removeAttr("value");
	$("#rsvDelPwd").removeAttr("value");
	$("#rsvMemPn").removeAttr("value");
	$("#rsvMemNm").removeAttr("value");
	$("#rsvMemEm").removeAttr("value");
	$("#rsvEmailCheck").prop('checked' , true); //default값으로 변경
	$("#rsvColor option[value='blue']").attr("selected", true);
	
	emptyErrorMsg();
	
	var startTime = moment(date).format('kk:mm:ss');

	var end = moment(date).add(1, 'hours');
	var endTime = moment(end).format('kk:mm:ss');

	$("#rsvStartTime option[value='"+startTime +"']").attr("selected", true);  //시작시간 자동 select
	$("#rsvEndTime option[value='"+endTime +"']").attr("selected", true);  //종료시간 자동 select
	
	$("#rsvConfNo option[value='"+resourceObj.id +"']").attr("selected", true); //회의실 자동 select
	$("#rsvTitle").focus();
	
	//해당 slot해당 날짜로 바뀜
	$("#rsvDate").removeAttr("value");
	$("#rsvDate").val(date.year()+"-"+(date.month()+1)+"-"+date.date());

	//이미 예약된 예약시간을 체크해 새로 등록할 예약의 시작/종료시간 불가능한 시간 disabled 시키기
	setTimeDisabled(resourceObj.id);

}

//예약 최대 시간 설정값 
function getMaxTime(){
	//예약 max time 설정값 가져오기
	var maxTime;
	$.ajax({
		url:"/Reservation/GetMaxTime",
		type:"post",
		dataType:"text",
		async: false,
		success:function(max){	
			maxTime = max;
		},
		error:function(request,status,error){
			alert("Get Max Time Error");
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	return maxTime;
}



//총 소요시간 계산
function calTotalTime(){
	
	var maxTime = getMaxTime();
 	var end = $("#rsvEndTime").val().substr(0, 2)+""+$("#rsvEndTime").val().substr(3, 2)+""+$("#rsvEndTime").val().substr(6, 2);
 	var start = $("#rsvStartTime").val().substr(0, 2)+""+$("#rsvStartTime").val().substr(3, 2)+""+$("#rsvStartTime").val().substr(6, 2);
 	var duration = (parseInt(end)-parseInt(start)).toString();
 	var min = "00";
	var overTime = "NULL";
	if(duration.length == 4){
		if(duration.substr(0,2) != "00"){
 	 		min = "30"
 	 	}
		duration = "0:"+min+":00";
		
	}else if(duration.length == 5){
 		if(duration.substr(1,2) != "00"){
 	 		min = "30"
 	 	}
	 	duration = duration.substr(0,1)+":"+min+":00";
	 	
	 	if(duration.substr(0,1) >= maxTime){
			overTime = "T";
		}
		
 	}else if(duration.length == 6){
 		if(duration.substr(2,2) != "00"){
 	 		min = "30"
 	 	}
	 	duration = duration.substr(0,2)+":"+min+":00";
	 	
	 	if(duration.substr(0,2) >= maxTime){
			overTime = "T";
		}
	 	
 	}else{
 		duration = "00:"+duration.substr(0,2)+":00";
 	}
	$("#rsvTotalTime").val(duration);
	
	return overTime;
}

function emailcheck(email){
	
	$("#memEmChkMsg").empty();
	var regExp = /[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/;
	
	//입력을 안했으면
	if(email.lenght == 0){
		$("#memEmChkMsg").empty();
		$("#memEmChkMsg").append("이메일을 입력하세요.");
		$("#memEmChkMsg").show();
		return false;
	}
	//이메일 형식에 맞지않으면
	if (!email.match(regExp)){
		$("#memEmChkMsg").empty();
		$("#memEmChkMsg").append("올바르지 않은 이메일 형식입니다.");
		$("#memEmChkMsg").show();
		return false;
	}
	
	return true;
}

function emailStateCheck(){
	
	//checkbox의 checked 상태 확인
	if($('input:checkbox[id="rsvEmailCheck"]').is(":checked") == true){
		$("#emailCheckValue").val("Y");
	}else{
		$("#emailCheckValue").val("N");
	}
}

function chkRsvedTime(){
	
	var res = "able";
	$.ajax({
		url:"/Reservation/ChkRsvedTime",
		type:"post",
		dataType:"text",
		data:{
			"rsvNo":$("#rsvNo").val(),
			"rsvDate":$("#rsvDate").val(),
		 	"rsvStartTime":$("#rsvStartTime").val(),
			"rsvEndTime":$("#rsvEndTime").val(), 
	 		"rsvConfNo":$("#rsvConfNo").val() 
		},
		async: false,
		success:function(val){	
			if(val == 'disable'){
				res = "dis";
			}
		},
		error:function(request,status,error){
			alert("Check Reserved Time Error");
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	
	return res;
}

var submitted = false;
function doubleSubmitCheck(){
    if(submitted){
        return submitted;
    }else{
       submitted = true;
        return false;
    }
}


var submitted = false;
function doubleSubmitCheck(){
    if(submitted){
        return submitted;
    }else{
       submitted = true;
        return false;
    }
}


//등록/수정 버튼 클릭시 실행
function checkForm(){
   if(!doubleSubmitCheck()){
   
   var banState = memberBanCheck();
   if(banState == "T"){
      alert("차단된 회원이므로 예약 등록 및 수정을 진행하실 수 없습니다.");
      $('#calendar').fullCalendar('refetchEvents');
      return false;
   }
   
   //관리자로 로그인했는지 체크 , 로그인X : null
   var loginChk = <%= session.getAttribute("id") %>;
      if(loginChk == null){ //관리자 로그인되지 않았을 때
         if($("#rsvNo").val() != null){
            var rsvConfirmStateVal = "Y";
         rsvConfirmStateVal = getRsvConfirmStateVal($("#rsvNo").val());
          if(rsvConfirmStateVal == "ACCEPT"){
             alert("가예약 승인받은 예약은 관리자만 수정가능합니다.");
             $('#calendar').fullCalendar('refetchEvents');
             return false;
          }
         }
      }

      //가예약 상태이면 수정 불가
   var rsvConfirmStateVal = "Y";
    rsvConfirmStateVal = getRsvConfirmStateVal($("#rsvNo").val());
      
   if(rsvConfirmStateVal == "N"){
      alert("가예약 승인 대기 중인 예약은 수정할 수 없습니다.");
      $('#calendar').fullCalendar('refetchEvents');
      return false;
   }
      
   //시작시간 < 종료시간
   var startIndex = $("#rsvStartTime option").index($("#rsvStartTime option:selected"));
   var endIndex = $("#rsvEndTime option").index($("#rsvEndTime option:selected"));
   if(startIndex-1 >= endIndex){
      alert("유효하지 않는 시간입니다.");
      $("#rsvStartTime").focus();
      return false;
   }
   
   //이미 예약된 시간선택 후 등록/수정 진행시, return false
   var dis = chkRsvedTime()
   if(dis == "dis"){
      alert("선택한 시간대에 이미 예약이 되어있어 예약을 진행할 수 없습니다.");
      return false;
   }
   
    var overTime = calTotalTime();
    var maxTime = getMaxTime();
    
   var submit = "Y";
   if(overTime == 'T'){
      if(!confirm(maxTime+"시간 이상 예약은 관리자 승인이 필요합니다. 진행하시겠습니까?")){
         submit = "N";
         $('#calendar').fullCalendar('refetchEvents');
         return false;
      }else{
         $("#rsvConfirmState").val("N");
      }
   }
 
   preventMonopoly($("#rsvNo").val());
   var count = $("#monopolyCount").val();

   //이 값이 "T"이면 가예약상태
   if(count == "T"){
      if(!confirm("이번주에 "+maxTime*3+"시간 이상 예약을 진행 중이셔서 가예약됩니다. 진행하시겠습니까?")){
         submit = "N";
         $('#calendar').fullCalendar('refetchEvents');
         return false;
      }else{
         $("#rsvConfirmState").val("N");
      }
   } 
   
   if(submit == "N"){
      return false;
   }
    
   emailStateCheck();
   
   //validation
   //전화번호 -> 숫자이외의 값이 적혀있을 때, 에러 출력
   var tel = $("#rsvMemPn").val();
   if(tel.length>13 || tel.length < 12){
      alert("전화번호를 다시 확인 후 입력하여 주세요.");
      $("#rsvMemPn").focus();
      return false;
   }
   
   //이메일
   var bool = emailcheck($("#rsvMemEm").val());
    if(bool == false){
       alert("이메일 형식에 맞춰 값을 적어주세요.");
       $("#rsvMemEm").focus();
       return false;
    }
   }else{
      return false;
   }
   
}

//textbox에 값 입력되면 validation error 메세지 지우기
function chkData(){
	$("#rsvMemEm").blur(function(){
		if($("#rsvMemEm").val().length != 0){
			$("#errorEmail").empty();
		}
	 });	
	 
	$("#rsvTitle").blur(function(){
		if($("#rsvTitle").val().length != 0){
			$("#errorTitle").empty();
		}
	});	
	 
	$("#rsvDelPwd").blur(function(){
		if($("#rsvDelPwd").val().length != 0){
			$("#errorPwd").empty();
		}
	});	
	 
	$("#rsvMemPn").blur(function(){
		if($("#rsvMemPn").val().length != 0){
			$("#errorPn").empty();
		}
	});
	
	$("#rsvMemNm").blur(function(){
		if($("#rsvMemNm").val().length != 0){
			$("#errorName").empty();
		}
	});
}

//등록된 회의실 목록 가져와 select option에 뿌리기
function conf(){
	
	$.ajax({
		url:"/MeetingRoom/GetResources",
		type:"post",
		dataType:"JSON",
		success:function(list){		
			var conf = new Array();
			$.each(list, function(i){
								conf[i] = {"no":this.confNo,
											"name":this.confNm		
											};
					});
			
			for(var i=0; i<conf.length; i++){
				$("#rsvConfNo").append("<option value='"+conf[i].no+"'>"+conf[i].name+"</option>");
			}
			
		},
		error:function(request,status,error){
			$('#calendar').fullCalendar('today');
		}
	});

}


//예약내역 수정하기
function modify(){
	
	var isFalse = checkForm();
	if(isFalse == false){
		return false;
	}
	//validation
	
	
	//수정 작업
	$.ajax({
			url:"/Reservation/ModifyReservation",
			type:"post",
			dataType:"text",
			data:{
				"rsvNo":$("#rsvNo").val(),
				"rsvDate":$("#rsvDate").val(),
				"rsvStartTime":$("#rsvStartTime").val(),
				"rsvEndTime":$("#rsvEndTime").val(),
				"rsvColor":$("#rsvColor").val(),
				"rsvConfNo":$("#rsvConfNo").val(),
				"rsvTitle":$("#rsvTitle").val(),
				"rsvMemPn":$("#rsvMemPn").val(),
				"rsvMemNm":$("#rsvMemNm").val(),
				"rsvMemEm":$("#rsvMemEm").val(),
				"rsvDelPwd":$("#rsvDelPwd").val(),
				"rsvTotalTime":$("#rsvTotalTime").val(),
				"rsvConfirmState":$("#rsvConfirmState").val(),
				
				"emailCheckValue":$("#emailCheckValue").val()
			},
			success:function(success){		
				alert("수정이 완료되었습니다.");
				$('#calendar').fullCalendar('refetchEvents');
			},
			error:function(request,status,error){
				alert("Modify Reservation Error");
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
}

//예약내역 삭제하기
function deleteRsv(){
	
	calTotalTime();
	
	$.ajax({
		url:"/Reservation/DeleteReservation",
		type:"post",
		dataType:"text",
		data:{
			"rsvNo":$("#rsvNo").val(),
			"rsvDate":$("#rsvDate").val(),
			"rsvStartTime":$("#rsvStartTime").val(),
			"rsvEndTime":$("#rsvEndTime").val(),
			"rsvConfNo":$("#rsvConfNo").val(),
			"rsvTitle":$("#rsvTitle").val(),
			"rsvMemPn":$("#rsvMemPn").val(),
			"rsvMemNm":$("#rsvMemNm").val(),
			"rsvMemEm":$("#rsvMemEm").val(),
			"rsvDelPwd":$("#rsvDelPwd").val(),
			"rsvTotalTime":$("#rsvTotalTime").val()
		},
		success:function(success){		
			alert("삭제되었습니다.");
			$('#calendar').fullCalendar('refetchEvents');
			//이미 입력되어있는 textbox내의 값들 지우기
			$("#rsvTitle").removeAttr("value");
			$("#rsvDelPwd").removeAttr("value");
			$("#rsvMemPn").removeAttr("value");
			$("#rsvMemNm").removeAttr("value");
			$("#rsvMemEm").removeAttr("value");
			$("#rsvEmailCheck").prop('checked' , true); //default값으로 변경
			$("#rsvColor option[value='blue']").attr("selected", true);
		},
		error:function(request,status,error){
			alert("Delete Reservation Error");
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	
}

//회의실 독점 방지 - 같은 이름+같은 제목 (+ 스페이스바로 구별되지 않게 (ex. '회의' = '회    의' ))
function preventMonopoly(rsvNo){
	$.ajax({
		url:"/Reservation/PreventMonopoly",
		type:"post",
		dataType:"text",
		async: false,
		data:{
			"rsvTitle":$("#rsvTitle").val(),
			"rsvMemPn":$("#rsvMemPn").val(),
			"rsvDate":$("#rsvDate").val(),
			"rsvTotalTime":$("#rsvTotalTime").val(),
			"rsvNo":rsvNo
		},
		success:function(count){	
			$("#monopolyCount").val(count);
		},
		error:function(request,status,error){
			alert("Prevent Monopoly Error");
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

//이미 예약된 시작&종료시간 select option hide 시키기
function setTimeDisabled(confNo){
	
	startEndDisabledFalse();
	
	$.ajax({
		url:"/Reservation/ControlStartTime",
		type:"post",
		dataType:"JSON",
		data:{
			"rsvDate":$("#rsvDate").val(),
			"rsvConfNo":confNo
		},
		async: false,
		success:function(list){
			
			var min = "24:00:00";
			
			$.each(list, function(j){
			
				if($("#rsvStartTime").val() < this.rsvStartTime){ //시작시간 선택시, 종료시간은 시작시간 이전 시간은 선택 불가 & 다음 예약의 시작시간 이후 시간 전부 disabled 시키기
					var val = this.rsvStartTime;
					if(min < val)
						min = min;
					else
						min = val;
				}
		
				for(var i=0; i<$("#rsvStartTime option").size(); i++){ //시작시간 disabled
					if(this.rsvStartTime <= $("#rsvStartTime option").eq(i).val() && $("#rsvStartTime option").eq(i).val() < this.rsvEndTime){
				//		$("#rsvStartTime option").eq(i).attr("disabled", true);
						$("#rsvStartTime option").eq(i).hide();
					}				
				}
				
				for(var i=0; i<$("#rsvEndTime option").size(); i++){ //종료시간 disabled
					if(min < $("#rsvEndTime option").eq(i).val() || $("#rsvStartTime").val() >= $("#rsvEndTime option").eq(i).val() || this.rsvStartTime < $("#rsvEndTime option").eq(i).val() && $("#rsvEndTime option").eq(i).val() <= this.rsvEndTime){
				//		$("#rsvEndTime option").eq(i).attr("disabled", true);
						$("#rsvEndTime option").eq(i).hide();
					}				
				}
				
			});
	
		},
		error:function(request,status,error){
			alert("Get Reserved Time Error");
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
} 

//드래그앤드롭을 통해 수정
function modifyByDragNDrop(){

	$.ajax({
		url:"/Reservation/ModifyRsvByDrop",
		type:"post",
		dataType:"text",
		data:{
			"rsvNo":event.id,
			"rsvConfNo":event.resourceId,
			"rsvStartTime":$.fullCalendar.formatDate(event.start, 'kk:mm:ss'),
			"rsvEndTime":$.fullCalendar.formatDate(event.end, 'kk:mm:ss')
		},
		success:function(result){
			alert("이동이 완료되었습니다.");
			location.href = "/";
		},
		error:function(request,status,error){
			alert("Modify Reservation By Drop Error");
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	 
 });  
	
}

//비밀번호 확인을 위한 function
function checkPwd(rsvNo){
	
	if($("#chkPwd").val() == ""){
		$("#chkPwdErrMsg").empty();
		$("#chkPwdErrMsg").append("비밀번호를 입력해주세요.");
		$("#chkPwdErrMsg").show();
		return false;
	}else{
		$("#chkPwdErrMsg").empty();
	}
	
	var success = "N";
	
	$.ajax({
			url:"/Reservation/GetPwd",
			type:"post",
			dataType:"text",
			data:{"rsvNo":rsvNo},
			async:false,
			success:function(pwd){		
				if($("#chkPwd").val() == pwd){
					success = "Y";
				}else{
					$("#chkPwdErrMsg").empty();
					$("#chkPwdErrMsg").append("일치하지 않는 비밀번호입니다.");
					$("#chkPwdErrMsg").show();
		 			return false;
				}
			},
			error:function(request,status,error){
				alert("Get Password Error");
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	
	return success;
}

//오늘로부터 최대 예약 가능 기간(설정값) 값 가져오기
function getRsvMaxMonth(){
	var date = new Date();
	var maxMonthAfter;
	
	$.ajax({
		url:"/Reservation/GetRsvMaxMonth",
		type:"post",
		dataType:"text",
		async:false,
		success:function(maxMonth){		
			//오늘로부터 maxMonth 이후
			var d = new Date(date.setMonth(date.getMonth()+parseInt(maxMonth)));
			
			var dateLength = (d.getDate()+1).toString().length;
			var monthLength = (d.getMonth()+1).toString().length;
			
			if(dateLength == 1){	
				if(monthLength == 1){
					maxMonthAfter= d.getFullYear()+"-0"+(d.getMonth()+1)+"-0"+(d.getDate()+1);
				}else if(monthLength == 2){
					maxMonthAfter= d.getFullYear()+"-"+(d.getMonth()+1)+"-0"+(d.getDate()+1);
				}
			}else if(dateLength == 2){
				if(monthLength == 1){
					maxMonthAfter= d.getFullYear()+"-0"+(d.getMonth()+1)+"-"+(d.getDate()+1);
				}else if(monthLength == 2){
					maxMonthAfter= d.getFullYear()+"-"+(d.getMonth()+1)+"-"+(d.getDate()+1);
				}
			}
		},
		error:function(request,status,error){
			alert("Get Reservation Max Month Error");
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	
	return maxMonthAfter;
}

//차단된 회원인지 체크
function memberBanCheck(){
	
	var memberBanState = "";
	
	$.ajax({
		url:"/AdminBoarding/MemberBanCheck",
		dataType : "text",
		type : "POST",
		data : { "memberPn" : $("#rsvMemPn").val() },
		async:false,
		success : function(data) {
			if(data=="NOSHOWBAN" || data=="차단"){
				memberBanState =  "T";
			}
			else{
				memberBanState = "F";
			}
		},
		error : function(request, status, error) {
			alert("code : "+request.status + "\n" + "error : " + error);
		}
	});
	
	return memberBanState;
}

//해당 예약을 등록한 회원의 전화번호 가져오기
function getRsvedMemPn(rsvNo){
	
	$.ajax({
		url:"/Reservation/GetRsvedMemPn",
		type:"post",
		dataType:"text",
		data : { "rsvNo":rsvNo },
		async:false,
		success:function(pn){		
			$("#rsvMemPn").val(pn);
		},
		error:function(request,status,error){
			alert("Get Member Phone Number Error");
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

//예약 비밀번호 이메일로 전송
function sendEmail(rsvNo){
	$.ajax({
		url:"/Reservation/SendEmail",
		type:"post",
		dataType:"text",
		data:{"rsvNo":rsvNo},
		success:function(result){
			$("#detailInfo").empty();
	    	$("#detailInfo").append("이메일로 비밀번호를 전송하였습니다.");
	    	$("#detailInfo").show();
		},
		error:function(request,status,error){
			alert("Send Email By Member Phone Number Error");
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	 });   
}

function startEndDisabledFalse(){
 	//모두 show인 상태로 되돌리기
	$("#rsvStartTime option").show();
	$("#rsvEndTime option").show();
}

//가예약 승인된 예약은 관리자만 수정 가능
function getRsvConfirmStateVal(rsvNo){
	
	var confirmState = "";
	$.ajax({
		url:"/Reservation/GetRsvConfirmStateVal",
		type:"post",
		dataType:"text",
		data:{
			"rsvNo":rsvNo
		},
		async:false,
		success:function(state){
			confirmState = state;
		},
		error:function(request,status,error){
		//	alert("Get RsvConfirmState Value Error");
		//	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	
	return confirmState;
}


function autoRefresh_div(){
   $('#calendar').fullCalendar('refetchEvents');
}

function getRsvedTitle(rsvNo){
	
	$.ajax({
		url:"/Reservation/GetRsvedTitle",
		type:"post",
		dataType:"text",
		data : { "rsvNo":rsvNo },
		async:false,
		success:function(title){		
			$("#rsvTitle").val(title);
		},
		error:function(request,status,error){
			alert("Get Reservation Title Error");
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
	
}

function getInputDayLabel(date) { 
	var week = new Array('일', '월', '화', '수', '목', '금', '토');
	var today = new Date(date).getDay();
	var todayLabel = week[today];
	
	$("#day").append("("+todayLabel+")");
}

			
$(document).ready(function(){
	
	//관리자로 로그인했는지 체크 , 로그인X : null
	var loginChk = <%= session.getAttribute("id") %>;
	
	//화면 매 설정값(초)마다 refresh
	 var interValsec;

	   $.ajax({
	      url : "/AdminManagement/SettingLoad",
	      dataType : "json",
	      async : false,
	      type : "POST",
	      success : function(data) {
	         interValsec = data.SET_INDEX_REFRESH+'000';
	         setInterval('autoRefresh_div()', interValsec);
	      },
	      error : function(request, status, error) {
	      }
	   });
	   
	var date = new Date();	
	getInputDayLabel(date);


	var today = new Date().getDate();
	$("#todayDate").text(today);   
	$("#todayDate").on("click", function(){
		$('#calendar').fullCalendar('today');
		 $("#day").empty();
		 getInputDayLabel(date);
		 
		//이미 입력되어있는 textbox내의 값들 지우기
		$("#rsvTitle").removeAttr("value");
		$("#rsvDelPwd").removeAttr("value");
		$("#rsvMemPn").removeAttr("value");
		$("#rsvMemNm").removeAttr("value");
		$("#rsvMemEm").removeAttr("value");
		$("#rsvEmailCheck").prop('checked' , true); //default값으로 변경
		$("#rsvColor option[value='blue']").attr("selected", true);
			
		emptyErrorMsg();
	});
	   
	$("#prev").on("click", function(){
		 $('#calendar').fullCalendar('prev');
		 var prevDate = $('#calendar').fullCalendar('getDate');
		 $("#day").empty();
		 getInputDayLabel(prevDate);
	});  
	
	$("#next").on("click", function(){
		$('#calendar').fullCalendar('next');
		var nextDate = $('#calendar').fullCalendar('getDate');
		 $("#day").empty();
		 getInputDayLabel(nextDate);
	});
	
	//시작시간 < 종료시간
 	$("#rsvStartTime").on("change", function(){
 		startEndDisabledFalse();
 		
 		var startTime = $("#rsvStartTime").val();
 		var index = 0;
 		for(var i=0; i<$("#rsvEndTime option").size(); i++){ 
 			if(startTime == $("#rsvEndTime option").eq(i).val()){
				index = i;
 			}
 		}
 		for(var j=0; j<=index; j++){ 
 			//$("#rsvEndTime option").eq(j).attr("disabled", true);
 			$("#rsvEndTime option").eq(j).hide();
 		}
	}); 
 	$("#rsvEndTime").on("change", function(){
 		startEndDisabledFalse();
 		
 		var endTime = $("#rsvEndTime").val();
 		var index = 0;
 		for(var i=0; i<$("#rsvStartTime option").size(); i++){ 
 			if(endTime == $("#rsvStartTime option").eq(i).val()){
				index = i;
 			}
 		}
 		for(var j=index; j<=$("#rsvStartTime option").size(); j++){ 
 			$("#rsvStartTime option").eq(j).hide();
 		}
 	});  

 	//작성자 : 박세연, 최문정
	//전화번호 입력을 통해 이름과 이메일 자동입력
	var data = new Array();
	
	$("#rsvMemPn").autocomplete({
	   source: function (request, response) {
		   $.ajax({
				url:"/Member/GetMemInfoByPn",
				type:"post",
				dataType:"json",
				data:{ "memPn" : request.term },
				success:function(data){				
					$("#telChkMsg").empty();

					//data :: JSON list defined
			 	  	response($.map(data, function (item) {
 			        	  return {
 									label: item.pn,
 				                    value: item.pn,
 				                    nm : item.nm,
 				                    em : item.em
 				               }
 			         }));
				},
				error:function(request,status,error){
					alert("Get Member Information Error");
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
	    },
	    select: function(event, ui) {
	    	$("#errorPn").empty();
	    	$("#errorName").empty();
	    	$("#errorEmail").empty();
			$("#rsvMemNm").val(ui.item.nm);
			$("#rsvMemEm").val(ui.item.em);
		}
 	});
  
  
	//등록된 회의실 불러와 select option 주기
	conf();
	//값이 채워지는지 체크
	chkData();
	
	//전화번호 형식 체크 - 숫자만 입력 가능
	$("#rsvMemPn").keyup(function(event){
		if (!(event.keyCode >=37 && event.keyCode<=40)) {
            var inputVal = $(this).val();
            $(this).val(inputVal.replace(/[^0-9]/gi,''));
		}
		var pn = inputDash();
		$("#rsvMemPn").val(pn); // - 포함해 실시간 값 업데이트	
	});

 	//수정 버튼 클릭시
 	$("#modifyBtn").on("click", function(){
 		if($("#rsvDelPwd").val()==""){
 			$("#pwdChkMsg").empty();
 			$("#pwdChkMsg").append("비밀번호를 입력하세요.");
 			$("#pwdChkMsg").show();
 			$("#rsvDelPwd").focus();
 			
 			return false;
 		}else{
 			//사용자가 이전에 적은 비밀번호와 비교
 			//일치 -> 수정, 불일치 -> alert
 			$.ajax({
 				url:"/Reservation/GetPwd",
 				type:"post",
 				dataType:"text",
 				data:{"rsvNo":$("#rsvNo").val()},
 				success:function(pwd){		
 					if($("#rsvDelPwd").val() == pwd){
 						modify();
 					}else{
 						$("#pwdChkMsg").empty();
 			 			$("#pwdChkMsg").append("비밀번호가 일치하지 않습니다.");
 			 			$("#pwdChkMsg").show();
 			 			$("#rsvDelPwd").focus();
 			 			
 			 			return false;
 					}
 				},
 				error:function(request,status,error){
 					alert("Get Password Error");
 					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
 				}
 			});
 			
 		}
 		
 	});
 	
 	//삭제 버튼 클릭시
 	$("#deleteBtn").on("click", function(){
 		if($("#rsvDelPwd").val()==""){
 			$("#pwdChkMsg").empty();
 			$("#pwdChkMsg").append("비밀번호를 입력하세요.");
 			$("#pwdChkMsg").show();
 			$("#rsvDelPwd").focus();
 			
 			return false;
 		}else{
 			//사용자가 이전에 적은 비밀번호와 비교
 			//일치 -> 수정, 불일치 -> alert
 			$.ajax({
 				url:"/Reservation/GetPwd",
 				type:"post",
 				dataType:"text",
 				data:{"rsvNo":$("#rsvNo").val()},
 				success:function(pwd){		
 					if($("#rsvDelPwd").val() == pwd){		
 						if(!confirm("삭제시 복구할 수 없습니다. 정말 삭제하시겠습니까?")){
 							return false;
 						}else{
 							deleteRsv();
 						}
 					}else{
 						$("#pwdChkMsg").empty();
 			 			$("#pwdChkMsg").append("비밀번호가 일치하지 않습니다.");
 			 			$("#pwdChkMsg").show();
 			 			$("#rsvDelPwd").focus();
 			 			
 			 			return false;
 					}
 				},
 				error:function(request,status,error){
 					alert("Get Password Error");
 					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
 				}
 			});
 			
 		}
 		
 	});
 	
 
	 $("#rsvMemEm").blur(function(){
		emailcheck($("#rsvMemEm").val());
	 });
/* 	 
	 $("#rsvMemPn").blur(function(){
		$("#telChkMsg").empty();
		if($("#rsvMemPn").val().length < 12){ 
			$("#telChkMsg").append("올바르지 않은 전화번호 길이입니다.");
			$("#telChkMsg").show();
		}
	 }); */
 
	 //달력 첫 조회시 오늘 날짜로 조회
	  var date = new Date();
	
	  $( function() {
		    $("#rsvDate").datepicker();
		    $("#rsvDate").datepicker("option", "dateFormat", "yy-mm-dd");
		    $("#rsvDate").datepicker().datepicker("setDate", new Date()); //defaultDate 설정
		    
		    $("#date").datepicker();
		    $("#date").datepicker("option", "dateFormat", "yy-mm-dd");
		    $("#date").datepicker().datepicker("setDate", new Date()); //defaultDate 설정
		} );

	  //datepicker 이용해 사용자가 원하는 날짜로 이동
	  $("#rsvDate").datepicker({ 
		//  minDate : date, //오늘 이전 날짜는 선택 불가 -> 예약 불가
		  onSelect: function(dateText) {  
			  $('#calendar').fullCalendar('gotoDate', dateText);
			  
			  var rsvDate = $('#calendar').fullCalendar('getDate');
			  $("#day").empty();
			  getInputDayLabel(rsvDate);
		  }
	   }); 

	//datepicker 이용해 사용자가 원하는 날짜로 이동
	  $("#date").datepicker({ 
		//  minDate : date, //오늘 이전 날짜는 선택 불가 -> 예약 불가
		  onSelect: function(dateText) {  
			  $('#calendar').fullCalendar('gotoDate', dateText);
			  
			  var date = $('#calendar').fullCalendar('getDate');
			  $("#day").empty();
			  getInputDayLabel(date);
		  }
	   });
		
	$('#calendar').fullCalendar({
		locale:"ko", //한글로 변환
		navLinks: false, // can click day/week names to navigate views
		eventOverlap: false, // will cause the event to take up entire resource height , 이벤트 겹치기 불가
		contentHeight: 'auto',
		minTime: "09:00:00", //하루의 시작 시간
		maxTime: "18:00:00", //하루의 종료 시간
		schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
		defaultView: 'agendaDay', //하루 기준
		defaultDate: date, //오늘 날짜가 default
		editable: true, // events can be dragged and resizedevents can be dragged and resized
	//	eventDurationEditable: false, //resize 막기
		slotEventOverlap: false, //같은 slot위에 오버랩불가
		selectable: true, //slot select true (칸 한칸씩 선택 가능)
		eventLimit: true, // allow "more" link when too many events
		selectOverlap: false,
		dragOpacity: 0.6,
//		filterResourcesWithEvents: true,
		
		eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc, jsEvent) {
		 
			var sTime = $.fullCalendar.formatDate(event.start, 'kk:mm:ss');
			var eTime = $.fullCalendar.formatDate(event.end, 'kk:mm:ss');
	
			//드롭시, 시작시간이 9시 이전이거나 종료시간이 18시 이후일때,
 			if(sTime.substr(0,2)< '09'){
				sTime = "09:00:00";
			}else if(eTime.substr(0,2)>'18'){
				eTime = "18:00:00";
			}
 			$("#rsvStartTime").val(sTime);
 			$("#rsvEndTime").val(eTime);
 			calTotalTime();

			//오늘 이전 날짜의 예약은 수정 불가
	    	var today = new Date();
	    	var rsvDate = $.fullCalendar.formatDate(event.start, 'YYYY-MM-DD');
	     	var dateLength = date.getDate().toString().length;
			var monthLength = (date.getMonth()+1).toString().length;
			
			if(dateLength == 1){	
				if(monthLength == 1){ 
					today = today.getFullYear()+"-0"+(today.getMonth()+1)+"-0"+today.getDate();
				}else if(monthLength == 2){ 
					today = today.getFullYear()+"-"+(today.getMonth()+1)+"-0"+today.getDate();
				}
			}else if(dateLength == 2){	
				if(monthLength == 1){ 
					today = today.getFullYear()+"-0"+(today.getMonth()+1)+"-"+today.getDate();
				}else if(monthLength == 2){ 
					today = today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate();
				}
			} 

	    	if(rsvDate < today){
	    		alert("오늘 날짜 이전의 예약은 수정할 수 없습니다.");
	    		$('#calendar').fullCalendar('refetchEvents');
	    		return false;
	    	}
			
			getRsvedMemPn(event.id);
	    	var banState = memberBanCheck();
	    	if(banState == "T"){
	    		alert("차단된 회원이므로 수정을 진행하실 수 없습니다.");
	    		$('#calendar').fullCalendar('refetchEvents');
	    		return false;
	    	}
			
	    	var rsvConfirmStateVal = "Y";
	    	//가예약 상태이면 수정 불가
	    	rsvConfirmStateVal = getRsvConfirmStateVal(event.id);
	    	if(rsvConfirmStateVal == "N"){
	    		alert("가예약 승인 대기 중인 예약은 수정할 수 없습니다.");
	    		$('#calendar').fullCalendar('refetchEvents');
	    		return false;
	    	}
	    	
			if(loginChk == null){ //관리자 로그인되지 않았을 때
			
		    	var rsvConfirmStateVal = getRsvConfirmStateVal(event.id);
		    	if(rsvConfirmStateVal == "ACCEPT"){
		    		alert("가예약 승인받은 예약은 관리자만 수정가능합니다.");
		    		$('#calendar').fullCalendar('refetchEvents');
		    		return false;
		    	}
		    	    	
				//비밀번호 확인
			 	$("#eventInfo").html("<input type='password' id='chkPwd'> <input type='button' id='chkBtn' value='확인'><br><div id='chkPwdErrMsg' style='display:none'></div>");
				$("#eventContent").dialog({ modal: true, title: "비밀번호를 입력해주세요.",  width:370, height:200, resizable:false, close: function( event, ui ) { $('#calendar').fullCalendar('refetchEvents');} });
			    $("#eventLink").show();
			    $("#detailInfo").empty();
				$("#eventLink").on("click", function(){
					sendEmail(event.id);
			    });
			    
				$("#chkBtn").on("click", function(){
					var res = "N";
					res = checkPwd(event.id);
					
					if(res == "Y"){
						$.ajax({
							url:"/Reservation/ModifyRsvByDrop",
							type:"post",
							dataType:"text",
							data:{
								"rsvNo":event.id,
								"rsvConfNo":event.resourceId,
								"rsvStartTime":sTime,
								"rsvEndTime":eTime,
								"rsvTotalTime":$("#rsvTotalTime").val()
							},
							success:function(result){
								alert("이동이 완료되었습니다.");
								$("#eventContent").dialog("close");
								
							},
							error:function(request,status,error){
								alert("Modify Reservation By Drop Error");
								alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
							}
						 
						 });   
					}
					
				});
			}else if(loginChk != null){ //관리자로 로그인시
				if(!confirm("예약내역을 이동해 수정하시겠습니까?")){
					$('#calendar').fullCalendar('refetchEvents');
		    		return false;
				}else{
					
					$.ajax({
						url:"/Reservation/ModifyRsvByDrop",
						type:"post",
						dataType:"text",
						data:{
							"rsvNo":event.id,
							"rsvConfNo":event.resourceId,
							"rsvStartTime":sTime,
							"rsvEndTime":eTime,
							"rsvTotalTime":$("#rsvTotalTime").val()
						},
						success:function(result){
							alert("이동이 완료되었습니다.");
							$("#eventContent").dialog("close");
						},
						error:function(request,status,error){
							alert("Modify Reservation By Drop Error");
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
					 
					 });   
				}
			}
		},
		 		
	//	windowResize:true,
		slotLabelFormat :'H:mm',
		header: {
			left: 'none',
			center: 'none',
			right: 'none'
		},
		views: {
			agendaTwoDay: {
				type: 'agenda',
				duration: { days: 2 },

				// views that are more than a day will NOT do this behavior by default
				// so, we need to explicitly enable it
				groupByResource: true

				//// uncomment this line to group by day FIRST with resources underneath
				//groupByDateAndResource: true
			}
		},

		//// uncomment this line to hide the all-day slot
		allDaySlot:false,
		
		eventClick: function(calEvent, jsEvent, view) { //이미 등록된 이벤트 클릭시 예약 내역 조회
			
			$("#rsvDelPwd").removeAttr("value");
			emptyErrorMsg();
			
			$.ajax({
				url:"/Reservation/GetReservationInfo",
				type:"post",
				dataType:"JSON",
				data:{"no":calEvent.id},
				success:function(info){
					var date = new Date(info[0].rsvDate);
					$("#rsvDate").val(date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
					$("#rsvStartTime option[value='"+info[0].rsvStartTime+"']").attr("selected", true);
					$("#rsvEndTime option[value='"+info[0].rsvEndTime+"']").attr("selected", true);
					$("#rsvColor option[value='"+info[0].rsvColor+"']").attr("selected", true);					
					$("#rsvConfNo option[value='"+info[0].rsvConfNo+"']").attr("selected", true);
					$("#rsvTitle").val(info[0].rsvTitle);
					$("#rsvMemPn").val(info[0].rsvMemPn);
					$("#rsvMemNm").val(info[0].rsvMemNm);
					$("#rsvMemEm").val(info[0].rsvMemEm);
					if(info[0].rsvEmailCheck == "N"){
						$('input:checkbox[id="rsvEmailCheck"]').attr("checked", false); 
					}else{
						$('input:checkbox[id="rsvEmailCheck"]').attr("checked", true); 
					}
					
					if(loginChk != null){ //관리자 로그인시
						$("#rsvDelPwd").val(info[0].rsvDelPwd); //비번은 모두가 조회 불가 -> 수정/삭제시에 본인확인을 위해서 필요
					}
					
					$("#rsvNo").val(calEvent.id);
					$("#submit").hide();
					$("#modify").show();
					
				},
				error:function(request,status,error){
					alert("EventClick Error");
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
	    },
	    dayClick: function(date, jsEvent, view, resourceObj) {
			regRsv(date, resourceObj);
	    },
		resources: function(callback, start, end, timezone) {
			$.ajax({
					url:"/MeetingRoom/GetResources",
					type:"post",
					dataType:"JSON",
					success:function(list){
						var resources = new Array();
						
						$.each(list, function(i){
							resources[i] = {"id":this.confNo,
												"title":this.confNm		
							};
						});
						
						callback(resources);
					},
					error:function(){
						$('#calendar').fullCalendar('today');
					}
			});
		},
		events:function(start, end, timezone, callback){
			$.ajax({
				url:"/Reservation/GetReservation",
				type:"post",
				dataType:"JSON",
				success:function(list){
					var events = new Array();
					var date = null;
					
					$.each(list, function(i){
						date = new Date(this.rsvDate);
						var dateLength = date.getDate().toString().length;
						var monthLength = (date.getMonth()+1).toString().length;
						
						if(dateLength == 1){	
							if(monthLength == 1){
								events[i] = {
										"title":this.rsvTitle,
										"id":this.rsvNo,
									 	"start":date.getFullYear()+"-0"+(date.getMonth()+1)+"-0"+date.getDate()+"T"+this.rsvStartTime,
										"end":date.getFullYear()+"-0"+(date.getMonth()+1)+"-0"+date.getDate()+"T"+this.rsvEndTime,
										"memNm":this.rsvMemNm,
										"color":this.rsvColor,
										"confirm":this.rsvConfirmState,
										"resourceId":this.rsvConfNo,
										"borderColor":"#FAFAFA",
										eventDurationEditable:false
									};
							}else if(monthLength == 2){
								events[i] = {
										"title":this.rsvTitle,
										"id":this.rsvNo,
									 	"start":date.getFullYear()+"-"+(date.getMonth()+1)+"-0"+date.getDate()+"T"+this.rsvStartTime,
										"end":date.getFullYear()+"-"+(date.getMonth()+1)+"-0"+date.getDate()+"T"+this.rsvEndTime,
										"memNm":this.rsvMemNm,
										"color":this.rsvColor,
										"confirm":this.rsvConfirmState,
										"resourceId":this.rsvConfNo,
										"borderColor":"#FAFAFA",
										eventDurationEditable:false
									};
							}
						}else if(dateLength == 2){
							if(monthLength == 1){
								events[i] = {
										"title":this.rsvTitle,
										"id":this.rsvNo,
										"start":date.getFullYear()+"-0"+(date.getMonth()+1)+"-"+date.getDate()+"T"+this.rsvStartTime,
										"end":date.getFullYear()+"-0"+(date.getMonth()+1)+"-"+date.getDate()+"T"+this.rsvEndTime,
										"memNm":this.rsvMemNm,
										"color":this.rsvColor,
										"confirm":this.rsvConfirmState,
										"resourceId":this.rsvConfNo,
										"borderColor":"#FAFAFA",
										eventDurationEditable:false
										}; 
							}else if(monthLength == 2){
								events[i] = {
										"title":this.rsvTitle,
										"id":this.rsvNo,
										"start":date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"T"+this.rsvStartTime,
										"end":date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"T"+this.rsvEndTime,
										"memNm":this.rsvMemNm,
										"color":this.rsvColor,
										"confirm":this.rsvConfirmState,
										"resourceId":this.rsvConfNo,
										"borderColor":"#FAFAFA",
										eventDurationEditable:false
										}; 
							}
							
						}
					
					});
					
					for(var j=0; j<events.length; j++){
						if(events[j].confirm == 'N'){
							events[j].color="rgba(179, 179, 179, 0.5)";
						}else{
							if(events[j].color == 'red'){ //빨강
								events[j].color="#ff1a1a"; 
							}else if(events[j].color == 'orange'){ //주황
								events[j].color="#fe9a2e";
							}else if(events[j].color == 'green'){ //초록
								events[j].color="#33cc33";
							}else if(events[j].color == 'blue'){ //파랑
								events[j].color="#00599D";
							}else if(events[j].color == 'navy'){ //남색
								events[j].color="#001D59";
							}else if(events[j].color == 'skyblue'){ //하늘
								events[j].color="#3399ff";
							}
						}
					}
					
					callback(events);
				},
				error:function(request,status,error){
					$('#calendar').fullCalendar('today');
				}
			});	
		},
		selectable:true, 
		select: function(start, end, jsEvent, view, resource) {
		
			$("#rsvNo").val(0);
			$("#submit").show();
			$("#modify").hide();
			
			//이미 입력되어있는 textbox내의 값들 지우기
			$("#rsvTitle").removeAttr("value");
			$("#rsvDelPwd").removeAttr("value");
			$("#rsvMemPn").removeAttr("value");
			$("#rsvMemNm").removeAttr("value");
			$("#rsvMemEm").removeAttr("value");
			$("#rsvEmailCheck").prop('checked' , true); //default값으로 변경
			$("#rsvColor option[value='blue']").attr("selected", true);
			
			emptyErrorMsg();
			
			endtime = $.fullCalendar.formatDate(end, 'kk:mm:ss');
	        starttime = $.fullCalendar.formatDate(start, 'kk:mm:ss');
	        $("#rsvStartTime option[value='"+starttime+"']").attr("selected", true);
	        $("#rsvEndTime option[value='"+endtime+"']").attr("selected", true);
	        $("#rsvConfNo option[value='"+resource.id+"']").attr("selected", true);
	   		$("#rsvTitle").focus();
	   		
	 	  	//이미 예약된 예약시간을 체크해 새로 등록할 예약의 시작/종료시간 불가능한 시간 disabled 시키기
	   		setTimeDisabled(resource.id);
	    },
	    eventResize: function(event,dayDelta,minuteDelta,revertFunc) {
 
	    	getRsvedMemPn(event.id);
	    	var banState = memberBanCheck();
	    	if(banState == "T"){
	    		alert("차단된 회원이므로 수정을 진행하실 수 없습니다.");
	    		$('#calendar').fullCalendar('refetchEvents');
	    		return false;
	    	}
	  
	    	var rsvConfirmStateVal = "Y"
	    	//가예약 상태이면 수정 불가
	    	rsvConfirmStateVal = getRsvConfirmStateVal(event.id);
	    	if(rsvConfirmStateVal == "N"){
	    		alert("가예약 승인 대기 중인 예약은 수정할 수 없습니다.");
	    		$('#calendar').fullCalendar('refetchEvents');
	    		return false;
	    	}
	    	
			//오늘 이전 날짜의 예약은 수정 불가
	    	var today = new Date();
	    	var rsvDate = $.fullCalendar.formatDate(event.start, 'YYYY-MM-DD');
	     	var dateLength = date.getDate().toString().length;
			var monthLength = (date.getMonth()+1).toString().length;
			
			if(dateLength == 1){	
				if(monthLength == 1){ 
					today = today.getFullYear()+"-0"+(today.getMonth()+1)+"-0"+today.getDate();
				}else if(monthLength == 2){ 
					today = today.getFullYear()+"-"+(today.getMonth()+1)+"-0"+today.getDate();
				}
			}else if(dateLength == 2){	
				if(monthLength == 1){ 
					today = today.getFullYear()+"-0"+(today.getMonth()+1)+"-"+today.getDate();
				}else if(monthLength == 2){ 
					today = today.getFullYear()+"-"+(today.getMonth()+1)+"-"+today.getDate();
				}
			} 

	    	if(rsvDate < today){
	    		alert("오늘 날짜 이전의 예약은 수정할 수 없습니다.");
	    		$('#calendar').fullCalendar('refetchEvents');
	    		return false;
	    	}
	    	
	    	endtime = $.fullCalendar.formatDate(event.end, 'kk:mm:ss');
	        starttime = $.fullCalendar.formatDate(event.start, 'kk:mm:ss');
	        $("#rsvStartTime option[value='"+starttime+"']").attr("selected", true);
	        $("#rsvEndTime option[value='"+endtime+"']").attr("selected", true);
	
	    	var overTime = calTotalTime();
	     	var maxTime = getMaxTime();
	     	
	    	if(overTime == 'T'){
	    		if(!confirm(maxTime+"시간 이상 예약은 관리자 승인이 필요합니다. 진행하시겠습니까?")){
	    			$('#calendar').fullCalendar('refetchEvents');
	    			return false;
	    		}else{
	    			$("#rsvConfirmState").val("N");
	    		}
	    	}
	    
	    	getRsvedTitle(event.id);
	    	preventMonopoly(event.id);
	    	var count = $("#monopolyCount").val();

	    	//이 값이 "T"이면 가예약상태
	    	if(count == "T"){
	    		if(!confirm("이번주에 "+maxTime*3+"시간 이상 예약을 진행 중이셔서 가예약됩니다. 진행하시겠습니까?")){
	    			$('#calendar').fullCalendar('refetchEvents');
	    			return false;
	    		}else{
	    			$("#rsvConfirmState").val("N");
	    		}
	    	} 
	    	
	    	var res = $("#rsvTotalTime").val().split(":");
	    	var total = "총 "+res[0]+"시간 "+res[1]+"분";
	    	
	    	if(loginChk == null){ //관리자 로그인되지 않았을 때
	    		var rsvConfirmStateVal = "Y";
	    		rsvConfirmStateVal = getRsvConfirmStateVal(event.id);
		    	if(rsvConfirmStateVal == "ACCEPT"){
		    		alert("가예약 승인받은 예약은 관리자만 수정가능합니다.");
		    		$('#calendar').fullCalendar('refetchEvents');
		    		return false;
		    	}
		    	
		    	//비밀번호 확인
			 	$("#eventInfo").html("<input type='password' id='chkPwd'> <input type='button' id='chkBtn' value='확인'><br><div id='chkPwdErrMsg' style='display:none'></div>");
				$("#eventContent").dialog({ modal: true, title: "비밀번호를 입력해주세요.",  width:350, height:200, resizable:false, close: function( event, ui ) { $('#calendar').fullCalendar('refetchEvents');} });
			    $("#eventLink").show();
			    $("#detailInfo").empty();
				$("#eventLink").on("click", function(){
					sendEmail(event.id);
			    });

				
				$("#chkBtn").on("click", function(){
					var res = "N";
					res = checkPwd(event.id);
					
					if(res == "Y"){
						
				    	if(!confirm($("#rsvStartTime option:selected").text()+" - "+$("#rsvEndTime option:selected").text()+" ("+total+") 으로 예약을 변경하시겠습니까?")){
				    		$("#eventContent").dialog("close");
				    		$('#calendar').fullCalendar('refetchEvents');
							return false;
						}else{
							$.ajax({
								url:"/Reservation/ModifyRsvByResize",
								type:"post",
								dataType:"text",
								data:{
									"rsvNo":event.id,
									"rsvStartTime":$.fullCalendar.formatDate(event.start, 'kk:mm:ss'),
									"rsvEndTime":$.fullCalendar.formatDate(event.end, 'kk:mm:ss'),
									"rsvTotalTime":$("#rsvTotalTime").val(),
									"rsvConfirmState":$("#rsvConfirmState").val() 
								},
								success:function(result){
									alert("수정이 완료되었습니다.");
									$("#eventContent").dialog("close");
									window.location.reload(true);
								},
								error:function(request,status,error){
									alert("Modify Reservation By Resize Error");
									alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
								}
							 
							 });   
						}
					
					}
				});
	    	}else if(loginChk != null){
	    		if(!confirm($("#rsvStartTime option:selected").text()+" - "+$("#rsvEndTime option:selected").text()+" ("+total+") 으로 예약을 변경하시겠습니까?")){
		    		$("#eventContent").dialog("close");
		    		$('#calendar').fullCalendar('refetchEvents');
					return false;
				}else{
					$.ajax({
						url:"/Reservation/ModifyRsvByResize",
						type:"post",
						dataType:"text",
						data:{
							"rsvNo":event.id,
							"rsvStartTime":$.fullCalendar.formatDate(event.start, 'kk:mm:ss'),
							"rsvEndTime":$.fullCalendar.formatDate(event.end, 'kk:mm:ss'),
							"rsvTotalTime":$("#rsvTotalTime").val(),
							"rsvConfirmState":$("#rsvConfirmState").val() 
						},
						success:function(result){
							alert("수정이 완료되었습니다.");
							$("#eventContent").dialog("close");
						},
						error:function(request,status,error){
							alert("Modify Reservation By Resize Error");
							alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
						}
					 
					 });   
				}
	    	}
	    	
	    },
	    viewRender: function (view, element) {
	    	$("#submitForm :input").attr("disabled", false);
	    	
	        var b = $('#calendar').fullCalendar('getDate');
	        $("#date").val(b.format('YYYY-MM-DD'));
	        $("#rsvDate").val(b.format('YYYY-MM-DD'));
	        
	        var date = new Date();
			date.setTime(new Date().getTime() - (1 * 24 * 60 * 60 * 1000));
	   		date = moment(date).format('YYYY-MM-DD');
	        	
	        if($("#date").val() <= date){
	        	$("#submitForm :input").attr("disabled", true);
	        }
	        
	        var maxDate = getRsvMaxMonth();
	        if(maxDate <= $("#date").val()){
	        	$("#submitForm :input").attr("disabled", true);
	        }
	     },
		 eventMouseover: function (data, event, view) {
			/* 
			var name = "";
			var phoneNum = "";
			var email = "";
			var totalTime = "";
			var state = "";
			var noshow = 0;
			 
			//예약 등록자 정보 가져오기
			$.ajax({
				url:"/Reservation/ShowInfoByTooltip",
				type:"post",
				dataType:"json",
				data:{
					"rsvNo":data.id 
				},
				async : false,
				success:function(list){
					name = list[0].rsvMemNm;
					phoneNum = list[0].rsvMemPn;
					email = list[0].rsvMemEm;
					totalTime = list[0].rsvTotalTime;
				},
				error:function(request,status,error){
					alert("Show Information By Tooltip Error");
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
			
			$.ajax({
				url:"/Member/MemInfoByTooltip",
				type:"post",
				dataType:"json",
				data:{
					"memPn":phoneNum
				},
				async : false,
				success:function(list){
					state = list[0].memState;
					noshow = list[0].countWarn;
				},
				error:function(request,status,error){
					alert("Member Information By Tooltip Error");
					alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
			 
			var hour = totalTime.substr(1,1);
			var min = totalTime.substr(3,2);

			if(loginChk == null){ //관리자 로그인되지 않았을 때
				tooltip = '<div class="tooltiptopicevent" style="width:auto;border-color:#d9d9d9;border-style:solid;border-width:0.2px;height:auto;background:white;position:absolute;z-index:10001;padding:10px 10px 10px 10px ;  line-height: 200%;">' + name +' 님의 예약입니다.<br>Email : '+ email + '<br>전화번호 : ' + phoneNum + '<br>총 소요시간  : '+ hour +'시간 '+ min +'분</div>';
			}else if(loginChk != null){ //관리자로 로그인시
				if(state != "정상"){
					tooltip = '<div class="tooltiptopicevent" style="width:auto;border-color:#d9d9d9;border-style:solid;border-width:0.2px;height:auto;background:white;position:absolute;z-index:10001;padding:10px 10px 10px 10px ;  line-height: 200%;">' + name +' 님의 예약입니다.<br>Email : '+ email + '<br>전화번호 : ' + phoneNum + '<br>총 소요시간  : '+ hour +'시간 '+ min +'분<br>NoShow 수 : '+ noshow +'<br>차단된 회원입니다.</div>';
				}else{
					tooltip = '<div class="tooltiptopicevent" style="width:auto;border-color:#d9d9d9;border-style:solid;border-width:0.2px;height:auto;background:white;position:absolute;z-index:10001;padding:10px 10px 10px 10px ;  line-height: 200%;">' + name +' 님의 예약입니다.<br>Email : '+ email + '<br>전화번호 : ' + phoneNum + '<br>총 소요시간  : '+ hour +'시간 '+ min +'분<br>NoShow 수 : '+ noshow +'</div>';
				}
			}
			
	        $("body").append(tooltip);
	        $(this).mouseover(function (e) {
	        	$(this).css('z-index', 10000);
	            $('.tooltiptopicevent').fadeIn('500');
	            $('.tooltiptopicevent').fadeTo('10', 1.9);
	         }).mousemove(function (e) {
	         	$('.tooltiptopicevent').css('top', e.pageY + 10);
	         	$('.tooltiptopicevent').css('left', e.pageX + 20);
	         });
	         */
		 },
	     eventMouseout: function (data, event, view) {
	    	 /* 
	         $(this).css('z-index', 8);
	         $('.tooltiptopicevent').remove();
			  */
	     }
	});


	//검색페이지에서 하이퍼링크를 타고 넘어왔을 때
	if($("#fromSearch").val() == "T"){
		
		var d =  $("#rsvDateFromSearch").val().toString();
		d= new Date(d);
		$('#calendar').fullCalendar('gotoDate', d);
	
	}

	//예약 등록 완료 했을 때, 등록한 날짜로 돌아가기
	if(!($("#registRsvDate").val() == null || $("#registRsvDate").val().length == 0)){
		var d =  $("#registRsvDate").val().toString();
		d= new Date(d);
		
		$('#calendar').fullCalendar('gotoDate', d);
		
		var regDate = $('#calendar').fullCalendar('getDate');
		$("#day").empty();
		getInputDayLabel(regDate);
	
	}
	
});


</script>
<style>
@import url(https://fonts.googleapis.com/earlyaccess/jejugothic.css);
	body {	
		margin: 0;
		padding: 0;
		font-family: 'Jeju Gothic', serif;
		font-size: 17px;
	}

	#calendar {
		max-width: 1400px;
		margin: 20px auto;
	}
	
	.fc-widget-content {
    position: relative;
}

.fc-day-number {
    position: fixed;
    right: 0;
    bottom: 0;
}
	.typeColor{
		display: inline;
		width: 100px;
	}

	span.error{
		font-size:small; /* 1em : 기본 크기의 1배수 크기 */
		color: red;
	}
	
	#memEmChkMsg{
		font-size:small; /* 1em : 기본 크기의 1배수 크기 */
		color: red;
	}
	
	#pwdChkMsg{
		font-size:small; /* 1em : 기본 크기의 1배수 크기 */
		color: red;
	}
	
	#telChkMsg{
		font-size:small; /* 1em : 기본 크기의 1배수 크기 */
		color: red;
	}
	
	#chkPwdErrMsg{
		font-size:small; /* 1em : 기본 크기의 1배수 크기 */
		color: red;
	}
	
	#detailInfo{
		font-size:small; /* 1em : 기본 크기의 1배수 크기 */
		color: red;
	}
	
	.calendar-text { margin-top: .32em; }
	
	#calendar {
	    background-color: #F9F9F9;
	}

</style>

