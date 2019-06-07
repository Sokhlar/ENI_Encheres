<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="fr">
<%@include file="templates/head.jspf"%>
<body class="m-2">
  <%@include file="templates/nav.jspf"%>
  <%--@elvariable id="page" type="string"--%>
  <c:if test="${page == 'login'}">
    <%@include file="pages/login.jspf"%>
  </c:if>
  <c:if test="${page == 'createLogin'}">
    <%@include file="pages/createLogin.jspf"%>
  </c:if>
  <c:if test="${page == 'home'}">
    <%@include file="pages/home.jspf"%>
  </c:if>
  <c:if test="${page == 'profile'}">
    <%@include file="pages/profile.jspf"%>
  </c:if>
  <c:if test="${page == 'updateProfile'}">
    <%@include file="pages/updateProfile.jspf"%>
  </c:if>
  <c:if test="${page == 'postAuction'}">
    <%@include file="pages/newAuction.jspf"%>
  </c:if>
</body>
</html>