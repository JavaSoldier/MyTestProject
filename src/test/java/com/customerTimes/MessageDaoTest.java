package com.customerTimes;


import com.customerTimes.dao.MessageDAO;
import com.customerTimes.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 29.05.14
 * Time: 13:49
 * To change this template use File | Settings | File Templates.
 */
@TransactionConfiguration(defaultRollback = true)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MessageDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    public MessageDAO messageDAO;

    private final String firstDate = "2014-01-01";
    private final String lastDate = "2014-06-01";

    @Test
    public void createUserTest() {
        messageDAO.createUser("testUser");
    }

    @Test(expected = DataAccessException.class)
    public void createUserAlreadyExistTest() {
        messageDAO.createUser("Gleb");
    }

    @Test(expected = DataAccessException.class)
    public void createUserNullTest() {
        messageDAO.createUser(null);
    }

    @Test
    public void getUserTest() {
        User user = messageDAO.getUser("Gleb");
        Assert.assertNotNull(user);
        Assert.assertTrue(new User().getClass().isInstance(user));
        Assert.assertEquals("Gleb", user.getUserName());
    }

    @Test
    public void getUserNullTest() {
        Assert.assertNull(messageDAO.getUser(null).getUserName());
    }

    @Test
    public void getUserIncorrectTest() {
        Assert.assertNull(messageDAO.getUser("unexpectedUser").getUserName());
    }

    @Test
    public void filterByUserNameNullTest() {
        Assert.assertEquals(0, messageDAO.filterMessagesByUserName(null).size());
    }

    @Test
    public void filterByUserNameUnexpectedTest() {
        Assert.assertEquals(0, messageDAO.filterMessagesByUserName("unexpectedUser").size());
    }

    @Test
    public void filterMessagesByUserNameTest() {
        List<String> messages = messageDAO.filterMessagesByUserName("Gleb");
        Assert.assertNotNull(messages);
        Assert.assertEquals(2, messages.size());
    }

    @Test
    public void filterMessagesByUserNameUnexpectedTest() {
        Assert.assertEquals(0, messageDAO.filterMessagesByUserName("unexpectedUser").size());
    }

    @Test
    public void filterMessagesByUserNameNullTest() {
        Assert.assertEquals(0, messageDAO.filterMessagesByUserName(null).size());
    }


    @Test
    public void filterMessagesByDatesTest() throws ParseException {
        List<String> messages = messageDAO.filterMessagesByDates(getDate(firstDate), getDate(lastDate));
        Assert.assertNotNull(messages);
        Assert.assertEquals(10, messages.size());
    }

    @Test
    public void filterMessagesByIncorrectDatesTest() throws ParseException {
        Assert.assertEquals(0, messageDAO.filterMessagesByDates(getDate(lastDate), getDate(lastDate)).size());
    }

    @Test
    public void filterMessagesByUserAndDatesTest() throws ParseException {
        List<String> messages = messageDAO.filterMessagesByUserAndDates("Gleb", getDate(firstDate), getDate(lastDate));
        Assert.assertNotNull(messages);
        Assert.assertEquals(2, messages.size());
    }

    @Test
    public void filterMessagesByUserAndDatesCloseDatesNullTest() throws ParseException {
        Assert.assertEquals(0, (messageDAO.filterMessagesByUserAndDates(null, getDate(lastDate), getDate(lastDate))).size());
    }

    private Date getDate(String dateStr) throws ParseException {
        DateFormat lFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return lFormatter.parse(dateStr);
    }
}
