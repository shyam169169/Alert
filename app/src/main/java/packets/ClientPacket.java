package packets;

import java.io.Serializable;

public class ClientPacket implements Serializable {
    public static final long serialVersionUID = 169L;
    public String message = "default";
    public int interval = 0;

    public ClientPacket(String message, int interval) {
        this.message = message;
        this.interval = interval;
    }
}
