package com.github.charadziej.project.dao;

import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * DAO interface of HardwareModel
 */
public interface HardwareModelDao {

    /**
     * Get all models list.
     *
     * @return all
     */
    List<HardwareModel> getAllModels() throws DataAccessException;

    /**
     * Get models quantity.
     *
     * @return quantity
     */
    int getModelsQuantity() throws DataAccessException;

    /**
     * Get model by id.
     *
     * @param modelId type identifier.
     * @return model.
     */
    HardwareModel getModelById(Integer modelId) throws DataAccessException;

    /**
     * Get model by name.
     *
     * @param modelName model name.
     * @return model.
     */
    HardwareModel getModelByName(String modelName) throws DataAccessException;

    /**
     * Add new model.
     *
     * @param model
     * @return id.
     */
    int addModel(HardwareModel model) throws DataAccessException;

    /**
     * Update model.
     *
     * @param model
     * @return id.
     */
    int updateModel(HardwareModel model) throws DataAccessException;

    /**
     * Delete model.
     *
     * @param modelId
     * @return effectedRowsNumber
     */
    int deleteModel(Integer modelId) throws DataAccessException;

    /**
     * Sort model.
     *
     * @param begin, end
     * @return models list
     */
    List<HardwareModel> getModelsByPeriod(Date begin, Date end) throws DataAccessException;
}
