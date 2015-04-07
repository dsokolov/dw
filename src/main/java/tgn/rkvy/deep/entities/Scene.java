package tgn.rkvy.deep.entities;

import tgn.rkvy.deep.IOController;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private final IOController ioController;
    private String settingTitle;
    private String locationTitle;
    private String roomTitle;
    private String description;
    private List<String> doors = new ArrayList<>();
    private List<String> events = new ArrayList<>();
    private String teleport = "";

    public Scene(IOController ioController) {
        this.ioController = ioController;
    }

    public void render() {
        ioController.ln();
        String header = String.format("%s (%s, %s)", roomTitle, locationTitle, settingTitle);
        ioController.outln(header);
        ioController.outln(description);
        if (events.size() > 0) {
            ioController.outln(events);
        }
        if (doors.size() > 0) {
            ioController.outln(doors);
        }
        if (!teleport.isEmpty()) {
            ioController.outln(teleport);
        }
    }

    public void setSettingTitle(String s) {
        settingTitle = s;
    }

    public void setLocationTitle(String s) {
        locationTitle = s;
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
