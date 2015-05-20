package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Item;
import me.ilich.helloworld.app.entities.Room;

import java.util.List;

public class DropCommand extends Command {

    public DropCommand() {
        super("выбросить", "Выбросить предмет себе под ноги.");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            Room room = controller.getCurrentRoom();
            Item aItem = controller.getInventory().stream().filter(item -> item.getTitle().equalsIgnoreCase(params[0])).findFirst().orElse(null);
            if (aItem == null) {
                controller.println(String.format("У вас нет %s.", params[0]));
            } else {
                //aItem.onMove(controller.getInventory(), room.getItems());
                controller.println(String.format("Вы выбросили %s.", aItem.getTitle()));
            }
        };

        cases.add(new Case("drop ([\\w\\s]*)", oneParam));
        cases.add(new Case("вбр ([\\w\\s]*)", oneParam));
    }

}
