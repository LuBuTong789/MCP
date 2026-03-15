package com.example.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class FileDownloadController {

    /**
     * 文件下载接口（仿微信传输）
     * @param fileName 文件名（支持中文）
     */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable("fileName") String fileName) {
        try {
            // 1. 指定服务器文件路径（替换成你自己的文件路径）
            String filePath = "D:/AA简历/" + fileName;
            File file = new File(filePath);

            // 2. 解决中文文件名乱码
            String encodeFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.name());

            // 3. 设置响应头 = 核心！告诉前端这是下载文件
            HttpHeaders headers = new HttpHeaders();
            // 强制下载（attachment），而非浏览器预览
            headers.add("Content-Disposition", "attachment;filename=" + encodeFileName);
            // 流式传输
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // 4. 返回文件流给前端
            return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}