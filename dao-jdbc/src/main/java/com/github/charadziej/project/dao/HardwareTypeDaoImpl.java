package com.github.charadziej.project.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.List;

/**
 * Implementation of HardwareType's DAO
 */
public class HardwareTypeDaoImpl implements HardwareTypeDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TYPE_ID = "type_id";
    private static final String TYPE_NAME = "type_name";
    private static final String QUANTITY = "quantity";

    @Value("${types.select}")
    String getAllTypesSql;
    @Value("${types.quantity}")
    String getTypesQuantitySql;
    @Value("${type.selectById}")
    String getTypeById;
    @Value("${type.selectByName}")
    String getTypeByName;
    @Value("${type.insert}")
    String insertTypeSql;
    @Value("${type.update}")
    String updateTypeSql;
    @Value("${type.delete}")
    String deleteTypeSql;

    public HardwareTypeDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int getTypesQuantity() throws DataAccessException {
        LOGGER.debug("getTypesQuantity() in dao");
        return jdbcTemplate.queryForObject(getTypesQuantitySql, Integer.class);
    }

    @Override
    public List<HardwareType> getAllTypes() throws DataAccessException {
        LOGGER.debug("getAllTypes() in dao");
        List<HardwareType> typesList = jdbcTemplate.query(getAllTypesSql, new HardwareTypeRowMapper());
        return typesList;
    }

    @Override
    public HardwareType getTypeById(Integer typeId) throws DataAccessException {
        LOGGER.debug("getTypeById({}) in dao", typeId);
        HardwareType type;

        try {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource("p_type_id", typeId);
            type = namedParameterJdbcTemplate.queryForObject(getTypeById,
                    sqlParameterSource, new HardwareTypeRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

        return type;
    }

    @Override
    public HardwareType getTypeByName(String typeName) throws DataAccessException {
        LOGGER.debug("getTypeByName({}) in dao", typeName);
        HardwareType type;

        try {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource("p_type_name", typeName);
            type = namedParameterJdbcTemplate.queryForObject(getTypeByName,
                    sqlParameterSource, new HardwareTypeRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

        return type;
    }

    @Override
    public int addType(HardwareType type) throws DataAccessException {
        LOGGER.debug("addType({}) in dao", type);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("p_type_name", type.getTypeName());
        namedParameterJdbcTemplate.update(insertTypeSql, sqlParameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateType(HardwareType type) throws DataAccessException {
        LOGGER.debug("updateType({}) in dao", type);
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("p_type_id", type.getTypeId());
        sqlParameterSource.addValue("p_type_name", type.getTypeName());
        return namedParameterJdbcTemplate.update(updateTypeSql, sqlParameterSource);
    }

    @Override
    public int deleteType(Integer typeId) throws DataAccessException {
        LOGGER.debug("deleteType({}) in dao", typeId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("p_type_id", typeId);
        return namedParameterJdbcTemplate.update(deleteTypeSql, sqlParameterSource);
    }

    private class HardwareTypeRowMapper implements RowMapper<HardwareType> {
        public HardwareType mapRow(ResultSet resultSet, int i) throws SQLException {
            HardwareType type = new HardwareType(
                    resultSet.getInt(TYPE_ID),
                    resultSet.getString(TYPE_NAME),
                    resultSet.getInt(QUANTITY)
            );
            return type;
        }
    }
}
