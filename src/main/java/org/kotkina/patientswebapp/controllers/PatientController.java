package org.kotkina.patientswebapp.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.kotkina.patientswebapp.dtos.PatientDto;
import org.kotkina.patientswebapp.entities.Patient;
import org.kotkina.patientswebapp.exceptions.EntityNotFoundException;
import org.kotkina.patientswebapp.exceptions.PageNotFoundException;
import org.kotkina.patientswebapp.exceptions.PatientControllerActionException;
import org.kotkina.patientswebapp.repositories.H2PatientRepository;
import org.kotkina.patientswebapp.repositories.PatientRepository;
import org.kotkina.patientswebapp.services.H2Connection;
import org.kotkina.patientswebapp.services.PatientService;
import org.kotkina.patientswebapp.services.PatientServiceImpl;
import org.kotkina.patientswebapp.utils.PatientUtils;
import org.kotkina.patientswebapp.utils.mapping.PatientMapping;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "patientController", urlPatterns = "/patients")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024,
        maxRequestSize = 1024 * 1024)
public class PatientController extends HttpServlet {

    private PatientService patientService;
    private PatientMapping patientMapping;

    private static final String JSP_PATH_PREFIX = "/WEB-INF/patients";
    public static final String JSP_PATIENT_LIST = JSP_PATH_PREFIX + "/list.jsp";
    public static final String JSP_NEW_PATIENT = JSP_PATH_PREFIX + "/new.jsp";
    public static final String JSP_EDIT_PATIENT = JSP_PATH_PREFIX + "/edit.jsp";
    private static final String SERVLET_PATH = "/patients";
    public static final String PATIENT_FIELD = "patient";
    public static final String PATIENTS_LIST_FIELD = "patients";
    public static final String PATIENT_EDIT_SUCCESS_FIELD = "success";
    public static final String PATIENT_EDIT_ERROR_FIELD = "error";
    public static final String UPLOAD_FILE_RESULT_FIELD = "uploadResult";
    public static final String ERROR_CREATING_MESSAGE = "Ошибка создания пациента. Обратитесь к администратору";
    public static final String ERROR_CHANGING_MESSAGE = "Ошибка изменения пациента. Обратитесь к администратору";
    public static final String ERROR_PATIENT_NOT_FOUNT_MESSAGE = "Пациент с указанным id не найден";
    private static final String ERROR_FIELDS_MESSAGE = "Все поля должны быть заполнены. СНИЛС должен содержать 11 цифр";
    public static final String ERROR_UPLOAD_MESSAGE = "Файл не найден";
    public static final String ERROR_404_MESSAGE = "Страница не найдена";

    public void init() {
        H2Connection h2Connection = new H2Connection();
        PatientRepository patientRepository = new H2PatientRepository(h2Connection.getConnection());
        patientService = new PatientServiceImpl(patientRepository);
        patientMapping = new PatientMapping();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            openPatientListPage(req, resp);
            return;
        }

        switch (action) {
            case "new" -> newPatientGetRequest(req, resp);
            case "edit" -> editPatientGetRequest(req, resp);
            case "delete" -> deletePatientGetRequest(req, resp);
            default -> throw new PageNotFoundException(ERROR_404_MESSAGE + ": " + getUrlWithQueryParams(req));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.removeAttribute(PATIENT_EDIT_SUCCESS_FIELD);
        req.removeAttribute(PATIENT_EDIT_ERROR_FIELD);
        req.removeAttribute("uploadResult");

        String action = req.getParameter("action");
        if (action == null) {
            throw new PageNotFoundException(ERROR_404_MESSAGE + ": " + getUrlWithQueryParams(req));
        }

        switch (action) {
            case "new" -> addPatient(req, resp);
            case "edit" -> editPatient(req, resp);
            case "upload" -> uploadFile(req, resp);
            default -> throw new PageNotFoundException(ERROR_404_MESSAGE + ": " + getUrlWithQueryParams(req));
        }
    }

    public void destroy() {
    }

