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

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.List;

/**
 * Implementation of HardwareType's DAO
 */
public class HardwareTypeDaoImpl implements HardwareTypeDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TYPE_ID = "type_id";
    private static final String TYPE_NAME = "type_name";
    private static final String QUANTITY = "quantity";

    @Value("${type.select}")
    String getAllTypesSql;
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

    public List<HardwareType> getAllTypes() throws DataAccessException {
        List<HardwareType> typesList = jdbcTemplate.query(getAllTypesSql, new HardwareTypeRowMapper());
        return typesList;
    }

    public HardwareType getTypeById(Integer typeId) throws DataAccessException {
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

    public HardwareType getTypeByName(String typeName) throws DataAccessException {
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

    public int addType(HardwareType type) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("p_type_name", type.getTypeName());
        namedParameterJdbcTemplate.update(insertTypeSql, sqlParameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public int updateType(HardwareType type) throws DataAccessException {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("p_type_id", type.getTypeId());
        sqlParameterSource.addValue("p_type_name", type.getTypeName());
        return namedParameterJdbcTemplate.update(updateTypeSql, sqlParameterSource);
    }

    public void deleteType(Integer typeId) throws DataAccessException {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("p_type_id", typeId);
        namedParameterJdbcTemplate.update(deleteTypeSql, sqlParameterSource);
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
