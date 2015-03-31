package me.ilich.dw.commands;


public class LookAroundCommand extends Command {

    private static final String[] aliases = {
            "осмотреться"
    };

    public LookAroundCommand(Controller controller) {
        super(controller, aliases);
    }

    @Override
    protected void onExecute(Controller controller) {
        controller.out("LookAroundCommand");
    }
}