    private void openPatientListPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Patient> patients;
        patients = patientService.findAll();
        req.setAttribute(PATIENTS_LIST_FIELD, patientMapping.patientListToDtos(patients));
        getServletContext().getRequestDispatcher(JSP_PATIENT_LIST).forward(req, resp);
    }

    private void newPatientGetRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(JSP_NEW_PATIENT).forward(req, resp);
    }

    private void editPatientGetRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Patient patientById = findPatientById(req.getParameter("id"));
        if (patientById == null) {
            throw new EntityNotFoundException(ERROR_PATIENT_NOT_FOUNT_MESSAGE);
        }

        req.setAttribute(PATIENT_FIELD, patientMapping.patientToDto(patientById));
        getServletContext().getRequestDispatcher(JSP_EDIT_PATIENT).forward(req, resp);
    }

    private void deletePatientGetRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            throw new EntityNotFoundException(ERROR_PATIENT_NOT_FOUNT_MESSAGE);
        }

        Patient patientById = findPatientById(req.getParameter("id"));
        if (patientById == null) {
            throw new EntityNotFoundException(ERROR_PATIENT_NOT_FOUNT_MESSAGE);
        }

        patientService.delete(patientById.getId());
        resp.sendRedirect(SERVLET_PATH);
    }

    private void addPatient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PatientDto patientRequest = new PatientDto(req.getParameter("surname"),
                req.getParameter("firstname"),
                req.getParameter("patronymic"),
                req.getParameter("birthdate"),
                req.getParameter("gender"),
                req.getParameter("snils"));

        if (!PatientUtils.checkPatientDto(patientRequest)) {
            req.setAttribute(PATIENT_EDIT_ERROR_FIELD, ERROR_FIELDS_MESSAGE);
            getServletContext().getRequestDispatcher(JSP_NEW_PATIENT).forward(req, resp);
            return;
        }

        if (patientService.create(patientMapping.dtoToPatient(patientRequest))) {
            req.setAttribute(PATIENT_EDIT_SUCCESS_FIELD, "Пациент добавлен");
            getServletContext().getRequestDispatcher(JSP_NEW_PATIENT).forward(req, resp);
            return;
        }

        throw new PatientControllerActionException(ERROR_CREATING_MESSAGE);
    }

    private void editPatient(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Patient patientById = findPatientById(req.getParameter("id"));
        if (patientById == null) {
            throw new EntityNotFoundException(ERROR_PATIENT_NOT_FOUNT_MESSAGE);
        }

        PatientDto patientRequest = new PatientDto(req.getParameter("id"),
                req.getParameter("surname"),
                req.getParameter("firstname"),
                req.getParameter("patronymic"),
                req.getParameter("birthdate"),
                req.getParameter("gender"),
                req.getParameter("snils")
        );

        if (!PatientUtils.checkPatientDto(patientRequest)) {
            req.setAttribute(PATIENT_EDIT_ERROR_FIELD, ERROR_FIELDS_MESSAGE);
            req.setAttribute(PATIENT_FIELD, patientRequest);
            getServletContext().getRequestDispatcher(JSP_EDIT_PATIENT).forward(req, resp);
            return;
        }

        if (patientService.update(patientById.getId(), patientMapping.dtoToPatient(patientRequest))) {
            req.setAttribute(PATIENT_EDIT_SUCCESS_FIELD, "Данные изменены");
            req.setAttribute(PATIENT_FIELD, patientMapping.patientToDto(patientService.findById(patientById.getId())));
            getServletContext().getRequestDispatcher(JSP_EDIT_PATIENT).forward(req, resp);
            return;
        }

        throw new PatientControllerActionException(ERROR_CHANGING_MESSAGE);
    }

    private void uploadFile(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part uploadFile = req.getPart("uploadFile");
        if (uploadFile == null) {
            throw new PatientControllerActionException(ERROR_UPLOAD_MESSAGE);
        }

        int result = 0;

        List<PatientDto> patientDtos = PatientUtils.uploadFromFile(uploadFile.getInputStream());
        if (patientDtos != null && patientDtos.size() > 0) {
            result = patientService.batchCreate(patientMapping.dtosToPatientList(patientDtos));
        }

        req.setAttribute(UPLOAD_FILE_RESULT_FIELD, "Загружено пациентов: " + result);
        getServletContext().getRequestDispatcher(JSP_NEW_PATIENT).forward(req, resp);
    }

    private Patient findPatientById(String id) {
        try {
            long patientId = Long.parseLong(id);
            return patientService.findById(patientId);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String getUrlWithQueryParams(HttpServletRequest req) {
        return req.getQueryString() == null ? req.getRequestURL().toString() :
                req.getRequestURL().append("?").append(req.getQueryString()).toString();
    }
}