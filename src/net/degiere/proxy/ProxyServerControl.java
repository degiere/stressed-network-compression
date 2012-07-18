package net.degiere.proxy;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.moneybender.proxy.Proxy;
import com.moneybender.proxy.ProxySettings;
import com.moneybender.proxy.channels.ByteReaderFactory;

public class ProxyServerControl {

    private static Logger logger = Logger.getLogger(ProxyServerControl.class);
    private static Proxy proxy;

    public static final String LATENCY_DEFAULT = "300";
    public static final String PACKET_LOSS_DEFAULT = "100";
    public static final String THROTTLE_DEFAULT = "40";
    public static final String TARGET_HOST = ProxySettings.DEFAULT_TARGET_HOST;
    public static final int LISTEN_PORT = ProxySettings.DEFAULT_LISTEN_PORT;
    public static final int TARGET_PORT = ProxySettings.DEFAULT_TARGET_PORT;

    public static void startServer() {
        logger.info("starting proxy");
        System.setProperty(ByteReaderFactory.LATENCY_PROPERTY_NAME, LATENCY_DEFAULT);
        System.setProperty(ByteReaderFactory.PACKET_LOSS_PROPERTY_NAME, PACKET_LOSS_DEFAULT);
        System.setProperty(ByteReaderFactory.THROTTLE_PROPERTY_NAME, THROTTLE_DEFAULT);
        proxy = new Proxy();
        ProxySettings proxySettings = new ProxySettings();
        try {
            proxy.start(proxySettings);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("couldn't start proxy server", e);
        }
    }

    public static void stopServer() {
        logger.info("stopping proxy");
        proxy.stop();
    }

}
