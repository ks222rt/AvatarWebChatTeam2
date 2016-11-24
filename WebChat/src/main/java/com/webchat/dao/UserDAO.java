/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.webchat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.webchat.util.HashUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Kristoffer
 */
@Component
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /*
        addUser(User user)
        Checks if input Username and Email is unique.
        If it is add the user to database.
    */
    public boolean addUser(final User user) {
        if (isUsernameFree(user.getUsername()) && isEmailFree(user.getEmail())) {
            final String sqlForChatUserInfo = "insert into avatar_webchat.chat_user_info(firstname, lastname, email, created)"
                    + "values(?, ?, ?, NOW())";
            String sqlForChatUser = "insert into avatar_webchat.chat_user(username, password, salt, info_id)\n"
                    + "values(?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            try {
                Number keyResult;
                int result = 0;
                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sqlForChatUserInfo, Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, user.getFirstname());
                        ps.setString(2, user.getLastname());
                        ps.setString(3, user.getEmail());
                        return ps;
                    }

                }, keyHolder);

                keyResult = keyHolder.getKey();
                if (keyResult != null) {
                    result = jdbcTemplate.update(sqlForChatUser,
                            new Object[]{user.getUsername(),
                                user.getPassword(),
                                user.getSalt(),
                                (int) keyResult.intValue()});
                }
                return result != 0;
            } catch (IncorrectResultSizeDataAccessException e) {
                return false;
            }
        }
        return false;
    }
     /*
        LoginUser(User user)
        Tries to find the user using method getUserObject(username).
        Returns a result and compare the input password with the user password in database.
    */
    public User loginUser(String username, String password) {
        User userResult = getUserObject(username);
        if (userResult != null) {
            String userPassword = HashUtil.hashPassword(password, userResult.getSalt());
            if (userPassword.equals(userResult.getPassword())) {
                return userResult;
            }
        }
        return null;

    }

    public User searchUser(String username) {
        return getUserObject(username);
    }
     /*
        Returns a List<User> with all the users in the database.
    */
    public List<User> getAllUsers() {
        String sql = "SELECT avatar_webchat.chat_user.id as id,\n"
                + "avatar_webchat.chat_user.username as username,\n"
                + "avatar_webchat.chat_user.password as password,\n"
                + "avatar_webchat.chat_user.salt as salt,\n"
                + "avatar_webchat.chat_user_info.email as email,\n"
                + "avatar_webchat.chat_user_info.firstname as firstname,\n"
                + "avatar_webchat.chat_user_info.lastname as lastname,\n"
                + "avatar_webchat.chat_user_info.isAdmin as isAdmin,\n"
                + "avatar_webchat.chat_user_info.created as created\n"
                + "FROM\n"
                + "avatar_webchat.chat_user_info, avatar_webchat.chat_user\n"
                + "WHERE\n"
                + "avatar_webchat.chat_user_info.id = avatar_webchat.chat_user.info_id;";
        LinkedList<User> users = new LinkedList();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            User user = new User();
            user.setUsername((String) row.get("username"));
            user.setFirstname((String) row.get("firstname"));
            user.setLastname((String) row.get("lastname"));
            user.setEmail((String) row.get("email"));
            user.setSalt((byte[]) row.get("salt"));
            user.setPassword((String) row.get("password"));
            user.setId((Integer) row.get("id"));
            user.setCreated((Timestamp) row.get("created"));
            user.setIsAdmin((Integer) row.get("isAdmin"));
            users.add(user);
        }

        return users;
    }

    public boolean alreadyFriends(final int senderID, final int recieverID) {
        
        String sqlAlreadyFriends = "SELECT avatar_webchat.friend.id1\n"+
                                   "FROM avatar_webchat.friend\n"+
                                   "WHERE avatar_webchat.friend.id1 = " + senderID +"\n"+
                                   "AND avatar_webchat.friend.id2 = " + recieverID;
       
        List<Integer> identifiers = new LinkedList<>();  
       try{
           
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlAlreadyFriends);
        for (Map row : rows) {
            identifiers.add((int)row.get("id1"));
            
        }
        
       }
       catch(EmptyResultDataAccessException e){
           return false;
       }
       
       if(identifiers.size() <= 0)
       {
           return false;
       }
       else
        return true;              
    }
    
    public List<User> getUserFriends(int userID){
       String sqlGetFriends = "SELECT avatar_webchat.chat_user.id as id,\n" + 
                               "avatar_webchat.chat_user.username as username,\n" + 
                               "avatar_webchat.chat_user_info.firstname as firstname,\n" +
                               "avatar_webchat.chat_user_info.lastname as lastname,\n" + 
                               "avatar_webchat.chat_user_info.email as email\n" + 
                               "FROM\n"+
                               "avatar_webchat.chat_user\n" +
                               "INNER JOIN avatar_webchat.chat_user_info\n" + 
                               "on avatar_webchat.chat_user.info_id = avatar_webchat.chat_user_info.id\n" +
                               "INNER JOIN avatar_webchat.friend\n" + 
                               "on\n" + 
                               "avatar_webchat.chat_user.id = avatar_webchat.friend.id2\n" +
                               "WHERE avatar_webchat.friend.id1 = " + userID;
       List<User> friends = new LinkedList<>();  
       try{
           
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlGetFriends);
        for (Map row : rows) {
            User user = new User();
                user.setUsername((String) row.get("username"));
                user.setFirstname((String) row.get("firstname"));
                user.setLastname((String) row.get("lastname"));
                user.setEmail((String) row.get("email"));
                user.setId((Integer) row.get("id"));
            friends.add(user);
        }
       }
       catch(Exception e){
           return null;
       }
        return friends;      
    }
    
    /*
        Gets the user object based on the username input.
    */
    private User getUserObject(String username) {
        String sql = "SELECT avatar_webchat.chat_user.id as id,"
                + "avatar_webchat.chat_user.username as username, \n"
                + "avatar_webchat.chat_user.password as password,\n"
                + "avatar_webchat.chat_user.salt as salt,\n"
                + "avatar_webchat.chat_user_info.email as email,\n"
                + "avatar_webchat.chat_user_info.firstname as firstname,\n"
                + "avatar_webchat.chat_user_info.lastname as lastname,\n"
                + "avatar_webchat.chat_user_info.isAdmin as isAdmin,\n"
                + "avatar_webchat.chat_user_info.created as created\n"
                + "FROM\n"
                + "avatar_webchat.chat_user_info, avatar_webchat.chat_user\n"
                + "WHERE \n"
                + "avatar_webchat.chat_user.username = ? AND chat_user_info.id = chat_user.info_id;";

        try {

            User userResult = jdbcTemplate.queryForObject(sql,
                    new Object[]{username},
                    new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setFirstname(rs.getString("firstname"));
                    user.setLastname(rs.getString("lastname"));
                    user.setEmail(rs.getString("email"));
                    user.setSalt(rs.getBytes("salt"));
                    user.setPassword(rs.getString("password"));
                    user.setId(rs.getInt("id"));
                    user.setCreated(rs.getTimestamp("created"));
                    user.setIsAdmin(rs.getInt("isAdmin"));
                    return user;
                }
            });
            
            return userResult;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    /*
        addFriendRequest(int senderID, int recieverID).
        returns true if the request successfully added
        the sender and reciever in the database.
    */
    public boolean addFriendRequest(final int senderID, final int recieverID) {
        if(friendRequestExists(senderID, recieverID) || friendRequestExists(recieverID, senderID) || alreadyFriends(senderID, recieverID) || senderID == recieverID){
            return false;
        }
        final String sqlAddFriend = "insert into avatar_webchat.friend_requests(sender, reciever)\n"
                + "VALUES (?, ?)";
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlAddFriend);
                    ps.setInt(1, senderID);
                    ps.setInt(2, recieverID);
                    return ps;
                }
            });
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
    
    
    /*
    respondToFriendRequest(int senderID, int recieverID, boolean accepted).
    Checks if there actually is a friend request between the two identfiers.
    Removes the friend request from the database.
    If accepted is -> true, insert the two identifiers in the friends table.
    If accepted is -> false, do nothing.
    */
    public boolean respondToFriendRequest(final int senderID, final int recieverID, final boolean accepted) {
        if (!friendRequestExists(recieverID, senderID)) {
            return false;
        }
        final String sqlFriendRequestResponse = "insert into avatar_webchat.friend(id1, id2) \n"
                + "values(?, ?), (?, ?)";
        final String sqlRemoveFriendRequest = "delete from avatar_webchat.friend_requests\n"
                + "where avatar_webchat.friend_requests.reciever = ? AND avatar_webchat.friend_requests.sender = ?";
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlRemoveFriendRequest);
                    ps.setInt(1, senderID);
                    ps.setInt(2, recieverID);
                    return ps;
                }
            });

            if (accepted) {
                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sqlFriendRequestResponse);
                        ps.setInt(1, senderID);
                        ps.setInt(2, recieverID);
                        ps.setInt(3, recieverID);
                        ps.setInt(4, senderID);
                        return ps;
                    }
                });
            }
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    
    }
    /*
    Returns list of user objects containing ID and username
    Used to list all friend requests to provided user
    */
    
    public List<User> getFriendRequests(final int userID) {
        
        final String sqlGetFriendRequests = "SELECT avatar_webchat.friend_requests.sender,\n"+
                                            "avatar_webchat.chat_user.username,\n"+
                                            "avatar_webchat.chat_user_info.firstname,\n"+
                                            "avatar_webchat.chat_user_info.lastname\n"+
                                            "FROM avatar_webchat.friend_requests\n"+
                                            "INNER JOIN avatar_webchat.chat_user\n"+
                                            "ON avatar_webchat.friend_requests.sender = avatar_webchat.chat_user.id\n"+
                                            "INNER JOIN avatar_webchat.chat_user_info\n"+
                                            "ON avatar_webchat.chat_user.info_id = avatar_webchat.chat_user_info.id\n"+
                                            "WHERE avatar_webchat.friend_requests.reciever = " + userID;
        
        List<User> friendRequests = new LinkedList<>();  
        
        try{

             List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlGetFriendRequests);
             for (Map row : rows) {
                 User user = new User();
                     user.setUsername((String) row.get("username"));
                     user.setId((int)row.get("sender"));
                     user.setFirstname((String)row.get("firstname"));
                     user.setLastname((String)row.get("lastname"));
                 friendRequests.add(user);
             }
        }
        catch(Exception e){
            return null;
        }
         return friendRequests;     
    }
    
    /*
    removeFriend(int senderID, int recieverID)
    Removes all instances of friend requests between senderID and recieverID and vice versa (due to double linking in DB)
    */
    public boolean removeFriend(final int senderID, final int recieverID){
        final String sqlRemoveFriend = "DELETE FROM avatar_webchat.friend\n"+
                                       "WHERE (avatar_webchat.friend.id1 = ? AND avatar_webchat.friend.id2 = ?)\n"+
                                       "OR (avatar_webchat.friend.id1 = ? AND avatar_webchat.friend.id2 = ?)";
      
            try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlRemoveFriend);
                    ps.setInt(1, senderID);
                    ps.setInt(2, recieverID);
                    ps.setInt(3, recieverID);
                    ps.setInt(4, senderID);
                    return ps;
                }
            });
            return true;
            
             } catch (Exception e) {
                return false;
            }
    }
    /*
    isFriendRequestValid(int senderID, int recieverID).
    private method which checks if the actually is a friend request between
    two users. Prevents force-adding.
    */
    private boolean friendRequestExists(final int senderID, final int recieverID) {
        final String sqlValidFriendRequest = " SELECT * FROM avatar_webchat.friend_requests\n"
                + "WHERE avatar_webchat.friend_requests.sender = ?\n"
                + " AND avatar_webchat.friend_requests.reciever = ?";

        try {
            HashMap<Integer, Integer> myHashMap = jdbcTemplate.queryForObject(sqlValidFriendRequest,
                    new Object[]{senderID, recieverID},
                    new RowMapper<HashMap<Integer, Integer>>() {
                @Override
                public HashMap<Integer, Integer> mapRow(ResultSet rs, int i) throws SQLException {
                    HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
                    hm.put(rs.getInt("sender"), rs.getInt("reciever"));
                    return hm;
                }
            });
            if (myHashMap.containsKey(senderID) && myHashMap.containsValue(recieverID)) {
                return true;
            }
            return false;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }

    }

    private boolean isUsernameFree(String username) {
        String sqlUsername = "SELECT avatar_webchat.chat_user.username as username\n"
                + "FROM avatar_webchat.chat_user\n"
                + "WHERE avatar_webchat.chat_user.username = ?";

        try {
            User result;
            result = jdbcTemplate.queryForObject(sqlUsername,
                    new Object[]{username},
                    new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    return user;
                }
            });

            return result == null;
        } catch (EmptyResultDataAccessException e) {
            return true;
        }
    }

    private boolean isEmailFree(String email) {
        String sqlEmail = "SELECT avatar_webchat.chat_user_info.email as email\n"
                + "FROM avatar_webchat.chat_user_info\n"
                + "WHERE avatar_webchat.chat_user_info.email = ?";
        try {
            User result;
            result = jdbcTemplate.queryForObject(sqlEmail,
                    new Object[]{email},
                    new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int i) throws SQLException {
                    User user = new User();
                    user.setEmail(rs.getString("email"));
                    return user;
                }
            });
            return result == null;
        } catch (EmptyResultDataAccessException e) {
            return true;
        }
    }

}
