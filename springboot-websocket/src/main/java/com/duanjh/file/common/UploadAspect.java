package com.duanjh.file.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.id.NanoId;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.DateUtils;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.concurrent.*;


/**
 * 自定义切面
 */
@Slf4j
@Aspect
@Component
public class UploadAspect {

    public static ThreadFactory commonThreadFactory = Executors.defaultThreadFactory();
    /**
     * 创建线程池
     */
    public static ExecutorService uploadExecuteService = new ThreadPoolExecutor(10, 20, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024), commonThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 切入点，自定义注解的包路径
     */
    @Pointcut("@annotation(com.duanjh.file.common.Upload)")
    public void uploadPoint(){}

    /**
     * @AfterReturning： 处理完请求后执行
     *  @AfterThrowing: 拦截异常操作
     *
     *
     */
    @Around(value = "uploadPoint()")
    public Object uploadControl(ProceedingJoinPoint joinPoint){
        //获取方法上的注解，进而获取uploadType
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Upload annotation = signature.getMethod().getAnnotation(Upload.class);
        UploadType type = null == annotation ? UploadType.UNKONW : annotation.type();

        //获取batchNo，使用NanoId
        String batchNo = NanoId.randomNanoId();

        //初始化一条上传的日志，记录开始时间
        //TODO: Write To DB

        //线程池启动异步线程，开始执行上传的逻辑，joinPoint.proceed就是实现的上传功能
        uploadExecuteService.submit(() -> {
            try{
                Object errorMsg = joinPoint.proceed();
                //没有异常直接成功
                if(ObjectUtil.isEmpty(errorMsg)){
                    //成功，直接写入数据库
                    //TODO: Write Success To DB
                }else {
                    //失败，返回校验信息
                    fail(errorMsg.toString(), batchNo);
                }
            }catch (Throwable e){
                log.error("导入失败： {}", e);
                //失败，抛出异常并记录
                fail(e.toString(), batchNo);
            }
        });
        return new Object();
    }

    private void fail(String msg, String batchNo){
        //生成上传错误日志的文件Key
        String  key = NanoId.randomNanoId();
        //生成文件名称
        String fileName = "错误日志_" + DateUtil.formatDateTime(new Date()) + ".log";

        String filePath = "/home/xxx/xxx" + fileName;

        //生成文件
        File file = new File(filePath);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(msg.getBytes());

        } catch (Exception e) {
            log.error("写入文件错误", e);
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (Exception e) {
                log.error("关闭错误", e);
            }
        }
        //上传错误日志文件到文件服务器
        //TODO: UploadFileToS3
        //同时记录上传失败到数据库，方便用户查看
        //TODO：Write To DB
        //删除文件，防止硬盘满
        //TODO： Delete File
    }

}
