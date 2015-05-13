package me.ilich.helloworld.app.commands;

import me.ilich.helloworld.app.Controller;

import java.util.regex.Pattern;

/**
 * Created by disokolov on 12.05.15.
 */
public class Action {

    private final Pattern pattern;
    private final OnExecute onExecute;

    public Action(String pattern, OnExecute onExecute) {
        this.pattern = Pattern.compile(pattern);
        this.onExecute = onExecute;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public OnExecute getOnExecute() {
        return onExecute;
    }

    public interface OnExecute {
        void onExecute(Controller controller);
    }

}
