package net.degiere.soc.clientserver.gzip;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

import net.degiere.soc.clientserver.Server;
import net.degiere.soc.util.Utils;

import org.apache.log4j.Logger;

public class ServerGzip extends Server {

    private static Logger logger = Logger.getLogger(ServerGzip.class);

    public ServerGzip(int port, int count) {
        super(port, count);
    }

    @Override
    public void run() {
        while (running) {
            logger.info("Waiting for connections on compressed stream.");
            try {
                Socket client = this.listener.accept();
//              GZIPOutputStream gzos = new GZIPOutputStream(client.getOutputStream());
                // use an anonymous constructor to set the compression level since it's missing from the descendant class
                // however BEST_COMPRESSION and DEFAULT_COMPRESSION appear the same
                GZIPOutputStream gzos = new GZIPOutputStream(client.getOutputStream()) {
                    {
                        def.setLevel(Deflater.BEST_COMPRESSION);
                    }
                };
                ObjectOutputStream oos = new ObjectOutputStream(gzos);
                // write a bunch of objects to the stream for the client to grab
                for (int i = 0; i < count; i++) {
                    oos.writeObject(Utils.getRandomEmployee());
                }
                oos.close();
                gzos.close();
                client.close();
            } catch (IOException e) {
                // TODO: improve, socket handling
            }
        }
    }

}
