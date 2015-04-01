package me.ilich.dw.commands;

public class EmptyCommand extends Command {

    public EmptyCommand() {
        super(new String[]{""}, "");
    }

    @Override
    protected void onExecute(Controller controller, String[] params) {

    }
}
