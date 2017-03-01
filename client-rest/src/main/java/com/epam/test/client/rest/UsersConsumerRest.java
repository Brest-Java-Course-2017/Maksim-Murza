package com.epam.test.client.rest;

import com.epam.test.client.exception.ServerDataAccessException;
import com.epam.test.client.rest.api.UsersConsumer;
import com.epam.test.dao.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by charadziej on 2/28/17.
 */
public class UsersConsumerRest implements UsersConsumer {

    private String hostUrl;
    private String urlUsers;
    private String urlUser;

    RestTemplate restTemplate;


    public UsersConsumerRest(String hostUrl, String urlUsers, String urlUser) {
        this.hostUrl = hostUrl;
        this.urlUsers = urlUsers;
        this.urlUser = urlUser;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getAllUsers() throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.getForEntity(hostUrl + "/" + urlUsers, List.class);
        List<User> users = (List<User>) responseEntity.getBody();
        return users;
    }

    @Override
    public User getUserById(Integer userId) throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.getForEntity(hostUrl + "/" + urlUser+ "/id/" + userId, User.class);
        Object user = responseEntity.getBody();
        return (User) user;
    }

    @Override
    public User getUserByLogin(String login) throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.getForEntity(hostUrl + "/" + urlUser + "/" + login, User.class);
        Object user = responseEntity.getBody();
        return (User) user;
    }

    @Override
    public Integer addUser(User user) throws ServerDataAccessException {
        ResponseEntity responseEntity = restTemplate.postForEntity(hostUrl + "/" + urlUser, HttpMethod.POST, User.class);
        //ResponseEntity responseEntity = restTemplate.exchange(hostUrl + "/" + urlUser, HttpMethod.POST, , User.class);
        Integer id = (Integer) responseEntity.getBody();
        return id;
    }

    @Override
    public int updateUser(User user) throws ServerDataAccessException {
        ResponseEntity neededEntity = restTemplate.getForEntity(hostUrl + "/" + urlUser+ "/id/" + user.getUserId(), User.class);
        restTemplate.exchange(hostUrl + "/" + urlUser + "/" +
                user.getUserId() + "/" + user.getLogin() + "/" + user.getPassword() + "/" +
                user.getDescription(), HttpMethod.PUT, neededEntity, User.class);
        return 0;
    }

    @Override
    public int deleteUser(Integer userId) throws ServerDataAccessException {
        restTemplate.delete(hostUrl + "/" + urlUser + "/" + userId);
        return 0;
    }
}