package com.github.charadziej.project.dao;

import org.springframework.dao.DataAccessException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Implementation of Type's DAO
 */
public class TypeDaoImpl implements TypeDao {
    public List<Type> getAllTypes() throws DataAccessException {
        return null;
    }

    public Type getTypeById(Integer typeId) throws DataAccessException {
        return null;
    }

    public Type getTypeByName(String name) throws DataAccessException {
        return null;
    }

    public Integer getQuantity(Integer typeId) throws DataAccessException {
        return null;
    }

    public int addType(Type type) throws DataAccessException {
        return 0;
    }

    public int updateType(Type type) throws DataAccessException {
        return 0;
    }

    public int deleteType(Type type) throws DataAccessException {
        return 0;
    }
}
