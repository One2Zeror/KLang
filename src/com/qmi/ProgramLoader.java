package com.qmi;

import java.io.*;

/**
 * Created by Administrator on 2015/11/13.
 */
public class ProgramLoader {
    public static String load(String path) {
        String program;
        byte[] strBuffer = null;
        File file = new File(path);
        try {
            InputStream in = new FileInputStream(file);
            int flen = (int)file.length();
            strBuffer = new byte[flen];
            in.read(strBuffer, 0, flen);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        program = new String(strBuffer);

        return program;
    }
}
