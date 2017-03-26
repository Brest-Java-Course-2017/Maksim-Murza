package com.github.charadziej.project.service;

import com.github.charadziej.project.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by charadziej on 3/18/17.
 */
public class HardwareModelServiceImpl implements HardwareModelService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    HardwareModelDao hardwareModelDao;

    @Autowired
    HardwareTypeDao hardwareTypeDao;

    public void setHardwareModelDao(HardwareModelDao hardwareModelDao) {
        this.hardwareModelDao = hardwareModelDao;
    }

    @Override
    public List<HardwareModel> getAllModels() throws DataAccessException {
        LOGGER.debug("getAllModels() in service");
        return hardwareModelDao.getAllModels();
    }

    @Override
    public HardwareModel getModelById(Integer modelId) throws DataAccessException {
        LOGGER.debug("getModelById({}) in service", modelId);
        Assert.notNull(modelId, "Parameter modelId shouldn't be null");
        return hardwareModelDao.getModelById(modelId);
    }

    @Override
    public HardwareModel getModelByName(String modelName) throws DataAccessException {
        LOGGER.debug("getModelByName({}) in service", modelName);
        Assert.hasText(modelName, "Parameter modelName shouldn't be empty");
        return hardwareModelDao.getModelByName(modelName);
    }

    @Override
    public int addModel(HardwareModel model) throws DataAccessException {
        Assert.notNull(model);
        LOGGER.debug("addModel({}) in service", model);

        Assert.hasText(model.getModelName(), "Parameter modelName shouldn't be empty");
        Assert.hasText(model.getModelType(), "Parameter modelType shouldn't be empty");
        Assert.notNull(model.getReleaseDate(), "Parameter releaseDate shouldn't be empty");

        if(hardwareTypeDao.getTypeByName(model.getModelType()) == null) {
            throw new IllegalArgumentException("There are no type with such name");
        }

        if (hardwareModelDao.getModelByName(model.getModelName()) != null) {
            throw new IllegalArgumentException("Object with such name is already exist");
        }

        return hardwareModelDao.addModel(model);
    }

    @Override
    public int updateModel(HardwareModel model) throws DataAccessException {
        Assert.notNull(model);
        LOGGER.debug("updateModel({}) in service", model);

        Assert.notNull(model.getModelId(),"Parameter modelId shouldn't be null");
        Assert.hasText(model.getModelName(), "Parameter modelName shouldn't be empty");
        Assert.hasText(model.getModelType(), "Parameter modelType shouldn't be empty");
        Assert.notNull(model.getReleaseDate(), "Parameter releaseDate shouldn't be empty");

        if(hardwareTypeDao.getTypeByName(model.getModelType()) == null) {
            throw new IllegalArgumentException("There are no type with such name");
        }

        if (hardwareModelDao.getModelById(model.getModelId()) == null) {
            throw new IllegalArgumentException("There are no such row in table");
        }

        if (hardwareModelDao.getModelByName(model.getModelName()) != null &&
                hardwareModelDao.getModelByName(model.getModelName()).getModelId() != model.getModelId()) {
            throw new IllegalArgumentException("Object with this name is already exist");
        }

        return hardwareModelDao.updateModel(model);
    }

    @Override
    public int deleteModel(Integer modelId) throws DataAccessException {
        Assert.notNull(modelId);
        Assert.notNull(hardwareModelDao.getModelById(modelId), "Object is not exist");
        LOGGER.debug("deleteModel({}) in service", modelId);
        return hardwareModelDao.deleteModel(modelId);
    }

    @Override
    public List<HardwareModel> getModelsByPeriod(LocalDate begin, LocalDate end) throws DataAccessException {
        LOGGER.debug("getModelsByPeriod() in service");
        Assert.notNull(begin);
        Assert.notNull(end);
        Assert.isTrue(begin.compareTo(end) < 0);
        return hardwareModelDao.getModelsByPeriod(begin, end);
    }
}
