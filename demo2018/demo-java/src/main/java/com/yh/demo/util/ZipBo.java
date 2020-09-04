package com.yh.demo.util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

/**
 * 压缩包应用类
 * 基于java自带ZipOutputStream，文件名在压缩时写入，所以支持中文
 * 解压基于org.apache.ant:ant:1.9.4的jar，java自带的不支持中文
 * @author yanghan
 * @date 2019/4/22
 */
@SuppressWarnings("all")
public class ZipBo {

    public static void main(String[] args) throws Exception {
        testZip();
        testUnZip();
    }

    public static void testZip() {
        String basePath = "C:\\Users\\fw\\Desktop";
        String zipPath = "\\压缩文件.zip";
        ZipBo zipBo = new ZipBo(basePath + zipPath);
        List<ZipBeanBo> data = zipBo.getData();
        /**
         * 测试数据
         */
        // 根目录
        data.add(new ZipBeanBo(basePath + "\\zip", "别名zip"));
        // 根目录下文件夹
        data.add(new ZipBeanBo(basePath + "\\zip\\测试a-1", "别名zip\\别名测试a-1"));
        // 根目录下文件夹-文件夹...
        data.add(new ZipBeanBo(basePath + "\\zip\\测试a-1\\测试b-1", "别名zip\\别名测试a-1\\别名测试b-1"));
        // 根目录下文件夹-文件
        data.add(new ZipBeanBo(basePath + "\\zip\\测试a-1\\测试b-1文件.txt", "别名zip\\别名测试a-1\\别名测试b-1文件.txt"));
        // 根目录下文件
        data.add(new ZipBeanBo(basePath + "\\zip\\测试a-1文件.txt", "别名zip\\别名测试a-1文件.txt"));
        // 根文件
        data.add(new ZipBeanBo(basePath + "\\测试文件.txt", "别名测试文件.txt"));
        //错误路径
        //data.add(new ZipBeanBo(basePath + "\\zip\\2\\a.txt", "zip\\文件夹2\\测试文本.txt"));
        zipBo.pack();
    }

    public static void testUnZip() throws Exception {
        String basePath = "C:\\Users\\fw\\Desktop";
        String zipPath = "\\压缩文件.zip";
        ZipBo.unzip(new File(basePath + zipPath), basePath);
    }


    /** 压缩包绝对路径 */
    private String zipPath;

    /**
     * 压缩对象集合
     * 按文件夹—>文件的正常顺序存放，排后面的文件(夹)路径，它的上一级必须排在前面
     */
    private List<ZipBeanBo> data = new ArrayList<>(16);

    public ZipBo(String zipPath) {
        this.zipPath = zipPath;
    }

    public List<ZipBeanBo> getData() {
        return data;
    }

    public void setData(List<ZipBeanBo> data) {
        this.data = data;
    }

