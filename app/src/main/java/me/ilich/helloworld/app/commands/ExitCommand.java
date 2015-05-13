package me.ilich.helloworld.app.commands;

import java.util.List;

public class ExitCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute onExecute = (controller, params) -> controller.stop();
        cases.add(new Case("exit", onExecute));
        cases.add(new Case("выход", onExecute));
    }

}
