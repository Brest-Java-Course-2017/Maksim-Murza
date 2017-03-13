package com.github.charadziej.project.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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
    String getModelById;
    @Value("${type.selectByName}")
    String getModelByName;
    @Value("${type.insert}")
    String insertModelSql;
    @Value("${type.update}")
    String updateModelSql;
    @Value("${type.delete}")
    String deleteModelSql;

    public HardwareTypeDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<HardwareType> getAllTypes() throws DataAccessException {
        List<HardwareType> typesList = jdbcTemplate.query(getAllTypesSql, new HardwareTypeRowMapper());
        return typesList;
    }

    public HardwareType getTypeById(Integer typeId) throws DataAccessException {
        return null;
    }

    public HardwareType getTypeByName(String typeName) throws DataAccessException {
        return null;
    }

    public int addType(HardwareType type) throws DataAccessException {
        return 0;
    }

    public int updateType(HardwareType type) throws DataAccessException {
        return 0;
    }

    public int deleteType(HardwareType type) throws DataAccessException {
        return 0;
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
