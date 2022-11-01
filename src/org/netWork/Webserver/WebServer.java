package org.netWork.Webserver;


import netscape.javascript.JSObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebServer {
    public static void main(String[] args) {
        try {
            WebServer server = new WebServer();
            server.boot();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    //포트 설정
    private static int port = 8000;
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
            // 요청 어떤 메쏘드인지 저장할 변수 선언.
            String method = null;
            // 요철 URL 저장할 변수 선언
            String reqUrl = null;
            //HTTP 버전 받을 URL 선언
            String httpVersion = null;

            String Json = "";

            byte[] thisBytes;

            //Body 할당할 변수 선언
            List<Byte> bodyByteList = null;
            boolean bodyFlag = false;

            //헤더정보 넣기
            Map<String, String> headerMap = new HashMap<String, String>();

            int contentLength = 0;


            Socket clientSocket = serverSocket.accept();
            InputStream is = clientSocket.getInputStream();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(is));

            String line;
            // 1번라인부터 ~ 헤더까지.

            while ((line = in.readLine()) != null && line.length()!=0) {


//                System.out.println(line);

                int firstBlank = line.indexOf(" ");
                int lastBlank = line.lastIndexOf(" ");
                int colonIndex = line.indexOf(":");
                if (line.length() == 0 && method == "GET") {
                    break;
                }

                if (colonIndex != -1) {

                    String headerName = line.substring(0, colonIndex);
                    String headerValue = line.substring(colonIndex + 2);
                    headerMap.put(headerName, headerValue);
//                    System.out.println(headerMap);

                } else if (firstBlank != -1 && lastBlank != -1) {
                    method = line.substring(0, firstBlank);
                    reqUrl = line.substring(firstBlank + 1, lastBlank);
                    httpVersion = line.substring(lastBlank + 1);

                    System.out.println(method);
                    System.out.println(reqUrl);
                    System.out.println(httpVersion);
                }

            }
            System.out.println(contentLength);


            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());

            if (method != "GET") {
                String contentLengthValue = headerMap.get("Content-Length");
                if (contentLengthValue != null) {
                    contentLength = Integer.parseInt(contentLengthValue.trim());
                }
//                System.out.println(contentLength);
//                System.out.println(in.readLine());
                System.out.println(headerMap.get("Content-Type"));

                if (contentLength > 0) {
                    System.out.println("contentLength:"+contentLength);

                    if (headerMap.get("Content-Type").contains("multipart/form-data")) {

                        while ((line = in.readLine()) != null && line.length() != 0) {
                            System.out.println(line);
//                        int tabIndex = line.indexOf(" ");
//                        System.out.println(tabIndex);
//
//                        Json+=line.trim();
//                        if(line == "}"){
//                            break;
//                        }
//                    }
                        }
                    } else if (headerMap.get("Content-Type").equals("application/json")) {
                        String K;
                        while ((K = in.toString()) != null && line.length() != 0 )  {

                            Json += line;
                            System.out.println(Json);


                        }

                    }
//                    while ((line= in.readLine())!=null&&line.length()!=0){
//                        System.out.println(line);
//                        int tabIndex = line.indexOf(" ");
//                        System.out.println(tabIndex);
//
//                        Json+=line.trim();
//                        if(line == "}"){
//                            break;
//                        }
//                    }


                }
//
            }
//

            String res = "HTTP/1.1 200 OK\r\n\r\n" + method;
//            out.write("res".getBytes("UTF-8"));

            clientSocket.getOutputStream().write(res.getBytes("UTF-8"));
            clientSocket.close();




        }

    }


}
