package me.ilich.dw.entities;

public abstract class Entity {

    private final String[] aliases;
    private final String shortText;
    private final String longText;

    public Entity(String[] aliases) {
        this.aliases = aliases;
        this.shortText = null;
        this.longText = null;
    }

    public Entity(String[] aliases, String shortText) {
        this.aliases = aliases;
        this.shortText = shortText;
        this.longText = null;
    }

    public Entity(String[] aliases, String shortText, String longText) {
        this.aliases = aliases;
        this.shortText = shortText;
        this.longText = longText;
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

    public String getShortText() {
        return shortText;
    }

    public String getLongText() {
        return longText;
    }

    public static class Alias {

        private final String alias;
        private final Entity entity;
        private String description;

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
