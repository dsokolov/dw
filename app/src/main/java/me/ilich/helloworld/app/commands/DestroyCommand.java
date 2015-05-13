package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Item;
import me.ilich.helloworld.app.Room;

import java.util.List;

public class DestroyCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            Room room = controller.getCurrentRoom();
            Item aItem = room.getItems().stream().filter(item -> item.getTitle().equalsIgnoreCase(params[0])).findFirst().orElse(null);
            if (aItem == null) {
                System.out.println("Не так легко уничтожить то, чего нет.");
            } else {
                System.out.println("Не скрывая своего удовольствия, вы уничтожаете " + aItem.getTitle() + ".");
                room.getItems().remove(aItem);
            }
        };

        cases.add(new Case("destroy ([\\w\\s]*)", oneParam));
    }

}
