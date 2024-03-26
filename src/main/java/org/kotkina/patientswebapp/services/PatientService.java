package org.kotkina.patientswebapp.services;

import org.kotkina.patientswebapp.entities.Patient;

import java.util.List;

public interface PatientService {

    Patient findById(Long id);

    boolean create(Patient patient);

    boolean update(Long id, Patient patient);

    boolean delete(Long id);

    List<Patient> findAll();

    int batchCreate(List<Patient> patients);
}
