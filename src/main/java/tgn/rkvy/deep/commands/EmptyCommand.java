package tgn.rkvy.deep.commands;

import tgn.rkvy.deep.actions.Action;

import java.util.List;

public class EmptyCommand extends Command {

    public EmptyCommand(String id, Action defaultAction) {
        super(id, new String[]{""}, defaultAction);
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
