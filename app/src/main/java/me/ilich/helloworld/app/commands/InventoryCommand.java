package me.ilich.helloworld.app.commands;

import java.util.List;

public class InventoryCommand extends Command {

    public InventoryCommand() {
        super("ИНВЕНТАРЬ");
    }

    @Override
    protected void onPreparePatterns(List<Case> cases) {
        Action.OnExecute onExecute = (controller, params) -> {
            int count = controller.getInventory().size();
            if (count == 0) {
                System.out.println("У вас ничего нет.");
            } else {
                System.out.println(String.format("У вас %s предметов:", count));
                controller.getInventory().forEach(item -> System.out.println(item.getTitle()));
            }
        };
        cases.add(new Case("inventory", onExecute));
        cases.add(new Case("i", onExecute));
        cases.add(new Case("инвентарь", onExecute));
        cases.add(new Case("и", onExecute));
    }
}
