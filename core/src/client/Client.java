package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private String serverIp;
    private int port;
    private String userName;

    public Client(String serverIp, int port, String userName) throws IOException {
        this.serverIp = serverIp;
        this.port = port;
        this.userName = userName;

        this.socket = new Socket(this.serverIp, this.port);
        this.output = new ObjectOutputStream(this.socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }


}
