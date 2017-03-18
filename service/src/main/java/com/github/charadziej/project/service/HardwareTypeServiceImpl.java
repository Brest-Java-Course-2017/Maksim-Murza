package com.github.charadziej.project.service;

import com.github.charadziej.project.dao.HardwareType;
import com.github.charadziej.project.service.HardwareTypeService;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by charadziej on 3/18/17.
 */
public class HardwareTypeServiceImpl implements HardwareTypeService {

    @Override
    public List<HardwareType> getAllTypes() throws DataAccessException {
        return null;
    }

    @Override
    public HardwareType getTypeById(Integer typeId) throws DataAccessException {
        return null;
    }

    @Override
    public HardwareType getTypeByName(String typeName) throws DataAccessException {
        return null;
    }

    @Override
    public int addType(HardwareType type) throws DataAccessException {
        return 0;
    }

    @Override
    public int updateType(HardwareType type) throws DataAccessException {
        return 0;
    }

    @Override
    public void deleteType(Integer typeId) throws DataAccessException {

    }
}
