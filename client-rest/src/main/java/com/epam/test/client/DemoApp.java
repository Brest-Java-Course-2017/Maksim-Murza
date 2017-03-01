package com.epam.test.client;

import com.epam.test.client.rest.api.UsersConsumer;
import com.epam.test.client.exception.ServerDataAccessException;
import com.epam.test.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

/**
 * Created by charadziej on 2/28/17.
 */
@Component
public class DemoApp {

    @Autowired
    UsersConsumer usersConsumer;

    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");

        DemoApp demoApp = ctx.getBean(DemoApp.class);
        demoApp.menu();
    }

    private void menu() {

        int swValue = 0;

        System.out.println("=================================");
        System.out.println("|   MENU SELECTION DEMO         |");
        System.out.println("=================================");
        System.out.println("| Options:                      |");
        System.out.println("|        1. Get all users       |");
        System.out.println("|        2. Get user by login   |");
        System.out.println("|        3. Get user by id      |");
        System.out.println("|        4. Add user            |");
        System.out.println("|        5. Update user         |");
        System.out.println("|        6. Delete user         |");
        System.out.println("|        7. Exit                |");
        System.out.println("=================================");
        while (swValue != 7) {
            System.out.print("Select option: ");
            if (sc.hasNextInt()) {
                swValue = sc.nextInt();
                checkValue(swValue);
            } else {
                System.out.println("Bad value: " + sc.next());
            }
        }
    }

    private void checkValue(int item) {
        switch (item) {
            case 1:
                getAllUsers();
                break;
            case 2:
                getUserByLogin();
                break;
            case 3:
                getUserById();
                break;
            case 4:
                addUser();
                break;
            case 5:
                updateUser();
                break;
            case 6:
                deleteUser();
                break;
            case 7:
                System.out.println("Exit.");
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    private void getAllUsers() {
        List<User> users = usersConsumer.getAllUsers();
        System.out.println("users: " + users);
    }

    private void getUserByLogin() {
        String userLogin = "";
        System.out.print("    Enter user login: ");
        if (sc.hasNextLine()) {
            userLogin = sc.next();
        }

        try {
            User user = usersConsumer.getUserByLogin(userLogin);
            System.out.println("    User: " + user);
        } catch (ServerDataAccessException ex) {
            System.out.println("    ERROR: " + ex.getMessage());
        }
    }

    private void getUserById() {
        Integer userId = 0;
        System.out.print("    Enter user id: ");
        if (sc.hasNextInt()) {
            userId = sc.nextInt();
        }

        try {
            User user = usersConsumer.getUserById(userId);
            System.out.println("    User: " + user);
        } catch (ServerDataAccessException ex) {
            System.out.println("    ERROR: " + ex.getMessage());
        }
    }

    private void addUser() {
        User user = new User();
        Integer userId = 0;
        String userLogin = "";
        String userPassword = "";
        String userDescription = "";

        System.out.print("    Enter user id: ");
        if (sc.hasNextInt()) {
            userId = sc.nextInt();
        }
        System.out.print("    Enter user login: ");
        if (sc.hasNextLine()) {
            userLogin = sc.next();
        }
        System.out.print("    Enter user password: ");
        if (sc.hasNextLine()) {
            userPassword = sc.next();
        }
        System.out.print("    Enter user description: ");
        if (sc.hasNextLine()) {
            userDescription = sc.next();
        }

        user.setUserId(userId);
        user.setLogin(userLogin);
        user.setPassword(userPassword);
        user.setDescription(userDescription);

        try {
            Integer id = usersConsumer.addUser(user);
            System.out.println("    User: " + user);
        } catch (ServerDataAccessException ex) {
            System.out.println("    ERROR: " + ex.getMessage());
        }
    }

    private void updateUser() {
        Integer userId = 0;
        String userLogin = "";
        String userPassword = "";
        String userDescription = "";

        System.out.print("    Enter user id: ");
        if (sc.hasNextInt()) {
            userId = sc.nextInt();
        }

        try {
            User user = usersConsumer.getUserById(userId);
            user.setUserId(userId);
            System.out.print("    Enter new login: ");
            if (sc.hasNextLine()) {
                userLogin = sc.next();
            }
            System.out.print("    Enter new password: ");
            if (sc.hasNextLine()) {
                userPassword = sc.next();
            }
            System.out.print("    Enter new description: ");
            if (sc.hasNextLine()) {
                userDescription = sc.next();
            }

            user.setUserId(userId);
            user.setLogin(userLogin);
            user.setPassword(userPassword);
            user.setDescription(userDescription);

            int id = usersConsumer.updateUser(user);
        } catch (ServerDataAccessException ex) {
            System.out.println("    ERROR: " + ex.getMessage());
        }
    }

    private void deleteUser() {
        Integer userId = 0;
        System.out.print("    Enter user id: ");
        if (sc.hasNextInt()) {
            userId = sc.nextInt();
        }

        try {
            usersConsumer.deleteUser(userId);
        } catch (ServerDataAccessException ex) {
            System.out.println("    ERROR: " + ex.getMessage());
        }
    }
}
