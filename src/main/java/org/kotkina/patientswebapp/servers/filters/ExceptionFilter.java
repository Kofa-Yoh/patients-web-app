package org.kotkina.patientswebapp.servers.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kotkina.patientswebapp.exceptions.DatabaseException;
import org.kotkina.patientswebapp.exceptions.EntityNotFoundException;
import org.kotkina.patientswebapp.exceptions.PageNotFoundException;
import org.kotkina.patientswebapp.exceptions.PatientControllerActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;

@WebFilter(urlPatterns = {"/*"}, initParams = {
        @WebInitParam(name = "errorHandler", value = "errorHandler", description = "Errors handling")})
public class ExceptionFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionFilter.class);
    private static final String ERROR_PATH = "/WEB-INF/errors/error.jsp";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Throwable throwable) {
            LOGGER.debug("StackTrace: ", throwable);
            LOGGER.error(throwable.getMessage());

            int status;
            String error;
            String message;

            if (throwable instanceof DatabaseException ex) {
                status = HttpServletResponse.SC_BAD_REQUEST;
                error = MessageFormat.format("{0} {1}", HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
                message = ex.getMessage();
            } else if (throwable instanceof PatientControllerActionException ex) {
                status = HttpServletResponse.SC_BAD_REQUEST;
                error = MessageFormat.format("{0} {1}", HttpServletResponse.SC_BAD_REQUEST, "Bad Request");
                message = ex.getMessage();
            } else if (throwable instanceof EntityNotFoundException ex) {
                status = HttpServletResponse.SC_NOT_FOUND;
                error = MessageFormat.format("{0} {1}", HttpServletResponse.SC_NOT_FOUND, "Not Found");
                message = ex.getMessage();
            } else if (throwable instanceof PageNotFoundException ex) {
                status = HttpServletResponse.SC_NOT_FOUND;
                error = MessageFormat.format("{0} {1}", HttpServletResponse.SC_NOT_FOUND, "Not Found");
                message = ex.getMessage();
            } else {
                status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                error = MessageFormat.format("{0} {1}", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
                message = throwable.getMessage();
            }

            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            request.setAttribute("error", error);
            request.setAttribute("message", message);
            response.setStatus(status);
            request.getRequestDispatcher(ERROR_PATH).forward(request, response);
        }
    }
}