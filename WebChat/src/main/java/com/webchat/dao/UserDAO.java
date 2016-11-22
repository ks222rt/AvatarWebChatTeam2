/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.webchat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.webchat.util.HashUtil;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;

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

    public User loginUser(String username, String password) {
        User userResult = getUserObject(username);
        String userPassword = HashUtil.hashPassword(password, userResult.getSalt());        
        if (userPassword.equals(userResult.getPassword())) {
            return userResult;
        }
        return null;
        
    }
    
    public User searchUser(String username){
        return getUserObject(username);
    } 
    
    public List<User> getAllUsers(){
        String sql = "SELECT * FROM avatar_webchat.user";       
        LinkedList<User> users = new LinkedList();
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            User user = new User();
            user.setUsername((String)row.get("username"));
            user.setFirstname((String)row.get("firstname"));
            user.setLastname((String)row.get("lastname"));
            user.setEmail((String)row.get("email"));
            user.setSalt((byte[])row.get("salt"));
            user.setPassword((String)row.get("password"));
            user.setID((Integer)row.get("id"));
            users.add(user);
        }
        
        return users;
    }
    
    private User getUserObject(String username){
        String sql = "SELECT * FROM avatar_webchat.user WHERE username = ?";
        
        try{
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
            return userResult;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}
