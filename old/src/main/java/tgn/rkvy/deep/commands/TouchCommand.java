package tgn.rkvy.deep.commands;

import tgn.rkvy.deep.actions.Action;
import tgn.rkvy.deep.entities.CommandableEntity;
import tgn.rkvy.deep.entities.Entity;

import java.util.List;

public class TouchCommand extends Command {

    private final String noParams;
    private final String manyParams;
    private final String invalidType;

    public TouchCommand(String id, String[] aliases, String noParams, String manyParams, String invalidType, Action defaultAction) {
        super(id, aliases, defaultAction);
        this.noParams = noParams;
        this.manyParams = manyParams;
        this.invalidType = invalidType;
    }

    @Override
    protected void onExecute(Controller controller) {
        controller.getIO().outln(noParams);
    }

    @Override
    protected void onExecute(Controller controller, Alias param) {
        Entity entity = param.getEntity();
        if (entity instanceof CommandableEntity) {
            CommandableEntity commandableEntity = (CommandableEntity) entity;
            CommandableEntity.CommandPattern commandPattern = commandableEntity.getSuitablePattern(this);
            if (commandPattern == null) {
                controller.getIO().outln(String.format(invalidType, param.getAliasText()));
            } else {
                controller.getIO().outln(commandPattern.getActionText());
                //TODO может ещё что-то произойдёт?
            }
        } else {
            controller.getIO().outln(String.format(invalidType, param.getAliasText()));
        }
    }

    @Override
    protected void onExecute(Controller controller, Alias param1, Alias param2) {
        controller.getIO().outln(manyParams);
    }

    @Override
    protected void onExecute(Controller controller, List<Alias> params) {
        controller.getIO().outln(manyParams);
    }
}
