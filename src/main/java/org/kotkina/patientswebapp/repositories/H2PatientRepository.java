package org.kotkina.patientswebapp.repositories;

import org.kotkina.patientswebapp.entities.Gender;
import org.kotkina.patientswebapp.entities.Patient;
import org.kotkina.patientswebapp.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2PatientRepository implements PatientRepository {

    private final Connection connection;

    public H2PatientRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Patient findById(Long id) {
        String sql = "SELECT id, surname, firstname, patronymic, birthdate, gender, snils " +
                " FROM patient WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Patient patient = null;

            while (resultSet.next()) {
                patient = createPatientFromResultSet(resultSet);
            }
            resultSet.close();

            return patient;
        } catch (SQLException | NullPointerException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int create(Patient patient) {
        String sql = "INSERT INTO patient (surname, firstname, patronymic, birthdate, gender, snils)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient.getSurname());
            statement.setString(2, patient.getFirstname());
            statement.setString(3, patient.getPatronymic());
            statement.setDate(4, Date.valueOf(patient.getBirthdate()));
            statement.setByte(5, patient.getGender().getDbNum());
            statement.setString(6, patient.getSnils());

            return statement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int update(Long id, Patient patient) {
        String sql = "UPDATE patient SET (surname, firstname, patronymic, birthdate, gender, snils)" +
                " = (?, ?, ?, ?, ?, ?) WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient.getSurname());
            statement.setString(2, patient.getFirstname());
            statement.setString(3, patient.getPatronymic());
            statement.setDate(4, Date.valueOf(patient.getBirthdate()));
            statement.setByte(5, patient.getGender().getDbNum());
            statement.setString(6, patient.getSnils());
            statement.setLong(7, id);

            return statement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM patient WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            return statement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Patient> findAll() {
        String sql = "SELECT id, surname, firstname, patronymic, birthdate, gender, snils FROM patient";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<Patient> patients = new ArrayList<>();
            while (resultSet.next()) {
                patients.add(createPatientFromResultSet(resultSet));
            }

            return patients;
        } catch (SQLException | NullPointerException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public int[] batchCreate(List<Patient> patients) {
        String sql = "INSERT INTO patient (surname, firstname, patronymic, birthdate, gender, snils)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Patient patient : patients) {
                statement.setString(1, patient.getSurname());
                statement.setString(2, patient.getFirstname());
                statement.setString(3, patient.getPatronymic());
                statement.setDate(4, Date.valueOf(patient.getBirthdate()));
                statement.setByte(5, patient.getGender().getDbNum());
                statement.setString(6, patient.getSnils());
                statement.addBatch();
            }

            return statement.executeBatch();
        } catch (SQLException | NullPointerException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private Patient createPatientFromResultSet(ResultSet resultSet) throws SQLException {
        Patient patient = new Patient();
        patient.setId(resultSet.getLong("id"));
        patient.setSurname(resultSet.getString("surname"));
        patient.setFirstname(resultSet.getString("firstname"));
        patient.setPatronymic(resultSet.getString("patronymic"));
        patient.setBirthdate(resultSet.getDate("birthdate").toLocalDate());
        patient.setGender(Gender.getGender(resultSet.getByte("gender")));
        patient.setSnils(resultSet.getString("snils"));
        return patient;
    }
}
