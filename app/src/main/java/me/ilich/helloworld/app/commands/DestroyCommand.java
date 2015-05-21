package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Room;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Titlelable;

import java.util.List;

public class DestroyCommand extends Command {

    public DestroyCommand() {
        super("УНИЧТОЖИТЬ", "Уничтожить предмет, если это возможно.");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            Room room = controller.getCurrentRoom();
            List<Entity> entities = controller.getCurrentRoomEntities();
            Entity aItem = entities.stream().filter(item -> {
                final boolean b;
                if (item instanceof Titlelable) {
                    b = ((Titlelable) item).getTitle().equalsIgnoreCase(param);
                } else {
                    b = false;
                }
                return b;
            }).findFirst().orElse(null);
            if (aItem == null) {
                controller.println("Не так легко уничтожить то, чего нет.");
            } else {
                controller.println("Не скрывая своего удовольствия, вы уничтожаете " + ((Titlelable) aItem).getTitle() + ".");
                aItem.setParentId(null);
            }
        };

        cases.add(new Case("destroy ([\\w\\s]*)", oneParam));
    }

}
