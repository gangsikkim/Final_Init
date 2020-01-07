<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Black</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<style>
.tbBtn {
	box-shadow: 1px 1px 1px 1px gray;
	border-radius: 5px 5px 5px 5px;
}
#searchDiv{
	text-align : right;
}
</style>

</head>
<body>
<div class="container mt-5">
<div class="row">
<div class="col-md-2">
<div class="panel panel-info">
    <div class="panel-heading">
      <h3 class="panel-title">Menu</h3>
    </div>
    <!-- 사이드바 메뉴목록1 -->
    <ul class="list-group">
     <li class="list-group-item"><a href="${pageContext.request.contextPath}/admin/memberList.do">멤버관리</a></li>
      <li class="list-group-item"><a href="#">피드관리</a></li>
      <li class="list-group-item"><a href="${pageContext.request.contextPath}/admin/blackList.do">블랙리스트관리</a></li>
    </ul>
</div>
</div>

<div class="col-md-10">

<div class="row">
<div class="col">
<h3>블랙리스트 관리</h3>
</div>
</div>
<div class="row">
<div class="col" id="searchDiv">
<form action="${pageContext.request.contextPath}/admin/search.do" method="post" id="searchF">
<select id="searhTag" name="searhTag">
<option value="nickname">닉네임</option>
<option value="name">이름</option>
<option value="email">이메일</option>
</select>
<input type="text" id="search" name="search"><button type="submit" id="searchBtn">검색</button>
</form>
</div>
</div>
 <div class="row">
 <div class="col">
		<table class="table table-hover">
		<thead>
			<tr>
				<th>profile</th>
				<th>nickname</th>
				<th>name</th>
				<th>email</th>
				<th>edit</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${blackList}" var="mdto">
			<tr>
				<td>${mdto.profile_img}</td>
				<td>${mdto.nickname}</td>
				<td>${mdto.name}</td>
				<td>${mdto.email}</td>
				<td><button class="wdBtn" id="out_${mdto.email}">탈퇴</button>
					<button class="cbBtn" id="cb_${mdto.email}">되살리기</button></td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
		</div>
		
		</div>
		</div>
		
</div>
</body>
<script>
	$(".wdBtn").on("click", function() {
		var withdrawal = $(this).attr("id");
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/withdrawalProc.do",
			type : "post",
			data : {
				withdrawal : withdrawal
			}
		}).done(function(resp) {
			if(resp == withdrawal){
				alert("탈퇴되었습니다.");
				console.log(withdrawal);
	
			}else{
				alert("탈퇴시키는 것에 실패하셨습니다.");
			}
	
		}).fail(function(a, b, c) {
			console.log(a);
			console.log(b);
			console.log(c);
		});
	});
	$(".cbBtn").on("click", function() {
		var cbMember = $(this).attr("id");
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/toMemberProc.do",
			type : "post",
			data : {
				cbMember : cbMember
			}
		}).done(function(resp) {
			console.log(cbMember);
			if(resp == cbMember){
				alert("멤버로 되살렸습니다.");
				console.log(cbMember);
				location.reload();
			}else{
				console.log(resp);
				alert("멤버 되살리는 것에 실패하셨습니다.");
				location.reload();
			}
		}).fail(function(a, b, c) {
			console.log(a);
			console.log(b);
			console.log(c);
		});
	});
</script>
</html>