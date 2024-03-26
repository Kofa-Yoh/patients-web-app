<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/WEB-INF/includes/header.jsp">
    <c:param name="pageName" value="Изменение" />
</c:import>

<div class="main">
    <c:import url="/WEB-INF/includes/patient_form.jsp">
        <c:param name="action" value="/patients?id=${patient.id}&action=edit" />
        <c:param name="withId" value="true" />
        <c:param name="submitText" value="Сохранить" />
        <c:param name="patient" value="${patient}" />
    </c:import>
</div>
</body>
</html>
