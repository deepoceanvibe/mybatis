<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    div {
        border:1px solid black;
    }
</style>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<div class = "container">
    <div class="row first-row">
        <div class="col-1">글번호</div>
        <div class="col-1">${blog.blogId}</div>
        <div class="col-2">제목</div>
        <div class="col-8">${blog.blogTitle}</div>
        <div class="col-2">작성자</div>
        <div class="col-2">${blog.writer}</div>
        <div class="col-2">조회수</div>
        <div class="col-2">${blog.blogCount}</div>
    </div>
    <div class="row second-row">
        <div class="col-1">작성일</div>
        <div class="col-5">${blog.publishedAt}</div>
        <div class="col-1">수정일</div>
        <div class="col-5">${blog.updatedAt}</div>
    </div>
    <div class="row third-row">
        <div class="col-1">본문</div>
        <div class="col-11">${blog.blogContent}</div>
  
    </div>

   



    <form action="/blog/delete" method="POST">
        <input type = "hidden" name = "blogId" value = "${blog.blogId}">
        <input type = "submit" class = "btn btn-danger" value = "삭제">
    </form>
    <form action="/blog/update-form" method="POST">
        <input type = "hidden" name = "blogId" value = "${blog.blogId}">
        <input type = "submit" class="btn btn-secondary" value = "수정">
    </form>
    <a href = "/blog/list" class="btn btn-secondary"> 목록으로 </a>
    </div>
</body>
</html>