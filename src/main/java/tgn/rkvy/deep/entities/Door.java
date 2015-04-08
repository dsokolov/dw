package tgn.rkvy.deep.entities;


import java.util.List;

public class Door extends CommandableEntity implements Sceneable {

    private final Point sourcePoint;
    private final Point destinationPoint;
    private String tag;

    public Door(String[] aliases, Point sourcePoint, Point destinationPoint, String title, String details, List<CommandPattern> commandPatterns) {
        super(aliases, title, details, commandPatterns);
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
    }

    public Point getSourcePoint() {
        return sourcePoint;
    }

    public Point getDestinationPoint() {
        return destinationPoint;
    }

    @Override
    public void processScene(Scene scene) {
        scene.addDoor(getTitle());
    }

    public String getTag() {
        return tag;
    }

    public Door copyWithTag(String tag) {
        Door door = new Door(getAliases(), sourcePoint, destinationPoint, getTitle(), getDetails(), getCommandPatterns());
        door.tag = tag;
        return door;
    }

}
