package me.ilich.dw.entities;

public abstract class Entity {

    private final String[] aliases;
    private final String description;

    public Entity(String[] aliases, String description) {
        this.aliases = aliases;
        this.description = description;
    }

    public Entity(String[] aliases) {
        this.aliases = aliases;
        this.description = null;
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

    public String getDescription() {
        return description;
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
