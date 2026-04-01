package com.duanjh;

import com.duanjh.tess4j.Tess4JConfig;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;


/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-04-01 周三 10:00
 * @Version: v1.0
 * @Description: 图像文字识别测试
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class Tess4JAppTests {

    @Autowired
    Tess4JConfig config;

    @Test
    public void chineseTest() throws Exception{
        long start = System.currentTimeMillis();

        ITesseract itsrt = new Tesseract();

        // 设置字体库绝对路径
        itsrt.setDatapath("E:\\git\\workspace\\project-personal\\spring-boot\\springboot3\\src\\main\\resources\\tessdata");

        // 设置语言
        itsrt.setLanguage(config.getChineseTrainData());

        // 待识别的文字图片：E:\git\workspace\project-personal\spring-boot\springboot3\src\main\resources\tessdata\ocr\test.png
        File file = new File("E:\\git\\workspace\\project-personal\\spring-boot\\springboot3\\src\\main\\resources\\tessdata\\ocr\\test.png");

        // 识别图片
        String result = itsrt.doOCR(file);

        long end = System.currentTimeMillis();

        log.info("耗时：{} ms", end - start);

        log.info("解析结果：{}", result);
    }
}
