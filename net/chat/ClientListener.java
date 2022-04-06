package net.chat;

import net.tools.IOMsg_File;
import net.tools.IOMsg_INT;
import net.tools.IOMsg_Str;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description: ClientUI 界面的监听器
 * 消息协议： 消息类型 (int)  日期 (String)   消息体
 *
 * 属性：
 * 1.ClientUI clientUI;
 * 2.JFrame jFrame;
 * 3.JTextArea inputArea;
 * 4.JTextArea showArea;
 * 5.IOMsg_File ioMsg_file = new IOMsg_File();
 * 6.IOMsg_INT ioMsg_int = new IOMsg_INT();
 * 7.IOMsg_Str ioMsg_str = new IOMsg_Str();
 *
 * 方法：
 * 1.属性对应的set方法
 * 2.void actionPerformed(ActionEvent e)
 *   监听到 button被单击的时候的需要执行的操作、
 *   1.获取按键的名称
 *   2.发送：获取到当前输入框的文字，发送数据
 *   3.文件：弹出文件选择页面，获取当前选择的文件数据，将文件发送给对方
 *   4.清空聊天框：将输入框的内容置为空
 *   5.清空聊天记录:：将聊天及录区置为空
 *
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 19:45
 * @version: 1.0
 */
public class ClientListener implements ActionListener, ListSelectionListener, MouseListener {
    ClientUI clientUI;
    JFrame jFrame;
    JTextArea inputArea;
    JTextArea showArea;
    IOMsg_File ioMsg_file = new IOMsg_File();
    IOMsg_INT ioMsg_int = new IOMsg_INT();
    IOMsg_Str ioMsg_str = new IOMsg_Str();

    public void setClientUI(ClientUI clientUI) {
        this.clientUI = clientUI;
    }

    public void setjFrame(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    public void setInputArea(JTextArea inputArea) {
        this.inputArea = inputArea;
    }

    public void setShowArea(JTextArea showArea) {
        this.showArea = showArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String BtnStr = e.getActionCommand();
        if (BtnStr.equals("发送")) {
            String msg = inputArea.getText().toString();
            System.out.println("点击发送信息" + msg);
            Date date = new Date() ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateStr = dateFormat.format(date);
            //发送消息
            Socket socket = clientUI.getSocket();
            try {
                OutputStream outputStream = socket.getOutputStream();
                ioMsg_int.sendInt(outputStream,1);
                ioMsg_str.SendStr(outputStream,dateStr);
                ioMsg_str.SendStr(outputStream,msg);
                outputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            showArea.append("\n    " + dateStr + "\n    " + "客户端: " + msg + '\n');
            inputArea.setText("");
        }
        else if(BtnStr.equals("文件")){
            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.addChoosableFileFilter(new ImageFilter());
            fileChooser.setAcceptAllFileFilterUsed(false);
            int option = fileChooser.showOpenDialog(jFrame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                System.out.println("成功获得文件");
                Socket socket = clientUI.getSocket();
                Date date = new Date() ;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = dateFormat.format(date);
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    ioMsg_int.sendInt(outputStream,2);
                    ioMsg_str.SendStr(outputStream, dateStr);
                    ioMsg_file.SendFile(outputStream, file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                showArea.append("\n    " + dateStr + "\n    " + "客户端 : 发送文件" + file.getName() + '\n');
            }

        }
        else if(BtnStr.equals("清空输入框")){ //清空输入区
            inputArea.setText("");
        }
        else if(BtnStr.equals("清空聊天记录")){ //清空显示区
            showArea.setText("");
            jFrame.repaint();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
