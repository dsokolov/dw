package me.ilich.dw.commands;

public class Controller {

    private boolean working = true;

    public boolean isWorking() {
        return working;
    }

    public void out(String lookAroundCommand) {
        //TODO
    }

    void stop() {
        working = false;
    }
}
