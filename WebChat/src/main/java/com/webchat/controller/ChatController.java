/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import com.webchat.model.ChatRoom;
import com.webchat.model.ChatUserHelper;
import com.webchat.model.Message;
import com.webchat.model.RoomHandler;
import com.webchat.model.User;
import com.webchat.service.ChatService;
import com.webchat.service.UserService;
import com.webchat.util.SessionUtil;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 *
 * @author filip
 */

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;
    
    
    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    private SimpMessagingTemplate template;
    
    User currentUser;
    List<ChatRoom> chatRoomAndFriendIds;
    
    @RequestMapping(value = "/main/chat/none", method = RequestMethod.GET)
    public String main(HttpServletRequest request, ModelMap model){
        currentUser = (User) request.getSession().getAttribute("user");
        chatRoomAndFriendIds = chatService.getRoomsForUser(currentUser.getId());
        
        model.addAttribute("username", currentUser.getUsername());
        return "main/chat";
    }
    
    @RequestMapping(value = "/main/chat/{roomId}", method = RequestMethod.GET)
    public String RoomController(HttpServletRequest request, ModelMap model, @PathVariable int roomId){
        currentUser = (User) request.getSession().getAttribute("user");
        chatRoomAndFriendIds = chatService.getRoomsForUser(currentUser.getId());
        
        if(hasAccessToRoom(roomId)){
           
            model.addAttribute("roomId", roomId);
            return "main/chat";
        }
        return "redirect:/main/chat/none";    
    }
    
    @RequestMapping(value = "/main/chat/leaveGroup", method = RequestMethod.POST)
    @ResponseBody
    public String leaveGroup(@RequestParam int roomId,
                             @RequestParam int userId,
                             @RequestParam String username,
                             RedirectAttributes redirectAttributes){
    
        if (chatService.leaveChatGroup(roomId, userId)) {
            sendMessageToRoom(roomId, username + " has left the chat room!");
            redirectAttributes.addFlashAttribute("success_message", "You left the chat!");
            return "/WebChat/main/chat/none";
        }
        else{
            redirectAttributes.addFlashAttribute("error_message", "Something went wrong when leaving group..");
            return "/WebChat/main/chat/" + roomId;
        }
    }
    
    @RequestMapping(value = "/main/chat/clearHistory", method = RequestMethod.POST)
    @ResponseBody
    public String clearHistory(@RequestParam int roomId,
                               @RequestParam String username){
        
        if (chatService.clearChatHistory(roomId)) {
            sendCommandToRoom(roomId, "clear");
            sendMessageToRoom(roomId, "History was cleared by " + username);
            return "/WebChat/main/chat/" + roomId;
        }else{
            sendMessageToRoom(roomId, "Something went wrong with the clear request!");
            return "/WebChat/main/chat/" + roomId;
        }
    }
    
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}/messages")
    public Message chatMessage(@DestinationVariable int roomId, Message message){
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        Message messageObject = new Message(message.getFrom(),message.getText(),time, message.getUser_id());
        chatService.addMessageToRoom(messageObject, roomId);
        
        return messageObject;
    }
    
    @MessageMapping("/chat/{roomId}/{newUser}/createGroup")
    public void createGroup(@DestinationVariable int roomId,
                            @DestinationVariable int newUser){
      
        if(chatService.createGroupChat(generateGroupName(), roomId, newUser))
        {
            sendMessageToRoom(roomId,"Group Was Successfully Created");   
        }else{
            sendMessageToRoom(roomId,"Whoops! We could not create a new Groupchat for you!");   
        } 
    }
    
    @SubscribeMapping("/getOnlineFriends")
    public List<ChatUserHelper> listFriends (){
        return getListOfOnlineFriends();
    } 
    
    @SubscribeMapping("/getGroups")
    public List<ChatRoom> listGroups (){
        return getListOfGroups();
    } 
    
    @SubscribeMapping("/{roomId}/getRoomUsers")
    public List<ChatUserHelper> listUsersInRoom (@DestinationVariable int roomId){
        return chatService.getUsersinRoom(roomId, currentUser.getId());
    }
    
    @SubscribeMapping("/{roomId}")
    public List<Message> listMessages (@DestinationVariable int roomId){
        return chatService.getMessagesByRoomId(roomId);
    }
    
    private List<ChatUserHelper> getListOfOnlineFriends(){
        HashSet<String> onlineUsernames = getOnlineUsernames();
        List<ChatUserHelper> friendsOnline = new ArrayList<>();    
        for(ChatRoom room : chatRoomAndFriendIds){
            List<ChatUserHelper> listOfUsersInRoom = room.getMembers();
            if(room.getIsGroupRoom() == 0){
               for(ChatUserHelper user : listOfUsersInRoom){
                    if(onlineUsernames.contains(user.getUsername())){
                       friendsOnline.add(user);
                    }     
               }
            }
        }
        return friendsOnline;
    }
    
    private List<ChatRoom> getListOfGroups(){
      
        List<ChatRoom> groups = new ArrayList<>();    
        for(ChatRoom room : chatRoomAndFriendIds){
            if(room.getIsGroupRoom() == 1){
               groups.add(room);
            }
        }
        return groups;
    }
    
    private HashSet<String> getOnlineUsernames(){
        List<User> onlineUsers = sessionUtil.getOnlineUsers();
        HashSet<String> onlineUsernames = new HashSet<>();
        for(User u : onlineUsers) { 
            onlineUsernames.add(u.getUsername());            
        }
        return onlineUsernames;
    }
    
    private boolean hasAccessToRoom(int roomId){
        for(ChatRoom room : chatRoomAndFriendIds){

            if(room.getRoomId() == roomId){
                return true;
            }
        }
        return false;
    }
    
    private void sendMessageToRoom(int roomId, String text){
        Message message = new Message("SERVER", text, "00:00:00", 0);
        chatService.addMessageToRoom(message, roomId);
        this.template.convertAndSend("/topic/"+roomId+"/messages", message);
    }
    
    private void sendCommandToRoom(int roomId, String command){
        this.template.convertAndSend("/topic/"+roomId+"/roomHandler", new RoomHandler(command));
    }
    
    private String generateGroupName(){
           
            String[] adjectives = {"Angry", "Fat", "Tiny", "Cute", "Scary", "Adorable", "Autistic",
                             "Mad", "Sleepy", "Hungry", "Annoyed", "Sarcastic", "Spiny"};
                             
            String[] animals = {"Harambes", "Turtles", "Hippos", "Dobbys", "Elephants", "Monkeys",
                            "Kittens", "Spiders", "Chickens", "Whales", "Snakes", "Phasmatidaes",
                            "Tigers", "Crabs", "Pumas", "Cougars", "Eagles", "Carl Von Linnés",
                            "Raptors"};
            
            Random rn = new Random();
            int rAdjectives = rn.nextInt(adjectives.length);
            int rAnimals = rn.nextInt(animals.length);;
            
            return adjectives[rAdjectives] + " " + animals[rAnimals];
        
    }
    
    
}



