package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import model.Pair;

public class Client implements Runnable {
    private Socket sSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private String serverIp;
    private int port;

    private String selfUserName;
    private String pairUserName;

    private Queue<TCPListener> listeners;

    public Client(String serverIp, int port, String selfUserName) throws IOException {
        this.serverIp = serverIp;
        this.port = port;
        this.selfUserName = selfUserName;

        this.sSocket = new Socket(this.serverIp, this.port);
        this.output = new ObjectOutputStream(this.sSocket.getOutputStream());
        this.input = new ObjectInputStream(sSocket.getInputStream());

        sendUserName();

        this.listeners = new LinkedList<>();
    }

    private void sendUserName() throws IOException {
        if (!sSocket.isClosed()){
            output.writeObject(this.selfUserName);
        }
    }

    @Override
    public void run() {
        try {
            Object name = input.readObject(); // pair userName blocking
            this.pairUserName = name.toString();

            Pair pair = new Pair(pairUserName, selfUserName);

            listeners.peek().contactPair(pair);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        while (!sSocket.isClosed()){

        }
    }

    public void contactListener(TCPListener process){
        this.listeners.add(process);
    }
}
