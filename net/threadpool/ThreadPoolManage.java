package net.threadpool;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * description: 线程池管理器
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 17:52
 * @version: 1.0
 */
public class ThreadPoolManage {
    int number ;
    ServerSocket serverSocket = new ServerSocket(9999);
    ArrayList<ServerThread> workersList = new ArrayList<>();
    ArrayList<TaskRunnable> tasksList = new ArrayList<TaskRunnable>();

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ThreadPoolManage(int number) throws IOException {
        this.number = number;
        // 初始化线程池，创建工作线程
        for (int i = 0; i < number; i++) {
            ServerThread serverThread = new ServerThread(tasksList, serverSocket);
            workersList.add(serverThread);
            serverThread.start();
        }
    }

    public void addTask (TaskRunnable taskRunnable) {
        tasksList.add(taskRunnable);
        synchronized (tasksList) {
            tasksList.notify();
        }
    }
}
