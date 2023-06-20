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

    
    <div class="row">
        <div id="replies">
        </div>
    </div>

    <Script>
    // 글 구성에 필요한 글 번호를 자바스크립트 변수에 저장
    let blogId = "${blog.blogId}";

    // blogId를 받아 전체 데이터를 JS 내부로 가져오는 함수 선언
    function getAllReplies(blogId) {
            let url = `http://localhost:8080/reply/${blogId}/all`;
            fetch(url, {method:'get'})      // get 방식으로 위 주소에 요청넣기
            .then((res) => res.json())      // 응답받은 요소 중 json만 뽑기 (res)
            .then(data => {                 // 뽑아온 json으로 처리작업 하기 (res -> data)
                console.log(data);
           });
    }

    // 함수 호출
    getAllReplies(blogId);

    </Script>
















</body>
</html>