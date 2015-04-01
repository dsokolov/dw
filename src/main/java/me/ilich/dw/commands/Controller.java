package me.ilich.dw.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {

    private boolean working = true;
    private boolean shouldReloadScene = true;

    public boolean isWorking() {
        return working;
    }

    public void out(String s) {
        System.out.println(s);
    }

    public void out() {
        System.out.println();
    }

    void stop() {
        working = false;
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

    public boolean isShouldReloadScene() {
        return shouldReloadScene;
    }

    public void setShouldReloadScene(boolean b) {
        shouldReloadScene = b;
    }

    public String getCurrentTag() {
        return "2682551";
    }

}
