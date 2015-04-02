package me.ilich.dw.commands;


import me.ilich.dw.entities.Entity;

public abstract class Command extends Entity {

    public Command(String[] aliases, String description) {
        super(aliases, description);
    }

    public Command(String[] aliases) {
        super(aliases);
    }

    public void execute(Controller controller, Alias[] params) {
        onExecute(controller, params);
    }

    protected abstract void onExecute(Controller controller, Alias[] params);

}
