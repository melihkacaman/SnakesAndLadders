package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SClient {
    private Socket socket;
    private ObjectOutputStream cOutput;
    private ObjectInputStream cInput;

    private SClient pair;

    public SClient(Socket socket) throws IOException {
        this.socket = socket;

        cOutput = new ObjectOutputStream(this.socket.getOutputStream());
        cInput = new ObjectInputStream(this.socket.getInputStream());

        System.out.println("A Client connected.");
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
}