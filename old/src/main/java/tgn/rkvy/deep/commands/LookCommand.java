package tgn.rkvy.deep.commands;


import tgn.rkvy.deep.actions.Action;
import tgn.rkvy.deep.entities.Entity;

import java.util.List;

public class LookCommand extends Command {

    private final String lookAround;
    private final String lookAroundNoDetails;
    private final String lookAtItem;
    private final String lookAtItemNoDetails;
    private final String failText;

    public LookCommand(String id, String[] aliases, Action defaultAction, String lookAround, String lookAroundNoDetails, String lookAtItem, String lookAtItemNoDetails, String failtText) {
        super(id, aliases, defaultAction);
        this.lookAround = lookAround;
        this.lookAroundNoDetails = lookAroundNoDetails;
        this.lookAtItem = lookAtItem;
        this.lookAtItemNoDetails = lookAtItemNoDetails;
        this.failText = failtText;
    }

    @Override
    protected void onExecute(Controller controller) {
        controller.getIO().outln(lookAround);
        controller.renderCurrentSceneLong(lookAroundNoDetails);
    }

    @Override
    protected void onExecute(Controller controller, Entity.Alias param) {
        controller.getIO().outln(String.format(lookAtItem, param.getAliasText()));
        String text = param.getEntity().getDetails();
        if (text == null || text.isEmpty()) {
            text = lookAtItemNoDetails;
        }
        controller.getIO().outln(text);
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
