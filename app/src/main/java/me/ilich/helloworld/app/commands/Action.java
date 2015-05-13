package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;

/**
 * Created by disokolov on 12.05.15.
 */
public class Action {

    private final String[] params;
    private final OnExecute onExecute;

    public Action(String[] params, OnExecute onExecute) {
        this.params = params;
        this.onExecute = onExecute;
    }

    public void execute(Controller controller) {
        onExecute.onExecute(controller, params);
    }

    public interface OnExecute {
        void onExecute(Controller controller, String[] params);
    }

}
