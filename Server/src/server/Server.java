package server;

import client.SClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket socket;
    private int port;
    private ListenThread listenThread;

    private static List<SClient> users = new ArrayList<>();

    public Server(int port) throws IOException {
        this.port = port;
        this.socket =new ServerSocket(port);

        listenThread = new ListenThread(this);
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
                sClient.listen();
                server.addUser(sClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}