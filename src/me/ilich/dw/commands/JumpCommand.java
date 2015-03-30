package me.ilich.dw.commands;


public class JumpCommand extends Command {

    private static final String[] aliases = {
            "прыгать"
    };

    public JumpCommand(Controller controller) {
        super(controller, aliases);
    }

    @Override
    protected void onExecute(Controller controller) {

    }
}
