package me.ilich.dw;

import me.ilich.dw.commands.Controller;

public class Game {

    private final Controller controller = new Controller();

    public boolean isWorking() {
        return controller.isWorking();
    }

    public void nextStep() {
        controller.loadSceneIfNeed();
        controller.processCommand();
    }
}
