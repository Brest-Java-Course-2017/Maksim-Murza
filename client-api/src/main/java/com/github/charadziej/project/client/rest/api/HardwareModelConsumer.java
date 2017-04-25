package com.github.charadziej.project.client.rest.api;

import com.github.charadziej.project.client.ServerDataAccessException;
import com.github.charadziej.project.dao.HardwareModel;

import java.util.Date;
import java.util.List;

/**
 * Interface for rest consumer
 */
public interface HardwareModelConsumer {

    /**
     * Get all models list.
     *
     * @return all models list
     */
    List<HardwareModel> getAllModels() throws ServerDataAccessException;

    /**
     * Get model by id.
     *
     * @param modelId type identifier.
     * @return model.
     */
    HardwareModel getModelById(Integer modelId) throws ServerDataAccessException;

    /**
     * Get model by name.
     *
     * @param modelName model name.
     * @return model.
     */
    HardwareModel getModelByName(String modelName) throws ServerDataAccessException;

    /**
     * Add new model.
     *
     * @param model
     * @return id.
     */
    int addModel(HardwareModel model) throws ServerDataAccessException;

    /**
     * Update model.
     *
     * @param model
     * @return id.
     */
    int updateModel(HardwareModel model) throws ServerDataAccessException;

    /**
     * Delete model.
     *
     * @param modelId
     * @return effectedRowsNumber
     */
    int deleteModel(Integer modelId) throws ServerDataAccessException;

    /**
     * Sort model.
     *
     * @param begin, end
     * @return modelsList
     */
    List<HardwareModel> getModelsByPeriod(Date begin, Date end) throws ServerDataAccessException;
}
