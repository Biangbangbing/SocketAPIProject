package net.udp.test;

import java.io.IOException;
import java.net.*;

/**
 * description:
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 15:58
 * @version: 1.0
 */
public class Server {
    public SocketAddress getLocalAddr() {
        SocketAddress localAddr = new InetSocketAddress("localhost",9997);
        System.out.println("服务器获取本地地址。");

        return localAddr;
    }
    public DatagramSocket getDateSocket(SocketAddress sa) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(sa);
        return datagramSocket;
    }
    public void sendMessage(String mess, DatagramSocket sendSocket, SocketAddress destAddr) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(mess.getBytes(),mess.getBytes().length, destAddr);
        sendSocket.send(datagramPacket);
        System.out.println("服务器端：消息已发送。");
    }
    public void receiveMessage(DatagramSocket receiveSocket) throws IOException {
        String msg = "";
        while (true) {
            byte[] buffer = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            System.out.println("准备接收消息");
            receiveSocket.receive(datagramPacket);
            System.out.println("收到消息了");
            System.out.println("length1: " + datagramPacket.getData().length);
            String mess = new String(datagramPacket.getData()).trim();
            System.out.println("length2: " + mess.getBytes().length);
            System.out.println("test:" + mess);
            msg += mess;
            if (mess.getBytes().length < 256)
                break;
        }
        System.out.println("服务器端：读取完毕：" + msg);
    }
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        SocketAddress localAdddr = server.getLocalAddr();
        DatagramSocket datagramSocket = server.getDateSocket(localAdddr);
        SocketAddress destAddr = new InetSocketAddress("localhost",9998);
        for (int i = 0; i < 100; i++) {
            server.receiveMessage(datagramSocket);
            server.sendMessage("hello, 我是 服务器" + i, datagramSocket, destAddr);
        }
        datagramSocket.close();
    }
}
