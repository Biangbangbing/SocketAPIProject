package net.threadpool;

import net.tools.IOMsg_Str;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * description: 任务线程：
 * 任务内容：
 * 1.监听请求
 * 2.建立连接
 * 3.接收客户端的消息
 * 4.回复客户端
 * 5.结束任务
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 18:30
 * @version: 1.0
 */
public class TaskRunnable implements Runnable{
    ServerSocket serverSocket;
    int num;

    public int getNum() {
        return num;
    }

    public TaskRunnable(ServerSocket serverSocket, int num) {
        this.serverSocket = serverSocket;
        this.num = num;
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

    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            this.receiveMess(socket);
            this.sendMessage("汤猪猪收到！", socket);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
