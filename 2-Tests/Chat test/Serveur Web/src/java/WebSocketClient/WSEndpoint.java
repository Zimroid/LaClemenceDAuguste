package WebSocketClient;

import SocketAuguste.SocketHandler;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/*
 * @author Vincent
 */

@ServerEndpoint(value="/endpoint", encoders = {JsonObjCustEncoder.class}, decoders = {JsonObjCustDecoder.class})
public class WSEndpoint {
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    // Déclenché à chaque réception de message
    @OnMessage
    public void whenReceive(JsonObjCust obj, Session session) throws IOException, EncodeException {
        // Récéption et décodage message
        String msg = JsonObjCustEncoder.speEncode(obj);
        System.out.println("Web socket : Réception message");
        System.out.println(msg);
        
        // Envoi Auguste
        SocketHandler se = SocketHandler.getSocket();
        se.closeConnection();
        se = SocketHandler.getSocket();
        System.out.println("Oh Auguste serveur, voyez mon message !");
        se.sendMessage(msg);
    }
    
    public static void broadcast(JsonObjCust obj) throws IOException, EncodeException {
        System.out.println("Envoi à tous les clients");
        for (Session sess : sessions) {
            sess.getBasicRemote().sendObject(obj);
        }
    }

    @OnOpen
    public void onOpen (Session sess) {
        sessions.add(sess);
    }

    @OnClose
    public void onClose (Session sess) {
        sessions.remove(sess);
    }

    @OnError
    public void onError(Throwable t) {
    }
}