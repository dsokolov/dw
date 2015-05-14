package me.ilich.helloworld.app.commands;

import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("ПОМОЩЬ");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute action = (controller, params) -> {
            System.out.println(String.format("Комманды (всего %s)", controller.getCommands().size()));
            controller.getCommands().stream().sorted((o1, o2) -> o1.getTitle().toUpperCase().compareTo(o2.getTitle().toUpperCase())).forEach(command -> {
                if (!command.isHidden()) {
                    System.out.println(command.getTitle().toUpperCase());
                }
            });
        };
        cases.add(new Case("help", action));
        cases.add(new Case("\\?", action));
        cases.add(new Case("помощь", action));
    }

}
