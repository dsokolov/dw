package me.ilich.dw.entities;


public class Door extends Entity implements Sceneable {

    private final String settingId;
    private final String sourceRoomId;
    private final String destinationRoomId;
    private final String description;
    private final String[] commandIds;

    public Door(String[] aliases, String settingId, String sourceRoomId, String destinationRoomId, String description, String[] commandIds) {
        super(aliases, description);
        this.settingId = settingId;
        this.sourceRoomId = sourceRoomId;
        this.destinationRoomId = destinationRoomId;
        this.description = description;
        this.commandIds = commandIds;
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
