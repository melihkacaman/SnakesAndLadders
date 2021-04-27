package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Client implements Runnable {
    private Socket sSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private InetSocketAddress pair = null;

    private String serverIp;
    private int port;
    private String userName;

    private Queue<PairProcess> listeners;

    public Client(String serverIp, int port, String userName) throws IOException {
        this.serverIp = serverIp;
        this.port = port;
        this.userName = userName;

        this.sSocket = new Socket(this.serverIp, this.port);
        this.output = new ObjectOutputStream(this.sSocket.getOutputStream());
        this.input = new ObjectInputStream(sSocket.getInputStream());

        this.listeners = new LinkedList<>();
    }

    @Override
    public void run() {
        while (!sSocket.isClosed()){
            try {
                this.pair = (InetSocketAddress) input.readObject();  // blocking

                int port = pair.getPort();
                String IP = pair.getAddress().getHostAddress();

                System.out.println("[Client in Core Module] Target socket is " + IP + "/" + port);

                listeners.peek().contactPair();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("[Client in Core Module] " + e.getMessage());
            }
        }
    }

    public void contactListener(PairProcess process){
        this.listeners.add(process);
    }

    public InetSocketAddress getPair() {
        return pair;
    }
}
