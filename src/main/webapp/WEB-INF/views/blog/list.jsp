<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h1>전체 게시글</h1>
        <br>
        <table class="table table-hover">
          <thead>
            <tr>
                <th>글번호</th>
                <th>제목</th>
                <th>글쓴이</th>
                <th>작성날짜</th>
                <th>수정날짜</th>
                <th>조회수</th>
            </tr>
          </thead>
          <tbody>
                <c:forEach var="blog" items="${blogList}">
                    <tr>
                        <td>${blog.blogId}</td>
                        <td><a href="/blog/detail/${blog.blogId}">${blog.blogTitle}</a></td>
                        <td>${blog.writer}</a></td>
                        <td>${blog.publishedAt}</td>
                        <td>${blog.updatedAt}</td>
                        <td>${blog.blogCount}</td>
                    </tr>
                </c:forEach>
          </tbody>
        </table>
                <div class="row">
                    <div class="col-1">
                        <a href="/blog/insert" class="btn btn-primary">글쓰기</a>
                    </div>
                </div>
    </div>
</body>
</html>