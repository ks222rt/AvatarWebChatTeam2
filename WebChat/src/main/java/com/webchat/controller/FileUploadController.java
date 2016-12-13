/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchat.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rikard
 */

// TODO: Catch the exception generated when the uploaded file exceeds maximum
// allowed file size.
@Controller
public class FileUploadController {

    @RequestMapping(value = "/main/chat/uloadFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                /* The following code creates a directory named "tmpFiles" in 
                the tomcat directory where uploaded files will be saved.
                TODO: Create a separate directory under this directory when a 
                file is uploaded that represents the chat room that the file 
                was uploaded to. */
                String root = System.getProperty("catalina.home");
                File dir = new File(root + File.separator + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                /* Create the file on server */
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename());
                try (BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile))) {
                    stream.write(bytes);
                }

                return "Uploaded file:" + file.getOriginalFilename();
            } catch (IOException e) {
                return "Failed to upload " + file.getOriginalFilename() + ": " + e.getMessage();
            }
        } else {
            return "Failed to upload " + file.getOriginalFilename() + ". File was empty.";
        }
    }
}
