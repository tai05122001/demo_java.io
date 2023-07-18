package com.core.demo_java_io.demo_io;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
@RequestMapping(path = "demo/FileInputStream")
public class FileInputStreamController {
    private String view;
    private String content;

    String contentFile;
    String urlFileTxt;

    public String getUrlFileTxt() {
        return urlFileTxt;
    }

    public void setUrlFileTxt(String urlFileTxt) {
        this.urlFileTxt = urlFileTxt;
    }

    public String getContentFile() {
        return contentFile;
    }

    public void setContentFile(String contentFile) {
        this.contentFile = contentFile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    @GetMapping
    private String demoCopyList(){
//        readFileVN();
        initList();
        showList();
        return view;

    }
    // Hàm khởi tạo giá trị của list
    private void initList() {
    }

    private void readFileVN(String urlFileTxtt) {
        contentFile = "";
        urlFileTxt = urlFileTxtt;
        try {
            FileInputStream fileInputStream = new FileInputStream(urlFileTxt);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                setContentFile(contentFile.concat(line + "<br/>"));

            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFileByte(String urlFileTxtt) {
        contentFile = "";
        urlFileTxt = urlFileTxtt;
        String content_byte = "";

        try {
            byte[] bytes = new byte[50];
            FileInputStream fileInputStream = new FileInputStream(urlFileTxt);
            int bytesRead= fileInputStream.read(bytes);
            // đọc dữ liệu 10 bytes đầu
            content_byte = new String(bytes, StandardCharsets.UTF_8);
//            System.out.println(content);

            fileInputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return content_byte.isEmpty()?"":content_byte;
    }

    private String readFileBytePosition(String urlFileTxtt) {
        contentFile = "";
        urlFileTxt = urlFileTxtt;
        String content_byte = "";
        int i = 0;

        try {
            byte[] bytes = new byte[50];
            bytes[0]= -60;
            bytes[1]= -69;
            FileInputStream fileInputStream = new FileInputStream(urlFileTxt);
            int bytesRead= fileInputStream.read(bytes ,2 , 10);
            // đọc dữ liệu 10 bytes đầu
            content_byte = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(bytesRead);
            System.out.println(Arrays.toString(bytes));
            fileInputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return content_byte.isEmpty()?"":content_byte;
    }
    //hàm dùng để thiết lập nội dung
    private void setContentWeb(){
        content = "<div>";
        setContent(content.concat(

                "<h1 style='color:red;text-align:center'>Demo cho thư viện FileOutputStream </h1>"+
                        "<a href='http://localhost:8080'>Home</a><br/>"
        ));

        readFileVN("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\example.txt");
        setContent(content.concat(

                "<br/> <strong> Nội dung của file example.txt</strong><br/>"
                        +"<span>"+contentFile+"</span>"
        ));



        setContent(content.concat(

                "<br/> <strong> Nội dung của 10 byte đầu trong file example.txt</strong><br/>"
                        +"<span>"+readFileByte(urlFileTxt)+"</span>"
        ));

        setContent(content.concat(

                "<br/> <strong> Nội dung bắt đầu từ offset 2 và dài 10 byte của 50 byte đầu trong file example.txt</strong><br/>"
                        +"<span>"+readFileBytePosition(urlFileTxt)+"</span>"
        ));

        setContent(content.concat("</div>"));
    }


    // hàm sử dụng để hiển thị thông tin lên web
    private void showList(){
        view = "<body style='overflow:scroll; width:800px;margin:auto;padding:16px'>";
        setContentWeb();
        setView(view.concat(content));
        setView(view.concat("</body>"));

    }


}
