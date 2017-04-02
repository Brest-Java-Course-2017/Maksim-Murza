package com.github.charadziej.project.service;

import com.github.charadziej.project.dao.HardwareModel;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface of HardwareModelService
 */
public interface HardwareModelService {

    /**
     * Get all models list.
     *
     * @return all models list
     */
    List<HardwareModel> getAllModels() throws DataAccessException;

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
     * @return modelsList
     */
    List<HardwareModel> getModelsByPeriod(LocalDate begin, LocalDate end) throws DataAccessException;
}
