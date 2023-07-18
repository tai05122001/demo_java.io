package com.core.demo_java_io.demo_io;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;
import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping(path = "demo/BufferedOutputStream")
public class BufferedOutputStreamController {
    
    private String view;
    private String content;
    private  boolean flag;
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
        flag = false;
        showList();
        return view;

    }
    // Hàm khởi tạo giá trị của list
    private void writeCSV(String urlFile){
        try {
            //Mở file CSV
            FileOutputStream fileOutputStream = new FileOutputStream(urlFile, true);
            //Cung cấp bộ nhớ đệm cho FileOutputStream
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            //Tạo một đối tượng PrintWriter để ghi vào file csv
            PrintWriter printWriter = new PrintWriter(bufferedOutputStream);
            //Ghi dữ liệu vào file CSV
            printWriter.println("14,Malet,132");
            //Đóng đối tượng ghi file CSV
            printWriter.close();
            //đóng stream cho bộ nhớ đệm
            bufferedOutputStream.close();
            //đóng stream cho file csv
            fileOutputStream.close();
            flag= true;
            
        }catch (IOException ex ){
            ex.printStackTrace();
        }

    }

    private void writeXLSX_NotAppend(String url){
        flag= false;
        try {
            // Tạo một Workbook mới
            Workbook workbook = new XSSFWorkbook(); // Đối với định dạng XLSX (Excel 2007 trở lên)

            // Tạo một Sheet mới
            Sheet sheet = workbook.createSheet("My Sheet");

            // Tạo các dòng và cột và ghi dữ liệu vào Sheet
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("1");

            // Tạo một đối tượng OutputStream để ghi tập tin Excel
            OutputStream outputStream = new FileOutputStream(url);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

            // Ghi workbook vào OutputStream bằng BufferedOutputStream
            workbook.write(bufferedOutputStream);

            // Đóng các luồng
            bufferedOutputStream.close();
            outputStream.close();

            System.out.println("Tập tin Excel đã được ghi thành công!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeImageIntoExcel(String url ){
        flag = false;
        try {
            // Tạo một đối tượng Workbook mới
            Workbook workbook = WorkbookFactory.create(new FileInputStream(url));
            Sheet sheet = workbook.getSheet("My Sheet");
            int countRow = sheet.getLastRowNum();
            Row row  = sheet.getRow(countRow+1 );
            //tiến hành đọc file ảnh
            File imageFile = new File("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\test_PNG.png");

            //các giá trị file ảnh được lưu vào mảng byte
            byte[] imageData = Files.readAllBytes(imageFile.toPath());

            // Tạo một đối tượng Drawing để vẽ hình ảnh trên Sheet
            Drawing<?> drawing = sheet.createDrawingPatriarch();

            // Tạo một đối tượng ClientAnchor để chỉ định vị trí và kích thước của hình ảnh
            ClientAnchor anchor = drawing.createAnchor(0,0,0,0,0, countRow+1, 3,countRow+ 4 );

            // Tạo một đối tượng Picture và chèn hình ảnh vào đó
            Picture picture = drawing.createPicture(anchor,workbook.addPicture(imageData,Workbook.PICTURE_TYPE_PNG));

            // Tạo một đối tượng ByteArrayOutputStream để ghi dữ liệu của Workbook vào đó
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Ghi dữ liệu vào trong Workbook
            workbook.write(baos);

            FileOutputStream fileOutputStream = new FileOutputStream(url);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(baos.toByteArray());

            bufferedOutputStream.close();
            fileOutputStream.close();
            workbook.close();
            flag = true ;

        } catch (IOException e ){
            e.printStackTrace();
        }

    }


    // ghi dữ liệu vào file Excel mà không append
    private void writeXLSX(String urlFile){
        flag = false;
        try {
            //Mở file XLSX
            Workbook workbook = new XSSFWorkbook(); // Đối với định dạng XLSX (Excel 2007 trở lên)

            // Tạo một Sheet mới

            Sheet sheet = workbook.createSheet("My Sheet");
            int rowCount = sheet.getLastRowNum();

            Row row = sheet.createRow(rowCount+1);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("132");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("Wowt");
            Cell cell3 = row.createCell(2);
            cell3.setCellValue("13");

            OutputStream outputStream = new FileOutputStream(urlFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

            // Ghi workbook vào BufferedOutputStream
            workbook.write(outputStream);
            // Đóng BufferedOutputStream và workbook
            outputStream.close();
            workbook.close();
            flag= true;

        }catch (IOException ex ){
            ex.printStackTrace();
        }

    }
    // ghi dữ liệu vào file Excel có append với dữ liệu cũ

    private void writeFileXLSX_Append( String url ){
        flag = false ;
        try {
            // Mở tập tin Excel đã có
            Workbook workbook = WorkbookFactory.create(new FileInputStream(url));

            // Lấy Sheet cần ghi dữ liệu
            Sheet sheet = workbook.getSheet("My Sheet");

            // Tạo một hàng mới và ghi dữ liệu vào các ô
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("Value 1");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("Value 2");
            Cell cell3 = row.createCell(2);
            cell3.setCellValue("Value 3");

            // Tạo một đối tượng OutputStream để ghi tập tin Excel
            OutputStream outputStream = new FileOutputStream(url);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

            // Ghi workbook vào OutputStream bằng BufferedOutputStream
            workbook.write(bufferedOutputStream);

            // Đóng các luồng
            bufferedOutputStream.close();
            outputStream.close();
            flag = true ;
            System.out.println("Dữ liệu đã được ghi vào tập tin Excel!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //hàm dùng để thiết lập nội dung
    private void setContentWeb(){
        content = "<div>";
        setContent(content.concat(

                "<h1 style='color:red;text-align:center'>Demo cho thư viện BufferedOutputStream </h1>"+
                        "<a href='http://localhost:8080'>Home</a><br/>"
        ));
        writeCSV("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\test_csv.csv");
        setContent(content.concat(

                "<br/> <strong> Ghi thêm dữ liệu vào test_csv.csv</strong><br/>"
                        +"<span>"+(flag?"Ghi thành công":"Ghi thất bại")+"</span>"
        ));
        writeFileXLSX_Append("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\text_Excel.xlsx");

//        setContent(content.concat(
//
//                "<br/> <strong> Ghi thêm dữ liệu vào text_writeEX.xlsx</strong><br/>"
//                        +"<span>"+(flag?"Ghi thành công":"Ghi thất bại")+"</span>"
//        ));

        // ghi ảnh vào trong file excel đã có từ trước
        writeImageIntoExcel("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\text_Excel.xlsx");

        setContent(content.concat(

                "<br/> <strong> Ghi  ảnh test_PNG.png vào trong file text_writeEX.xlsx</strong><br/>"
                        +"<span>"+(flag?"Ghi thành công":"Ghi thất bại")+"</span>"
        ));

        //ghi vào file Excel text và hình ảnh
        writeXLSX_MultiType("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\test_multiple.xlsx");
        setContent(content.concat(

                "<br/> <strong> Ghi  ảnh text và ảnh test_PNG.PNG vào trong file test_multiple.xlsx</strong><br/>"
                        +"<span>"+(flag?"Ghi thành công":"Ghi thất bại")+"</span>"
        ));

        // Xuất file blank1.pdf có văn bản là Hello Word
        exportPDF_NotAppend("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\blank1.pdf");
        setContent(content.concat(

                "<br/> <strong> Xuất file PDF có dòng chữ là HelloWord</strong><br/>"
                        +"<span>"+(flag?"Ghi thành công":"Ghi thất bại")+"</span>"
        ));

        //Xuất file blank2.pdf có chứa hình ảnh test_jpg.jpg
        exportPDF_Image("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\blank2.pdf" ,
                "F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\test_jpg.jpg"
                );
        setContent(content.concat(
                "<br/> <strong> Xuất file PDF có hình ảnh test_jpg.jpg</strong><br/>"
                        +"<span>"+(flag?"Ghi thành công":"Ghi thất bại")+"</span>"
        ));

        //Xuất file blank3 đồng thời vừa ghi ảnh vừa ghi đaon test vào
        exportPDF_Img_Txt("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\blank3.pdf");

        setContent(content.concat(
                "<br/> <strong> Xuất file PDF có hình ảnh test_jpg.jpg và dòng text vào file blank3.pdf</strong><br/>"
                        +"<span>"+(flag?"Ghi thành công":"Ghi thất bại")+"</span>"
        ));

        // Xuất file blankmerge.pdf file này là merge 2 file blank1 và blank2 lại với nhau
        exportPDF_Append("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\blank1.pdf",
                "F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\blank2.pdf",
                "F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\blankmerge.pdf"
        );
        setContent(content.concat(
                "<br/> <strong> Merge 2 file PDF trên lại với nhau thành file tổng </strong><br/>"
                        +"<span>"+(flag?"Ghi thành công":"Ghi thất bại")+"</span>"
        ));

        // chuyển từ file word sang file PDF
        exportPDF_toWord("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\blankw.pdf",
                "F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\test_word.docx"
                );

        setContent(content.concat(
                "<br/> <strong>Chuyển file word sang 1 trang pdf </strong><br/>"
                        +"<span>"+(flag?"Ghi thành công":"Ghi thất bại")+"</span>"
        ));

        setContent(content.concat("</div>"));
    }

    private void writeXLSX_MultiType(String url ) {
        flag = false;
        try {
                // mở file excel đã có sẵn bằng workbook
             Workbook workbook = WorkbookFactory.create(new FileInputStream(url));
             Sheet sheet = workbook.getSheet("My Sheet");
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("ABC");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(123);
            Cell cell3 = row.createCell(2);
            cell3.setCellValue("Nguyễn văn C");

            File imageFile = new File("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\test_PNG.png");

            //các giá trị file ảnh được lưu vào mảng byte
            byte[] imageData = Files.readAllBytes(imageFile.toPath());

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor( 0,0,0,0,0,sheet.getLastRowNum()+1, 3, sheet.getLastRowNum()+4);

            Picture picture = drawing.createPicture(anchor, workbook.addPicture(imageData, Workbook.PICTURE_TYPE_PNG));


            FileOutputStream fileOutputStream = new FileOutputStream(url);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            workbook.write(bufferedOutputStream);

            workbook.close();
            bufferedOutputStream.close();
            fileOutputStream.close();
            flag = true;

        } catch (IOException e ) {

        }

    }

    private  void exportPDF_Image(String urlPDF , String urlJPG){
        flag = false ;
        try {
            Document document = new Document();
            // Tạo đối tượng PdfWriter để ghi tài liệu PDF
            PdfWriter.getInstance(document, new FileOutputStream(urlPDF));
            document.open();

            // Đọc ảnh từ tệp ảnh và tạo đối tượng Image
            Image image = Image.getInstance(urlJPG);

            // Thêm đối tượng Image vào tài liệu PDF
            document.add(image);

            // Đóng tài liệu PDF
            document.close();
            flag = true;
            System.out.println("Tài liệu PDF mới đã được tạo thành công!");
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
    //ghi text vào file pdf
    private void exportPDF_NotAppend(String url ){
        flag= false;
        try {
            //Tạo một đối tượng document mới
            Document document = new Document();
            //Tạo một đối tượng PdfWriter để ghi tài liệu PDF
            OutputStream outputStream = new FileOutputStream(url);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            PdfWriter.getInstance(document,bufferedOutputStream);
            // Mở tài liệu PDF
            document.open();

            // Thêm nội dung vào tài liệu PDF
            document.add(new Paragraph("Hello, world "));

            // Đóng tài liệu PDF
            document.close();

            // Đóng các luồng
            bufferedOutputStream.close();
            outputStream.close();
            flag = true ;
            System.out.println("Tài liệu PDF đã được xuất thành công!");
        }catch (IOException| DocumentException e ){
            e.printStackTrace();
        }

    }
    // hàm này dùng để trộn 2 file pdf lại với nhau
    private void exportPDF_Append(String urlFile1 , String urlFile2 , String urlFileMerge ){
        flag= false;
        try {
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(urlFileMerge));
            document.open();
            // Mở đối tượng PdfCopy
            copy.open();


            // Đọc các trang từ tập tin PDF đầu tiên và thêm chúng vào tài liệu mới
            PdfReader reader1 = new PdfReader(urlFile1);
            copy.addDocument(reader1);
            reader1.close();

            System.out.println(copy.toString());


            // Đọc các trang từ tập tin PDF thứ hai và thêm chúng vào tài liệu mới
            PdfReader reader2 = new PdfReader(urlFile2);
            copy.addDocument(reader2);
            reader2.close();

            System.out.println(copy.toString());

            // Đóng đối tượng PdfCopy
            copy.close();

            flag = true;
            System.out.println("Tài liệu PDF mới đã được tạo thành công!");
        }catch (IOException| DocumentException e ){
            e.printStackTrace();
        }

    }

    private void exportPDF_Img_Txt(String url ){
        flag= false;
        try {
            //Tạo một đối tượng document mới
            Document document = new Document();
            //Tạo một đối tượng PdfWriter để ghi tài liệu PDF
            OutputStream outputStream = new FileOutputStream(url);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            PdfWriter.getInstance(document,bufferedOutputStream);
            // Mở tài liệu PDF
            document.open();

            // Thêm nội dung vào tài liệu PDF

            Image image = Image.getInstance("F:\\SparkMinds\\file_bc\\demo\\demo_java_io\\test_jpg.jpg");
            document.add(new Paragraph("Hello, Everyone! My name is Tai "));
            image.scaleAbsolute(100,100);
            // Thêm đối tượng Image vào tài liệu PDF
            document.add(image);

            // Đóng tài liệu PDF
            document.close();

            // Đóng các luồng
            bufferedOutputStream.close();
            outputStream.close();
            flag = true ;
            System.out.println("Tài liệu PDF đã được xuất thành công!");
        }catch (IOException| DocumentException e ){
            e.printStackTrace();
        }
    }

    private void exportPDF_toWord(String urlPDF, String urlWord) {
        flag = true ;
        try {
            //đọc được file word
            FileInputStream fileInputStream = new FileInputStream(urlWord);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            // tạo đối tượng từ file word
            XWPFDocument xwpfDocument = new XWPFDocument(bufferedInputStream);

            //thiết lập đầu ra cho file PDF
            FileOutputStream fileOutputStream = new FileOutputStream(urlPDF);
            com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(fileOutputStream);

            // Tạo một đối tượng PdfDocument từ PdfWriter
            PdfDocument pdfDocument = new PdfDocument(writer);

            // Tạo một đối tượng Document từ PdfDocument
            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(
                    pdfDocument,
                    new com.itextpdf.kernel.geom.PageSize(com.itextpdf.kernel.geom.PageSize.A4).rotate());

            //đọc tất cả các trong file word
            for (XWPFParagraph paragraph : xwpfDocument.getParagraphs()) {
                //đoc từ từng đoạn trong file word
                for (XWPFRun run : paragraph.getRuns()) {
                    //nếu là ảnh và có kích thước lớn hơn 0
                    if (run.getEmbeddedPictures() != null && run.getEmbeddedPictures().size() > 0) {
                        for (org.apache.poi.xwpf.usermodel.XWPFPicture picture : run.getEmbeddedPictures()) {

                            // Tạo một đối tượng Image từ ImageData

                            ImageData imageData = ImageDataFactory.create(picture.getPictureData().getData());
                            com.itextpdf.layout.element.Image image = new com.itextpdf.layout.element.Image(imageData);
                            // Đặt kích thước của hình ảnh
                            image.scaleToFit(
                                    com.itextpdf.kernel.geom.PageSize.A4.getWidth() - document.getLeftMargin() - document.getRightMargin(),
                                    com.itextpdf.kernel.geom.PageSize.A4.getHeight() - document.getTopMargin() - document.getBottomMargin());

                            // Thêm hình ảnh vào tài liệu PDF
                            document.add(image);
                        }
                    } else {
                        // Thêm các đoạn văn bản vào tài liệu PDF
                        document.add(new com.itextpdf.layout.element.Paragraph(run.toString()));
                    }
                }
            }

            // Đóng tệp PDF
            document.close();

            // Đóng tệp Word
            xwpfDocument.close();

            // Đóng đối tượng FileInputStream
            bufferedInputStream.close();

            // Đóng đối tượng FileOutputStream
            fileInputStream.close();

            fileOutputStream.close();
            flag = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // hàm sử dụng để hiển thị thông tin lên web
    private void showList(){
        view = "<body style='overflow:scroll; width:800px;margin:auto;padding:16px'>";
        setContentWeb();
        setView(view.concat(content));
        setView(view.concat("</body>"));

    }


}
