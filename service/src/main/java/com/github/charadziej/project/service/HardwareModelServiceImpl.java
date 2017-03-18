package com.github.charadziej.project.service;

import com.github.charadziej.project.dao.HardwareModel;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by charadziej on 3/18/17.
 */
public class HardwareModelServiceImpl implements HardwareModelService {

    @Override
    public List<HardwareModel> getAllModels() throws DataAccessException {
        return null;
    }

    @Override
    public HardwareModel getModelById(Integer modelId) throws DataAccessException {
        return null;
    }

    @Override
    public HardwareModel getModelByName(String modelName) throws DataAccessException {
        return null;
    }

    @Override
    public int addModel(HardwareModel model) throws DataAccessException {
        return 0;
    }

    @Override
    public int updateModel(HardwareModel model) throws DataAccessException {
        return 0;
    }

    @Override
    public void deleteModel(Integer modelId) throws DataAccessException {

    }

    @Override
    public List<HardwareModel> getModelsByPeriod(LocalDate begin, LocalDate end) throws DataAccessException {
        return null;
    }
}
