package client;

import model.Couple;

public abstract class TCPListener {
    public abstract void contactPair(Couple couple, ClientManager clientManager);
}
