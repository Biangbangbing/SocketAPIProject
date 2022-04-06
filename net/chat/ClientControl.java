package net.chat;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

/**
 * description: 启动客户端的调度器
 * 1.创建客户端
 * 2.启动客户端，发起请求
 * 3.创建客户端 UI对象
 * 4.创建客户端接收消息线程对象
 * 5.接收消息线程绑定创建 client 的 socket
 * 6.客户端 UI绑定创建 client 的 socket
 * 7.客户端 UI绑定接收消息线程对象
 * 8.绘制客户端 UI界面
 * 9.接收消息的线程启动
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 19:48
 * @version: 1.0
 */
public class ClientControl {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        Socket socket = client.connect();
        ClientUI clientUI = new ClientUI();
        ClientReceiveMsg clientReceiveMsg = new ClientReceiveMsg();

        clientReceiveMsg.setSocket(socket);

        clientUI.setSocket(socket);
        clientUI.setClientReceiveMsg(clientReceiveMsg);
        JFrame jFrame = clientUI.initUI();
        clientReceiveMsg.run();
    }
}
