package me.ilich.dw.commands;


public class ExitCommand extends Command {

    private String[] positive;

    public ExitCommand(String[] aliases, String actionText, String[] positive) {
        super(aliases, actionText);
        this.positive = positive;
    }

    @Override
    protected void onExecute(Controller controller, String[] params) {
        String in = controller.in();
        boolean exit = false;
        for (String s : positive) {
            if (s.equalsIgnoreCase(in)) {
                exit = true;
            }
        }
        if (exit) {
            controller.stop();
        }
    }

}
