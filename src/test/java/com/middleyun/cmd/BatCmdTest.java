package com.middleyun.cmd;

import org.junit.Test;

import java.io.*;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/26
 * @version 1.0
 */
public class BatCmdTest {

    @Test
    public void testBat() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process exec = runtime.exec("cmd /c start d://HW//say.bat");
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(exec.getOutputStream()));
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
//        String line;
//        while ((line = bufferedReader.readLine()) != null) {
//            System.out.println(line);
//        }
    }

}
