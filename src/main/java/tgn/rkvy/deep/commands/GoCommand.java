package tgn.rkvy.deep.commands;

import tgn.rkvy.deep.actions.Action;
import tgn.rkvy.deep.entities.Door;
import tgn.rkvy.deep.entities.Entity;
import tgn.rkvy.deep.entities.Teleport;

import java.util.List;

public class GoCommand extends Command {

    private final String noParams;
    private final String manyParams;
    private final String invalidType;

    public GoCommand(String id, String[] aliases, Action defaultAction, String noParams, String manyParams, String invalidType) {
        super(id, aliases, defaultAction);
        this.noParams = noParams;
        this.manyParams = manyParams;
        this.invalidType = invalidType;
    }

    @Override
    protected void onExecute(Controller controller) {
        controller.getIO().out(noParams);
    }

    @Override
    protected void onExecute(Controller controller, Alias param) {
        Entity entity = param.getEntity();
        if (entity instanceof Door) {
            Door door = (Door) entity;
            String tag = door.getTag();
            //controller.setCurrentTag(tag);
            controller.setIds(door.getDestinationPoint());
        } else if (entity instanceof Teleport) {
            Teleport teleport = (Teleport) entity;
            Teleport.CommandPattern commandPattern = teleport.getSuitablePattern(this);
            if (commandPattern == null) {
                controller.getIO().outln(String.format(invalidType, param.getAliasText()));
            } else {
                controller.getIO().outln(commandPattern.getActionText());
                String tag = controller.randomTag();
                //controller.setCurrentTag(tag);
                //TODO телепорт
            }
        } else {
            controller.getIO().outln(String.format(invalidType, param.getAliasText()));
        }
    }

    @Override
    protected void onExecute(Controller controller, Alias param1, Alias param2) {
        controller.getIO().out(manyParams);
    }

    @Override
    protected void onExecute(Controller controller, List<Alias> params) {
        controller.getIO().out(manyParams);
    }

}
