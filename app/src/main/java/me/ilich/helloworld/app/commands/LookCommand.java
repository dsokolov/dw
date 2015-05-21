package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LookCommand extends Command {

    public LookCommand() {
        super("смотреть", "Осмотреть предмет");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute noParams = (controller, params) -> controller.showRoomDescription();
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            List<Entity> roomEntities = controller.getCurrentRoomEntities(Lookable.class, Titlelable.class);
            List<Entity> lookables = roomEntities.stream().filter(entity -> Objects.equals(((Titlelable) entity).getTitle(), param)).collect(Collectors.toCollection(ArrayList::new));
            if (lookables.size() == 0) {
                controller.println(String.format("Вы оглядываетесь вокруг в поисках предмета '%s'.", param));
            } else if (lookables.size() == 1) {
                Entity entity = lookables.get(0);
                controller.println(String.format("Вы смотрите на %s.", ((Titlelable) entity).getTitle()));
                ((Lookable) entity).onLook(controller);
                if (entity instanceof Pickable) {
                    controller.println(String.format("%s можно взять с собой.", ((Titlelable) entity).getTitle()));
                }
                if (entity instanceof Containable) {
                    controller.println(String.format("В %s можно что-нибудь положить.", ((Titlelable) entity).getTitle()));
                    List<Entity> containItems = controller.getChildEntities(entity.getId(), Titlelable.class);
                    switch (containItems.size()) {
                        case 0:
                            controller.println(String.format("Сейчас внутри %s пусто.", ((Titlelable) entity).getTitle()));
                            break;
                        default:
                            controller.println(String.format("Сейчас %s содержит в себе:", ((Titlelable) entity).getTitle()));
                            containItems.forEach(entity1 -> controller.println(String.format("\t%s", ((Titlelable) entity1).getTitle())));
                    }
                }
            } else {
                controller.println("много такого");
            }
        };

        cases.add(new Case("look", noParams));
        cases.add(new Case("look ([\\w\\s]*)", oneParam));

        cases.add(new Case("см", noParams));
        cases.add(new Case("см ([\\w\\s]*)", oneParam));
    }

}
