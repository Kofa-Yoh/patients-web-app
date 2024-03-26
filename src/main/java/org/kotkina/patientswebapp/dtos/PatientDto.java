package org.kotkina.patientswebapp.dtos;

public class PatientDto {

    private String id;
    private String surname;
    private String firstname;
    private String patronymic;
    private String birthdate;
    private String gender;
    private String snils;

    public PatientDto() {
    }

    public PatientDto(String surname, String firstname, String patronymic, String birthdate, String gender, String snils) {
        this.surname = surname;
        this.firstname = firstname;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
        this.snils = snils;
    }

    public PatientDto(String id, String surname, String firstname, String patronymic, String birthdate, String gender, String snils) {
        this.id = id;
        this.surname = surname;
        this.firstname = firstname;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
        this.snils = snils;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }
}
