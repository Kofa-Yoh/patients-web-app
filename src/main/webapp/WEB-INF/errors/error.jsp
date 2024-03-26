<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/WEB-INF/includes/header.jsp">
    <c:param name="pageName" value="Ошибка" />
</c:import>

<div class="main">
    <h1>${error}</h1>
    <p>${message}</p>
    <a href="/patients">Перейти на главную</a>
</div>
</body>
</html>
