package tgn.rkvy.deep.entities;

import java.util.List;

public class Teleport extends CommandableEntity implements Sceneable {

    private final Point point;

    public Teleport(Point point, String[] aliases, String shortText, String longText, List<CommandPattern> commandPatterns) {
        super(aliases, shortText, longText, commandPatterns);
        this.point = point;
    }

    @Override
    public void processScene(Scene scene) {
        scene.addTeleport(getTitle());
    }

    public Teleport copy() {
        return new Teleport(point, getAliases(), getTitle(), getDetails(), getCommandPatterns());
    }

    public Point getPoint() {
        return point;
    }
}
