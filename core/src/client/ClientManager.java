package client;


import com.melihkacaman.entity.StartingSignal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ClientManager {
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientManager(ObjectOutputStream output, ObjectInputStream input) {
        // server input output
        this.output = output;
        this.input = input;
    }

    public void sendStartSignal() {
        try {
            output.writeObject(StartingSignal.WORK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
