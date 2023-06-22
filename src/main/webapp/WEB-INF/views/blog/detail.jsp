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
<bo>
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
    <div class="row">
        <!-- 비동기 form의 경우는 목적지로 이동하지 않고 페이지 내에서 처리가 되므로
        action을 가지지 않습니다. 그리고 제출버튼도 제출기능을 막고 fetch 요청만 넣는다. -->
        
            <div class="col-1">
                <input type="text" class="form-control" id="replyWriter" name="replyWriter">
            </div>
            <div class="col-6">
                <input type="text" class="form-control" id="replyContent" name="replyContent">
            </div>
            <div class="col-1">
                <button class="btn btn-primary" id="replySubmit">댓글쓰기</button>
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
                    str += 
                        `<h3>\${i+1}번째 댓글 || 글쓴이 : \${reply.replyWriter}, 댓글내용 : \${reply.replyContent}
                        <span class="deleteReplyBtn" data-replyId="\${reply.replyId}">[삭제]</span>
                        </h3>`;
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


    //  해당 함수 실행시 비동기 폼에 작성된 글쓴이, 내용으로 댓글 입력
    function insertReply() {
        let url = `http://localhost:8080/reply`;

        if(document.getElementById("replyWriter").value.trim() === "") {
            alert("글쓴이를 작성해주세요.");
            return;
        }
        if(document.getElementById("replyContent").value.trim() === "") {
            alert("본문을 작성해주세요.");
            return;
        }
        fetch(url, {
                    method: 'post',                         // POST 방식으로
                    headers: {
                        "Content-Type": "application/json",     
                    },
                    body: JSON.stringify({                  // 요청 내용 담아보내기
                        replyWriter: document.getElementById("replyWriter").value,
                        replyContent: document.getElementById("replyContent").value,
                        blogId: "${blog.blogId}",

                    }), // insert 로직이기 때문에 response에 응답코드 외에 보내줄껀 없음
                }).then(() => {

                        // 댓글 작성 후 폼에 작성되어있던 내용 지우기
                    document.getElementById("replyWriter").value = "";
                    document.getElementById("replyContent").value = "";
                    alert("댓글 작성이 완료되었습니다!");
                    getAllReplies(blogId);
                });
    }
    


    // 버튼 누르면 비동기요청 연결하기
    $replySubmit = document.getElementById("replySubmit");
    $replySubmit.addEventListener("click", insertReply);

    // 클릭한 요소가 #replies의 자손 태그인 .deleteReplyBtn가 맞는지 검사 후, 맞으면 삭제
    const $replies = document.querySelector("#replies");
    $replies.onclick = (e) => {
        if(!e.target.matches('#replies .deleteReplyBtn')) {
            return;

            // 클릭이벤트 객체 e의 target 속성의 dataset 속성 내부에 댓글번호가 있으므로 확인하는 디버깅
            console.log(e.target.dataset['replyId']);

            

            const replyId = e.target.dataset['replyid'];

            // confirm : y / n 경고창 띄움
            if(confirm("정말로 삭제하시겠어요?")){

                let url = `http://localhost:8080/reply/\${replyId}`;
                fetch(url, { method: 'delete' })                        
                .then(() => {
                    
                    alert("댓글이 잘 삭제됐습니다!");
                    getAllReplies(blogId);
                });

            }
        }

    }


    </Script>


</body>
</html>