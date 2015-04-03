package tgn.rkvy.deep;

import tgn.rkvy.deep.commands.Controller;

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