    /**
     * 按文件夹—>文件的正常顺序存放，排后面的文件(夹)路径，它的上一级必须排在前面
     */
    public void pack() {
        ZipOutputStream zos = null;
        FileOutputStream fos = null;
        try {
            //压缩包路径
            File baseTempZip = new File(zipPath);
            //最终压缩包文件对象
            fos = new FileOutputStream(baseTempZip);
            //压缩包文件对象
            zos = new ZipOutputStream(fos);

            for (ZipBeanBo zipBeanBo : data) {
                File zipFile = new File(zipBeanBo.getFilePath());
                if (zipFile.exists()) {
                    this.packAddFile(zos, zipBeanBo.getRelativePath(), zipFile);
                } else {
                    System.err.println("文件不存在，文件来源:" + zipFile.getAbsolutePath());
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            close(zos, fos);
        }
    }

    /**
     * 压缩文件(夹)
     *
     * @param zos
     * @param zipEntryName ：待压缩文件(夹)的相对路劲
     * @param file         ：待压缩文件(夹)
     */
    private void packAddFile(ZipOutputStream zos, String zipEntryName, File file) {
        BufferedInputStream bis = null;
        try {
            if (file.isDirectory()) {
                //格式：根目录/
                //格式：根目录/XX/
//                zipEntryName= zipEntryName.replace(File.separator, "/");
                /**
                 * ZipEntry的格式要求，目录结尾为/，不能用File.separator
                 */
                zos.putNextEntry(new ZipEntry(zipEntryName + "/"));
            } else {
                //格式：根文件
                //格式：根目录/XX
                zos.putNextEntry(new ZipEntry(zipEntryName));
                InputStream inputStream = new FileInputStream(file);
                //把文件流获取缓冲，开始向zos写入流
                bis = new BufferedInputStream(inputStream);
                byte[] b = new byte[1024 * 4];
                int len;
                while ((len = bis.read(b)) != -1) {
                    zos.write(b, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(bis);
            try {
                zos.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 采用jdk自带的ZipInputStream解压zip文件，不支持中文文件名条目（utf8编码可能可以，没测试）
     * 结果 toDir/压缩包内文件 (不包含zip文件名)
     *
     * @param zipFile
     * @param toDir
     */
    public static void unzip(File zipFile, String toDir) throws Exception {
        if (toDir == null) {
            toDir = ".";
        }
        ZipFile zf = null;
        ZipInputStream zis = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            zf = new ZipFile(zipFile);
            zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File outFile = new File(toDir + File.separator + entry.getName());
                File outDir = outFile.getParentFile();
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }
                // ZipEntry的目录判断，结尾为"/"
                if (entry.isDirectory()) {
                    outFile.mkdir();
                    continue;
                }
                bis = new BufferedInputStream(zf.getInputStream(entry));
                bos = new BufferedOutputStream(new FileOutputStream(outFile));
                byte[] b = new byte[1024 * 4];
                int len;
                while ((len = bis.read(b)) != -1) {
                    bos.write(b, 0, len);
                }
                bos.flush();
                close(bos, bis);
            }
            System.out.println(String.format("解压文件%s到%s", zipFile.getAbsolutePath(), toDir));
        } catch (Exception e) {
            System.err.println(String.format("解压文件%s到%s出错:%s", zipFile.getAbsolutePath(), toDir, e.getMessage()));
            throw e;
        } finally {
            close(bos, bis, zis, zf);
        }
    }

    /**
     * Apache解压zip包,支持递归
     *
     * @param archive
     * @param decompressDir
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ZipException
     */
    /*public static void unzip(String archive, String decompressDir)
            throws IOException, FileNotFoundException, ZipException {
        BufferedInputStream bi;
        System.out.println("readByApacheZipFile ,from:【" + archive + "】 - to:【" + decompressDir + "】");

        org.apache.tools.zip.ZipFile zf = new org.apache.tools.zip.ZipFile(archive, "GBK");//支持中文
        Enumeration e = zf.getEntries();
        while (e.hasMoreElements()) {
            org.apache.tools.zip.ZipEntry ze2 = (org.apache.tools.zip.ZipEntry) e.nextElement();
            String entryName = ze2.getName();
            entryName = entryName.replaceAll("\\\\", "/").replaceAll("////", "/");
            String path = decompressDir + "/" + entryName;

            if (ze2.isDirectory()) {
                System.out.println("正在创建解压目录 - " + entryName);
                File decompressDirFile = new File(path);
                if (!decompressDirFile.exists()) {
                    decompressDirFile.mkdirs();
                }
            } else {
                System.out.println("正在创建解压文件 - " + entryName);

                File outFile = new File(path);
                File outDir = outFile.getParentFile();    //文件父级目录创建
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(path));
                bi = new BufferedInputStream(zf.getInputStream(ze2));
                byte[] readContent = new byte[1024];
                int readCount = bi.read(readContent);
                while (readCount != -1) {
                    bos.write(readContent, 0, readCount);
                    readCount = bi.read(readContent);
                }
                bos.close();
            }
        }
        zf.close();
        System.out.println("readByApacheZipFile ,from:【" + archive + "】 - to:【" + decompressDir + "】");
    }*/

    public static void close(Closeable... closes) {
        for (Closeable closeable : closes) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (IOException ioe) {
                // ignore
                ioe.printStackTrace();
            }
        }
    }
}

/**
 * 压缩对象
 * 路径分隔符必须双斜杠\\，用\也行压缩，但无法解压
 *
 * @author yanghan
 * @date 2019/4/22
 */
class ZipBeanBo {
    /** 文件(夹)绝对路径 */
    private String filePath;
    /** 文件(夹)打包后相对路径 */
    private String relativePath;

    public ZipBeanBo(String filePath, String relativePath) {
        this.filePath = filePath;
        this.relativePath = relativePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}