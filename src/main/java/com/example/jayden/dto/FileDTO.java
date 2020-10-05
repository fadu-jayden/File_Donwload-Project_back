package com.example.jayden.dto;

public class FileDTO {
    public String fileName;

    FileDTO(){ }
    public FileDTO(String fileName){
        this.fileName=fileName;
    }

    public void setFileName(String fileName){
        this.fileName=fileName;
    }//setFileName() end

}
