package com.github.charadziej.project.service;

import com.github.charadziej.project.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Implementation of HardwareModelService
 */
public class HardwareModelServiceImpl implements HardwareModelService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private HardwareModelDao hardwareModelDao;

    @Autowired
    private HardwareTypeDao hardwareTypeDao;

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
        LOGGER.debug("addModel({}) in service", model);

        Assert.notNull(model);
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
        LOGGER.debug("updateModel({}) in service", model);
        Assert.notNull(model);
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
                !hardwareModelDao.getModelByName(model.getModelName()).getModelId().equals(model.getModelId())) {
            throw new IllegalArgumentException("Object with this name is already exist");
        }

        return hardwareModelDao.updateModel(model);
    }

    @Override
    public int deleteModel(Integer modelId) throws DataAccessException {
        LOGGER.debug("deleteModel({}) in service", modelId);
        Assert.notNull(modelId);
        Assert.notNull(hardwareModelDao.getModelById(modelId), "Object is not exist");
        return hardwareModelDao.deleteModel(modelId);
    }

    @Override
    public List<HardwareModel> getModelsByPeriod(Date begin, Date end) throws DataAccessException, ParseException {
        LOGGER.debug("getModelsByPeriod({},{}) in service", begin, end);

        if(begin == null && end == null)
            return hardwareModelDao.getAllModels();
        else if(begin == null)
            begin = FORMATTER.parse("1000-01-01");
        else if(end == null)
            end = FORMATTER.parse("3000-01-01");

        if(begin.compareTo(end) > 0)
            throw new IllegalArgumentException("Begin date is more then end date");

        return hardwareModelDao.getModelsByPeriod(begin, end);
    }
}
