package com.github.charadziej.project.dao;

import java.util.Objects;

/**
 * This class describes our entity "HardwareType"
 */
public class HardwareType {

    private Integer typeId;
    private String typeName;
    private Integer quantity;

    public HardwareType() {

    }

    public HardwareType(Integer typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public HardwareType(Integer typeId, String typeName, Integer quantity) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.quantity = quantity;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HardwareType type = (HardwareType) o;

        return typeId.equals(type.typeId) &&
                typeName.equals(type.typeName) &&
                quantity.equals(type.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, typeName, quantity);
    }

    @Override
    public String toString() {
        return "HardwareType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
