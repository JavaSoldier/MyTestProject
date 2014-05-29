package com.customerTimes;


import com.customerTimes.dao.MessageDAO;
import com.customerTimes.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 29.05.14
 * Time: 13:49
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class MessageDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private MessageDAO messageDAO;

    @Rollback
    @Test
    public void createUserTest() {
        messageDAO.createUser("testUser");
    }

    @Rollback
    @Test(expected = DataAccessException.class)
    public void createUserAlreadyExistTest() {
        messageDAO.createUser("Gleb");
    }

    @Rollback
    @Test(expected = DataAccessException.class)
    public void createUserNullTest() {
        messageDAO.createUser(null);
    }

    @Rollback
    @Test
    public void getUserTest() {
        User user = messageDAO.getUser("Gleb");
        Assert.assertNotNull(user);
        Assert.assertEquals(new User(), user);
        Assert.assertEquals("Gleb", user.getUserName());
    }

    @Rollback
    @Test(expected = DataAccessException.class)
    public void getUserNullTest() {
        messageDAO.getUser(null);
    }

    @Rollback
    @Test
    public void getUserIncorrectTest() {
        Assert.assertNull(messageDAO.getUser("unexpectedUser"));
    }

    @Rollback
    @Test(expected = DataAccessException.class)
    public void filterByUserNameNullTest() {
        messageDAO.filterMessagesByUserName(null);
    }

    @Rollback
    @Test
    public void filterByUserNameUnexpectedTest() {
        messageDAO.filterMessagesByUserName("unexpectedUser");
    }

    @Rollback
    @Test
    public void filterMessagesByUserNameTest() {
        List<String> messages = messageDAO.filterMessagesByUserName("Gleb");
        Assert.assertNotNull(messages);
        Assert.assertEquals(2, messages.size());
    }

    @Rollback
    @Test
    public void filterMessagesByUserNameUnexpectedTest() {
        Assert.assertNull(messageDAO.filterMessagesByUserName("unexpectedUser"));
    }

    @Rollback
    @Test(expected = DataAccessException.class)
    public void filterMessagesByUserNameNullTest() {
        messageDAO.filterMessagesByUserName(null);
    }


    @Rollback
    @Test
    public void filterMessagesByDatesTest() {
        List<String> messages = messageDAO.filterMessagesByDates(new Date(2014, 01, 01), new Date(2014, 12, 31));
        Assert.assertNotNull(messages);
        Assert.assertEquals(6, messages.size());
    }

    @Rollback
    @Test
    public void filterMessagesByIncorrectDatesTest() {
        List<String> messages = messageDAO.filterMessagesByDates(new Date(2014, 01, 01), new Date(2014, 12, 31));
        Assert.assertNull(messages);
    }

    @Rollback
    @Test
    public void filterMessagesByUserAndDatesTest() {
        List<String> messages = messageDAO.filterMessagesByUserAndDates("Gleb", new Date(2014, 01, 01), new Date(2014, 06, 10));
        Assert.assertNotNull(messages);
        Assert.assertEquals(2, messages.size());
    }

    @Rollback
    @Test
    public void filterMessagesByUserAndDatesCloseDatesTest() {
        List<String> messages = messageDAO.filterMessagesByUserAndDates("Gleb", new Date(2014, 05, 28), new Date(2014, 05, 29));
        Assert.assertNotNull(messages);
        Assert.assertEquals(1, messages.size());
    }

    @Rollback
    @Test(expected = DataAccessException.class)
    public void filterMessagesByUserAndDatesCloseDatesNullTest() {
        List<String> messages = messageDAO.filterMessagesByUserAndDates(null, new Date(2014, 05, 28), new Date(2014, 05, 29));
    }

    @Rollback
    @Test
    public void filterMessagesByUserAndDatesCloseDatesNullDatesTest() {
        List<String> messages = messageDAO.filterMessagesByUserAndDates("Gleb", null, null);
        Assert.assertNotNull(messages);
        Assert.assertEquals(1, messages.size());
    }
}
