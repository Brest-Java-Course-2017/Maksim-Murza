package com.epam.test.dao;

import java.util.List;

/**
* DAO interface.
*/
public interface UserDao {

    List<User> getAllUsers();

    User getUserById(Integer userId);

    Integer addUser(User user);

    void updateUser(User user);

    void deleteUser(Integer userId);

}
