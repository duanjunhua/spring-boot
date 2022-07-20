package com.boot.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;

public class PdfUtils {

    private static final int wdFormatPDF = 17;
    private static final int xlTypePDF = 0;
    private static final int ppSaveAsPDF = 32;
    private static final int msoTrue = -1;
    private static final int msofalse = 0;

    public boolean word2PDF(String inputFile,String pdfFile){
        ActiveXComponent app = null;
        Dispatch doc = null;
        boolean result=true;
        try{
            //打开word应用程序
            app = new ActiveXComponent("Word.Application");
            //设置word不可见
            app.setProperty("Visible", false);
            //获得word中所有打开的文档,返回Documents对象
            Dispatch docs = app.getProperty("Documents").toDispatch();
            //调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
            doc = Dispatch.call(docs,
                    "Open",
                    inputFile,
                    false,
                    true
            ).toDispatch();

            Dispatch.call(doc,
                    "ExportAsFixedFormat",
                    pdfFile,
                    wdFormatPDF     //word保存为pdf格式宏，值为17
            );

            result= true;
        }catch(Exception e){
            result= false;
        }finally {
            if (doc != null) {
                Dispatch.call(doc, "Close");
            }
            if (app != null) {
                app.invoke("Quit");
            }
        }
        return result;
    }

    public boolean excel2PDF(String inputFile,String pdfFile){
        ActiveXComponent app = null;
        Dispatch excel = null;
        boolean result=true;
        try{
            app = new ActiveXComponent("Excel.Application");
            app.setProperty("Visible", false);
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            excel = Dispatch.call(excels, "Open", inputFile,
                    false,
                    true
            ).toDispatch();
            Dispatch.call(excel,
                    "ExportAsFixedFormat",
                    xlTypePDF,
                    pdfFile
            );
            result= true;
        }catch(Exception e){
            result= false;
        }finally {
            if (excel != null) {
                Dispatch.call(excel, "Close");
            }
            if (app != null) {
                app.invoke("Quit");
            }
        }
        return result;
    }

    public boolean ppt2PDF(String srcFilePath, String pdfFilePath){
        ActiveXComponent app = null;
        Dispatch ppt = null;
        boolean result=true;
        try {
            ComThread.InitSTA();
            app = new ActiveXComponent("PowerPoint.Application");
            Dispatch ppts = app.getProperty("Presentations").toDispatch();

            // 因POWER.EXE的发布规则为同步，所以设置为同步发布
            ppt = Dispatch.call(ppts, "Open", srcFilePath, true,// ReadOnly
                    true,// Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
            ).toDispatch();

            Dispatch.call(ppt, "SaveAs", pdfFilePath, 32); //ppSaveAsPDF为特定值32

            result=true; // set flag true;
        } catch (ComFailException e) {
            result=false;
        } catch (Exception e) {
            result=false;
        } finally {
            if (ppt != null) {
                Dispatch.call(ppt, "Close");
            }
            if (app != null) {
                app.invoke("Quit");
            }
            ComThread.Release();
        }
        return result;
    }
}
