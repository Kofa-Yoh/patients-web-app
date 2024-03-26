package org.kotkina.patientswebapp.utils.mapping;

import org.kotkina.patientswebapp.dtos.PatientDto;
import org.kotkina.patientswebapp.entities.Gender;
import org.kotkina.patientswebapp.entities.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PatientMapping {

    public Patient dtoToPatient(PatientDto dto) {
        if (dto == null) return null;

        Patient patient = new Patient();
        if (notEmpty(dto.getId())) {
            try {
                patient.setId(Long.parseLong(dto.getId()));
            } catch (NumberFormatException e) {
            }
        }
        try {
            patient.setBirthdate(LocalDate.parse(dto.getBirthdate()));
        } catch (DateTimeParseException e) {
        }

        patient.setSurname(dto.getSurname());
        patient.setFirstname(dto.getFirstname());
        patient.setPatronymic(dto.getPatronymic());
        patient.setSnils(dto.getSnils());
        String gender = dto.getGender();
        if (gender != null) {
            switch (gender) {
                case "мужской":
                case "м":
                case "0": {
                    patient.setGender(Gender.MALE);
                    break;
                }
                case "женский":
                case "ж":
                case "1": {
                    patient.setGender(Gender.FEMALE);
                    break;
                }
            }
        }
        return patient;
    }

    public PatientDto patientToDto(Patient patient) {
        if (patient == null) return null;

        return new PatientDto(
                Long.toString(patient.getId()),
                patient.getSurname(),
                patient.getFirstname(),
                patient.getPatronymic(),
                patient.getBirthdate().toString(),
                patient.getGender() != null ? patient.getGender().getPrint() : "",
                patient.getSnils()
        );
    }

    public List<Patient> dtosToPatientList(List<PatientDto> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(dto -> dtoToPatient(dto))
                .toList();
    }

    public List<PatientDto> patientListToDtos(List<Patient> patients) {
        if (patients == null) return null;

        return patients.stream()
                .map(patient -> patientToDto(patient))
                .toList();
    }

    private boolean notEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
