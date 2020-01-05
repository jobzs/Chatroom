
package ChatRoom;

import Business.UserManager;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

@ServerEndpoint("/ChatRoomServerEndPoint")

public class ChatRoomServerEndPoint {
    static Set<Session> chatroomUsers = Collections.synchronizedSet(new HashSet<Session>());
    @OnOpen
    public void handleOpen(Session userSession){
        chatroomUsers.add(userSession);
    }
    @OnMessage
    public void handleMessage(String message, Session userSession) throws IOException{
        String username = (String) userSession.getUserProperties().get("username");
        
        
        
        if ("/QUIT".equals(message) || "/quit".equals(message)){
            userSession.getUserProperties().put("username", message);
            userSession.getBasicRemote().sendText(buildJsonData("Sistema ", "você desconectou!"));
            Iterator<Session> iterator = chatroomUsers.iterator();
            while(iterator.hasNext()) iterator.next().getBasicRemote().sendText(buildJsonData("Sistema ", username + " desconectou!"));
            chatroomUsers.remove(userSession);
            new UserManager().RetirarLogin(username);
            return;
        }
        
       
        
        
        if (message.contains("/NOME ") || message.contains("/nome ")){
            String newuserName = message.split(" ")[1].trim();
            new UserManager().TrocarNome(username, newuserName);
            userSession.getBasicRemote().sendText(buildJsonData("Sistema", "O nome foi trocado!"));
            Iterator<Session> iterator = chatroomUsers.iterator();
            while(iterator.hasNext()) iterator.next().getBasicRemote().sendText(buildJsonData("Sistema ", username + " trocou de nome para "+newuserName));
            userSession.getUserProperties().put("username", newuserName);
            return;
        }
        if(message.contains("/SORTEIO") || message.contains("/sorteio")){
            String nome = new UserManager().RealizarSorteio();
              Iterator<Session> iterator = chatroomUsers.iterator();
              if("Não existe mais usuarios para sortear!".equals(nome)){
            while(iterator.hasNext()) iterator.next().getBasicRemote().sendText(buildJsonData("Sorteio", nome));
            }else{
            while(iterator.hasNext()) iterator.next().getBasicRemote().sendText(buildJsonData("Sorteio", "Usuário "+nome+" foi selecionado, e seu nome registrado."));
            
              }
                return;
        }
        
        if (username == null){
            if(message.equals("") || message == null){
                userSession.getBasicRemote().sendText(buildJsonData("Sistema","Insira um nome para conseguir logar"));
                return;
            }else{
                userSession.getUserProperties().put("username", message);
                Iterator<Session> iterator = chatroomUsers.iterator();
                new UserManager().RealizarLogin(message);
            while(iterator.hasNext()) iterator.next().getBasicRemote().sendText(buildJsonData("Sistema","Um novo usuario entrou com o nome de: " + message));
                userSession.getBasicRemote().sendText(buildJsonData("Sistema","Você esta conectado como: " + message));
                userSession.getBasicRemote().sendText(buildJsonData("-----------------------------------------------------------\n"
                        + "Sistema", "\nInsira /QUIT ou /quit para sair\n"
                        + "Insira /NOME {newname] ou /nome {newname] para trocar de nome\n"
                        + "Insira /SORTEIO ou /sorteio para sortear um participante\n"
                        + "-----------------------------------------------------------"));
                new UserManager().RealizarLogin(username);
                return;
            }
        } else {
            Iterator<Session> iterator = chatroomUsers.iterator();
            while(iterator.hasNext()) iterator.next().getBasicRemote().sendText(buildJsonData(username, message));
        }
    }
    @OnClose
    public void handleClose(Session userSession){
        chatroomUsers.remove(userSession);
    }
   private String buildJsonData(String username, String message){
       JsonObject jsonObject = Json.createObjectBuilder().add("message", username+": "+message).build();
       StringWriter stringWriter = new StringWriter();
       try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(jsonObject);}
       return stringWriter.toString();
   }

    @OnError
    public void onError(Throwable t) {
    
    }
    
    
}
