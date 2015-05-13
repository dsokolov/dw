package me.ilich.helloworld.app.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public abstract class Command {

    private List<Action> actions = new ArrayList<>();

    public Command() {
        onPreparePatterns(actions);
    }

    protected abstract void onPreparePatterns(List<Action> patterns);

    public List<Action> suitableActions(String input) {
        List<Action> result = new ArrayList<>();
        for (Action action : actions) {
            Matcher matcher = action.getPattern().matcher(input);
            if (matcher.matches()) {
                result.add(action);
            }
        }
        return result;
    }

}
