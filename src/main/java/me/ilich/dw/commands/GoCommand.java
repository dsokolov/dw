package me.ilich.dw.commands;

import me.ilich.dw.entities.Door;
import me.ilich.dw.entities.Entity;
import me.ilich.dw.entities.Teleport;

import java.util.List;

public class GoCommand extends Command {

    private final String noParams;
    private final String manyParams;
    private final String invalidType;

    public GoCommand(String id, String[] aliases, String noParams, String manyParams, String invalidType) {
        super(id, aliases);
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
            controller.setCurrentTag(tag);
        } else if (entity instanceof Teleport) {
            Teleport teleport = (Teleport) entity;
            Teleport.CommandPattern commandPattern = teleport.getSuitablePattern(this);
            if (commandPattern == null) {
                controller.getIO().outln(String.format(invalidType, param.getAliasText()));
            } else {
                controller.getIO().outln(commandPattern.getActionText());
                String tag = controller.randomTag();
                controller.setCurrentTag(tag);
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
