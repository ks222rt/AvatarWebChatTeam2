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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Kristoffer
 */
@Component
public class UserDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
        
    public boolean addUser(final User user){
        if(isUsernameFree(user.getUsername()) && isEmailFree(user.getEmail())){
            final String sqlForChatUserInfo = "insert into avatar_webchat.chat_user_info(firstname, lastname, email, created)" +
                                         "values(?, ?, ?, NOW())";
            String sqlForChatUser = "insert into avatar_webchat.chat_user(username, password, salt, info_id)\n" +
                                     "values(?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            try{
                Number keyResult;
                int result = 0;
                jdbcTemplate.update(new PreparedStatementCreator(){
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {                       
                        PreparedStatement ps = connection.prepareStatement(sqlForChatUserInfo, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, user.getFirstname());
                        ps.setString(2, user.getLastname());
                        ps.setString(3, user.getEmail());
                        return ps;
                    }
                            
                }, keyHolder);
                
                keyResult = keyHolder.getKey();
                if (keyResult != null){
                    result = jdbcTemplate.update(sqlForChatUser,
                            new Object[] { user.getUsername(),
                                user.getPassword(),
                                user.getSalt(),
                                (int)keyResult.intValue()});
                }
                return result != 0; 
            }catch(IncorrectResultSizeDataAccessException e){
                return false;
            }
        }
        return false;
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
        String sql = "SELECT avatar_webchat.chat_user.id as id,\n" +
                     "avatar_webchat.chat_user.username as username,\n" +
                     "avatar_webchat.chat_user.password as password,\n" +
                     "avatar_webchat.chat_user.salt as salt,\n" +
                     "avatar_webchat.chat_user_info.email as email,\n" +
                     "avatar_webchat.chat_user_info.firstname as firstname,\n" +
                     "avatar_webchat.chat_user_info.lastname as lastname,\n" +
                     "avatar_webchat.chat_user_info.isAdmin as isAdmin,\n" +
                     "avatar_webchat.chat_user_info.created as created\n" +
                     "FROM\n" +
                     "avatar_webchat.chat_user_info, avatar_webchat.chat_user\n" +
                     "WHERE\n" +
                     "avatar_webchat.chat_user_info.id = avatar_webchat.chat_user.info_id;";       
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
            user.setCreated((Timestamp)row.get("created"));
            user.setIsAdmin((Integer)row.get("isAdmin"));
            users.add(user);
        }
        
        return users;
    }
    
    private User getUserObject(String username){
        String sql = "SELECT avatar_webchat.chat_user.id as id," +
                     "avatar_webchat.chat_user.username as username, \n" +
                     "avatar_webchat.chat_user.password as password,\n" +
                     "avatar_webchat.chat_user.salt as salt,\n" +
                     "avatar_webchat.chat_user_info.email as email,\n" +
                     "avatar_webchat.chat_user_info.firstname as firstname,\n" +
                     "avatar_webchat.chat_user_info.lastname as lastname,\n" +
                     "avatar_webchat.chat_user_info.isAdmin as isAdmin,\n" +
                     "avatar_webchat.chat_user_info.created as created\n" +
                     "FROM\n" +
                     "avatar_webchat.chat_user_info, avatar_webchat.chat_user\n" +
                     "WHERE \n" +
                     "avatar_webchat.chat_user.username = ? AND chat_user_info.id = chat_user.info_id;";
        
        try{
            System.out.println("inne och skall köra queryn");
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
                            user.setCreated(rs.getTimestamp("created"));
                            user.setIsAdmin(rs.getInt("isAdmin"));
                            return user;                     
                        }
                        });  
            System.out.println(userResult.getClass());
            System.out.println(userResult.getUsername());
            return userResult;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    private boolean isUsernameFree(String username) {
        String sqlUsername = "SELECT avatar_webchat.chat_user.username as username\n" +
                             "FROM avatar_webchat.chat_user\n" +
                             "WHERE avatar_webchat.chat_user.username = ?";
        
        try{
            User result;
            result = jdbcTemplate.queryForObject(sqlUsername,
                new Object[] {username},
                new RowMapper<User>(){
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {
                        User user = new User();
                        user.setUsername(rs.getString("username"));
                        return user;                     
                    }
                });
            
            return result == null;
        }catch(EmptyResultDataAccessException e){
            return true;
        }
    }
    
    private boolean isEmailFree(String email){
        String sqlEmail = "SELECT avatar_webchat.chat_user_info.email as email\n" +
                          "FROM avatar_webchat.chat_user_info\n" +
                          "WHERE avatar_webchat.chat_user_info.email = ?";
        try{
            User result;
            result = jdbcTemplate.queryForObject(sqlEmail,
                new Object[] {email},
                new RowMapper<User>(){
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {
                        User user = new User();
                        user.setEmail(rs.getString("email"));
                        return user;                     
                    }
                });
            return result == null;
        }catch(EmptyResultDataAccessException e){
            return true;
        }
    }
}
