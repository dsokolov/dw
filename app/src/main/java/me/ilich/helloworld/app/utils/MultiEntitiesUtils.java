package me.ilich.helloworld.app.utils;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Entity;

import java.util.List;

public class MultiEntitiesUtils {

    public static void process(Controller controller, String userInput, List<Entity> list, Processor processor) {
        switch (list.size()) {
            case 0:
                processor.onNo(controller, userInput);
                break;
            case 1:
                processor.onOne(controller, userInput, list.get(0));
                break;
            case 2:
                processor.onTwo(controller, userInput, list.get(0), list.get(1));
                break;
            default:
                processor.onMany(controller, userInput);
        }
    }

    public interface Processor {

        void onNo(Controller controller, String userInput);

        void onOne(Controller controller, String userInput, Entity entity);

        void onTwo(Controller controller, String userInput, Entity entity1, Entity entity2);

        void onMany(Controller controller, String userInput);
    }

    public static abstract class InventoryProcessor implements Processor {

        @Override
        public void onNo(Controller controller, String userInput) {
            controller.println(String.format("У вас нет предмета '%s'.", userInput));
        }

        @Override
        public void onTwo(Controller controller, String userInput, Entity entity1, Entity entity2) {
            controller.println(String.format("Возможно, вы имели ввиду '%s' или '%s'.", TitleUtils.i(entity1), TitleUtils.i(entity2)));
        }

        @Override
        public void onMany(Controller controller, String userInput) {
            controller.println(String.format("Название '%s' допускает много трактований.", userInput));
        }
    }

    public static abstract class GroundProcessor implements Processor {

        @Override
        public void onNo(Controller controller, String userInput) {
            controller.println(String.format("Здесь нет предмета '%s'.", userInput));
        }

        @Override
        public void onTwo(Controller controller, String userInput, Entity entity1, Entity entity2) {
            controller.println(String.format("Возможно, вы имели ввиду '%s' или '%s'.", TitleUtils.i(entity1), TitleUtils.i(entity2)));
        }

        @Override
        public void onMany(Controller controller, String userInput) {
            controller.println(String.format("Название '%s' допускает много трактований.", userInput));
        }
    }

}
