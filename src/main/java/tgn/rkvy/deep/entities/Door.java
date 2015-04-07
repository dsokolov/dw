package tgn.rkvy.deep.entities;


import java.util.List;

public class Door extends CommandableEntity implements Sceneable {

    private final Point sourcePoint;
    private final Point destinationPoint;
    private final String description;
    private final String[] commandIds;
    private String tag;

    public Door(String[] aliases, Point sourcePoint, Point destinationPoint, String description, String[] commandIds, List<CommandPattern> commandPatterns) {
        super(aliases, description, commandPatterns);
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
        this.description = description;
        this.commandIds = commandIds;
    }

    public Point getSourcePoint() {
        return sourcePoint;
    }

    public Point getDestinationPoint() {
        return destinationPoint;
    }

    @Override
    public void processScene(Scene scene) {
        scene.addDoor(description);
    }

    public String getTag() {
        return tag;
    }

    public Door copyWithTag(String tag) {
        Door door = new Door(getAliases(), sourcePoint, destinationPoint, description, commandIds, getCommandPatterns());
        door.tag = tag;
        return door;
    }

}
