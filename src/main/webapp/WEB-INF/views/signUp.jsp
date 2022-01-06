<%--
  Created by IntelliJ IDEA.
  User: thddb
  Date: 2021-12-31
  Time: 오전 12:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method ={RequestParam.POST}>
    <!-- 아이디 -->
    <div>
        <label for="account">아이디</label>
        <input type="text" class="form-control" id="account" name="account" placeholder="ID" required>
        <div class="check_font" id="id_check"></div>
    </div>
    <!-- 비밀번호 -->
    <div>
        <label for="password">비밀번호</label>
        <input type="password" class="form-control" id="password" name="password" placeholder="PASSWORD" required>
        <div class="check_font" id="pw_check"></div>
    </div>
<%--    <!-- 비밀번호 재확인 -->--%>
<%--    <div>--%>
<%--        <label for="password2">비밀번호 확인</label>--%>
<%--        <input type="password" class="form-control" id="password2" name="password2" placeholder="Confirm Password" required>--%>
<%--        <div class="check_font" id="pw2_check"></div>--%>
<%--    </div>--%>
    <!-- 이름 -->
    <div>
        <label for="name">이름</label>
        <input type="text" class="form-control" id="name" name="name" placeholder="Name" required>
        <div class="check_font" id="name_check"></div>
    </div>
    <!-- 본인확인 이메일 -->
    <div>
        <label for="email">이메일</label>
        <input type="text" class="form-control" name="email" id="email" placeholder="E-mail" required>
        <div class="check_font" id="email_check"></div>
    </div>
    <div>
        <button class="btn btn-primary px-3" id="reg_submit">
            <i class="fa fa-heart pr-2" aria-hidden="true"></i>가입하기
        </button>
    </div>
</form>
</body>
</html>
