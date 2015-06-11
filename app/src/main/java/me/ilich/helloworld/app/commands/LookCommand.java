package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Containable;
import me.ilich.helloworld.app.entities.primitives.Lookable;
import me.ilich.helloworld.app.entities.primitives.Pickable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;
import me.ilich.helloworld.app.utils.MultiEntitiesUtils;
import me.ilich.helloworld.app.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LookCommand extends Command {

    public LookCommand() {
        super("смотреть", "Осмотреть предмет");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            List<Entity> roomEntities = controller.
                    getCurrentRoomEntities(Lookable.class, Titlelable.class).
                    stream().
                    filter(entity -> TitleUtils.isSuitable(entity, param)).
                    collect(Collectors.toCollection(ArrayList::new));
            MultiEntitiesUtils.process(controller, param, roomEntities, new MultiEntitiesUtils.GroundProcessor() {
                @Override
                public void onOne(Controller controller, String userInput, Entity entity) {
/*                    controller.println(String.format("Вы смотрите на %s.", TitleUtils.v(entity)));*/
                    ((Lookable) entity).onLook(controller);
/*                    if (entity instanceof Pickable) {
                        controller.println(String.format("%s можно взять с собой.", TitleUtils.V(entity)));
                    }
                    if (entity instanceof Containable) {
                        controller.println(String.format("В %s можно что-нибудь положить.", TitleUtils.v(entity)));
                        List<Entity> containItems = controller.getChildEntities(entity.getId(), Titlelable.class);
                        switch (containItems.size()) {
                            case 0:
                                controller.println(String.format("Сейчас внутри %s пусто.", TitleUtils.r(entity)));
                                break;
                            default:
                                controller.println(String.format("Сейчас %s содержит в себе:", TitleUtils.i(entity)));
                                containItems.forEach(entity1 -> controller.println(String.format("\t%s", TitleUtils.v(entity1))));
                        }
                    }*/
                }
            });
        };


        cases.add(new Case("look ([\\w\\s]*)", oneParam));
        cases.add(new Case("см ([\\w\\s]*)", oneParam));

        Action.OnExecute noParams = (controller, params) -> controller.showRoomDescription();
        cases.add(new Case("look", noParams));
        cases.add(new Case("см", noParams));
    }

}
