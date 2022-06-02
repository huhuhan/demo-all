package com.bstek.ureport.export.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 背景图作为水印，打印导出
 */
@Slf4j
public class BgImgWatermark extends PdfPageEventHelper {

    /** 背景图，作为图片水印 */
    private String bgImage;

    public BgImgWatermark(String bgImage) {
        this.bgImage = bgImage;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        // 以背景图作水印
        if(!StringUtils.isEmpty(this.bgImage)){
            this.addImgWatermark(writer, document);
        }
    }

    private void addImgWatermark(PdfWriter writer, Document document) {
        PdfContentByte pdfContentByte = writer.getDirectContentUnder();
        //设置水印透明度
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.7f);
        pdfContentByte.setGState(gs);
        InputStream input = null;
        try {
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
        } catch ( IOException e) {
            log.error("获取背景图片出现异常，图片路径为：{}", url);
        }
        return input;
    }
}
