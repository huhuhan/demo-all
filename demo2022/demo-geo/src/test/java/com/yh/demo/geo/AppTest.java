package com.yh.demo.geo;

import com.yh.demo.geo.util.FileUtil;
import com.yh.demo.geo.util.SpatialDataTransformUtil;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@SuppressWarnings("all")
public class AppTest {
    String basePath = "/demo2022/demo-geo";

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    //json格式参考demo.json 不同jar解析json的shp图形查看可能会变形
    @Test
    public void json2shp() {
        String userDir = System.getProperty("user.dir");
        String projectPath = userDir; // + basePath;

        String shpPath = projectPath + "/data/shapefile/demo.shp";
        String jsonPath = projectPath + "/data/json/demo.json";

        File outputShpFile = FileUtil.getNewFile(shpPath);
        String json = FileUtil.readFile(jsonPath);
        SpatialDataTransformUtil.transformGeoJsonStrToShp(json, outputShpFile.getAbsolutePath());
        System.out.println("ok");
    }


    @Test
    public void shp2json() {
        String userDir = System.getProperty("user.dir");
        String projectPath = userDir; // + basePath;

        String shpPath = projectPath + "/data/shapefile/demo.shp";
        String jsonPath = projectPath + "/data/json/demo.json";

        SpatialDataTransformUtil.transformShpToGeoJSON(shpPath, jsonPath);
        System.out.println("ok");
    }
}
