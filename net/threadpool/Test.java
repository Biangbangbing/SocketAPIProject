package net.threadpool;

import net.tcp.test.Client;

import java.io.IOException;
import java.net.Socket;

/**
 * description: 测试函数 ： 创建线程池管理器，循环创建客户端发送请求，创建任务线程接收请求【监听请求】
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 18:59
 * @version: 1.0
 */
public class Test {
    public static void main(String[] args) throws IOException {
        ThreadPoolManage threadPoolManage = new ThreadPoolManage(5);
        for (int i = 0; i < 100; i++) {
//            Client client = new Client();
            threadPoolManage.addTask(new TaskRunnable(threadPoolManage.getServerSocket(), i));
//            Socket socket = client.connect();
//            client.sendMessage("hello, 我是 客户端" + i, socket);
//            client.receiveMess(socket);
//            socket.close();
        }
        for (int i = 0; i < 100; i++) {
            Client client = new Client();
//            threadPoolManage.addTask(new TaskRunnable(threadPoolManage.getServerSocket(), i));
            Socket socket = client.connect();
            client.sendMessage("hello, 我是 客户端" + i, socket);
            client.receiveMess(socket);
            socket.close();
        }
    }
}
