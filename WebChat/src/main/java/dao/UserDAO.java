/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import util.HashUtil;

/**
 *
 * @author Kristoffer
 */
@Component
public class UserDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    public boolean addUser(User user){
        String sql = "INSERT INTO avatar_webchat.user "
                + "(username, firstname, lastname, email, password, salt) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        int result;
        result = jdbcTemplate.update(sql, 
                new Object[] { user.getUsername(), user.getFirstname(),
                    user.getLastname(), user.getEmail(),
                    user.getPassword(), user.getSalt()});
        
        return result != 0;
    }

    public boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM avatar_webchat.user WHERE username = ?";

        User userResult = jdbcTemplate.queryForObject(sql,
                new Object[] { username },
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {
                        User user = new User();
                        user.setUsername(rs.getString("username"));
                        user.setFirstname(rs.getString("firstname"));
                        user.setLastname(rs.getString("lastname"));
                        user.setEmail(rs.getString("email"));
                        user.setSalt(rs.getBytes("salt"));
                        user.setPassword(rs.getString("password"));
                        user.setID(rs.getInt("id"));
                        return user;                     
                    }
                    });  
        
        String userPassword = HashUtil.hashPassword(password, userResult.getSalt());        
        return (userPassword == null ? userResult.getPassword() == null 
                    : userPassword.equals(userResult.getPassword()));
    }
}
