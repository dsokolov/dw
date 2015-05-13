package me.ilich.helloworld.app.commands;

import java.util.List;

public class HelpCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Action> patterns) {
        Action.OnExecute action = controller -> System.out.println("здесь справка");
        patterns.add(new Action("помощь", action));
        patterns.add(new Action("help", action));
        patterns.add(new Action("\\?", action));
    }

}
