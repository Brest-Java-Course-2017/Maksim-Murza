package com.github.charadziej.project.dao;


import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Implementation of HardwareModel's DAO
 */
public class HardwareModelDaoImpl implements HardwareModelDao {

    public List<HardwareModel> getAllModels() throws DataAccessException {
        return null;
    }

    public HardwareModel getModelById(Integer modelId) throws DataAccessException {
        return null;
    }

    public HardwareModel getModelByName(String name) throws DataAccessException {
        return null;
    }

    public int addModel(HardwareModel model) throws DataAccessException {
        return 0;
    }

    public int updateModel(HardwareModel model) throws DataAccessException {
        return 0;
    }

    public int deleteModel(HardwareModel model) throws DataAccessException {
        return 0;
    }
}
