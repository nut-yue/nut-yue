package com.cbd.demo.controller;

import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.util.ResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @ClassName : UploadCompanyController
 * @Date ：2019/6/5/0005 9:28
 * @Desc ：类的介绍：
 * @author：张皓
 */
@RestController
@RequestMapping("/upload")
public class UploadCompanyController {
    /**
     * 上传图片的相对路径
     */
    private final String FILE_PATH = "src/main/resources/static/image/administrator/";

    @Introduce(desc = "图片上传")
    @RequestMapping(value = "/companyImg", method = RequestMethod.POST)
    public ResponseData singleFileUpload(@RequestParam("file") MultipartFile file,
                                         RedirectAttributes redirectAttributes) {
        ResponseData responseData = new ResponseData();
        //获取文件名
        String filename = file.getOriginalFilename();
        //获取文件的后缀名
        String suffixName = filename.substring(filename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            responseData.setCode(500);
            responseData.setMessage("上传失败");
            return responseData;
        }
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            //文件名 项目相对路径 + uuid + 文件后缀名
            Path path = Paths.get(FILE_PATH + uuid + filename);
            System.out.println(path);
            Files.write(path, bytes);
            responseData.getData().put("path", uuid + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
