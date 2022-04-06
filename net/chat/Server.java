package net.chat;

import net.tools.IOMsg_Str;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * description: 测试tcp socket通信的服务器端
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 14:50
 * @version: 1.0
 */
public class Server {
    public Socket connect() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务器已启动，监听连接……");
        Socket socket = serverSocket.accept();
        return socket;
    }
    public void sendMessage(String mess, Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        IOMsg_Str ioMsg = new IOMsg_Str();
        ioMsg.SendStr(outputStream,mess);
        System.out.println("服务器端：已发送消息。");
        outputStream.flush();
//        outputStream.close();
    }
    public void receiveMess(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        IOMsg_Str ioMsg_str = new IOMsg_Str();
        String mess =  ioMsg_str.receiveStr(inputStream);
        System.out.println("服务器端：收到消息：" + mess);
    }
//    public static void main (String[] args) throws IOException {
//        Server server = new Server();
//        Socket socket = null;
//        try {
//            socket = server.connect();
//            for (int i = 0; i < 100; i++) {
//                server.receiveMess(socket);
//                server.sendMessage("hello, 我是 服务器端" + i, socket);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            socket.close();
//        }
//    }
}
