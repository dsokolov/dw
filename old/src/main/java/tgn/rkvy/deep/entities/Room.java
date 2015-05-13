package tgn.rkvy.deep.entities;

public class Room implements Sceneable {

    private final Point point;
    private final String title;
    private final String details;

    public Room(Point point, String title, String details) {
        this.point = point;
        this.title = title;
        this.details = details;
    }

    @Override
    public void processScene(Scene scene) {
        scene.setRoomTitle(title);
        scene.setRoomDetails(details);
    }

    public Point getPoint() {
        return point;
    }
}
