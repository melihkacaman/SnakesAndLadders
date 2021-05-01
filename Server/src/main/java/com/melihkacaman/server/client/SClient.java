package com.melihkacaman.server.client;

import com.melihkacaman.entity.AckSignal;
import com.melihkacaman.entity.Pair;
import com.melihkacaman.entity.PairMovement;
import com.melihkacaman.entity.StartingSignal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import movement.Movement;

public class SClient {
    private int id;
    private Socket socket;
    private ObjectOutputStream cOutput;
    private ObjectInputStream cInput;
    private String userName;
    protected boolean startingGame = false;

    private SClient pair;

    private ClientListenThread clientListenThread;

    public SClient(Socket socket, int id) throws IOException {
        this.socket = socket;
        this.id = id;
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

    public int getId() {
        return id;
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
            Pair pair = new Pair(sClient.getPair().getId(), sClient.getPair().getUserName(),sClient.getId());
            sClient.getcOutput().writeObject(pair);

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
            while (!(sClient.getPair().startingGame && sClient.startingGame)){
                Thread.sleep(200);
            }


            if (sClient.getPair().startingGame && sClient.startingGame){
                sClient.getcOutput().writeObject(AckSignal.ACK);
                sClient.getPair().getcOutput().writeObject(AckSignal.ACK);
                while (!sClient.getSocket().isClosed()){
                    Object obj = sClient.getcInput().readObject();
                    if(obj instanceof PairMovement){
                      if ((((PairMovement) obj).getId() == sClient.getId())) {   // Todo: might be unnecessary
                          sClient.getPair().getcOutput().writeObject(obj);
                      }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}