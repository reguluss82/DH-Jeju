<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<% 
String context = request.getContextPath();
%>
<body>
									<div class="modal-header">
										<div class="modal-title d-flex">
											<img class="mt-1 mb-1 img-fluid rounded-circle" alt="회원이미지" src="dh_images/user.png" style="height: 30px; margin-right: 5px;">
											<span class="mt-2">${commu.user_id }</span>
											<!-- dropdown menu는 작성자만 수정 삭제 접근가능, else alert("작성자만 수정 삭제 가능합니다.") -->
											<div class="dropdown ms-auto">
								  				<button class="btn btn-icon-only" type="button" data-bs-toggle="dropdown" aria-expanded="false">
								  					<span class="bi bi-three-dots-vertical"></span>
								  				</button>
								  				<ul class="dropdown-menu">
								    				<li><a class="dropdown-item" href="#">수정</a></li>
								   					<li><a class="dropdown-item" href="#">삭제</a></li>
								  				</ul>
											</div>
										</div>
								        
								        <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
								        	<span aria-hidden="true">&times;</span>
								        </button>
								    </div>
								    <div class="modal-body container justify-content-md-center">
								    	<c:forEach var="image" items="${imgList }">
								    		<div class="row"> <img class="rounded" src="<%=context %>/${image.c_img_path }"></div>
								    	</c:forEach>
								    </div>
								    <div class="modal-footer">
									    <div class="container">
										    <div class="row mt-2">
												<p class="col">${commu.c_content }</p>
											</div>
											<div class="row d-flex">
												<p class="col mt-2">##${commu.c_hash }</p>
												<span class="col ms-auto mt-2">${commu.c_date }</span>
											</div>
										</div>
								    </div>
</body>
</html>