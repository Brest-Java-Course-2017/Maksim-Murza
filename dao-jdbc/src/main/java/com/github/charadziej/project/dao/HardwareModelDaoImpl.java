package com.github.charadziej.project.dao;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Implementation of HardwareModel's DAO
 */
public class HardwareModelDaoImpl implements HardwareModelDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String MODEL_ID = "model_id";
    private static final String MODEL_NAME = "model_name";
    private static final String MODEL_TYPE = "model_type";
    private static final String RELEASE_DATE = "release_date";

    @Value("${model.select}")
    String getAllModelsSql;
    @Value("${model.selectById}")
    String getModelById;
    @Value("${model.selectByName}")
    String getModelByName;
    @Value("${model.insert}")
    String insertModelSql;
    @Value("${model.update}")
    String updateModelSql;
    @Value("${model.delete}")
    String deleteModelSql;
    @Value("${model.sort}")
    String sortModelSql;

    public HardwareModelDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

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

    public void sortModel(Date begin, Date end) throws DataAccessException {

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
