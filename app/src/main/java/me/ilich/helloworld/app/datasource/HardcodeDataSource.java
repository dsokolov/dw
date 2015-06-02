package me.ilich.helloworld.app.datasource;

import me.ilich.helloworld.app.entities.*;
import me.ilich.helloworld.app.entities.primitives.Primitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class HardcodeDataSource implements DataSource {

    private static final List<Entity> entities = new ArrayList<>();

    @Override
    public List<Entity> getEntities(UUID id) {
        return entities.stream().filter(entity -> Objects.equals(id, entity.getId())).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Entity> getEntities(Class<? extends Primitive>... primitives) {
        return entities.stream().filter(entity -> {
            boolean result = true;
            for (Class<? extends Primitive> primitive : primitives) {
                if (!primitive.isInstance(entity)) {
                    result = false;
                    break;
                }
            }
            return result;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Entity> getChildEntities(UUID parentId, Class<? extends Primitive>... primitives) {
        return entities.stream().filter(entity -> {
            boolean b = true;
            for (Class<? extends Primitive> primitive : primitives) {
                if (!primitive.isInstance(entity)) {
                    b = false;
                    break;
                }
            }
            if (b) {
                b = Objects.equals(parentId, entity.getParentId());
            }
            return b;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void add(Entity entity) {
        if (entity != null) {
            entities.add(entity);
        }
    }
}
