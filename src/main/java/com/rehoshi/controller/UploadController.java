package com.rehoshi.controller;

import com.rehoshi.dto.RespData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片上传处理
 */

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @RequestMapping(value = "/imgUpload",method = RequestMethod.POST)
    public RespData<String> imageUpload(@RequestParam(value = "file",required = false) MultipartFile file, HttpServletRequest request) {
        //原始图片名称
        String originalFilename = file.getOriginalFilename();
        //新的图片名称
        String newFileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //当前项目绝对路径
        String webRoot = request.getSession().getServletContext().getRealPath("/");
        //图片根目录  不存在就创建
        File imgRoot = new File(webRoot + "/upload/images");
        if (!imgRoot.exists()) {
            imgRoot.mkdirs();
        }
        RespData<String> respData = new RespData<>();
        //将图片写进磁盘
        try {
            file.transferTo(new File(imgRoot +"/" +newFileName));
            respData.setCode(200).setData("../../upload/images/" + newFileName).setMsg("上传成功");
            // RespData.success("upload/images/"+newFileName).setCode(200).setMsg("上传成功");
        } catch (IOException e) {

            respData.setData("").setCode(0).setMsg("上传失败");
        }finally {

            return respData;

        }
    }
}
