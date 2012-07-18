package net.degiere.soc.clientserver.gzip;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.GZIPInputStream;

import net.degiere.soc.model.Employee;

import org.apache.log4j.Logger;

public class ClientGzip {

    private static Logger logger = Logger.getLogger(ClientGzip.class);

    /**
     * Fetch numerous serialized employee objects via the compressed stream
     * 
     * @return duration
     * @throws IOException
     * @throws UnknownHostException
     * @throws ClassNotFoundException
     */
    public long fetchEmployeesGzip(int port, int count) throws UnknownHostException, IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", port);
        GZIPInputStream gzis = new GZIPInputStream(socket.getInputStream());
        ObjectInputStream ois = new ObjectInputStream(gzis);
        long start = System.currentTimeMillis();
        logger.info("Start: " + start);
        for (int i = 0; i < count; i++) {
            Employee employee = null;
            employee = (Employee) ois.readObject();
            logger.debug("Fetched employee gzip: " + employee);
        }
        long end = System.currentTimeMillis();
        logger.info("End: " + end);
        socket.close();
        long duration = end - start;
        logger.info("Took: " + duration);
        ois.close();
        gzis.close();
        return duration;
    }

}
