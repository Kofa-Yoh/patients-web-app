package org.kotkina.patientswebapp.utils;

import org.kotkina.patientswebapp.dtos.PatientDto;
import org.kotkina.patientswebapp.entities.Patient;
import org.kotkina.patientswebapp.utils.mapping.PatientMapping;

import java.io.InputStream;
import java.util.List;

public class PatientUtils {

    private static final PatientMapping patientMapping = new PatientMapping();

    public static PatientDto createPatientDto(String id, String surname, String firstname, String patronymic, String birthdate, String gender, String snils) {
        if (id == null || id.isEmpty()) {
            return new PatientDto(surname, firstname, patronymic, birthdate, gender, snils);
        } else {
            return new PatientDto(id, surname, firstname, patronymic, birthdate, gender, snils);
        }
    }

    public static PatientDto createPatientDto(String line) {
        String[] values = line.split(";");
        if (values.length == 6) {
            return createPatientDto(null, values[0], values[1], values[2], values[3], values[4], values[5]);
        }
        return null;
    }

    public static boolean checkPatientDto(PatientDto patientDto) {
        Patient patient;
        try {
            patient = patientMapping.dtoToPatient(patientDto);
        } catch (Exception e) {
            return false;
        }

        if (patient == null) return false;

        if (patient.getSurname() == null || patient.getSurname().isEmpty()) return false;
        if (patient.getFirstname() == null || patient.getFirstname().isEmpty()) return false;
        if (patient.getPatronymic() == null || patient.getPatronymic().isEmpty()) return false;
        if (patient.getGender() == null) return false;
        if (patient.getBirthdate() == null) return false;
        if (patient.getSnils() == null || patient.getSnils().isEmpty() || patient.getSnils().length() != 11)
            return false;

        return true;
    }

    public static List<PatientDto> uploadFromFile(InputStream inputStream) {
        List<PatientDto> list = FileUtils.parseFile(inputStream, PatientUtils::createPatientDto);
        return list.stream().filter(PatientUtils::checkPatientDto).toList();
    }
}
