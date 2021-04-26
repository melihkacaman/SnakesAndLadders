package server;

import client.SClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Server {
    private ServerSocket socket;
    private int port;
    private ListenThread listenThread;
    private Mapping mapping;

    private static Queue<SClient> users = new LinkedList<>();

    public Server(int port) throws IOException {
        this.port = port;
        this.socket =new ServerSocket(port);
        this.mapping = new Mapping(users);

        listenThread = new ListenThread(this);
        this.mapping.start();
    }

    public void listen(){
        listenThread.start();
    }

    protected void addUser(SClient user){
        users.add(user);
    }

    protected ServerSocket getSocket() {
        return socket;
    }

    protected Queue<SClient> getUsers(){
        return users;
    }
}

class ListenThread extends Thread {
    private Server server;

    protected ListenThread(Server server){
        this.server = server;
    }

    @Override
    public void run() {
        while (!server.getSocket().isClosed()){
            try {
                System.out.println("Accepting state");
                Socket client = server.getSocket().accept(); // blocking function
                SClient sClient = new SClient(client);

                server.addUser(sClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}