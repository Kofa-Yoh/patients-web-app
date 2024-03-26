package org.kotkina.patientswebapp.entities;

import java.time.LocalDate;

public class Patient {

    private Long id;
    private String surname;
    private String firstname;
    private String patronymic;
    private LocalDate birthdate;
    private Gender gender;
    private String snils;

    public Patient() {
    }

    public Patient(String surname, String firstname, String patronymic, LocalDate birthdate, Gender gender, String snils) {
        this.surname = surname;
        this.firstname = firstname;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
        this.snils = snils;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }
}
