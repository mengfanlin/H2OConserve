package com.example.mengfanlin.h2oreserve.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mengfanlin on 18/08/2017.
 */

public class Report implements Serializable {
    public Integer id;
    public String user;
    public String campus;
    public String building;
    public String level;
    public String room;
    public Date date;
    public String description;

    public Report(Integer id, String user, String campus, String building, String level, String room, String description, Date date) {
        this.id = id;
        this.user = user;
        this.campus = campus;
        this.building = building;
        this.level = level;
        this.room = room;
        this.description = description;
        this.date = date;
    }

    public Report(String user, String campus, String building, String level, String room, String description, Date date) {
        this.user = user;
        this.campus = campus;
        this.building = building;
        this.level = level;
        this.room = room;
        this.description = description;
        this.date = date;
    }

    public Report() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", campus='" + campus + '\'' +
                ", building='" + building + '\'' +
                ", level='" + level + '\'' +
                ", room='" + room + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
