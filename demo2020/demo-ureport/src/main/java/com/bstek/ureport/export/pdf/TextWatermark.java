package com.bstek.ureport.export.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * 文字水印
 */
public class TextWatermark extends PdfPageEventHelper {

    private final Font font = new Font(Font.FontFamily.HELVETICA, 50, Font.BOLD, BaseColor.RED);
    private String waterCont;

    public TextWatermark(Object waterCont) {
        if (null != waterCont) {
            this.waterCont = waterCont.toString();
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        PdfContentByte pdfContentByte = writer.getDirectContentUnder();

        //设置水印透明度
        PdfGState gs = new PdfGState();
        //设置填充字体的不透明度
        gs.setFillOpacity(0.2f);
        font.setSize(50);

        pdfContentByte.setGState(gs);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ColumnText.showTextAligned(pdfContentByte,
                        Element.ALIGN_CENTER,
                        new Phrase(this.waterCont == null ? "" : this.waterCont, font),
                        (60.5f + i * 350),
                        (40.0f + j * 280),
                        writer.getPageNumber() % 2 == 1 ? 45 : -45);
            }
        }

    }
}
