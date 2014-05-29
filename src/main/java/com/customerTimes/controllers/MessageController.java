package com.customerTimes.controllers;

import com.customerTimes.model.User;
import com.customerTimes.services.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 23.05.14
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    private static Logger log = Logger.getLogger(MessageController.class);

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String redirectToMessage() {
        return "message";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/send")
    public String sendMessage(@RequestParam(value = "name", required = true) String name, @RequestParam(value = "message", required = true) String message, Model model) {
        try {
            messageService.sendMessage(name, message);
            model.addAttribute("result", "your message has been saved");
        } catch (DataAccessException e) {
            model.addAttribute("error", e.getMessage());
            log.error(e.getMessage(), e);
        }
        if (log.isInfoEnabled()) {   // need to use if debug enabled, but i use logging to console...
            log.info(" sending message by user " + name + " ---> " + message);
        }
        return "message";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filter")
    public String getUsersForDropDownList(Model model) {
        List<User> users = null;
        try {
            users = this.messageService.getUsers();
            model.addAttribute("Users", users);
        } catch (DataAccessException e) {
            model.addAttribute("error", e.getMessage());
            log.error(e.getMessage(), e);
        }
        if (log.isInfoEnabled()) {
            log.info("get list of users" + ((users != null) ? users.size() : null));
        }
        return "filter";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/filter")
    public String filter(@RequestParam(value = "name", required = true) String name,
                         @RequestParam(value = "fromDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
                         @RequestParam(value = "toDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to,
                         Model model) {
        try {
            model.addAttribute("messages", this.messageService.filter(name, from, to));
            return "showMessages";
        } catch (DataAccessException e) {
            model.addAttribute("error", e.getMessage());
            log.error(e.getMessage(), e);
        }
        if (log.isInfoEnabled()) {
            log.info("filter by name " + name + " dateFrom " + from + " dateTo " + to);
        }
        return "message";
    }
}
