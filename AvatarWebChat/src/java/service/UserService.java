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
    
    public boolean registerUser(User user){
        UserDAO dao = new UserDAO();
        return dao.addUser(user);
    }
    
}
