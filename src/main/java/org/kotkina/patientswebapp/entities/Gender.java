package org.kotkina.patientswebapp.entities;

public enum Gender {
    MALE((byte) 0, "мужской"), FEMALE((byte) 1, "женский");

    private byte dbNum;

    private String print;

    Gender(byte dbNum, String print) {
        this.dbNum = dbNum;
        this.print = print;
    }

    public static Gender getGender(byte dbNum) {
        switch (dbNum) {
            case 0 -> {
                return Gender.MALE;
            }
            case 1 -> {
                return Gender.FEMALE;
            }
        }

        return null;
    }

    public byte getDbNum() {
        return dbNum;
    }

    public String getPrint() {
        return print;
    }
}
