package net.degiere.soc.clientserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import net.degiere.soc.model.Employee;

import org.apache.log4j.Logger;

public class Client {

    private static Logger logger = Logger.getLogger(Client.class);

    /**
     * Fetch numerous serialized employee objects via the uncompressed stream
     * 
     * @return duration
     * @throws IOException
     * @throws UnknownHostException
     * @throws ClassNotFoundException
     */
    public long fetchEmployees(int port, int count) throws UnknownHostException, IOException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        Socket socket = new Socket("localhost", port);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        logger.info("Start: " + start);
        for (int i = 0; i < count; i++) {
            Employee employee = null;
            employee = (Employee) ois.readObject();
            if (logger.isDebugEnabled()) {
                logger.debug("Fetched employee: " + employee);
            }
        }
        long end = System.currentTimeMillis();
        logger.info("End: " + end);
        long duration = end - start;
        logger.info("Took: " + duration);
        ois.close();
        socket.close();
        return duration;
    }

}
