package tgn.rkvy.deep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class IOController {

    public void out(String s) {
        if (s != null && !s.isEmpty()) {
            System.out.print(s);
        }
    }

    public void outln(String s) {
        if (s != null && !s.isEmpty()) {
            System.out.println(s);
        }
    }

    public void outln(List<String> list, String start, String separator, String end) {
        boolean lastIsEmpty = true;
        System.out.print(start);
        for (String s : list) {
            if (!lastIsEmpty) {
                System.out.print(separator);
            }
            if (s != null && !s.isEmpty()) {
                System.out.print(s);
                lastIsEmpty = false;
            } else {
                lastIsEmpty = true;
            }
        }
        System.out.print(end);
        System.out.println();
    }

    public void ln() {
        System.out.println();
    }

    public void debug(String s) {
        if (Constans.DEBUG) {
            System.out.println(String.format("[DEBUG] %s", s));
        }
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
