package com.example.codingboy.zhihupaper.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by codingBoy on 16/8/2.
 */
public class ParseInputStream
{
    public static String getStringFromStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        int len=0;
        byte[] b=new byte[1024];
        while ((len=inputStream.read(b))!=-1)
        {
            bos.write(b,0,len);
        }
        return bos.toString();

    }
}
