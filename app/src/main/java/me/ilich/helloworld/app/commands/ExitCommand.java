package me.ilich.helloworld.app.commands;

import java.util.List;

public class ExitCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Action> patterns) {
        Action.OnExecute onExecute = controller -> controller.stop();
        patterns.add(new Action("выход", onExecute));
        patterns.add(new Action("exit", onExecute));
    }
}
