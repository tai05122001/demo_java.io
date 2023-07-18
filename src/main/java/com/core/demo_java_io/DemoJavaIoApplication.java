package com.core.demo_java_io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoJavaIoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoJavaIoApplication.class, args);
    }

    @GetMapping
    public String demojavaio(){
        return "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" >\n" +
                "<style >\n" +
                "\t.btn{\n" +
                "\t\tcolor: white!important\n" +
                "\t}\n" +
                "\t.content{\n" +
                "\t\twidth:800px;\n" +
                "\t\tmargin:auto;\n" +
                "\t\tpadding: 16px;\n" +
                "\t\t\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t}\n" +
                "\t.title{\n" +
                "\t\ttext-align:center;\n" +
                "\t\tcolor: red\n" +
                "\t}\n" +
                "\t\n" +
                "\n" +
                "</style>\n" +
                "<body class=\"content\">\n" +
                "\t<h1 class=\"title\">Demo về thư viện java.io </h1>\n" +
                "\t<div>\n" +
                "\t\t<h5>Demo - FileInputStream  </h5>\n" +
                "\t\t<a href=\"http://localhost:8080/demo/FileInputStream\" class=\"btn btn-primary\">FileInputStream</a>\n" +
                "\t</div>\n" +
                "\t<div>\n" +
                "\t\t<h5>Demo - FileOutputStream  </h5>\n" +
                "\t\t<a href=\"http://localhost:8080/demo/FileOutputStream\" class=\"btn btn-success\">FileOutputStream</a>\n" +
                "\t</div>\n" +
                "\n" +
                "\t<div>\n" +
                "\t\t<h5>Demo - BufferedInputStream  </h5>\n" +
                "\t\t<a href=\"http://localhost:8080/demo/BufferedInputStream\" class=\"btn btn-info\">BufferedInputStream</a>\n" +
                "\t</div>\n" +
                "\n" +
                "\n" +
                "\t<div>\n" +
                "\t\t<h5>Demo - BufferedOutputStream  </h5>\n" +
                "\t\t<a href=\"http://localhost:8080/demo/BufferedOutputStream\" class=\"btn btn-danger\">BufferedOutputStream</a>\n" +
                "\t</div>\n" +
                "\n" +
                "\t\n" +
                "\n" +
                "</body>";
    }

}
