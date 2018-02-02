package io.ymq.example.demo2;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2018-02-02 18:23
 **/


public class SocketUtils {
    private static final Logger LOG = LoggerFactory.getLogger(SocketUtils.class);

    public SocketUtils() {
    }

    public static byte[] readMsg(InputStream is, int readLen) throws Exception {
        return readByte(is, readLen);
    }

    public static byte[] readByte(InputStream is, int readLen) throws Exception {
        int count = readLen;
        byte[] bytesAll = new byte[readLen];
        int tempLen = 0;

        while(true) {
            int cha = count - tempLen;
            if (cha <= 0) {
                return bytesAll;
            }

            byte[] bytes = new byte[cha];
            int len = is.read(bytes);
            if (len <= 0) {
                throw new RuntimeException("Input stream is not the end of normal!!");
            }

            System.arraycopy(bytes, 0, bytesAll, tempLen, len);
            tempLen += len;
        }
    }

    public static void closeInput(InputStream is) {
        if (null != is) {
            try {
                is.close();
                is = null;
            } catch (IOException var2) {
                LOG.error("Close the input stream failed!!", var2);
            }
        }

    }

    public static void closeOutput(OutputStream os) {
        if (null != os) {
            try {
                os.close();
                os = null;
            } catch (IOException var2) {
                LOG.error("Close the output stream failed!!", var2);
            }
        }

    }

    public static void closeSocket(Socket socket) {
        OutputStream os = null;
        InputStream is = null;

        try {
            if (null != socket && !socket.isClosed()) {
                os = socket.getOutputStream();
                is = socket.getInputStream();
            }
        } catch (IOException var12) {
            LOG.error("Get input or output stream failed!!", var12);
        } finally {
            closeInput(is);
            closeOutput(os);
            if (null != socket && !socket.isClosed()) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException var11) {
                    LOG.error("Close socket failed!!", var11);
                }
            }

        }

    }

    public static void closeServerSocket(ServerSocket serverSocket) {
        if (null != serverSocket && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                serverSocket = null;
            } catch (IOException var2) {
                LOG.error("Close server socket failed!!", var2);
            }
        }

    }
}
