package com.boot.util;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 实现Excel、Word在线预览
 */
public class FileUtils {

    /** 默认转换后文件后缀 */
    private static final String DEFAULT_SUFFIX = "pdf";

    /** openoffice_port */
    private static final Integer OPENOFFICE_PORT = 8100;

    /**
     * 方法描述 office文档转换为pdf(处理本地文件)
     * @param sourcepath 源文件路径
     * @param suffix 源文件后缀
     * @return inputstream 转换后文件输入流
     */
    public static InputStream convertlocalefile(String sourcepath, String suffix) throws Exception {
        File inputfile = new File(sourcepath);
        InputStream inputstream = new FileInputStream(inputfile);
        return covertcommonbystream(inputstream, suffix);
    }

    /**
     * 方法描述  office文档转换为pdf(处理网络文件)
     * @param netfileurl 网络文件路径
     * @param suffix     文件后缀
     * @return inputstream 转换后文件输入流
     */
    public static InputStream convertnetfile(String netfileurl, String suffix) throws Exception {
        // 创建url
        URL url = new URL(netfileurl);
        // 试图连接并取得返回状态码
        URLConnection urlconn = url.openConnection();
        urlconn.connect();
        HttpURLConnection httpconn = (HttpURLConnection) urlconn;
        int httpresult = httpconn.getResponseCode();
        if (httpresult == HttpURLConnection.HTTP_OK) {
            InputStream inputstream = urlconn.getInputStream();
            return covertcommonbystream(inputstream, suffix);
        }
        return null;
    }

    /**
     * 方法描述  将文件以流的形式转换
     * @param inputstream 源文件输入流
     * @param suffix      源文件后缀
     * @return inputstream 转换后文件输入流
     */
    public static InputStream covertcommonbystream(InputStream inputstream, String suffix) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(OPENOFFICE_PORT);
        connection.connect();
        DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        DefaultDocumentFormatRegistry formatreg = new DefaultDocumentFormatRegistry();
        DocumentFormat targetformat = formatreg.getFormatByFileExtension(DEFAULT_SUFFIX);
        DocumentFormat sourceformat = formatreg.getFormatByFileExtension(suffix);
        converter.convert(inputstream, sourceformat, out, targetformat);
        connection.disconnect();
        return outputstreamconvertinputstream(out);
    }

    /**
     * 方法描述 outputstream转inputstream
     */
    public static ByteArrayInputStream outputstreamconvertinputstream(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos=(ByteArrayOutputStream) out;
        return new ByteArrayInputStream(baos.toByteArray());
    }

}
