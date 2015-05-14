package me.ilich.helloworld.app.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Command {

    private List<Case> cases = new ArrayList<>();
    private final String title;
    private final boolean hidden;

    public Command(String title) {
        this(title, false);
    }

    public Command(String title, boolean hidden) {
        this.title = title;
        this.hidden = hidden;
        onPreparePatterns(cases);
    }

    protected abstract void onPreparePatterns(List<Case> cases);

    public List<Action> suitableActions(String input) {
        List<Action> result = new ArrayList<>();
        for (Case aCase : cases) {
            Matcher matcher = aCase.getPattern().matcher(input);
            if (matcher.matches()) {
                String[] params = new String[matcher.groupCount()];
                for (int i = 0; i < matcher.groupCount(); i++) {
                    params[i] = matcher.group(i + 1);
                }
                Action action = aCase.createAction(params);
                result.add(action);
                break;
            }
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public boolean isHidden() {
        return hidden;
    }

    public class Case {

        private final Pattern pattern;
        private final Action.OnExecute onExecute;

        public Case(String pattern, Action.OnExecute onExecute) {
            this.pattern = Pattern.compile(pattern, Pattern.UNICODE_CHARACTER_CLASS);
            this.onExecute = onExecute;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public Action createAction(String[] params) {
            return new Action(params, onExecute);
        }

    }

}
