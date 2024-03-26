package org.kotkina.patientswebapp.repositories;

import org.kotkina.patientswebapp.entities.Patient;

import java.util.List;

public interface PatientRepository {

    Patient findById(Long id);

    int create(Patient patient);

    int update(Long id, Patient patient);

    int delete(Long id);

    List<Patient> findAll();

    int[] batchCreate(List<Patient> patients);
}
