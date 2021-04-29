package com.melihkacaman.server.client;

import com.melihkacaman.entity.AckSignal;
import com.melihkacaman.entity.StartingSignal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SClient {
    private Socket socket;
    private ObjectOutputStream cOutput;
    private ObjectInputStream cInput;
    private String userName;
    protected boolean startingGame = false;

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

             // TODO: Might be unnecessary
            while (!sClient.getSocket().isClosed()) {
                Object obj = sClient.getcInput().readObject();
                if (obj instanceof StartingSignal){
                    if (obj == StartingSignal.WORK){
                        sClient.startingGame = true;
                        break;
                    }
                }
                else {
                    // Todo: send message to client
                    break;
                }
            }

            // start Game
            if (sClient.getPair().startingGame && sClient.startingGame){
                sClient.getcOutput().writeObject(AckSignal.ACK);
                sClient.getPair().getcOutput().writeObject(AckSignal.ACK);
                while (!sClient.getSocket().isClosed()){

                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}