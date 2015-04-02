package me.ilich.dw.commands;

import me.ilich.dw.entities.Door;
import me.ilich.dw.entities.Entity;

public class WalkCommand extends Command {

    private final String noParams;
    private final String manyParams;
    private final String invalidType;

    public WalkCommand(String[] aliases, String noParams, String manyParams, String invalidType) {
        super(aliases);
        this.noParams = noParams;
        this.manyParams = manyParams;
        this.invalidType = invalidType;
    }

    @Override
    protected void onExecute(Controller controller, Alias[] params) {
        switch (params.length) {
            case 0:
                controller.getIO().out(noParams);
                break;
            case 1:
                Entity entity = params[0].getEntity();
                if (entity instanceof Door) {
                    Door door = (Door) entity;
                    String tag = door.getTag();
                    controller.setCurrentTag(tag);
                } else {
                    controller.getIO().out(invalidType);
                }
                break;
            default:
                controller.getIO().out(manyParams);
        }
    }

}
