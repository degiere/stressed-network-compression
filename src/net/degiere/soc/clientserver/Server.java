package net.degiere.soc.clientserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import net.degiere.soc.util.Utils;

import org.apache.log4j.Logger;

public class Server extends Thread {

    private static Logger logger = Logger.getLogger(Server.class);

    protected ServerSocket listener;
    protected boolean running;
    protected int port;
    protected int count;

    public Server(int port, int count) {
        this.count = count;
        this.port = port;
        this.startServer();
    }

    public void startServer() {
        try {
            this.listener = new ServerSocket(port);
            logger.info("Starting server listening on port " + port + ".");
            this.running = true;
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        logger.info("Stopping server listening on port " + port + ".");
        this.running = false;
        this.interrupt();
        try {
            this.listener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (running) {
            logger.info("Waiting for connections on umcompressed stream.");
            try {
                Socket client = this.listener.accept();
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                // write a bunch of objects to the stream for the client to grab
                for (int i = 0; i < count; i++) {
                    oos.writeObject(Utils.getRandomEmployee());
                }
                oos.close();
                client.close();
            } catch (IOException e) {
                // TODO: improve, socket handling
            }
        }
    }

}
