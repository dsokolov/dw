package tgn.rkvy.deep.entities;

public class Room implements Sceneable {

    private final Point point;
    private final String shortText;

    public Room(Point point, String shortText) {
        this.point = point;
        this.shortText = shortText;
    }

    @Override
    public void processScene(Scene scene) {
        scene.setRoomTitle(shortText);
    }

    public Point getPoint() {
        return point;
    }
}
