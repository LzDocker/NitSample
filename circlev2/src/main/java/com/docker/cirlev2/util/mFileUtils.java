package com.docker.cirlev2.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class mFileUtils {
    public static String DOWNLOADFOLDER = Environment.getExternalStorageDirectory() + "/shuangchuang/download/";
    public static String SDPATH = Environment.getExternalStorageDirectory() + "/shuangchuang/formats/";
    public static String SDPATH2 = Environment.getExternalStorageDirectory() + "/shuangchuang/formats2/";
    public static String SDPATH3 = Environment.getExternalStorageDirectory() + "/shuangchuang/posters/";
    public static String SDPATH4 = Environment.getExternalStorageDirectory() + "/shuangchuang/audio/";
    public static String VideoImages = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.com.bfhd.android.lamiyun/" + "videoCache/dstImages/";

    public static synchronized void saveBitmap(Bitmap bm, String picName) {
        // Log.e("", "保存图片");
        try {
            File dir = new File(SDPATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(SDPATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bm.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bos.flush();
            bos.close();
            // Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void saveBitmapToPng(Bitmap bm, String picName) {
        // Log.e("", "保存图片");
        try {
            File dir = new File(SDPATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(SDPATH, picName + ".png");
            if (f.exists()) {
                f.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bm.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bos.flush();
            bos.close();
            // Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储未压缩的视频图片
     *
     * @param bm
     * @param picName
     */
    public static synchronized void saveBitmap2(Bitmap bm, String picName) {
        try {
            File dir = new File(SDPATH2);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(SDPATH2, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bm.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bos.flush();
            bos.close();
            // Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Bitmap bitmap = revitionImageSize(SDPATH2 + picName + ".JPEG");
            saveBitmap(bitmap, picName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储未压缩的视频图片
     *
     * @param bm
     * @param picName
     */
    public static synchronized void saveBitmapPng(Bitmap bm, String picName) {
        try {
            File dir = new File(SDPATH2);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(SDPATH2, picName + ".png");
            if (f.exists()) {
                f.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bm.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bos.flush();
            bos.close();
            // Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Bitmap bitmap = revitionImageSize(SDPATH2 + picName + ".png");
            saveBitmapToPng(bitmap, picName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    public static void saveVideoImage(Bitmap bm, String picName) {
        // Log.e("", "保存图片");
        try {
            File dir = new File(VideoImages);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(VideoImages, picName + ".png");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            // Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }

    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    public static void deleteDir( File dir ) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除�?有文�?
            else if (file.isDirectory())
                    deleteDir(file); // 递规的方式删除文件夹
        }

        dir.delete();// 删除目录本身
    }
    public static void deleteAllFile1(){
        File dir = new File(SDPATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        deleteAllFiles(dir);
    }
    /**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
    private static void deleteAllFiles(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                deleteAllFiles(f);
            }
            file.delete();
        }
    }

    public static void deleteAllDir() {
        File dir = new File(SDPATH);
        File dir2 = new File(SDPATH2);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(file); // 递规的方式删除文件夹
        }
        if (dir2 == null || !dir2.exists() || !dir2.isDirectory())
            return;
        if (dir2.listFiles() != null) {

            for (File file : dir2.listFiles()) {
                if (file.isFile())
                    file.delete(); // 删除所有文件
                else if (file.isDirectory())
                    deleteDir(file); // 递规的方式删除文件夹
            }
        }

        dir.delete();// 删除目录本身
        dir2.delete();
    }

    public static void saveStickerBitmap(Bitmap bm, String path, String name) {
        // Log.e("", "保存图片");
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(path, name + ".png");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            // Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return buffer;
    }

    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File creatFile(String string) {

        File dir = new File(SDPATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(SDPATH, string + ".JPEG");
        if (f.exists()) {
            f.delete();
        }
        return f;
    }


}
