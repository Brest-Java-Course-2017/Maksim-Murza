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
     * @param name type name.
     * @return type.
     */
    Type getTypeByName(String name) throws DataAccessException;

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
    int addType(Type type) throws DataAccessException;

    /**
     * Update type.
     *
     * @param type
     * @return id.
     */
    int updateType(Type type) throws DataAccessException;

    /**
     * Delete type.
     *
     * @param type
     * @return id.
     */
    int deleteType(Type type) throws DataAccessException;
}
