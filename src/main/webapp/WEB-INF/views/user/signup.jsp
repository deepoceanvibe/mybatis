<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

</head>
<body>
    <div class=".container">
        <form action="/signup" method="POST">
            <div class="col-3">
            아이디 : <input type="text" name="loginId" placeholder="아이디" required/>
            </div>
            <div class="col-3">
            이메일 : <input type="email" name="email" placeholder="이메일" required/>
            </div>
            <div class="col-3">
            비밀번호 : <input type="password" name="password" placeholder="비밀번호"required/>
            </div>
            <div class="col-3">
            비밀번호 재입력 : <input type="password" placeholder="비밀번호 재입력"required/>
            </div>
            <input type="submit" value="회원가입">
        </form>
    </div>
</body>
</html>