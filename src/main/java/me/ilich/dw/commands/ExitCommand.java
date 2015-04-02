package me.ilich.dw.commands;


public class ExitCommand extends Command {

    private final String promt;
    private final String[] positive;
    private final String[] negative;
    private final String unknown;

    public ExitCommand(String[] aliases, String promt, String[] positive, String[] negative, String unknown) {
        super(aliases);
        this.promt = promt;
        this.positive = positive;
        this.negative = negative;
        this.unknown = unknown;
    }

    @Override
    protected void onExecute(Controller controller, Alias[] params) {
        doExit(controller);
    }

    private void doExit(Controller controller) {
        controller.getIO().out(promt);
        boolean found = false;
        int retryTimes = 0;
        while (!found) {
            String in = controller.getIO().in();
            boolean exit = false;
            for (String s : positive) {
                if (s.equalsIgnoreCase(in)) {
                    exit = true;
                    found = true;
                }
            }
            for (String s : negative) {
                if (s.equalsIgnoreCase(in)) {
                    exit = false;
                    found = true;
                }
            }
            if (found) {
                if (exit) {
                    controller.stop();
                    break;
                }
            } else {
                if (retryTimes == 3) {
                    break;
                } else {
                    controller.getIO().out(unknown);
                }
            }
            retryTimes++;
        }
    }

}
