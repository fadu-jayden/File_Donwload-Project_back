package com.example.jayden.contoller;

import com.example.jayden.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class TestController {

    @PostMapping("/uploadFile")
    @ResponseBody
    public String fileUpload(HttpServletRequest request, @RequestParam("targetFile") MultipartFile mFile) {
        System.out.println("접근");
        System.out.println(mFile.getOriginalFilename());
        try{
//            mFile.transferTo(new File("D:/workspace/annes_order/filedownload_project/public/datas/"+mFile.getOriginalFilename()));
            mFile.transferTo(new File("C:/Program Files/Apache Software Foundation/Tomcat 8.5/webapps/ROOT/datas/"+mFile.getOriginalFilename()));
        }catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }//fileUpload() end

    @GetMapping("/getFiles")
    @ResponseBody
    public List<FileDTO> fileList(){
        File dir = new File("C:/Program Files/Apache Software Foundation/Tomcat 8.5/webapps/ROOT/datas/");
        File files[] = dir.listFiles();
//        System.out.println("리스트 접근은 했다.");

        List<FileDTO> fileDTOS = new ArrayList<>();

        for(int i=0; i<files.length; i++){
//            System.out.println("출력한다 "+ files[i].getName());
            fileDTOS.add(new FileDTO(files[i].getName()));
        }//for end

        return fileDTOS;
    }//fileList() end
}