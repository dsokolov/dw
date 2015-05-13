package tgn.rkvy.deep.commands;


import tgn.rkvy.deep.actions.Action;

import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand(String id, String[] aliases, Action defaultAction) {
        super(id, aliases, defaultAction);
    }

    @Override
    protected void onExecute(Controller controller) {
        controller.getIO().out("TODO help"); //TODO
    }

    @Override
    protected void onExecute(Controller controller, Alias param) {
        controller.getIO().out("TODO help"); //TODO
    }

    @Override
    protected void onExecute(Controller controller, Alias param1, Alias param2) {
        //TODO fail
    }

    @Override
    protected void onExecute(Controller controller, List<Alias> params) {
        //TODO fail
    }

}
