package com.github.charadziej.project.client.rest.api;

import com.github.charadziej.project.client.ServerDataAccessException;
import com.github.charadziej.project.dao.HardwareType;

import java.util.List;

/**
 * Interface for rest consumer
 */
public interface HardwareTypeConsumer {

    /**
     * Get all types list.
     *
     * @return all types list
     */
    List<HardwareType> getAllTypes() throws ServerDataAccessException;

    /**
     * Get type by id.
     *
     * @param typeId type identifier.
     * @return type.
     */
    HardwareType getTypeById(Integer typeId) throws ServerDataAccessException;

    /**
     * Get type by name.
     *
     * @param typeName type name.
     * @return type.
     */
    HardwareType getTypeByName(String typeName) throws ServerDataAccessException;

    /**
     * Add new type.
     *
     * @param type
     * @return id.
     */
    int addType(HardwareType type) throws ServerDataAccessException;

    /**
     * Update type.
     *
     * @param type
     * @return id.
     */
    int updateType(HardwareType type) throws ServerDataAccessException;

    /**
     * Delete type.
     *
     * @param typeId
     * @return effectedRowsNumber
     */
    int deleteType(Integer typeId) throws ServerDataAccessException;
}
