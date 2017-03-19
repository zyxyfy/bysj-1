package com.newview.bysj.web.identifyingcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Controller
@RequestMapping("kaptcha")
public class IdentifyingCodeController {

    //private static final Logger logger = Logger.getLogger(IdentifyingCodeController.class);
    @Autowired
    private Producer producer;

    @RequestMapping("/getKaptchaImage.html")
    public ResponseEntity<byte[]> getIdentifyingCode(HttpSession httpSession) throws IOException {
        //创建验证码
        String text = producer.createText();
        //添加到session中
        httpSession.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        //为验证码创建图片
        BufferedImage bi = producer.createImage(text);
        File file = new File("checkImage");
        //格式为jpg
        ImageIO.write(bi, "jpg", file);
        //文件下载
        HttpHeaders hh = new HttpHeaders();
        hh.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //设置名字
        hh.setContentDispositionFormData("attachment", "checkImage.jpg");
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), hh, HttpStatus.CREATED);
    }
}
