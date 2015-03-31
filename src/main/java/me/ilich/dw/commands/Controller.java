package me.ilich.dw.commands;

public class Controller {

    private boolean working = true;

    public boolean isWorking() {
        return working;
    }

    public void out(String s) {
        System.out.println(s);
    }

    void stop() {
        working = false;
    }
}
