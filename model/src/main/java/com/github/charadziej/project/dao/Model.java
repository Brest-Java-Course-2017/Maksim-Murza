package com.github.charadziej.project.dao;

import java.util.Date;
import java.util.Objects;

/**
 * This class describes our entity "Model"
 */
public class Model {

    private Integer modelId;
    private String name;
    private String type;
    private Date releaseDate;

    public Model() {

    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Model(int modelId, String name, String type, Date releaseDate) {
        this.modelId = (Integer) modelId;
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
        Model model = (Model) o;

        return modelId.equals(model.modelId) &&
                name.equals(model.name) &&
                type.equals(model.type) &&
                releaseDate.equals(model.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelId, name, type, releaseDate);
    }

    @Override
    public String toString() {
        return "Model{" +
                "modelId=" + modelId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
