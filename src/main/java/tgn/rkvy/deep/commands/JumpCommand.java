package tgn.rkvy.deep.commands;


import tgn.rkvy.deep.actions.Action;

import java.util.List;

public class JumpCommand extends Command {

    public JumpCommand(String id, String[] aliases, Action defaultAction) {
        super(id, aliases, defaultAction);
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
