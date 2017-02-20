package com.epam.test.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.beans.factory.annotation.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    @Value("${user.select}")
    private String GET_ALL_USERS_SQL;
    @Value("${user.selectById}")
    private String GET_USER_BY_ID_SQL;
    @Value("${user.addUser}")
    private String ADD_USER_SQL;
    @Value("${user.updateUser}")
    private String UPDATE_USER_SQL;
    @Value("${user.deleteUser}")
    private String DELETE_USER_BY_ID_SQL;

    private static final Logger LOGGER = LogManager.getLogger();
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.debug("getAllUsers()");
        return jdbcTemplate.query(GET_ALL_USERS_SQL, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer userId) {
        LOGGER.debug("getUserById({})", userId);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_id", userId);
        User user = namedParameterJdbcTemplate.queryForObject(
                GET_USER_BY_ID_SQL, namedParameters, new UserRowMapper());
        return user;
    }

    @Override
    public Integer addUser(User user) {
        LOGGER.debug("addUser({})", user);
        namedParameterJdbcTemplate.update(ADD_USER_SQL, getState(user));
        return user.getUserId();
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("updateUser({})", user);
        namedParameterJdbcTemplate.update(UPDATE_USER_SQL, getState(user));
    }

    @Override
    public void deleteUser(Integer userId) {
        LOGGER.debug("deleteUser({})", userId);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_user_id", userId);
        namedParameterJdbcTemplate.update(DELETE_USER_BY_ID_SQL, namedParameters);
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
