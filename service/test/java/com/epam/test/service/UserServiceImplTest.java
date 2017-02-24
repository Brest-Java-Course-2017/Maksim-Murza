package com.epam.test.service;

import com.epam.test.dao.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by charadziej on 2/24/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-test.xml"})
@Transactional
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USER_LOGIN_1 = "userLogin1";
    private static final Integer USER_ID = 1;
    public static final User USER = new User("login", "password");

    @Test
    public void getAllUsers() throws Exception {
        LOGGER.debug("test: getAllUsers()");
        List<User> users = userService.getAllUsers();
        Assert.assertEquals("", 2, users.size());
    }

    @Test
    public void getUserById() throws Exception {
        LOGGER.debug("test: getUserById()");
        User user = userService.getUserById(USER_ID);
        Assert.assertNotNull(user);
        Assert.assertEquals(USER_ID, user.getUserId());
    }

    @Test
    public void getUserByLogin() throws Exception {
        LOGGER.debug("test: getUserByLogin()");
        User user = userService.getUserByLogin(USER_LOGIN_1);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getLogin());
        Assert.assertEquals(USER_LOGIN_1, user.getLogin());
    }

    @Test
    public void addUser() throws Exception {
        LOGGER.debug("test: addUser()");
        List<User> usersList = userService.getAllUsers();
        Integer usersQuantity = usersList.size();
        userService.addUser(USER);
        usersList = userService.getAllUsers();
        assertEquals(usersQuantity + 1, usersList.size());
    }

    @Test
    public void updateUser() throws Exception {
        LOGGER.debug("test: UpdateUser()");
        User testUser = new User(1, "testUser", "pass", "some description");
        Assert.assertEquals(1,userService.updateUser(testUser));
        Assert.assertEquals(testUser, userService.getUserById(testUser.getUserId()));
    }

    @Test
    public void deleteUser() throws Exception {
        LOGGER.debug("test: deleteUser()");
        Integer userId = userService.addUser(USER);
        List<User> usersList = userService.getAllUsers();
        Integer usersQuantity = usersList.size();
        userService.deleteUser(userId);
        usersList = userService.getAllUsers();
        assertEquals(usersQuantity - 1, usersList.size());
    }
}
