package com.epam.test.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao implementation.
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    String getAllUsersSql = "SELECT user_id, login, password, description from app_user";
    String getUserByIdSql = "SELECT * from app_user WHERE user_id = :p_user_id";
    String addUserSql = "INSERT INTO app_user (user_id, login, password, description) VALUES(:p_user_id, :p_login, :p_password, :p_description);";
    String updateUserSql = "UPDATE app_user SET login=:p_login,password=:p_password,description=:p_description WHERE user_id=:p_user_id;";
    String deleteUserSql = "DELETE FROM app_user WHERE user_id = :p_user_id;";

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(getAllUsersSql, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_id", userId);
        User user = namedParameterJdbcTemplate.queryForObject(
                getUserByIdSql, namedParameters, new UserRowMapper());
        return user;
    }

    @Override
    public Integer addUser(User user) {
        namedParameterJdbcTemplate.update(addUserSql, getState(user));
        return user.getUserId();
    }

    @Override
    public void updateUser(User user) {
        namedParameterJdbcTemplate.update(updateUserSql, getState(user));
    }

    @Override
    public void deleteUser(Integer userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_id", userId);
        namedParameterJdbcTemplate.update(deleteUserSql, namedParameters);
    }

    private Map<String, java.lang.Object> getState(User user) {
        Map<String, java.lang.Object> userState = new HashMap<>();
        userState.put("p_user_id", user.getUserId());
        userState.put("p_login", user.getLogin());
        userState.put("p_password", user.getPassword());
        userState.put("p_description", user.getDescription());
        return userState;
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("description"));
            return user;
        }
    }
}
