package server;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

import client.SClient;

class Mapping extends Thread {

    private Queue<SClient> userList;

    Mapping(Queue<SClient> userList){
        this.userList = userList;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (userList.size() > 1){
                SClient client1 = userList.poll();
                SClient client2 = userList.poll();

                //mapped
                client1.setPair(client2);
                client2.setPair(client1);

                try {
                    client1.getcOutput().writeObject(client2.getSocket());
                    client2.getcOutput().writeObject(client1.getSocket());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
