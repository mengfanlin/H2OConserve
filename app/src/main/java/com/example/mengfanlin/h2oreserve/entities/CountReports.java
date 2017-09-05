package com.example.mengfanlin.h2oreserve.entities;

/**
 * Created by mengfanlin on 5/9/17.
 */

import java.io.Serializable;

public class CountReports
        implements Serializable
{
    public String building;
    public Integer count;

    public CountReports(String building, Integer count)
    {
        this.building = building;
        this.count = count;
    }

    public String getBuilding()
    {
        return this.building;
    }

    public Integer getCount()
    {
        return this.count;
    }

    public void setBuilding(String building)
    {
        this.building = building;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CountReports{" +
                "building='" + building + '\'' +
                ", count=" + count +
                '}';
    }
}