/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.sql.DataSource;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Kristoffer
 */
public class userDAO {
    
    @Autowired
    DataSource dataSource;
    
    private JdbcTemplate jdbcTemplate;
    
    public userDAO(){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public boolean addUser(User user){
        String sql = "INSERT INTO WebChat.chat_user "
                + "(username, firstname, lastname, email, password, salt) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        
        
        int result;
        result = jdbcTemplate.update(sql, 
                new Object[] { user.getUsername(), user.getFirstname(),
                    user.getLastname(), user.getEmail(),
                    user.getPassword(), user.getSalt()});
        
        return result != 0;
    }
}
