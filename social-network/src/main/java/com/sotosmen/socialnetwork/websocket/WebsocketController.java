package com.sotosmen.socialnetwork.websocket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.sotosmen.socialnetwork.messaging.Message;
import com.sotosmen.socialnetwork.services.MessageService;

@Controller
public class WebsocketController {
	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired
	private MessageService messageService;

    @MessageMapping("/messenger")
    private void message(String jsonMessage) {
    	SimpleDateFormat simpDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa",Locale.ENGLISH);
    	Message message = new Message();
    	try {
	    	JSONObject jsonObject = new JSONObject(jsonMessage);
	    	message.setOwnerConversation(jsonObject.getLong("ownerConversation"));
	    	message.setSenderUser(jsonObject.getString("senderUser"));
	    	message.setReceiverUser(jsonObject.getString("receiverUser"));
	    	message.setText(jsonObject.getString("text"));
			message.setTimestamp(simpDateFormat.parse(jsonObject.getString("timestamp")));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        System.out.println("Received message: " + message.getText());
        messageService.createMessage(message);
        simpMessagingTemplate.convertAndSend("/topic/"+message.getOwnerConversation(),message);
    }
}