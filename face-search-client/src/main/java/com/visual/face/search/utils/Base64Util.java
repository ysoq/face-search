package com.visual.face.search.utils;

import java.io.*;
import org.apache.commons.codec.binary.Base64;

public class Base64Util {

    public static String encode(byte[] binaryData) {
        byte[] bytes = Base64.encodeBase64(binaryData);
        return new String(bytes);
    }

    public static String encode(InputStream in) {
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc;
            while ((rc = in.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            return encode(swapStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String encode(String filePath){
        try {
            return encode(new FileInputStream(filePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
