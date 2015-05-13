package tgn.rkvy.deep.entities;

public abstract class Entity {

    private final String[] aliases;
    private final String title;
    private final String details;

    public Entity(String[] aliases) {
        this.aliases = aliases;
        this.title = null;
        this.details = null;
    }

    public Entity(String[] aliases, String title) {
        this.aliases = aliases;
        this.title = title;
        this.details = null;
    }

    public Entity(String[] aliases, String title, String details) {
        this.aliases = aliases;
        this.title = title;
        this.details = details;
    }

    public Alias getSuitableAlias(String alias) {
        Alias result = null;
        for (String currentAlias : aliases) {
            if (currentAlias.toLowerCase().contains(alias.toLowerCase())) {
                result = new Alias(currentAlias, this);
                break;
            }
        }
        return result;
    }

    protected String[] getAliases() {
        return aliases;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public static class Alias {

        private final String alias;
        private final Entity entity;

        public Alias(String alias, Entity entity) {
            this.alias = alias;
            this.entity = entity;
        }

        public Entity getEntity() {
            return entity;
        }

        public String getAliasText() {
            return alias;
        }

    }

}
