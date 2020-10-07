package com.example.jayden.contoller;

import com.example.jayden.dto.FileDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class TestController {

    @PostMapping("/uploadFile")
    @ResponseBody
    public String fileUpload(@RequestParam("targetFile") MultipartFile mFile) throws IOException {
        System.out.println("접근");
        System.out.println(mFile.getOriginalFilename());
        try{
//            mFile.transferTo(new File("D:/workspace/annes_order/filedownload_project/public/datas/"+mFile.getOriginalFilename()));
            mFile.transferTo(new File("/data/work/servers/tomcat9_ae_fileIO_back/datas/"+mFile.getOriginalFilename()));
        }catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }//fileUpload() end

    @GetMapping("/getFiles")
    @ResponseBody
    public List<FileDTO> fileList(){
        File dir = new File("/data/work/servers/tomcat9_ae_fileIO_back/datas/");
        File files[] = dir.listFiles();
//        System.out.println("리스트 접근은 했다.");

        List<FileDTO> fileDTOS = new ArrayList<>();

        for(int i=0; i<files.length; i++){
//            System.out.println("출력한다 "+ files[i].getName());
            fileDTOS.add(new FileDTO(files[i].getName()));
        }//for end

        return fileDTOS;
    }//fileList() end

    @PostMapping(path = "downloadFile")
    @ResponseBody
    public List<ResponseEntity<Resource>> fileDownload(HttpServletResponse response, @RequestParam("checkedFiles") List<String> checkedFiles) throws IOException {
        System.out.println("다운로드 접근");
        List<ResponseEntity<Resource>> list = new ArrayList<>();

        for(String fileName : checkedFiles){
//            System.out.println(fileName+" 을 다운로드합니다");
//            String saveFileName = "/data/work/servers/tomcat9_ae_fileIO_back/datas/"+fileName;
//
//            Path source = Paths.get(saveFileName);
//            String contentType = Files.probeContentType(source);
//            System.out.println(contentType+" 의 타입을 가지네요");
//
//            File file = new File(saveFileName);
//            long fileLength = file.length();
//
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
//            response.setHeader("Content-Transfer-Encoding", "binary");
//            response.setHeader("Content-Type", contentType);
//            response.setHeader("Content-Length", "" + fileLength);
//            response.setHeader("Pragma", "no-cache;");
//            response.setHeader("Expires", "-1;");
//            // 그 정보들을 가지고 reponse의 Header에 세팅한 후
//
//
//            try{
//                FileInputStream fis = new FileInputStream(saveFileName);
//                OutputStream out = response.getOutputStream();
//                // saveFileName을 파라미터로 넣어 inputStream 객체를 만들고
//                // response에서 파일을 내보낼 OutputStream을 가져와서
//                int readCount = 0;
//                byte[] buffer = new byte[1024];
//                // 파일 읽을 만큼 크기의 buffer를 생성한 후
//                while ((readCount = fis.read(buffer)) != -1) {
//                    out.write(buffer, 0, readCount);
//                    // outputStream에 씌워준다
//                }
//            } catch (Exception ex) {
//                throw new RuntimeException("file Load Error");
//            }

            Path path = Paths.get("/data/work/servers/tomcat9_ae_fileIO_back/datas/"+fileName);
            String contentType = Files.probeContentType(path);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString());

            Resource resource = new InputStreamResource(Files.newInputStream(path));
            list.add(new ResponseEntity<>(resource, headers, HttpStatus.OK));
        }
        return list;
    }//fileDownload() end
}