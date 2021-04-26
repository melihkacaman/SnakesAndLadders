package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SClient {
    private Socket socket;
    private ObjectOutputStream cOutput;
    private ObjectInputStream cInput;
    private ClientListenThread clientListenThread;

    public SClient(Socket socket) throws IOException {
        this.socket = socket;

        cOutput = new ObjectOutputStream(this.socket.getOutputStream());
        cInput = new ObjectInputStream(this.socket.getInputStream());

        this.clientListenThread = new ClientListenThread(this);
        System.out.println("A Client connected.");
    }

    public void listen(){
        clientListenThread.start();
        System.out.println("Client Listening...");  // client listening 
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
}

class ClientListenThread extends Thread{
    private final SClient sClient;

    ClientListenThread(SClient sClient) {
        this.sClient = sClient;
    }

    @Override
    public void run() {
        while (!sClient.getSocket().isClosed()){
            try {
                Object obj = sClient.getcInput().readObject(); // blocking
                // do something
                sClient.getcOutput().writeObject(true);    // response
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Client is left");
                e.printStackTrace();
            }
        }
    }
}
