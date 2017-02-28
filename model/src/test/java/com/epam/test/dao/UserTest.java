package com.epam.test.dao;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mentor on 13.2.17.
 */

public class UserTest {

    private static final int USER_ID = 11;

    @Test
    public void getUserId() throws Exception {

        User user = new User();
        user.setUserId(11);
        Assert.assertEquals("User id: ", (Integer) USER_ID, user.getUserId());
    }

    @Test
    public void getLogin() throws Exception {
        User user = new User();
        user.setLogin("login");
        Assert.assertEquals("User login: ", "login", user.getLogin());
    }

    @Test
    public void getPassword() throws Exception {
        User user = new User();
        user.setPassword("password");
        Assert.assertEquals("User password: ", "password", user.getPassword());
    }

    @Test
    public void getDescription() throws Exception {
        User user = new User();
        user.setDescription("description");
        Assert.assertEquals("User description: ", "description", user.getDescription());
    }

}