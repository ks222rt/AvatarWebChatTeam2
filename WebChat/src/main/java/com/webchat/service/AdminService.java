/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.service;

import com.webchat.dao.AdminDAO;
import com.webchat.model.UserReportHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author filip
 */
@Service
public class AdminService {
    
    @Autowired
    private AdminDAO adminDAO;
    
    public List<UserReportHelper> getListOfReports(){
        return adminDAO.getAllReportedUsers();
    }

    public boolean disableUser(int id) {
        return adminDAO.disableUser(id);
    }

    public boolean removeReportFromUser(int id) {
        return adminDAO.removeReportFromUser(id);
    }
}
