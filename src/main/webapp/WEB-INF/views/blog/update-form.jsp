<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <form action="/blog/update-form" method="POST">

        <div class="row">
            <div class="col-3">
            <label for="writer" class="form-label">글쓴이</label>
            <input type="text" class="form-control" id="writer" value="${blog.writer}" placeholder="글쓴이를 적어주세요." name="writer">
            </div>

            <div class="col-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" value="${blog.blogTitle}" placeholder="제목을 적어주세요." name="blogTitle">
            </div>
        </div>

        <div class="row">
            <div class="col-6">
            <label for="content" class="form-label">본문</label>
            <textarea class="form-control" id="content" value="${blog.blogContent}" rows="10" name="blogContent">${blog.blogContent}</textarea>
        </div>

        <div class="row">
            <div class="col-4">
            <form action="/blog/update" method="POST"> <!--왜안될까??? 쉣..-->
            <input type="submit" class="btn btn-primary" value="제출">
            </form>     
            </div>
        </div>

        </form>
    </div>
</body>
</html>