package client;

import com.melihkacaman.entity.Pair;
import com.melihkacaman.entity.PairMovement;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import model.Couple;
import movement.Movement;
import sceenes.PlayBoard;

public class Client implements Runnable {
    private Socket sSocket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private String serverIp;
    private int port;

    private String selfUserName;

    private ClientManager clientManager;

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
            Pair pair = (Pair) input.readObject(); // pair userName blocking

            Couple couple = new Couple(pair, selfUserName);
            clientManager = new ClientManager(sSocket,output, input);
            listeners.peek().contactPair(couple, clientManager);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        while (!clientManager.isGameStarted){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("IS GAME STARTED ICINDE...");
        while (!sSocket.isClosed()){
            System.out.println("READ OBJECT OLDU");
            try {
                Object obj = input.readObject();
                if (obj instanceof PairMovement){
                    PairMovement pairMovement = (PairMovement) obj;
                    // clientManager.activeMovement = new Movement()
                    System.out.println("ACTIVE MOVEMENT DOLDU");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void contactListener(TCPListener process){
        this.listeners.add(process);
    }
}
