package me.ilich.dw.commands;


import java.security.InvalidParameterException;

public abstract class Command {

    private final String[] aliases;
    private final String actionText;

    public Command(String[] aliases, String actionText) {
        if (aliases == null) {
            throw new NullPointerException("aliases");
        }
        if (aliases.length < 1) {
            throw new InvalidParameterException("aliases array should have one element at last");
        }
        this.aliases = aliases;
        this.actionText = actionText;
    }

    public boolean isSuitable(String command) {
        boolean suitable = false;
        for (String alias : aliases) {
            if (!alias.toLowerCase().contains(command.toLowerCase())) {
                suitable = false;
                break;
            }
            suitable = true;
            break;
        }
        return suitable;
    }

    public void execute(Controller controller) {
        controller.out("> " + aliases[0]);
        controller.out(actionText);
        onExecute(controller);
    }

    protected abstract void onExecute(Controller controller);

}
