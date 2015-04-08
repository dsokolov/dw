package tgn.rkvy.deep.entities;

public class Event implements Sceneable {

    private final Point point;
    private final String id;
    private final String text;

    public Event(Point point, String id, String text) {
        this.point = point;
        this.id = id;
        this.text = text;
    }

    public Point getPoint() {
        return point;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public void processScene(Scene scene) {
        scene.addEvent(text);
    }

    public Event copy() {
        return new Event(point, id, text);
    }
}
