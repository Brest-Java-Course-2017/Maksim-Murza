package com.github.charadziej.project.service;

import com.github.charadziej.project.dao.HardwareType;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Interface of HardwareTypeService
 */
public interface HardwareTypeService {

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
     * @return effectedRowsNumber
     */
    int deleteType(Integer typeId) throws DataAccessException;
}
