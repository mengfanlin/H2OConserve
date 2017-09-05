package com.example.mengfanlin.h2oreserve.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mengfanlin on 18/08/2017.
 */

public class Report implements Serializable {
    public Integer id;
    public String campus;
    public String building;
    public String level;
    public String room;
    public Date date;
    public String description;
    public String status;
    public String encodedImage;

    //default constructor
    public Report(Integer id, String campus, String building, String level, String room, String description, Date date, String status) {
        this.id = id;
        this.status = status;
        this.campus = campus;
        this.building = building;
        this.level = level;
        this.room = room;
        this.description = description;
        this.date = date;
    }

    //constructor for creating report
    public Report(String campus, String building, String level, String room, String description, String encodedImage) {
        this.campus = campus;
        this.building = building;
        this.level = level;
        this.room = room;
        this.description = description;
        this.encodedImage = encodedImage;
    }

    //constructor for modifying report
    public Report(Integer id, String campus, String building, String level, String room, String description) {
        this.id = id;
        this.campus = campus;
        this.building = building;
        this.level = level;
        this.room = room;
        this.description = description;
    }

    public Report() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", campus='" + campus + '\'' +
                ", building='" + building + '\'' +
                ", level='" + level + '\'' +
                ", room='" + room + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", encodedImage='" + encodedImage + '\'' +
                '}';
    }
}
