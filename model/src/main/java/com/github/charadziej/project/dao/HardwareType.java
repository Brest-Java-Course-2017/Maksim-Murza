package com.github.charadziej.project.dao;

import java.util.Objects;

/**
 * This class describes our entity "HardwareType"
 */
public class HardwareType {

    private Integer id;
    private String name;
    private Integer quantity;


    public HardwareType() {

    }

    public HardwareType(Integer id, String name, Integer quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

        return id.equals(type.id) &&
                name.equals(type.name) &&
                quantity.equals(type.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity);
    }

    @Override
    public String toString() {
        return "HardwareType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
