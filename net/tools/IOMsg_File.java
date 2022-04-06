package net.tools;

import java.io.*;

/**
 * description:
 *
 * @author: biangbangbing
 * @Date: 2022/4/5 21:16
 * @version: 1.0
 */
public class IOMsg_File {
    IOMsg_INT ioMsg_int = new IOMsg_INT();
    IOMsg_Str ioMsg_str = new IOMsg_Str();
    public void SendFile(OutputStream outputStream, File file) throws IOException {
        byte[] ByteList_Str = new byte[1024];
        FileInputStream fileInputStream = new FileInputStream(file);
//        FileOutputStream fileOutputStream = new FileOutputStream("process1");
        int len = 0;
        ioMsg_str.SendStr(outputStream, file.getName());
        while (true) {
            len = fileInputStream.read(ByteList_Str);
            System.out.println("发送文件片长度：" + len);
            if (len == -1) {
                ioMsg_int.sendInt(outputStream,len);
                break;
            }
            ioMsg_int.sendInt(outputStream,len);
//            fileOutputStream.write(ByteList_Str,0,len);
            outputStream.write(ByteList_Str,0,len); //如果不注明实际长度，会把1024整个都发过去，造成读取到空的数据
            System.out.println("读出的数据：" + ByteList_Str);
        }
        System.out.println("文件发送完毕");
    }

    public File receiveFile(InputStream inputStream) throws IOException {
        String fileName = ioMsg_str.receiveStr(inputStream);
        File file = new File(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        FileOutputStream fileOutputStream2 = new FileOutputStream("process2");
        int length = 0;
        while (true) {
            length = ioMsg_int.receiveInt(inputStream);
            if (length == -1) {
                System.out.println("读到的segment长度" + length);
                break;
            }
            System.out.println("读到的segment长度" + length);
            byte[] ByteList_Str= new byte[length];
            inputStream.read(ByteList_Str);
//            fileOutputStream.write(ByteList_Str);
            fileOutputStream.write(ByteList_Str, 0, length);
            System.out.println("读入的数据：" + ByteList_Str);
        }
        System.out.println("端读取文件为：" + fileName);
        return file;
    }
}
