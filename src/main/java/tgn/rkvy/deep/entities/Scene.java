package tgn.rkvy.deep.entities;

import tgn.rkvy.deep.commands.Controller;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final Controller controller;
    private String settingTitle;
    private String roomTitle;
    private String description;
    private List<String> doors = new ArrayList<>();
    private List<String> events = new ArrayList<>();
    private String teleport = "";

    public Scene(Controller controller) {
        this.controller = controller;
    }

    public void render() {
        controller.getIO().ln();
        String header = String.format("%s (%s)", roomTitle, settingTitle);
        controller.getIO().outln(header);
        controller.getIO().outln(description);
        if (events.size() > 0) {
            controller.getIO().outln(events);
        }
        if (doors.size() > 0) {
            controller.getIO().outln(doors);
        }
        if (!teleport.isEmpty()) {
            controller.getIO().outln(teleport);
        }
    }

    public void setSettingTitle(String s) {
        settingTitle = s;
    }

    public void setRoomTitle(String s) {
        roomTitle = s;
    }

    public void setDescription(String s) {
        description = s;
    }

    public void addDoor(String description) {
        doors.add(description);
    }

    public void addEvent(String event) {
        events.add(event);
    }

    public void addTeleport(String description) {
        this.teleport = description;
    }

}
