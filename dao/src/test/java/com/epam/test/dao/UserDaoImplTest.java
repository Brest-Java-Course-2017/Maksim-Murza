package com.epam.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
public class UserDaoImplTest {
    @Autowired
    UserDao userDao;

    @Test
    public void getAllUsersTest() throws Exception {
        List<User> users = userDao.getAllUsers();
        // error: user.size must be 2
        assertTrue(users.size() == 3);
    }

    @Test
    public void getUserByIdTest() throws Exception {
        User user = userDao.getUserById(1);
        assertNotNull(user);
        assertEquals("userLogin1", user.getLogin());
    }

    @Test
    public void addUserTest() throws Exception {
        User newUser = new User(3,"userLogin3","userPassword3","userDescription3");
        int newUserId = userDao.addUser(newUser);
        assertTrue(newUserId == newUser.getUserId());
    }

    @Test
    public void updateUserTest() throws Exception {
        String testDescription = "test";
        User testUser = new User(3, "userLogin3", "userPassword33", testDescription);
        userDao.updateUser(testUser);
        assertEquals(testDescription, userDao.getUserById(3).getDescription());
    }

    @Test
    public void deleteUserTest() throws Exception {
        int oldSize = userDao.getAllUsers().size();
        userDao.deleteUser(3);
        assertTrue(userDao.getAllUsers().size() == oldSize - 1);
    }
}