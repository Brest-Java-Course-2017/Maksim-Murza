package com.github.charadziej.project.service;

import com.github.charadziej.project.dao.HardwareModel;
import com.github.charadziej.project.dao.HardwareModelDao;
import com.github.charadziej.project.dao.HardwareType;
import com.github.charadziej.project.dao.HardwareTypeDao;
import com.github.charadziej.project.service.HardwareTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by charadziej on 3/18/17.
 */
public class HardwareTypeServiceImpl implements HardwareTypeService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    HardwareTypeDao hardwareTypeDao;

    @Autowired
    HardwareModelDao hardwareModelDao;

    public void setHardwareTypeDao(HardwareTypeDao hardwareTypeDao) {
        this.hardwareTypeDao = hardwareTypeDao;
    }

    @Override
    public List<HardwareType> getAllTypes() throws DataAccessException {
        LOGGER.debug("getAllTypes() in service");
        return hardwareTypeDao.getAllTypes();
    }

    @Override
    public HardwareType getTypeById(Integer typeId) throws DataAccessException {
        LOGGER.debug("getTypeById({}) in service", typeId);
        Assert.notNull(typeId, "Parameter typeId shouldn't be null");
        HardwareType type = hardwareTypeDao.getTypeById(typeId);
        return type;
    }

    @Override
    public HardwareType getTypeByName(String typeName) throws DataAccessException {
        LOGGER.debug("getTypeByName({}) in service", typeName);
        Assert.hasText(typeName, "Parameter typeName shouldn't be empty");
        HardwareType type = hardwareTypeDao.getTypeByName(typeName);
        return type;
    }

    @Override
    public int addType(HardwareType type) throws DataAccessException {
        Assert.notNull(type);
        LOGGER.debug("addType({}) in service", type);
        Assert.hasText(type.getTypeName(), "Parameter typeName shouldn't be empty");

        if (hardwareTypeDao.getTypeByName(type.getTypeName()) != null) {
            throw new IllegalArgumentException("Object with such name is already exist");
        }

        return hardwareTypeDao.addType(type);
    }

    @Override
    public int updateType(HardwareType type) throws DataAccessException {
        Assert.notNull(type);
        LOGGER.debug("updateType({}) in service", type);

        Assert.notNull(type.getTypeId(), "Parameter typeId shouldn't be empty");
        Assert.hasText(type.getTypeName(), "Parameter typeName shouldn't be empty");

        if (hardwareTypeDao.getTypeById(type.getTypeId()) == null) {
            throw new IllegalArgumentException("There are no object with such id");
        }

        if (hardwareTypeDao.getTypeByName(type.getTypeName()) != null &&
                hardwareTypeDao.getTypeByName(type.getTypeName()).getTypeId() != type.getTypeId()) {
            throw new IllegalArgumentException("Object with such name is already exist");
        }

        return hardwareTypeDao.updateType(type);
    }

    @Override
    public int deleteType(Integer typeId) throws DataAccessException {
        Assert.notNull(typeId);
        Assert.notNull(hardwareTypeDao.getTypeById(typeId), "Trying to delete nonexistent object");
        String typeName = hardwareTypeDao.getTypeById(typeId).getTypeName();
        List<HardwareModel> modelsList = hardwareModelDao.getAllModels();

        for(int i = 0; i < modelsList.size(); i++) {
            if (modelsList.get(i).getModelType() == typeName) {
                throw new IllegalArgumentException("Trying to delete type, which has models");
            }
        }

        LOGGER.debug("deleteType({}) in service", typeId);
        return hardwareTypeDao.deleteType(typeId);
    }
}
