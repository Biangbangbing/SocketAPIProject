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
public class Client {
    public SocketAddress getLocalAddr() {
        SocketAddress localAddr = new InetSocketAddress("localhost",9998);
        System.out.println("客户端：绑定本地地址");
        return localAddr;
    }
    public DatagramSocket getDateSocket(SocketAddress sa) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(sa);
        return datagramSocket;
    }
    public void sendMessage(String mess, DatagramSocket sendSocket, SocketAddress destAddr) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(mess.getBytes(),mess.getBytes().length, destAddr);
        sendSocket.send(datagramPacket);
        System.out.println("客户端：消息已发送。");
    }
    public void receiveMessage(DatagramSocket receiveSocket) throws IOException {
        String msg = "";
        while (true) {
            byte[] buffer = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            System.out.println("准备接收消息");
            receiveSocket.receive(datagramPacket);
            System.out.println("length1: " + datagramPacket.getData().length);
            String mess = new String(datagramPacket.getData()).trim();
            System.out.println("length2: " + mess.getBytes().length);
            System.out.println("test:" + mess);
            msg += mess;
            if (mess.getBytes().length < 256)
                break;
        }
        System.out.println("客户端：读取完毕：" + msg);
    }
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        SocketAddress localAddr = client.getLocalAddr();
        DatagramSocket datagramSocket = client.getDateSocket(localAddr);
        SocketAddress destAddr = new InetSocketAddress("localhost",9997);
        for (int i = 0; i < 100; i++) {
            client.sendMessage("hello, 我是 客户" + i, datagramSocket, destAddr);
            client.receiveMessage(datagramSocket);
        }
        datagramSocket.close();
    }
}
