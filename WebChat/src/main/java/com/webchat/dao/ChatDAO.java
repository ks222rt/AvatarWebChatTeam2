
/*To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package com.webchat.dao;

import com.webchat.model.ChatMessage;
import com.webchat.model.ChatRoom;
import com.webchat.model.Message;
import com.webchat.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;


// * @author Adam
 
@Component
public class ChatDAO {
    
   @Autowired
   private JdbcTemplate jdbcTemplate;
   
   private SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
   
   public boolean createPrivateChat(final String roomName, final int userId1, final int userId2) {
      
       try {
            jdbcCall.withProcedureName("proc_create_private_chat");
            SqlParameterSource in = new MapSqlParameterSource().addValue("chatRoomName",
               roomName).addValue("userId1", userId1).addValue("userId2", userId2);
           jdbcCall.execute(in); 
           
       } catch (Exception e) {
            return false;
       }
       return true; 
   }
   
   public boolean createGroup(final String roomName, final int newUserId, final int oldChatRoomId) {
       
       try {
           jdbcCall.withProcedureName("proc_create_group_room");
           SqlParameterSource in = new MapSqlParameterSource().addValue("chatRoomName",
                        roomName).addValue("newUserId",
                         newUserId).addValue("oldChatRoomId", oldChatRoomId);
           jdbcCall.execute(in);
       } catch (Exception e) {
           return false;
       }
       return true;
   }
   
   public boolean addMessageToRoom(final Message message, final int roomId) {
       
       try {
           jdbcCall.withProcedureName("proc_add_message_to_room");
           SqlParameterSource in = new MapSqlParameterSource().addValue("lineText",
                   message.getText()).addValue("userId",
                           message.getUser_id()).addValue("chatRoomId", roomId);
           jdbcCall.execute(in);

       } catch (Exception e) {
           return false;
       }      
       return true;
   }
}
