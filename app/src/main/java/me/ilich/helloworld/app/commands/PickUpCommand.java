package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Item;
import me.ilich.helloworld.app.entities.Room;

import java.util.List;

public class PickUpCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            Room room = controller.getCurrentRoom();
            Item aItem = room.getItems().stream().filter(item -> item.getTitle().equalsIgnoreCase(params[0])).findFirst().orElse(null);
            if (aItem == null) {
                System.out.println(String.format("Здесь нет %s.", params[0]));
            } else {
                aItem.onMove(room.getItems(), controller.getInventory());
                System.out.println(String.format("Вы взяли %s.", aItem.getTitle()));
            }
        };

        cases.add(new Case("pick up ([\\w\\s]*)", oneParam));
        cases.add(new Case("вз ([\\w\\s]*)", oneParam));
    }

}
