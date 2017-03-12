package com.github.charadziej.project.dao;

import org.springframework.dao.DataAccessException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * DAO interface of HardwareType
 */
public interface HardwareTypeDao {

    /**
     * Get all types list.
     *
     * @return all types list
     */
    List<Type> getAllTypes() throws DataAccessException;

    /**
     * Get type by id.
     *
     * @param typeId type identifier.
     * @return type.
     */
    Type getTypeById(Integer typeId) throws DataAccessException;

    /**
     * Get type by name.
     *
     * @param typeName type name.
     * @return type.
     */
    Type getTypeByName(String typeName) throws DataAccessException;

    /**
     * Get quantity of models, which are belong to selected type.
     *
     * @param typeId type identifier.
     * @return quantity.
     */
    Integer getQuantity(Integer typeId) throws DataAccessException;

    /**
     * Add new type.
     *
     * @param type
     * @return id.
     */
    int addType(HardwareType type) throws DataAccessException;

    /**
     * Update type.
     *
     * @param type
     * @return id.
     */
    int updateType(HardwareType type) throws DataAccessException;

    /**
     * Delete type.
     *
     * @param type
     * @return id.
     */
    int deleteType(HardwareType type) throws DataAccessException;
}
