package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Item;
import me.ilich.helloworld.app.entities.Room;

import java.util.List;

public class LookCommand extends Command {

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute noParams =  (controller, params) -> controller.showRoomDescription();
        Action.OnExecute oneParam =  (controller, params) -> {
            Room room = controller.getCurrentRoom();
            Item aItem = room.getItems().stream().filter(item -> item.getTitle().equalsIgnoreCase(params[0])).findFirst().orElse(null);
            if (aItem == null) {
                System.out.println("Вы оглядываетесь вокруг в поисках " + params[0] + ".");
            } else {
                System.out.println("Вы осматриваете " + params[0] + ".");
                System.out.println(aItem.getDescription());
            }
        };

        cases.add(new Case("look", noParams));
        cases.add(new Case("look ([\\w\\s]*)", oneParam));

        cases.add(new Case("см", noParams));
        cases.add(new Case("см ([\\w\\s]*)", oneParam));
    }

}
