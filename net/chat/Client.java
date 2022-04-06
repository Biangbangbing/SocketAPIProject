package net.chat;

import net.tools.IOMsg_Str;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * description: tcpSocket通信的客户端  —— 产生client对象
 * 方法:
 * 1.connect
 *   创建 socket对象, 向指定的ip，port的服务器发起请求
 *   返回 socket
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 14:53
 * @version: 1.0
 */
public class Client {
    public Socket connect() throws IOException {
        Socket socket = new Socket("localhost",9999);   //发送请求
        System.out.println("客户端已上线，发起请求……建立连接成功！");
        return socket;
    }
    public void sendMessage(String mess, Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        IOMsg_Str ioMsg = new IOMsg_Str();
        ioMsg.SendStr(outputStream,mess);
        System.out.println("客户端：已发送消息。");
        outputStream.flush();
//        outputStream.close();
    }
    public void receiveMess(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        IOMsg_Str ioMsg_str = new IOMsg_Str();
        String mess =  ioMsg_str.receiveStr(inputStream);
        System.out.println("客户端：收到消息：" + mess);
    }
//    public static void main (String[] args) throws IOException {
//        Client client = new Client();
//        Socket socket = null;
//        try {
//            socket = client.connect();
//            for (int i = 0 ;i < 100 ; i++) {
//                client.sendMessage("hello, 我是 客户" + i, socket);
//                client.receiveMess(socket);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            socket.close();
//        }
//    }
}
