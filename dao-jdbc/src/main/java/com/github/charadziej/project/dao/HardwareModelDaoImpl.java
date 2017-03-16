package com.github.charadziej.project.dao;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private static final String MODEL_TYPE_NAME = "model_type_name";
    private static final String RELEASE_DATE = "release_date";

    @Value("${model.select}")
    String getAllModelsSql;
    @Value("${model.selectById}")
    String getModelByIdSql;
    @Value("${model.selectByName}")
    String getModelByNameSql;
    @Value("${model.insert}")
    String insertModelSql;
    @Value("${model.update}")
    String updateModelSql;
    @Value("${model.delete}")
    String deleteModelSql;
    @Value("${model.sort}")
    String getModelsByPeriodSql;

    public HardwareModelDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<HardwareModel> getAllModels() throws DataAccessException {
        List<HardwareModel> modelsList = jdbcTemplate.query(getAllModelsSql, new HardwareModelRowMapper());
        return modelsList;
    }

    public HardwareModel getModelById(Integer modelId) throws DataAccessException {
        HardwareModel model;
        try {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource("p_model_id", modelId);
            model = namedParameterJdbcTemplate.queryForObject(getModelByIdSql,
                    sqlParameterSource, new HardwareModelRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        return model;
    }

    public HardwareModel getModelByName(String modelName) throws DataAccessException {
        HardwareModel model;
        try {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource("p_model_name", modelName);
            model = namedParameterJdbcTemplate.queryForObject(getModelByNameSql,
                    sqlParameterSource, new HardwareModelRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
        return model;
    }

    public int addModel(HardwareModel model) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("p_model_name", model.getModelName());
        sqlParameterSource.addValue("p_model_type_name", model.getModelType());
        sqlParameterSource.addValue("p_release_date", model.getReleaseDate());
        namedParameterJdbcTemplate.update(insertModelSql, sqlParameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public int updateModel(HardwareModel model) throws DataAccessException {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("p_model_id", model.getModelId());
        sqlParameterSource.addValue("p_model_name", model.getModelName());
        sqlParameterSource.addValue("p_model_type_name", model.getModelType());
        sqlParameterSource.addValue("p_release_date", model.getReleaseDate());
        return namedParameterJdbcTemplate.update(updateModelSql, sqlParameterSource);
    }

    public void deleteModel(Integer modelId) throws DataAccessException {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("p_model_id", modelId);
        namedParameterJdbcTemplate.update(deleteModelSql, sqlParameterSource);
    }

    public List<HardwareModel> getModelsByPeriod(LocalDate begin, LocalDate end) throws DataAccessException {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("p_begin", begin);
        sqlParameterSource.addValue("p_end", end);
        List<HardwareModel> modelsListByPeriod = namedParameterJdbcTemplate.query(getModelsByPeriodSql,
                sqlParameterSource, new HardwareModelRowMapper());
        return modelsListByPeriod;
    }

    private class HardwareModelRowMapper implements RowMapper<HardwareModel> {
        public HardwareModel mapRow(ResultSet resultSet, int i) throws SQLException {
            HardwareModel model = new HardwareModel(
                    resultSet.getInt(MODEL_ID),
                    resultSet.getString(MODEL_NAME),
                    resultSet.getString(MODEL_TYPE_NAME),
                    LocalDate.parse(resultSet.getString(RELEASE_DATE))
            );
            return model;
        }
    }
}
