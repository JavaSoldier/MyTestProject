package com.customerTimes.services;

import com.customerTimes.dao.MessageDAO;
import com.customerTimes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 26.05.14
 * Time: 9:33
 * To change this template use File | Settings | File Templates.
 */

@Service
public class MessageService {

    private MessageDAO messageDAO;

    @Autowired
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public List<User> getUsers() {
        return messageDAO.getUsers();
    }

    public String sendMessage(String name, String message) {
        User user = this.messageDAO.getUser(name);
        if (user.getUserName() == null) {
            this.messageDAO.createUser(name);
            user = this.messageDAO.getUser(name);
        }
        this.messageDAO.sendMessage(user.getUserId(), message);
        return "your message has bean saved";
    }

    public List<String> filter(String name, Date from, Date to) {
        if (name.equals("All")) {
            return messageDAO.filterMessagesByDates(from, to);
        } else if (from == null && to == null) {
            return messageDAO.filterMessagesByUserName(name);
        } else {
            return messageDAO.filterMessagesByUserAndDates(name, from, to);
        }
    }
}
