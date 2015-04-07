package tgn.rkvy.deep.commands;


import tgn.rkvy.deep.actions.Action;
import tgn.rkvy.deep.entities.Entity;

import java.util.List;

public class LookCommand extends Command {

    private final String lookAtItem;
    private final String failText;

    public LookCommand(String id, String[] aliases, Action defaultAction, String lookAtItem, String failtText) {
        super(id, aliases, defaultAction);
        this.lookAtItem = lookAtItem;
        this.failText = failtText;
    }

    @Override
    protected void onExecute(Controller controller) {
        controller.renderCurrentScene();
    }

    @Override
    protected void onExecute(Controller controller, Entity.Alias param) {
        controller.getIO().outln(String.format(lookAtItem, param.getAliasText()));
        controller.getIO().outln(param.getEntity().getLongText());
    }

    @Override
    protected void onExecute(Controller controller, Entity.Alias param1, Entity.Alias param2) {
        controller.getIO().out(failText);
    }

    @Override
    protected void onExecute(Controller controller, List<Entity.Alias> params) {
        controller.getIO().out(failText);
    }

}
