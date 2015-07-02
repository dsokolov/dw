package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.entities.primitives.Primitive;

import java.util.ArrayList;
import java.util.List;

public class Trigger<I extends Primitive> extends Entity {

    private final List<I> list = new ArrayList<>();

    public void add(I primitive) {
        list.add(primitive);
    }

    protected List<I> getList() {
        return list;
    }

}
