package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Item;
import me.ilich.helloworld.app.Room;

import java.util.List;

public class DropCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            Room room = controller.getCurrentRoom();
            Item aItem = controller.getInventory().stream().filter(item -> item.getTitle().equalsIgnoreCase(params[0])).findFirst().orElse(null);
            if (aItem == null) {
                System.out.println(String.format("У вас нет %s.", params[0]));
            } else {
                System.out.println(String.format("Вы выбросили %s.", aItem.getTitle()));
                controller.getInventory().remove(aItem);
                room.getItems().add(aItem);
            }
        };

        cases.add(new Case("drop ([\\w\\s]*)", oneParam));
    }

}
