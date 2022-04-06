package net.chat;

import net.tools.IOMsg_File;
import net.tools.IOMsg_INT;
import net.tools.IOMsg_Str;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * description: 监听消息的线程：一直 while 循环接收来自对方的消息
 * 属性：
 * 1.Socket socket;
 * 2.JTextArea showArea;
 * 3.IOMsg_File ioMsg_file = new IOMsg_File();
 * 4.IOMsg_INT ioMsg_int = new IOMsg_INT();
 * 5.IOMsg_Str ioMsg_str = new IOMsg_Str();
 *
 * 方法：
 * 1.属性的 set方法
 * 2.线程执行方法：
 *   1.读取收到的消息类型
 *   2.读取发送方发数据的日期str
 *   3.switch case判断消息类型
 *     1.文本类型：将文本读出显示在聊天区
 *     2.文件类型：将数据读出，显示收到文件地记录，并将文件下载到本地。
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 19:48
 * @version: 1.0
 */
public class ServerReceiveMsg extends Thread{
    Socket socket;
    JTextArea showArea;
    IOMsg_File ioMsg_file = new IOMsg_File();
    IOMsg_INT ioMsg_int = new IOMsg_INT();
    IOMsg_Str ioMsg_str = new IOMsg_Str();

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setShowArea(JTextArea showArea) {
        this.showArea = showArea;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("服务器端接收到消息");
            try {
                InputStream inputStream = socket.getInputStream();
                int messageType = ioMsg_int.receiveInt(inputStream);
                String date = ioMsg_str.receiveStr(inputStream);
                switch (messageType) {
                    case 1: {
                        String msg = ioMsg_str.receiveStr(inputStream);
                        System.out.println("读取到文字消息：" + msg);
                        showArea.append("\n    " + date + '\n' + "客户端 : " + msg + '\n');
                        break;
                    }
                    case 2: {
                        File file = ioMsg_file.receiveFile(inputStream);
                        System.out.println("收到文件消息：" + file.getName());
                        showArea.append("\n    " + date + '\n' + "客户端 : 发送文件" + file.getName() + "。 已下载到本地。\n");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
