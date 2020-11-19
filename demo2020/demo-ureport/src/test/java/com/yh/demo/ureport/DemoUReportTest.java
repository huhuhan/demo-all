package com.yh.demo.ureport;

import com.bstek.ureport.export.ExportConfigure;
import com.bstek.ureport.export.ExportConfigureImpl;
import com.bstek.ureport.export.ExportManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoUReportTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void export() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        ExportManager exportManager = (ExportManager) applicationContext.getBean(ExportManager.BEAN_ID);

        try (FileOutputStream fo = new FileOutputStream("D:\\YH\\tmp\\export-demo.xlsx")) {
            // 报表文件，调用加载报表方法loadReport
            String fileName = "yh-" + "demo" + ".ureport.xml";
            ExportConfigure exportConfigure = new ExportConfigureImpl(fileName, parameters, fo);
            exportManager.exportExcel(exportConfigure);
            //exportManager.exportPdf(exportConfigure);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
