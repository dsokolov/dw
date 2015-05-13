package me.ilich.helloworld.app.commands;

import java.util.List;

public class HelpCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute action = (controller, params) -> {
            System.out.println("здесь справка");
        };
        cases.add(new Case("help", action));
        cases.add(new Case("\\?", action));
        cases.add(new Case("помощь", action));
    }

}
