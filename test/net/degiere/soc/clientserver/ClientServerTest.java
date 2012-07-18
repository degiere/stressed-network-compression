package net.degiere.soc.clientserver;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

import net.degiere.proxy.ProxyServerControl;
import net.degiere.soc.clientserver.gzip.ClientGzip;
import net.degiere.soc.clientserver.gzip.ServerGzip;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientServerTest {

    private static Logger logger = Logger.getLogger(ClientServerTest.class);
    private static int count = 1000;

    @Before
    public void setUp() throws Exception {
        ProxyServerControl.startServer();
    }

    @After
    public void tearDown() throws Exception {
        ProxyServerControl.stopServer();
    }

    @Test
    public void testTransferTime() throws UnknownHostException, IOException, ClassNotFoundException {
        logger.info("testing...");

        // run the uncompressed transfer
        Server server = new Server(ProxyServerControl.TARGET_PORT, count);
        long uncompressed = new Client().fetchEmployees(ProxyServerControl.LISTEN_PORT, count);
        server.stopServer();

        // wait a bit to let the other server shutdown
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // run the compressed transfer
        ServerGzip serverGzip = new ServerGzip(ProxyServerControl.TARGET_PORT, count);
        long compressed = new ClientGzip().fetchEmployeesGzip(ProxyServerControl.LISTEN_PORT, count);
        serverGzip.stopServer();

        // compare results
        System.out.println("Uncompressed transfer took: " + uncompressed + " ms. Compressed took: " + compressed + " ms.");
        long diff = uncompressed - compressed;
        Double faster = (double) diff / (double) uncompressed;
        System.out.println("Difference: " + diff + "ms.");
        DecimalFormat df = new DecimalFormat("#");
        System.out.println("Compressed is faster by: %" + df.format(faster * 100) + ".");
        Assert.assertTrue("compressed not faster", uncompressed > compressed);
    }

}
