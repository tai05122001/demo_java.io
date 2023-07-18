package com.core.demo_java_io.demo_io;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Iterator;

@RestController
@RequestMapping(path = "demo/BufferedInputStream")
public class BufferedInputStreamController {
    private String view;
    private String content;
    String contentFile;

    String urlFileTxt;
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
    private String demoCopyList() {
        showList();
        return view;

    }

    public String getUrlFileTxt() {
        return urlFileTxt;
    }

    public void setUrlFileTxt(String urlFileTxt) {
        this.urlFileTxt = urlFileTxt;
    }

    private void readFileTxT(String urlFile) {
        contentFile = "";
        setUrlFileTxt(urlFile);
        try {
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(urlFileTxt));
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

    private void readFileCSV(String urlFile) {
        contentFile = "<table style='border-collapse: collapse;'>";
        int i = 0;
        setUrlFileTxt(urlFile);
        try {
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(urlFileTxt));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (i == 0) {

                    String[] titles = line.split(",");
                    setContentFile(contentFile.concat(
                            "<tr>"
                    ));
                    for (int k = 0; k < titles.length; k++) {
                        setContentFile(contentFile.concat(
                                "<th>" + titles[k] + "</th>"
                        ));
                    }
                    setContentFile(contentFile.concat(
                            "</tr>"
                    ));

                } else {
                    String[] titles = line.split(",");
                    setContentFile(contentFile.concat(
                            "<tr>"
                    ));
                    for (int k = 0; k < titles.length; k++) {
                        setContentFile(contentFile.concat(
                                "<td>" + titles[k] + "</td>"
                        ));
                    }
                    setContentFile(contentFile.concat(
                            "</tr>"
                    ));

                }
                i++;

            }
            setContentFile(contentFile.concat("</table>"));
            //            System.out.println(contentFile);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // hàm đọc file XLSX
    private void readFileXLSX(String urlFile) {
        contentFile = "<table style='border-collapse: collapse;'>";
        int i = 0;
        setUrlFileTxt(urlFile);
        try {
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(urlFileTxt));

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            Iterator < Row > iterator = xssfSheet.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                Iterator < Cell > cellIterator = row.iterator();
                setContentFile(contentFile.concat(
                        "<tr>"
                ));
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            if (i == 0) {
                                setContentFile(contentFile.concat(
                                        "<th>" + cell.getStringCellValue() + "</th>"
                                ));
                            } else {
                                setContentFile(contentFile.concat(
                                        "<td>" + cell.getStringCellValue() + "</td>"
                                ));
                            }
                            break;
                        case NUMERIC:
                            if (i == 0) {
                                setContentFile(contentFile.concat(
                                        "<th>" + cell.getNumericCellValue() + "</th>"
                                ));
                            } else {
                                setContentFile(contentFile.concat(
                                        "<td>" + cell.getNumericCellValue() + "</td>"
                                ));
                            }
                            break;

                        case BOOLEAN:
                            if (i == 0) {
                                setContentFile(contentFile.concat(
                                        "<th>" + cell.getBooleanCellValue() + "</th>"
                                ));
                            } else {
                                setContentFile(contentFile.concat(
                                        "<td>" + cell.getBooleanCellValue() + "</td>"
                                ));
                            }
                            break;

                        default:

                    }

                }
                setContentFile(contentFile.concat(
                        "</tr>"
                ));
                i++;
            }
            setContentFile(contentFile.concat("</table>"));
            System.out.println(contentFile);
            fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    private void readFileXLSX_HavePDF(String url ){
    //        try {
    //
    //
    //        }catch (IOException ex){
    //
    //        }
    //    }

    //hàm dùng để thiết lập nội dung
    private void setContentWeb() {
        content = "<div>";
        setContent(content.concat(

                "<h1 style='color:red;text-align:center'>Demo cho thư viện BufferedInputStream </h1>" +
                        "<a href='http://localhost:8080'>Home</a><br/>"
        ));
        readFileTxT("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\example.txt");
        setContent(content.concat(

                "<br/> <strong> Nội dung của file example.txt</strong><br/>" +
                        "<span>" + contentFile + "</span>"
        ));

        readFileCSV("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\test_csv.csv");
        setContent(content.concat(

                "<br/> <strong> Nội dung của file test_csv.csv</strong><br/>" +
                        contentFile
        ));

        readFileXLSX("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\test_EXCEL.xlsx");
        setContent(content.concat(

                "<br/> <strong> Nội dung của file test_EXCEL</strong><br/>" +
                        contentFile
        ));
        setContent(content.concat("</div>"));
    }

    // hàm sử dụng để hiển thị thông tin lên web
    private void showList() {
        view = "<body style='overflow:scroll; width:800px;margin:auto;padding:16px'>";
        setContentWeb();
        setView(view.concat(content));
        setView(view.concat("</body>"));

    }


}