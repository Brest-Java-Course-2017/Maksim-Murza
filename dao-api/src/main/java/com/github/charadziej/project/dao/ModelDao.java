package com.github.charadziej.project.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * DAO interface of Model
 */
public interface ModelDao {

    /**
     * Get all models list.
     *
     * @return all models list
     */
    List<Model> getAllModels() throws DataAccessException;

    /**
     * Get model by id.
     *
     * @param modelId type identifier.
     * @return model.
     */
    Model getModelById(Integer modelId) throws DataAccessException;

    /**
     * Get model by name.
     *
     * @param name model name.
     * @return model.
     */
    Model getModelByName(String name) throws DataAccessException;

    /**
     * Add new model.
     *
     * @param model
     * @return id.
     */
    int addModel(Model model) throws DataAccessException;

    /**
     * Update model.
     *
     * @param model
     * @return id.
     */
    int updateModel(Model model) throws DataAccessException;

    /**
     * Delete model.
     *
     * @param model
     * @return id.
     */
    int deleteModel(Model model) throws DataAccessException;
}
