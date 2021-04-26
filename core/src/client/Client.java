package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private Socket pair = null;

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

    @Override
    public void run() {
        while (!socket.isClosed()){
            try {
                this.pair = (Socket) input.readObject();  // blocking
                System.out.println("Bulundu");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getPair() {
        return pair;
    }
}
