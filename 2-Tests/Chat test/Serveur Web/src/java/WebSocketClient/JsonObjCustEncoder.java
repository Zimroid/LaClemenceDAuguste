package WebSocketClient;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/*
 * @author Vincent
 */

public class JsonObjCustEncoder implements Encoder.Text<JsonObjCust> {
    @Override
    public String encode(JsonObjCust obj) throws EncodeException {
        return obj.getJson().toString();
    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
    
    public static String speEncode(JsonObjCust obj) throws EncodeException {
        return obj.getJson().toString();
    }
}
