package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Titlelable;
import me.ilich.helloworld.app.utils.TitleUtils;

import java.util.List;

public class InventoryCommand extends Command {

    public InventoryCommand() {
        super("ИНВЕНТАРЬ", "Отображает содержимое инвентаря.");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute onExecute = (controller, params) -> {
            List<Entity> entities = controller.
                    getInventoryEntities(Titlelable.class);
            switch (entities.size()) {
                case 0:
                    controller.println("У вас ничего нет.");
                    break;
                default:
                    controller.println("Вы несёте с собой:");
                    entities.forEach(item -> controller.println(String.format("\t%s", TitleUtils.v(item))));
            }
        };
        cases.add(new Case("inventory", onExecute));
        cases.add(new Case("i", onExecute));
        cases.add(new Case("инвентарь", onExecute));
        cases.add(new Case("и", onExecute));
    }

}
