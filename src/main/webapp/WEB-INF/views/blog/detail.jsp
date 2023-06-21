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
    // 1. 글 구성에 필요한 글 번호를 자바스크립트 변수에 저장
    let blogId = "${blog.blogId}";

    // <%-- 3. 비동기 처리방식 : blogId를 받아 전체 데이터를 JS 내부로 가져오는 함수 선언 (blogId=전역변수 id=지역변수)
    // jsp파일에서 js문법을 쓸 때는 ${}앞에 \를 붙여주자 --%>
    function getAllReplies(id) {
            let url = `http://localhost:8080/reply/\${id}/all`;
            let str = "";                   // 받아온 json을 표현할 html 코드를 저장할 문자열 선언

            fetch(url, {method:'get'})      // get 방식으로 위 주소에 요청넣기
            .then((res) => res.json())      // 응답받은 요소 중 res에 담아서 -> json만 뽑기
            .then(replies => {              // 뽑아온 json으로 -> data에 담아서 처리하기

                console.log(replies);       // 배열

                // for(reply of replies) {     
                //    console.log(reply);     // 배열 안에 있는 댓글들 for문으로 풀기 -> str에 저장
                //    str += `<h3>글쓴이 : \${reply.replyWriter}, 댓글내용 : \${reply.replyContent}</h3>`;
                //}
                //console.log(str);

                
                // .map을 이용한 간결한 반복문
                replies.map((reply, i) => {    // 첫 파라미터 : 반복대상자료, 두번째 파라미터 : 순번
                    str += `<h3>\${i+1}번째 댓글 || 글쓴이 : \${reply.replyWriter}, 댓글내용 : \${reply.replyContent}</h3>`;
                });

                console.log(str);
                // #replies 요소를 변수에 저장한다.
                const $replies = document.getElementById("replies");
                // #replies의 innerHTML에 str을 대입해 실제 화면에 출력하기
                $replies.innerHTML = str;



           });
    }

    // 2. 함수 호출
    getAllReplies(blogId);

    </Script>
















</body>
</html>