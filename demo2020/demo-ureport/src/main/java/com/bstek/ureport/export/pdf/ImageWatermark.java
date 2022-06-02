package com.bstek.ureport.export.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 背景图作为水印，打印导出
 */
public class ImageWatermark extends PdfPageEventHelper {
    private String bgImage;

    public ImageWatermark(String bgImage) {
        this.bgImage = bgImage;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        if(this.bgImage == null){
            return;
        }
        PdfContentByte pdfContentByte = writer.getDirectContentUnder();
        // 设置水印透明度
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.5f);
        pdfContentByte.setGState(gs);
        InputStream input = null;
        try {
//            input = this.getImgInputStreamByLocal();
            input = this.getImgInputStream(this.bgImage);
            byte[] bytes = IOUtils.toByteArray(input);
            Image img = Image.getInstance(bytes);
            //设置图片水印的位置。
            img.setAbsolutePosition(0, 0);
            img.scaleToFit(img.getScaledWidth(), img.getScaledHeight());
            img.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());
            pdfContentByte.addImage(img);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * 项目中某固定图片
     * @return
     */
    private InputStream getImgInputStreamByLocal() {
        // 例子
        return this.getClass().getResourceAsStream("/static/404.png");
    }

    private InputStream getImgInputStream(String url) {
        InputStream input = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                input = connection.getInputStream();
            }
        } catch (IOException e) {
            System.err.println("获取背景图片出现异常，图片路径为：" + url);
        }
        return input;
    }
}
