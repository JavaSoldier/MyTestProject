package com.customerTimes.dao;

import com.customerTimes.model.User;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 26.05.14
 * Time: 9:36
 * To change this template use File | Settings | File Templates.
 */

@Repository
public interface MessageDAO {

    User getUser(String name);

    List<User> getUsers();

    void createUser(String name);

    void sendMessage(Integer userId, String message);

    List<String> filterMessagesByUserName(String userName);

    List<String> filterMessagesByDates(Date dateFrom, Date dateTo);

    List<String> filterMessagesByUserAndDates(String name, Date dateFrom, Date dateTo);
}
