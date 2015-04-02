package me.ilich.dw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOController {

    public void out(String s) {
        System.out.println(s);
    }

    public void out() {
        System.out.println();
    }

    public String in() {
        String s = null;
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            s = bufferRead.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

}
