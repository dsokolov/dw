package me.ilich.helloworld.app.commands;

import java.util.List;

public class WalkCommand extends Command {

    public WalkCommand() {
        super("ИДТИ", "Перемещение в заданном направлении.");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute noParams = (controller, params) -> {
            controller.println("Идти куда?");
        };
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            Action.OnExecute onExecute = DefaultMoveCommand.getOnExecute(param);
            if (onExecute == null) {
                controller.println(String.format("Что такое %s?", param));
            } else {
                onExecute.onExecute(controller, params);
            }
        };
        cases.add(new Case("идти на ([\\w\\s]*)", oneParam));
        cases.add(new Case("идти ([\\w\\s]*)", oneParam));
        cases.add(new Case("идти", noParams));

    }
}
