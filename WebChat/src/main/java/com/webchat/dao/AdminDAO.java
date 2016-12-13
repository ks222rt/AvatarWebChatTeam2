/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.dao;

import java.sql.SQLException;
import com.webchat.model.User;
import com.webchat.model.UserReportHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.PreparedStatementCreator;

/**
 *
 * @author filip
 */
public class AdminDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    
    public List<UserReportHelper> getAllReportedUsers() {
       final String sqlForInsertReport = "SELECT avatar_webchat.senderName.username as sender, avatar_webchat.reportedName.username as reported, avatar_webchat.chat_reports.senderId as sender, avatar_webchat.reportedUserId as reported, avatar_webchat.reason\n"+
                                         "FROM avatar_webchat.chat_reports\n"+
                                         "LEFT OUTER JOIN avatar_webchat.chat_user as senderName ON senderName.id = senderId\n"+
                                         "LEFT OUTER JOIN avatar_webchat.chat_user as reportedName ON reportedName.id = reportedUserId";
               
        List<UserReportHelper> listOfReports = new LinkedList<UserReportHelper>();
        try{

             List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlForInsertReport);
             for (Map row : rows) {
                 UserReportHelper urh = new UserReportHelper();
                     urh.setSenderName((String) row.get("sender"));
                     urh.setReportedName((String)row.get("reported"));
                     urh.setReportContent((String) row.get("reason"));
                 listOfReports.add(urh);
             }
             return listOfReports;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
             
    }

}
