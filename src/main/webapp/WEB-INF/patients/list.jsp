<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:import url="/WEB-INF/includes/header.jsp">
    <c:param name="pageName" value="Список пациентов" />
</c:import>

<div class="main">
    <c:set var="patients" value="${requestScope.patients}" />
    <table class="list">
        <thead>
            <tr>
                <th>№</th>
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Отчество</th>
                <th>Дата рождения</th>
                <th>Пол</th>
                <th>СНИЛС</th>
                <th colspan=2 class="colored"> Всего: ${fn:length(patients)}</th>
            </tr>
        </thead>
        <c:if test="${not empty patients}">
            <c:forEach var="patient" items="${patients}">
                <tr>
                <td>${patient.id}</td>
                <td>${patient.surname}</td>
                <td>${patient.firstname}</td>
                <td>${patient.patronymic}</td>
                <td>${patient.birthdate}</td>
                <td>${patient.gender}</td>
                <td>${patient.snils}</td>
                <td class="for-button"><a href="/patients?id=${patient.id}&action=edit">
                <svg width="18px" height="18px" viewBox="0 0 24 24"><path fill-rule="evenodd" clip-rule="evenodd" d="m3.99 16.854-1.314 3.504a.75.75 0 0 0 .966.965l3.503-1.314a3 3 0 0 0 1.068-.687L18.36 9.175s-.354-1.061-1.414-2.122c-1.06-1.06-2.122-1.414-2.122-1.414L4.677 15.786a3 3 0 0 0-.687 1.068zm12.249-12.63 1.383-1.383c.248-.248.579-.406.925-.348.487.08 1.232.322 1.934 1.025.703.703.945 1.447 1.025 1.934.058.346-.1.677-.348.925L19.774 7.76s-.353-1.06-1.414-2.12c-1.06-1.062-2.121-1.415-2.121-1.415z" fill="#fa4251"></path></svg>
                </a></td>
                <td class="for-button"><a href="/patients?id=${patient.id}&action=delete">
                <svg width="18px" height="18px" viewBox="0 0 1024 1024"><path fill="#fa4251" d="M352 192V95.936a32 32 0 0 1 32-32h256a32 32 0 0 1 32 32V192h256a32 32 0 1 1 0 64H96a32 32 0 0 1 0-64h256zm64 0h192v-64H416v64zM192 960a32 32 0 0 1-32-32V256h704v672a32 32 0 0 1-32 32H192zm224-192a32 32 0 0 0 32-32V416a32 32 0 0 0-64 0v320a32 32 0 0 0 32 32zm192 0a32 32 0 0 0 32-32V416a32 32 0 0 0-64 0v320a32 32 0 0 0 32 32z"></path></svg>
                </a></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
</body>
</html>