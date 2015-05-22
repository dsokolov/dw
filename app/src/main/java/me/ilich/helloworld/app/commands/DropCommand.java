package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Titlelable;
import me.ilich.helloworld.app.utils.MultiEntitiesUtils;
import me.ilich.helloworld.app.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DropCommand extends Command {

    public DropCommand() {
        super("выбросить", "Выбросить предмет себе под ноги.");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute oneParam = (controller, params) -> {
            String param = params[0];
            List<Entity> entities = controller.
                    getInventoryEntities(Titlelable.class).
                    stream().
                    filter(entity -> TitleUtils.isSuitable(entity, param)).
                    collect(Collectors.toCollection(ArrayList::new));
            MultiEntitiesUtils.process(controller, param, entities, new MultiEntitiesUtils.InventoryProcessor() {
                @Override
                public void onOne(Controller controller, String userInput, Entity entity) {
                    entity.setParentId(controller.getCurrentRoom().getId());
                    controller.println(String.format("Вы выбросили %s.", TitleUtils.v(entity)));
                }
            });
        };
        cases.add(new Case("drop ([\\w\\s]*)", oneParam));
        cases.add(new Case("выбросить ([\\w\\s]*)", oneParam));
        cases.add(new Case("вбр ([\\w\\s]*)", oneParam));

        Action.OnExecute noParam = (controller, params) -> {
            controller.println("Выбросить что?");
        };
        cases.add(new Case("выбросить", noParam));
        cases.add(new Case("вбр", noParam));
    }

}
