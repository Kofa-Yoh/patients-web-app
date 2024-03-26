package org.kotkina.patientswebapp.services;

import org.kotkina.patientswebapp.entities.Patient;
import org.kotkina.patientswebapp.repositories.PatientRepository;

import java.util.Arrays;
import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public boolean create(Patient patient) {
        int result = patientRepository.create(patient);
        return result > 0;
    }

    @Override
    public boolean update(Long id, Patient patient) {
        int result = patientRepository.update(id, patient);
        return result > 0;
    }

    @Override
    public boolean delete(Long id) {
        int result = patientRepository.delete(id);
        return result > 0;
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public int batchCreate(List<Patient> patients) {
        int[] result = patientRepository.batchCreate(patients);
        return Arrays.stream(result).sum();
    }
}
