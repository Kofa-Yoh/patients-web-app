package org.kotkina.patientswebapp.exceptions;

public class PatientControllerActionException extends RuntimeException {

    public PatientControllerActionException(String message) {
        super(message);
    }
}
