package me.ilich.dw.commands;


public abstract class Command {

    private final Controller controller;
    private final String[] aliases;

    public Command(Controller controller, String[] aliases) {
        this.controller = controller;
        this.aliases = aliases;
    }

    public boolean isSuitable(String s) {
        final String[] inputs = s.split(" ");
        boolean suitable = false;
        for (String alias : aliases) {
            boolean contains = true;
            for (String input : inputs) {
                if (!alias.contains(input)) {
                    contains = false;
                    break;
                }
            }
            if (contains) {
                suitable = true;
                break;
            }
        }
        return suitable;
    }

    public void execute() {
        onExecute(controller);
    }

    protected abstract void onExecute(Controller controller);

}
