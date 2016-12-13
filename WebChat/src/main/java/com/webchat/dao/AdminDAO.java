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
import org.springframework.stereotype.Component;

/**
 *
 * @author filip
 */
@Component
public class AdminDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    
    public List<UserReportHelper> getAllReportedUsers() {
       final String sqlForInsertReport = "SELECT senderName.username as sender, reportedName.username as reported, avatar_webchat.chat_reports.senderId as senderId, avatar_webchat.chat_reports.reportedUserId as reportedId, avatar_webchat.chat_reports.reason\n"+
                                         "FROM avatar_webchat.chat_reports\n"+
                                         "LEFT OUTER JOIN avatar_webchat.chat_user as senderName ON senderName.id = senderId\n"+
                                         "LEFT OUTER JOIN avatar_webchat.chat_user as reportedName ON reportedName.id = reportedUserId";
               
        List<UserReportHelper> listOfReports = new LinkedList<UserReportHelper>();
        try{

             List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlForInsertReport);
             for (Map row : rows) {
                 UserReportHelper urh = new UserReportHelper();
                     urh.setSenderName((String) row.get("sender"));
                     urh.setSenderId((int) row.get("senderId"));
                     urh.setReportedName((String)row.get("reported"));
                     urh.setReportedId((int) row.get("reportedId"));
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
    
    /* This method will disable a useraccount on the ID. It will also remove 
        the account from the table with ALL reports */
    public boolean disableUser(final int userId) {
        final String sqlForDisableUser = "insert into avatar_webchat.chat_blacklist(userId)\n"
               + "values(?)";
        
           try{
           jdbcTemplate.update(new PreparedStatementCreator() {
               @Override
               public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                   PreparedStatement ps = connection.prepareStatement(sqlForDisableUser);
                   ps.setInt(1, userId);
                   return ps;
               }
           });
           return true;
       }catch(Exception e){
           System.out.println("disableUserDAO:"+ e.getMessage());
           return false;
       }
    }

    /* This method will remove the report from the user */
    public boolean removeReportFromUser(int id) {
        // returnerar false so I can test it on the client, Kristoffer
        return false;
    }

}
