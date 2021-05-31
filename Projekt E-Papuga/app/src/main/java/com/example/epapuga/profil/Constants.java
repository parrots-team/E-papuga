package com.example.epapuga.profil;

public class Constants {

    //db name
    public static final String DB_NAME = "MY_PARROTS_DB";
    //db version
    public static final int DB_VERSION = 1;
    //table name
    public static final String TABLE_NAME = "MY_PARROTS_TABLE";
    //columns/fields of table
    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_DATE = "DATE";
    public static final String C_FOOD = "FOOD";
    public static final String C_TOY = "TOY";
    public static final String C_WORDS = "WORDS";
    public static final String C_CHARACTER = "CHARACTER";
    public static final String C_ADDED_TIMESTAMP = "ADDED_TIME_STAMP";
    public static final String C_UPDATE_TIMESTAMP = "UPDATE_TIME_STAMP";
    //create table query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_NAME + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_DATE + " TEXT,"
            + C_FOOD + " TEXT,"
            + C_TOY + " TEXT,"
            + C_WORDS + " TEXT,"
            + C_CHARACTER + " TEXT,"
            + C_ADDED_TIMESTAMP + " TEXT,"
            + C_UPDATE_TIMESTAMP + " TEXT"
            + ") ";
}
