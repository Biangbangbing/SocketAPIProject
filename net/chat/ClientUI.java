package net.chat;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

/**
 * description: 客户端 UI界面
 * 属性
 * 1.Socket socket;
 * 2.ClientListener clientListener = new ClientListener();
 * 3.ClientReceiveMsg clientReceiveMsg;
 *
 * 方法
 * 1.属性的 set，get方法
 * 2.JFrame initUI() 绘制 UI界面并返回界面对象
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 19:44
 * @version: 1.0
 */
public class ClientUI implements Config{
    Socket socket;
    ClientListener clientListener = new ClientListener();
    ClientReceiveMsg clientReceiveMsg;

    public void setClientReceiveMsg(ClientReceiveMsg clientReceiveMsg) {
        this.clientReceiveMsg = clientReceiveMsg;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public JFrame initUI() {
        JFrame jFrame = new JFrame("客户端");
        jFrame.setLocation(100,100);
        jFrame.setSize(UIWidth,UIHeight);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        // 聊天滚动框面板: 聊天记录
        JScrollPane dialogAbove = new JScrollPane();
        dialogAbove.setBackground(Color.GRAY);
        Dimension dimensionAbove = new Dimension(InnerWidth,diaHeightA);
        dialogAbove.setPreferredSize(dimensionAbove);
        dialogAbove.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dialogAbove.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // 聊天记录文字区域:
        JTextArea showArea = new JTextArea("dialog",20,28);
        showArea.setEditable(false);

        showArea.setLineWrap(true);
        showArea.setBackground(Color.white);
        showArea.setSize(InnerWidth,diaHeightA);

        // 将聊天记录组件加到面板 将面板加入到主面板的上部
        dialogAbove.setViewportView(showArea);
        jFrame.add(dialogAbove,BorderLayout.NORTH);
        clientListener.setShowArea(showArea);
        clientReceiveMsg.setShowArea(showArea);
        
        // 工具栏
        JPanel toolbar = new JPanel();
        toolbar.setBackground(Color.lightGray);
        toolbar.setSize(UIWidth,18);

        // 工具栏按键
        JButton ConfirmSendBtn = new JButton("发送");
        ConfirmSendBtn.setBackground(Color.white);
        ConfirmSendBtn.setSize(btnWidth,btnHeight);

        JButton FileBtn = new JButton("文件");
        FileBtn.setBackground(Color.white);
        FileBtn.setSize(btnWidth,btnHeight);

        JButton CleanInputBtn = new JButton("清空输入框");
        CleanInputBtn.setBackground(Color.white);
        CleanInputBtn.setSize(btnWidth,btnHeight);

        JButton CleanHistoryBtn = new JButton("清空聊天记录");
        CleanHistoryBtn.setBackground(Color.white);
        CleanHistoryBtn.setSize(btnWidth,btnHeight);

        toolbar.add(ConfirmSendBtn);
        toolbar.add(FileBtn);
        toolbar.add(CleanInputBtn);
        toolbar.add(CleanHistoryBtn);
        
        // 聊天输入框滚动栏面板
        JScrollPane dialogBottom = new JScrollPane();
        dialogBottom.setBackground(Color.WHITE);
        Dimension dimBottom = new Dimension(InnerWidth,diaHeightB);
        dialogBottom.setPreferredSize(dimBottom);
        dialogBottom.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dialogBottom.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // 输入框文本框
        JTextArea inputArea = new JTextArea("input",10,28);
        inputArea.setEditable(true);
        inputArea.setLineWrap(true);
        inputArea.setSize(InnerWidth,diaHeightB);
        inputArea.setBackground(Color.white);

        // 将输入框加载到面板 将面板加载到主面板
        dialogBottom.setViewportView(inputArea);
        jFrame.add(dialogBottom,BorderLayout.SOUTH);
        jFrame.add(toolbar,BorderLayout.CENTER);
        clientListener.setInputArea(inputArea);

        // 可视化之后再加监听器
        jFrame.setVisible(true);

        // 加载监听器
        clientListener.setClientUI(this);
        clientListener.setjFrame(jFrame);

        ConfirmSendBtn.addActionListener(clientListener);
        FileBtn.addActionListener(clientListener);
        CleanInputBtn.addActionListener(clientListener);
        CleanHistoryBtn.addActionListener(clientListener);

        jFrame.addMouseListener(clientListener);

        return jFrame;

    }
}
