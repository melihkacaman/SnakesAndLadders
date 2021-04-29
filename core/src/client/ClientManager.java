package client;


import com.melihkacaman.entity.AckSignal;
import com.melihkacaman.entity.StartingSignal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Pair;


public class ClientManager {
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientManager(ObjectOutputStream output, ObjectInputStream input) {
        // server input output
        this.output = output;
        this.input = input;
    }

    public boolean sendStartSignal() {
        try {
            System.out.println("WORK sinyali yollandı");
            output.writeObject(StartingSignal.WORK);
            System.out.println("Ack sinyali bekleniyor");
            AckSignal signalACK = (AckSignal)input.readObject();   // Todo : waiting main thread
            System.out.println("Ack sinyali geldi");
            if(signalACK == AckSignal.ACK){
              return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
