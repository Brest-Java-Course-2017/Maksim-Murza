package com.github.charadziej.project.dao;

import java.util.Date;
import java.util.Objects;

/**
 * This class describes our entity "HardwareModel"
 */
public class HardwareModel {

    private Integer modelId;
    private String modelName;
    private String modelType;
    private Date releaseDate;

    public HardwareModel() {

    }

    public HardwareModel(int modelId, String modelName, String modelType, Date releaseDate) {
        this.modelId = (Integer) modelId;
        this.modelName = modelName;
        this.modelType = modelType;
        this.releaseDate = releaseDate;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
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

        return modelId.equals(model.modelId) &&
                modelName.equals(model.modelName) &&
                modelType.equals(model.modelType) &&
                releaseDate.equals(model.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelId, modelName, modelType, releaseDate);
    }

    @Override
    public String toString() {
        return "HardwareModel{" +
                "modelId=" + modelId +
                ", modelName='" + modelName + '\'' +
                ", modelType='" + modelType + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}