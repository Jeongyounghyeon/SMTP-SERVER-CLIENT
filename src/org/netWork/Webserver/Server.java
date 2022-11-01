package org.netWork.Webserver;


import com.sun.net.httpserver.HttpServer;
import netscape.javascript.JSObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


public class Server {
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.boot();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //포트 설정
    private static int port = 9000;
    // serverSocket
    private ServerSocket serverSocket;


    private static final byte LF = '\n';
    private static final byte RF = '\r';

    // 서버 가동 method
    private void boot() throws IOException {
        // port 번호로 server socket열기
        serverSocket = new ServerSocket(port);
        System.out.println("Listening for Connection on " + port);

        //요청이 들어올때까지 대기.
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();

            PrintWriter out = new PrintWriter(socket.getOutputStream());

            BufferedReader in = new BufferedReader(new InputStreamReader(is));


            Stream<String> lines = in.lines();

//            lines.forEach(line->System.out.println(line));

//            while (in.ready()){
//                if (in.readLine()==null)break;
//                String line = in.readLine();
//                if(!in.ready()){
//                    break;
//                }
//                int length = line.length();
//                System.out.println(line);
////                System.out.println(length);
////                System.out.println(line.contains("-T"));
//
//            }

            String line;
            //getHeader
            while ((line= in.readLine())!=null ){
                System.out.println(line);
                if(!in.ready()){
                    break;
                }
            }

            String res = "HTTP/1.1 200 OK\r\n" + "Access-Control-Allow-Origin: *\r\n" + "\r\n" + "gd";
//            out.write(res);

            socket.getOutputStream().write(res.getBytes("UTF-8"));
            socket.close();


        }
    }
}
