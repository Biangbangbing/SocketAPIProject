package net.threadpool;

import net.tools.IOMsg_Str;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * description: 客户端
 * 方法：
 * 发送请求
 * 发送数据
 * 接收数据
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 19:06
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

//    public Client() {
//        new Thread() {
//            @Override
//            public void run() {
//                while(true) {
//                    try {
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
//    }
}
