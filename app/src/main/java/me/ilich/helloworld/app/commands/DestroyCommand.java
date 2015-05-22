package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.Room;
import me.ilich.helloworld.app.entities.primitives.Titlelable;
import me.ilich.helloworld.app.utils.MultiEntitiesUtils;
import me.ilich.helloworld.app.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DestroyCommand extends Command {

    public DestroyCommand() {
        super("УНИЧТОЖИТЬ", "Уничтожить предмет, если это возможно.");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            Room room = controller.getCurrentRoom();
            List<Entity> entities = controller.getCurrentRoomEntities(Titlelable.class).stream().filter(item -> TitleUtils.isSuitable(item, param)).collect(Collectors.toCollection(ArrayList::new));
            MultiEntitiesUtils.process(controller, param, entities, new MultiEntitiesUtils.GroundProcessor() {
                @Override
                public void onOne(Controller controller, String userInput, Entity entity) {
                    entity.setParentId(null);
                    controller.println(String.format("Не скрывая своего удовольствия, вы уничтожаете %s.", TitleUtils.d(entity)));
                }
            });
        };
        cases.add(new Case("destroy ([\\w\\s]*)", oneParam));
        cases.add(new Case("уничтожить ([\\w\\s]*)", oneParam));

        Action.OnExecute noParam = (controller, params) -> controller.println("Уничтожить что?");
        cases.add(new Case("destroy", noParam));
        cases.add(new Case("уничтожить", noParam));
    }

}
