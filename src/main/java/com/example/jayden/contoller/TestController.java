package com.example.jayden.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/api")
public class TestController {

    @PostMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(HttpServletRequest request, @RequestParam("targetFile") MultipartFile mFile) {
        System.out.println("접근");
        System.out.println(mFile.getOriginalFilename());
        try{
            mFile.transferTo(new File("D:/workspace/annes_order/filedownload_project/public/datas/"+mFile.getOriginalFilename()));
        }catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }//fileUpload() end
}
