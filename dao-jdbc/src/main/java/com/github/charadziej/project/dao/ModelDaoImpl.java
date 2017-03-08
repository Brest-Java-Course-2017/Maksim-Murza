package com.github.charadziej.project.dao;


import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Implementation of Model's DAO
 */
public class ModelDaoImpl implements ModelDao {

    public List<Model> getAllModels() throws DataAccessException {
        return null;
    }

    public Model getModelById(Integer modelId) throws DataAccessException {
        return null;
    }

    public Model getModelByName(String name) throws DataAccessException {
        return null;
    }

    public int addModel(Model model) throws DataAccessException {
        return 0;
    }

    public int updateModel(Model model) throws DataAccessException {
        return 0;
    }

    public int deleteModel(Model model) throws DataAccessException {
        return 0;
    }
}
