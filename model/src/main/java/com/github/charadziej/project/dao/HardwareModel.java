package com.github.charadziej.project.dao;

import java.util.Date;
import java.util.Objects;

/**
 * This class describes our entity "HardwareModel"
 */
public class HardwareModel {

    private Integer id;
    private String name;
    private String type;
    private Date releaseDate;

    public HardwareModel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HardwareModel(int id, String name, String type, Date releaseDate) {
        this.id = (Integer) id;
        this.name = name;
        this.type = type;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HardwareModel model = (HardwareModel) o;

        return id.equals(model.id) &&
                name.equals(model.name) &&
                type.equals(model.type) &&
                releaseDate.equals(model.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, releaseDate);
    }

    @Override
    public String toString() {
        return "HardwareModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
