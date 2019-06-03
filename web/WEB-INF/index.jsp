<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="fr">
<%@include file="templates/head.jspf"%>
<body class="m-2">
  <%@include file="templates/nav.jspf"%>
  <%--@elvariable id="page" type="string"--%>
  <c:if test="${page == 'login'}">
    <%@include file="pages/login.jspf"%>
  </c:if>
</body>
</html>