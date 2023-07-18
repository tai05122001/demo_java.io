package com.core.demo_java_io.demo_io;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(path = "demo/FileOutputStream")
public class FileOutputStreamController {
    private boolean flag;
    @GetMapping
    public String demoFileOutputStream(){
        flag = false;
//        writeFile("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\writeFile.txt");
//        writeFileByte("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\writeFile.txt");
        writeFileBytePosition("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\writeFile.txt");


        return flag?"Ghi file thành công":"Ghi file thất bại";
    }

    private void writeFile(String urlFile) {
        try{
            String data = "Hello World";
            FileOutputStream fileOutputStream =  new FileOutputStream(urlFile);
            // số hiệu 65 là chữ A trong bảng asc ii
            fileOutputStream.write(65);
            fileOutputStream.close();
            flag =true;

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void writeFileByte(String urlFile) {
        try{
            String data = "Hello World";
            FileOutputStream fileOutputStream =  new FileOutputStream(urlFile);
            fileOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            flag =true;

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void writeFileBytePosition(String urlFile) {
        try{
            byte[] data = { 72, 101, 108, 108, 111, 44, 32, 119, 111, 114, 108, 100, 33 };
            FileOutputStream fileOutputStream =  new FileOutputStream(urlFile , true);
            fileOutputStream.write(data,0 , data.length);
            fileOutputStream.close();


            File file = new File(urlFile);
            if (file.exists()) {
                flag =true;
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }


}
