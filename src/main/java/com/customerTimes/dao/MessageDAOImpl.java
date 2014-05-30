package com.customerTimes.dao;

import com.customerTimes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: GLEB
 * Date: 26.05.14
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */

@Repository(value = "messageDAO")
public class MessageDAOImpl implements MessageDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User getUser(String name) {
        String SQL = "SELECT u.userID, u.userName FROM Users u WHERE u.userName = ?";
        return this.jdbcTemplate.query(SQL, new Object[]{name}, new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                User user = new User();
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                    user.setUserName(rs.getString(2));
                }
                return user;
            }
        });
    }

    @Override
    public List<User> getUsers() {
        String SQL = "SELECT * FROM Users";
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(SQL);
        List<User> users = new ArrayList<User>();
        for (Map row : rows) {
            User user = new User();
            Long id = (Long) (row.get("userId"));
            user.setUserId(id.intValue());

            user.setUserName((String) row.get("userName"));
            users.add(user);
        }
        return users;
    }

    @Override
    public void createUser(String name) {
        String SQL = "INSERT INTO Users(userName) VALUES (?)";
        this.jdbcTemplate.update(SQL, name);
    }

    @Override
    public void sendMessage(Integer userId, String message) {
        String SQL = "INSERT INTO Messages(userId, message) VALUES (?,?)";
        this.jdbcTemplate.update(SQL, userId, message);
    }

    public List<String> filterMessagesByUserName(String userName) {
        String SQL = "SELECT m.message FROM Users u LEFT JOIN Messages m ON u.userId = m.userId WHERE u.userName = ?";
        return this.jdbcTemplate.queryForList(SQL, String.class, userName);
    }

    @Override
    public List<String> filterMessagesByDates(Date dateFrom, Date dateTo) {
        String SQL = "SELECT m.message FROM Messages m WHERE m.dateTime BETWEEN ? AND ?";
        return this.jdbcTemplate.queryForList(SQL, String.class, dateFrom, dateTo);
    }

    @Override
    public List<String> filterMessagesByUserAndDates(String name, Date dateFrom, Date dateTo) {
        String SQL = "SELECT m.message FROM Users u LEFT JOIN Messages m ON u.userId = m.userId WHERE u.userName = ? AND m.dateTime BETWEEN ? AND ?";
        return this.jdbcTemplate.queryForList(SQL, String.class, name, dateFrom, dateTo);
    }
}
