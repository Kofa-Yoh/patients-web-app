<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action=${param.action} method="post" accept-charset="UTF-8">
    <table class="for-form">
        <tr>
            <td>&nbsp;</td>
        </tr>
        <c:if test="${param.withId == 'true'}">
            <tr>
                <td><label>Номер</label></td>
                <td>
                    <input name="number" value="${patient.id}" type="number" disabled></td>
            </tr>
        </c:if>
        <tr>
            <td><label>Фамилия</label></td>
            <td><input name="surname" type="text" value="${patient.surname}" required></td>
        </tr>
        <tr>
            <td><label>Имя</label></td>
            <td><input name="firstname" type="text" value="${patient.firstname}" required></td>
        </tr>
        <tr>
            <td><label>Отчество</label></td>
            <td><input name="patronymic" type="text" value="${patient.patronymic}" required></td>
        </tr>
        <tr>
            <td><label>Дата рождения</label></td>
            <td><input name="birthdate" type="date" value="${patient.birthdate}" required></td>
        </tr>
        <tr>
            <td><label>СНИЛС (11 цифр)</label></td>
            <td><input name="snils" type="text" value="${patient.snils}" pattern="\d{11}" size="11" required></td>
        </tr>
        <tr>
            <td><label>Пол</label></td>
            <td>
                <input name="gender" id="female" type="radio"
                       value="женский" ${patient.gender != 'мужской' ? 'checked' : ''}>
                <label for="female">женский</label>
                <input name="gender" id="male" type="radio" value="мужской" ${patient.gender == 'мужской' ? 'checked' : ''}>
                <label for="male">мужской</label>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${!empty success}"><label class="result success">${success}</label></c:if>
                <c:if test="${!empty error}"><label class="result error">${error}</label></c:if>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="submit">${param.submitText}</button>
            </td>
        </tr>
    </table>
</form>
