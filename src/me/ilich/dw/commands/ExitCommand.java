package me.ilich.dw.commands;


public class ExitCommand extends Command {

    private static final String[] aliases = {
            "exit",
            "quit",
            "выход"
    };

    public ExitCommand(Controller controller) {
        super(controller, aliases);
    }

    @Override
    protected void onExecute(Controller controller) {
        controller.stop();
    }
}
