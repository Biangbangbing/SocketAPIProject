package net.chat;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

/**
 * description: 启动服务器端的调度器
 * 1.创建服务器端
 * 2.启动服务器端，发起请求
 * 3.创建服务器端 UI对象
 * 4.创建服务器端接收消息线程对象
 * 5.接收消息线程绑定创建 client 的 socket
 * 6.服务器端 UI绑定创建 client 的 socket
 * 7.服务器端 UI绑定接收消息线程对象
 * 8.绘制服务器端 UI界面
 * 9.接收消息的线程启动
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 19:49
 * @version: 1.0
 */
public class ServerControl {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        Socket socket = server.connect();
        ServerUI serverUI = new ServerUI();
        ServerReceiveMsg serverReceiveMsg = new ServerReceiveMsg();

        serverReceiveMsg.setSocket(socket);

        serverUI.setSocket(socket);
        serverUI.setServerReceiveMsg(serverReceiveMsg);
        JFrame jFrame = serverUI.initUI();
        serverReceiveMsg.run();
    }
}
