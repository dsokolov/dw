package me.ilich.helloworld.app.utils;

import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Titlelable;

public class TitleUtils {

    private static String firstUpper(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static boolean isSuitable(Entity entity, String s) {
        return ((Titlelable) entity).isTitleSuitable(s);
    }

    /**
     * кто, что
     *
     * @param entity
     * @return
     */
    public static String i(Entity entity) {
        return ((Titlelable) entity).getTitle(0);
    }

    /**
     * кого, что
     *
     * @param entity
     * @return
     */
    public static String r(Entity entity) {
        return ((Titlelable) entity).getTitle(1);
    }

    /**
     * кому, чему
     *
     * @param entity
     * @return
     */
    public static String d(Entity entity) {
        return ((Titlelable) entity).getTitle(2);
    }

    /**
     * кому, чему
     *
     * @param entity
     * @return
     */
    public static String D(Entity entity) {
        return firstUpper(((Titlelable) entity).getTitle(2));
    }

    /**
     * кого, что
     *
     * @param entity
     * @return
     */
    public static String v(Entity entity) {
        return ((Titlelable) entity).getTitle(3);
    }

    public static String V(Entity entity) {
        return firstUpper(((Titlelable) entity).getTitle(3));
    }

    /**
     * кем, чем
     *
     * @param entity
     * @return
     */
    public static String t(Entity entity) {
        return ((Titlelable) entity).getTitle(4);
    }

    /**
     * о ком, о чём
     *
     * @param entity
     * @return
     */
    public static String p(Entity entity) {
        return ((Titlelable) entity).getTitle(5);
    }

}
