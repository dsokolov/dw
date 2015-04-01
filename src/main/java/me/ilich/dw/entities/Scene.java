package me.ilich.dw.entities;

import me.ilich.dw.commands.Controller;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final Controller controller;
    private String settingTitle;
    private String roomTitle;
    private String description;
    private List<String> doors = new ArrayList<>();
    private List<String> events = new ArrayList<>();

    public Scene(Controller controller) {
        this.controller = controller;
    }

    public void render() {
        controller.out();
        controller.out(roomTitle + " (" + settingTitle + ")");
        controller.out(description);
        for (String event : events) {
            controller.out(event);
        }
        for (String door : doors) {
            controller.out(door);
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

}
