package netWork.SMTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerHandler extends Thread {
    Socket socket = null;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = datain.readLine()) != null) {
                System.out.println(line);
                socket.close();
            }
        } catch (Exception e) {
            try {
                socket.close();
            } catch (Exception err) {
                err.printStackTrace();
            }
        }


    }
}
