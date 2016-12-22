/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.dao;

import com.webchat.model.DisabledUserAccountHelper;
import java.sql.SQLException;
import com.webchat.model.User;
import com.webchat.model.UserReportHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
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
       final String sqlForInsertReport = "SELECT avatar_webchat.chat_reports.id, senderName.username as sender, reportedName.username as reported, avatar_webchat.chat_reports.senderId as senderId, avatar_webchat.chat_reports.reportedUserId as reportedId, avatar_webchat.chat_reports.reason\n"+
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
                     urh.setPrimaryKey((int) row.get("id"));
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
         SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
           
            jdbcCall.withProcedureName("proc_disable_user");
            SqlParameterSource in = new MapSqlParameterSource().addValue("userId",userId);
               
           jdbcCall.execute(in); 
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
       return true; 
    }

    /* This method will remove the report from the user */
    public boolean removeReportFromUser(int id) {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
           
            jdbcCall.withProcedureName("proc_delete_report_by_id");
            SqlParameterSource in = new MapSqlParameterSource().addValue("report_id",id);
               
           jdbcCall.execute(in); 
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
       return true; 
    }

    public List<DisabledUserAccountHelper> getAllDisabledAccounts() {
        final String sqlForInsertReport = "SELECT avatar_webchat.chat_blacklist.id, avatar_webchat.chat_blacklist.userId, avatar_webchat.chat_blacklist.time_of_ban, reported.username\n"+
                                          "FROM avatar_webchat.chat_blacklist\n" +
                                          "LEFT OUTER JOIN avatar_webchat.chat_user as reported ON reported.id = avatar_webchat.chat_blacklist.userId\n";
               
        
        List<DisabledUserAccountHelper> listOfDisabledAccounts = new LinkedList<DisabledUserAccountHelper>();
        try{

             List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlForInsertReport);
             for (Map row : rows) {
                 DisabledUserAccountHelper urh = new DisabledUserAccountHelper();
                     urh.setId((int) row.get("id"));
                     urh.setTimestamp((Timestamp) row.get("time_of_ban"));
                     urh.setUserId((int) row.get("userId"));
                     urh.setUsername((String) row.get("username"));
                 listOfDisabledAccounts.add(urh);
             }
             return listOfDisabledAccounts;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        } 
    }

    public boolean removeDisabledAccount(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
        
        try{
            jdbcCall.withProcedureName("proc_delete_disabled_account");
            SqlParameterSource in = new MapSqlParameterSource().addValue("report_id", id);
            
            jdbcCall.execute(in);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
