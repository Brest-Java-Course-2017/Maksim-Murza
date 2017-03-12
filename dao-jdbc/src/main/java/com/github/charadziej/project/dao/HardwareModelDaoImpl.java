package com.github.charadziej.project.dao;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Implementation of HardwareModel's DAO
 */
public class HardwareModelDaoImpl implements HardwareModelDao {

    private static final String MODEL_ID = "model_id";
    private static final String MODEL_NAME = "model_name";
    private static final String MODEL_TYPE = "model_type";
    private static final String RELEASE_DATE = "release_date";

    public List<HardwareModel> getAllModels() throws DataAccessException {
        return null;
    }

    public HardwareModel getModelById(Integer modelId) throws DataAccessException {
        return null;
    }

    public HardwareModel getModelByName(String modelName) throws DataAccessException {
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

    private class HardwareModelRowMapper implements RowMapper<HardwareModel> {
        public HardwareModel mapRow(ResultSet resultSet, int i) throws SQLException {
            HardwareModel model = new HardwareModel(
                    resultSet.getInt(MODEL_ID),
                    resultSet.getString(MODEL_NAME),
                    resultSet.getString(MODEL_TYPE),
                    resultSet.getDate(RELEASE_DATE)
            );
            return model;
        }
    }
}
