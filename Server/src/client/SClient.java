package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SClient {
    private Socket socket;
    private ObjectOutputStream cOutput;
    private ObjectInputStream cInput;
    private String userName;

    private SClient pair;

    private ClientListenThread clientListenThread;

    public SClient(Socket socket) throws IOException {
        this.socket = socket;

        cOutput = new ObjectOutputStream(this.socket.getOutputStream());
        cInput = new ObjectInputStream(this.socket.getInputStream());

        clientListenThread = new ClientListenThread(this);

        System.out.println("A Client connected.");
    }

    public void listen(){
        clientListenThread.start();
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getcOutput() {
        return cOutput;
    }

    public ObjectInputStream getcInput() {
        return cInput;
    }

    public SClient getPair() {
        return pair;
    }

    public void setPair(SClient pair) {
        this.pair = pair;
    }

    public boolean hasPair(){
        return pair != null;
    }

    protected void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }
}

class ClientListenThread extends Thread {
    private final SClient sClient;

    ClientListenThread(SClient sClient){
        this.sClient = sClient;
    }

    @Override
    public void run() {
        try {
            Object object = sClient.getcInput().readObject();
            sClient.setUserName(object.toString());

            while (sClient.getPair() == null){
                Thread.sleep(1000);
            }

            // mapped
            sClient.getcOutput().writeObject(sClient.getPair().getUserName());
            while (!sClient.getSocket().isClosed()){

            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}