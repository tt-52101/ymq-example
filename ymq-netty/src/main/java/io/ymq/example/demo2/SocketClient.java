package io.ymq.example.demo2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2018-02-02 18:22
 **/

public class SocketClient {
    private String Address;
    private int Port;
    private Boolean readline;

    public SocketClient(String address, int port, Boolean readline) {
        this.Address = address;
        this.Port = port;
        this.readline = readline;
    }

    public SocketClient(String address, int port) {
        this.Address = address;
        this.Port = port;
        this.readline = true;
    }

    public String SendData(String protocol) throws Exception {
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            StringBuilder buf = new StringBuilder();
            socket = new Socket(this.Address, this.Port);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK")), true);
            pw.write(protocol);
            pw.flush();
            socket.shutdownOutput();

            while(true) {
                String strLine;
                if ((strLine = br.readLine()) != null) {
                    buf.append(strLine);
                    if (this.readline.booleanValue()) {
                        continue;
                    }
                }

                String var7 = buf.toString();
                return var7;
            }
        } catch (Exception var16) {
            var16.printStackTrace();
            throw var16;
        } finally {
            if (socket != null) {
                try {
                    if (br != null) {
                        br.close();
                    }

                    if (pw != null) {
                        pw.close();
                    }

                    socket.close();
                } catch (Exception var15) {
                    var15.printStackTrace();
                    throw var15;
                }
            }

        }
    }
}
