var ws;
var stompClient;
        
$(document).ready(function(){
        document.querySelector('#sendMessage').addEventListener('click', send);
        ws = new SockJS("/WebChat/chat");
        //ws = new SockJS('http://' + window.location.host + '/chat');
        //ws = new SockJS('https://avatar-web-chat.herokuapp.com/chat');
        stompClient = Stomp.over(ws);

        stompClient.connect({}, function(frame){
        console.log("Connected to Websocket!");

        stompClient.subscribe("/topic/"+"[[${roomId}]]"+"/messages", function(message){
            var content = JSON.parse(message.body);
            var li = document.createElement("LI");
            var span = document.createElement("SPAN");
            var img = document.createElement("IMG");
            img.src = "@{/resources/AVATAR/avatar2.png}";
            var textnode = document.createTextNode(content.time +"| "+ img +content.from + " says " + content.text);
            li.appendChild(textnode);
            document.getElementById("messageList").appendChild(li);
        });

        stompClient.subscribe("/app/initChat", function(list){ 
            var node = document.createElement("LI");
            var textnode = document.createTextNode("You are are in the chat-room:"+"[[${roomId}]]");
            node.appendChild(textnode);
            document.getElementById("messageList").appendChild(node);

            var listOfChatRooms = JSON.parse(list.body);

            for (var chatRoom in listOfChatRooms) {
                if (listOfChatRooms.hasOwnProperty(chatRoom)) {
                    var members = listOfChatRooms[chatRoom].members;
                    if(members.length < 2){
                         for (var member in members) {
                            if (members.hasOwnProperty(member)) {
                                var aTag = document.createElement('a');
                                aTag.setAttribute('href',"/WebChat/main/chat/"+members[member].roomId);
                                aTag.setAttribute('id', 'friendStyle');
                                var textnode = document.createTextNode(members[member].username);
                                aTag.appendChild(textnode);
                                var mybr = document.createElement('br');
                                document.getElementById("usersOnline").appendChild(aTag);
                                document.getElementById("usersOnline").appendChild(mybr);
                            }
                        }  
                    }
                    else {
                        var aTag = document.createElement('a');
                        aTag.setAttribute('href',"/WebChat/main/chat/"+listOfChatRooms[chatRoom].roomId);
                        var textnode = document.createTextNode("Group of Awesome");
                        aTag.appendChild(textnode);
                        var mybr = document.createElement('br');
                        document.getElementById("groupList").appendChild(aTag);
                        document.getElementById("groupList").appendChild(mybr); 
                    }
                }
            }    
            });                 
        });
    });
     $(document).keypress(function (e) {
        if (e.which == 13) {
            send();
        }
    });

    function send(){
        var message = document.getElementById("inputChat").value;
        if(message === ""){
            return;
        }
        var user = "[[${session.user.username}]]";
        stompClient.send("/app/chat/[[${roomId}]]",{}, JSON.stringify({'from':user, 'text':message} ));
        document.getElementById('inputChat').value='';
    }
