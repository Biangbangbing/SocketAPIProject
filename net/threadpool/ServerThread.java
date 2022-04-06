package net.threadpool;

import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * description: 工作线程
 * 不断从任务队列中取出任务然后执行
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 18:00
 * @version: 1.0
 */
public class ServerThread extends Thread{
    ArrayList<TaskRunnable> tasks;
    ServerSocket serverSocket;
    public ServerThread(ArrayList<TaskRunnable> tasks, ServerSocket serverSocket) {
        this.tasks = tasks;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            Runnable task = null;
            synchronized (tasks) {
                if (tasks.isEmpty()) {
                    System.out.println("任务池为空，worker " + currentThread().getName() + " 正在等待任务来临");
                    try {
                        tasks.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    task = tasks.remove(0);
                }
            }
            if (task != null)
                task.run();
        }
    }
}
