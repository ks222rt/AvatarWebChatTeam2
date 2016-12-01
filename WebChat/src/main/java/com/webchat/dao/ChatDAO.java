/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.dao;

import com.webchat.model.ChatMessage;
import com.webchat.model.ChatRoom;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Adam
 */
@Component
public class ChatDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /*
    Takes ChatRoom obj as argument, returns true/false
    Has to be initiated with ChatRoom with set name and two members
    */
    public boolean addChatRoom(final ChatRoom chatRoom) { 
        
        if(chatRoom.getMembers().size() < 2)
            return false;
        
        final String sql = "INSERT INTO avatar_webchat.chat_room(chat_room_name)\n"+
                           "VALUES(?)";
        
        
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        
        try {
            final Number keyResult;
            int result = 0;
            jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, chatRoom.getRoomName());
                        return ps;
                    }

                }, keyHolder);

                keyResult = keyHolder.getKey();
                if (keyResult != null) {
                   jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        int generatedPrimaryKey = keyResult.intValue();
                        final String sqlAddMembers = "INSERT INTO avatar_webchat.chat_room_members(chat_room_id, user_id)\n"+
                                     "VALUES(?, ?), (?, ?)";
                        PreparedStatement ps2 = connection.prepareStatement(sqlAddMembers);
                        ps2.setInt(1, generatedPrimaryKey);
                        ps2.setInt(1, chatRoom.getMembers().get(0).getId());
                        ps2.setInt(3, generatedPrimaryKey);   
                        ps2.setInt(4, chatRoom.getMembers().get(1).getId());
                        return ps2;
                    }
                   });                         
                }
                return result != 0;
            } catch (IncorrectResultSizeDataAccessException e) {
                return false;
            }
        }           
                    
    /*
    Takes User obj as argument, returns List<ChatRoom>
    */
    public List<ChatRoom> getUserChatRooms(final User user) {
        final String sql = "SELECT * FROM avatar_webchat.chat_room\n"+
                           "LEFT OUTER JOIN avatar_webchat.chat_room_members ON avatar_webchat.chat_room_members.chat_room_id = avatar_webchat.chat_room.id\n"+
                           "WHERE avatar_webchat.chat_room_members.user_id = " + user.getId();
        
        List<ChatRoom> myList = new LinkedList<ChatRoom>();
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql); 
        
        for(Map row : rows) {
            ChatRoom cr = new ChatRoom();
            cr.setRoomId((Integer) row.get("id"));
            cr.setRoomName((String) row.get("chat_room_name"));
            myList.add(cr); 
        }   
        return myList;  
    }      
    
    /*
    Takes ChatRoom obj and User obj as argument
    //DATE_FORMAT(FROM_UNIXTIME('avatar_webchat.chat_line.posted_at'), '%e %b %Y') AS timestamp
    Can't get piece of shit timestamp to work with anything in java. PENDING RESULTS
    */
    public ChatRoom getChatRoomWithData(final ChatRoom chatRoom, final User user) {
        
        final String sql = "SELECT avatar_webchat.chat_user.username as username, DATE(avatar_webchat.chat_line.posted_at) as timestamp , avatar_webchat.chat_line.line_text as message\n"+
                           "FROM avatar_webchat.chat_user\n"+
                           "LEFT OUTER JOIN avatar_webchat.chat_relation ON (avatar_webchat.chat_relation.sender_user_id = avatar_webchat.chat_user.id)\n"+
                           "LEFT OUTER JOIN avatar_webchat.chat_room ON avatar_webchat.chat_room.id = avatar_webchat.chat_relation.chat_id\n"+
                           "LEFT OUTER JOIN avatar_webchat.chat_line ON avatar_webchat.chat_line.id = avatar_webchat.chat_relation.chat_line_id\n"+
                           "WHERE avatar_webchat.chat_room.id = " + chatRoom.getRoomId();
        
        List<ChatMessage> chatRoomMessages = new LinkedList<ChatMessage>();
        
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        
        for(Map row : rows) {
            ChatMessage cm = new ChatMessage();
            cm.setSender((String) row.get("username"));
            //cm.setTimeStamp( row.get("timestamp"));
            cm.setMessage((String) row.get("message"));
            chatRoomMessages.add(cm);  
        }
           
        chatRoom.setMessages(chatRoomMessages);
        return chatRoom;     
    }
    
    
    
    
}
