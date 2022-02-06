package com.example.kalashnikovconcerntest.data.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


@Entity(tableName = "Author")
public class Author implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "birth_date")
    private Long birthDate;

    public Author(int id, String name, long birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }
}
