package client;


import com.melihkacaman.entity.AckSignal;
import com.melihkacaman.entity.PairMovement;
import com.melihkacaman.entity.StartingSignal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientManager {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    public PairMovement activeMovement;

    protected boolean isGameStarted = false;

    public ClientManager(Socket socket, ObjectOutputStream output, ObjectInputStream input) {
        // server input output
        this.socket = socket;
        this.output = output;
        this.input = input;
    }

    public boolean sendStartSignal() {
        try {
            output.writeObject(StartingSignal.WORK);
            Object obj = input.readObject();   // Todo : waiting main thread
            AckSignal signalACK;
            if (obj instanceof AckSignal){
                signalACK = (AckSignal)obj;
                if(signalACK == AckSignal.ACK){
                    isGameStarted = true;
                    return true;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void sendMovement(PairMovement movement){
        try {
            System.out.println(movement.getId() + "veriyi yolladı");
            output.writeObject(movement);
            System.out.println(movement.getId() + "devam etti");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
