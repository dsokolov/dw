package me.ilich.dw.commands;

import java.util.List;

public class EmptyCommand extends Command {

    public EmptyCommand(String id) {
        super(id, new String[]{""});
    }

    @Override
    protected void onExecute(Controller controller) {

    }

    @Override
    protected void onExecute(Controller controller, Alias param) {

    }

    @Override
    protected void onExecute(Controller controller, Alias param1, Alias param2) {

    }

    @Override
    protected void onExecute(Controller controller, List<Alias> params) {

    }

}
