
/*To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package com.webchat.dao;

import com.webchat.model.ChatMessage;
import com.webchat.model.ChatRoom;
import com.webchat.model.ChatUserHelper;
import com.webchat.model.Message;
import com.webchat.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

   
   
   public boolean createPrivateChat(final String roomName, final int userId1, final int userId2) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
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
       SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
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
       SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
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
   
   public List<ChatRoom> getChatRooms(final int userID) {
        
        final String sqlForFetchingRooms = "SELECT avatar_webchat.chat_room.id as id, avatar_webchat.chat_room.isGroup as isGroup\n"+
                                            "FROM avatar_webchat.chat_room\n"+
                                            "INNER JOIN avatar_webchat.chat_room_members\n"+
                                            "ON avatar_webchat.chat_room_members.chat_room_id = avatar_webchat.chat_room.id\n"+
                                            "WHERE avatar_webchat.chat_room_members.user_id = "+userID;

        List<ChatRoom> userRoomList = new ArrayList<>();
        try{

             List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlForFetchingRooms);
             for (Map row : rows) {
                 userRoomList.add(new ChatRoom((Integer)row.get("id"),(Integer)row.get("isGroup")));
             }
             System.out.println("Amount of rooms user has access to: " + userRoomList.size());
             if(userRoomList.size() > 0 ) {
                 
                 for(ChatRoom room : userRoomList) { 
                    List<ChatUserHelper> usersInRoom = getUsersInRoom(room.getRoomId(), userID);
                    room.setMembers(usersInRoom);
                 } 
                 return userRoomList;
             }
        }
        catch(Exception e){
            return null;
        }
         return null;     
    }
   
   
    public List<ChatUserHelper> getUsersInRoom(int roomId, int userID){
         String sqlForFetchingFriendsInChatRoom = "SELECT avatar_webchat.chat_room_members.user_id as userID,\n"+
                                                            "avatar_webchat.chat_user.username as username\n"+
                                                            "FROM avatar_webchat.chat_room_members\n"+
                                                            "INNER JOIN avatar_webchat.chat_user ON avatar_webchat.chat_room_members.user_id = avatar_webchat.chat_user.id\n"+
                                                            "WHERE avatar_webchat.chat_room_members.chat_room_id = "+roomId+"\n"+
                                                            "AND avatar_webchat.chat_room_members.user_id != "+userID;
        List<ChatUserHelper> usersInRoom = new ArrayList<>();
        try{
            List<Map<String, Object>> rowsForUser = jdbcTemplate.queryForList(sqlForFetchingFriendsInChatRoom);

            for (Map row : rowsForUser) {            
               System.out.println("Adding friend " + (String)row.get("username") + " to room " + roomId);
               usersInRoom.add(new ChatUserHelper(roomId,
                                                 (Integer)row.get("userID"),                                      
                                                 (String)row.get("username")));
            } 
        }catch(Exception e){
            return null;
        }
        return usersInRoom;
    }
    
    public boolean insertUserToChatRoom(final int userId, final int roomId){
        final String sqlInsertUserToChatRoom = "insert into avatar_webchat.chat_room_members(chat_room_id, user_id) \n"
                + "values(?, ?)";
        
        try{
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlInsertUserToChatRoom);
                    ps.setInt(1, roomId);
                    ps.setInt(2, userId);
                    return ps;
                }
            });
          return true;
         }catch(Exception e){
             return false;
         }
    }
   
}
