package com.github.charadziej.project.dao;

import java.util.Objects;

/**
 * This class describes our entity "Type"
 */
public class Type {

    private Integer typeId;
    private String name;

    public Type() {

    }

    public Type(int typeId, String name) {
        this.typeId = (Integer) typeId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;

        return typeId.equals(type.typeId) &&
                name.equals(type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, name);
    }

    @Override
    public String toString() {
        return "Type{" +
                "typeId=" + typeId +
                ", name='" + name + '\'' +
                '}';
    }
}
