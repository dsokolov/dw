package tgn.rkvy.deep.entities;

import java.util.List;

public class Teleport extends CommandableEntity implements Sceneable {

    private final String settingId;

    public Teleport(String settingId, String[] aliases, String shortText, String longText, List<CommandPattern> commandPatterns) {
        super(aliases, shortText, longText, commandPatterns);
        this.settingId = settingId;
    }

    public String getSettingId() {
        return settingId;
    }

    @Override
    public void processScene(Scene scene) {
        scene.addTeleport(getShortText());
    }

}
