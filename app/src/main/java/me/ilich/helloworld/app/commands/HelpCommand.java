package me.ilich.helloworld.app.commands;

import java.util.List;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("ПОМОЩЬ", "Отображает справку.");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            Command command = controller.getCommands().stream().filter(cmd -> cmd.getTitle().equalsIgnoreCase(param)).findFirst().orElse(null);
            if (command == null) {
                System.out.println(String.format("Нет такой комманды %s.", param));
            } else {
                command.showHelp();
            }
        };
        cases.add(new Case("help ([\\w\\s]*)", oneParam));
        cases.add(new Case("\\? ([\\w\\s]*)", oneParam));
        cases.add(new Case("помощь ([\\w\\s]*)", oneParam));
        cases.add(new Case("справка ([\\w\\s]*)", oneParam));

        Action.OnExecute noParams = (controller, params) -> {
            System.out.println("Основные комманды:");
            controller.getCommands().stream().sorted((o1, o2) -> o1.getTitle().toUpperCase().compareTo(o2.getTitle().toUpperCase())).forEach(command -> {
                if (!command.isHidden()) {
                    System.out.println("\t" + command.getTitle().toUpperCase());
                }
            });
            System.out.println("Регистр значения не имеет.");
            System.out.println("Для справки по конкретной комманде наберите 'СПРАВКА <имя_комманды>'.");
        };

        cases.add(new Case("help", noParams));
        cases.add(new Case("\\?", noParams));
        cases.add(new Case("помощь", noParams));
        cases.add(new Case("справка", noParams));
    }

}
