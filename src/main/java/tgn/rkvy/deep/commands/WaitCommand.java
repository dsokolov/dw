package tgn.rkvy.deep.commands;

import tgn.rkvy.deep.actions.Action;

import java.util.List;

public class WaitCommand extends Command {

    public WaitCommand(String id, String[] aliases, String description, Action defaultAction) {
        super(id, aliases, description, defaultAction);
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
