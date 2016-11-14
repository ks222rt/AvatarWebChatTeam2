/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import model.User;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Kristoffer
 */
public class UserDAO {
    private JdbcTemplate jdbcTemplate;
    
    public UserDAO(){
        try {
            InitialContext ic = new InitialContext();
            DataSource myDS = (DataSource)ic.lookup("java:comp/env/jdbc/jdbc/AvatarWebChat");
            this.jdbcTemplate = new JdbcTemplate(myDS);
        } catch (NamingException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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
