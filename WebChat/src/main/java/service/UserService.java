/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import dao.UserDAO;
import model.User;
/**
 *
 * @author Filip
 */
public class UserService {
        private UserDAO dao = new UserDAO();
    
    public boolean registerUser(User user){
        return dao.addUser(user);
    }
    
}
