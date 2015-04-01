package me.ilich.dw.entities;


public class Door implements Sceneable {

    private final String settingId;
    private final String sourceRoomId;
    private final String destinationRoomId;
    private final String description;
    private final String[] commandIds;
    private final String[] aliases;

    public Door(String settingId, String sourceRoomId, String destinationRoomId, String description, String[] commandIds, String[] aliases) {
        this.settingId = settingId;
        this.sourceRoomId = sourceRoomId;
        this.destinationRoomId = destinationRoomId;
        this.description = description;
        this.commandIds = commandIds;
        this.aliases = aliases;
    }

    public String getSettingId() {
        return settingId;
    }

    public String getSourceRoomId() {
        return sourceRoomId;
    }

    public String getDestinationRoomId() {
        return destinationRoomId;
    }

    @Override
    public void processScene(Scene scene) {
        scene.addDoor(description);
    }

}
