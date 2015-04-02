package me.ilich.dw.entities;


public class Door extends Entity implements Sceneable {

    private final String settingId;
    private final String sourceRoomId;
    private final String destinationRoomId;
    private final String description;
    private final String[] commandIds;
    private String tag;

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

    public String getTag() {
        return tag;
    }

    public Door copy(String tag) {
        Door door = new Door(getAliases(), settingId, sourceRoomId, destinationRoomId, description, commandIds);
        door.tag = tag;
        return door;
    }

}
