package com.github.charadziej.project.dao;

import org.springframework.dao.DataAccessException;

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
    List<HardwareType> getAllTypes() throws DataAccessException;

    /**
     * Get type by id.
     *
     * @param typeId type identifier.
     * @return type.
     */
    HardwareType getTypeById(Integer typeId) throws DataAccessException;

    /**
     * Get type by name.
     *
     * @param typeName type name.
     * @return type.
     */
    HardwareType getTypeByName(String typeName) throws DataAccessException;


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
     * @param typeId
     *
     */
    void deleteType(Integer typeId) throws DataAccessException;
}
