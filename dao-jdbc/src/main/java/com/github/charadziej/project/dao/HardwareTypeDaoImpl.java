package com.github.charadziej.project.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of HardwareType's DAO
 */
public class HardwareTypeDaoImpl implements HardwareTypeDao {

    private static final String TYPE_ID = "type_id";
    private static final String TYPE_NAME = "type_name";
    private static final String QUANTITY = "quantity";

    public List<Type> getAllTypes() throws DataAccessException {
        return null;
    }

    public Type getTypeById(Integer typeId) throws DataAccessException {
        return null;
    }

    public Type getTypeByName(String typeName) throws DataAccessException {
        return null;
    }

    public Integer getQuantity(Integer typeId) throws DataAccessException {
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
