package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Client implements Runnable {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private InetSocketAddress pair = null;

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
                this.pair = (InetSocketAddress) input.readObject();  // blocking

                int port = pair.getPort();
                String IP = pair.getAddress().getHostAddress();

                System.out.println("[Client in Core Module] Target socket is " + IP + "/" + port);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("[Client in Core Module] " + e.getMessage());
            }
        }
    }

    public InetSocketAddress getPair() {
        return pair;
    }
}
