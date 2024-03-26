<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/WEB-INF/includes/header.jsp">
    <c:param name="pageName" value="Новый пациент" />
</c:import>

<div class="main">
    <c:import url="/WEB-INF/includes/patient_form.jsp">
        <c:param name="action" value="/patients?action=new" />
        <c:param name="withId" value="false" />
        <c:param name="submitText" value="Создать" />
    </c:import>

    <form action="/patients?action=upload" enctype="multipart/form-data" method="post">
        <table class="for-form">
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td><label>Загрузите пациентов из файла csv</label></td>
            </tr>
            <tr>
                <td><input type="file" name="uploadFile"/></td>
            </tr>
            <tr>
                <td>
                    <c:if test="${!empty uploadResult}"><label class="result success">${uploadResult}</label></c:if>
                </td>
            </tr>
            <tr>
                <td><button type="submit">Загрузить</button></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
