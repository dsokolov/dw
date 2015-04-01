package me.ilich.dw;

import me.ilich.dw.commands.Command;
import me.ilich.dw.commands.Controller;
import me.ilich.dw.data.DataSeedAdapter;
import me.ilich.dw.data.JsonDataSource;
import me.ilich.dw.data.Seed;
import me.ilich.dw.entities.*;

import java.util.ArrayList;
import java.util.List;


public class App {

    public static void main(String[] params) {
        System.out.println("dw v0.1");
        Controller controller = new Controller();
        DataSeedAdapter dataSeedAdapter = new DataSeedAdapter(new JsonDataSource());
        //SeedSource seedSource = new HardcodeSeedSource();
        SeedSource seedSource = new VkSeedSource();

        boolean working = true;
        while (working) {

            boolean reloadScene = controller.isShouldReloadScene();
            if (reloadScene) {
                String tag = controller.getCurrentTag();
                seedSource.load(tag);
                Seed currentSeed = seedSource.getCurrentSeed();
                List<Seed> directionSeeds = seedSource.getDirectionSeeds();

                List<Sceneable> sceneableList = new ArrayList<>();
                Setting setting = dataSeedAdapter.getSetting(currentSeed);
                sceneableList.add(setting);
                Room room = dataSeedAdapter.getRoom(currentSeed);
                sceneableList.add(room);
                List<Event> events = dataSeedAdapter.getEvents(currentSeed);
                sceneableList.addAll(events);
                List<Door> doors = dataSeedAdapter.getDoors(currentSeed, directionSeeds);
                sceneableList.addAll(doors);
                Scene scene = new Scene(controller);
                for (Sceneable sceneable : sceneableList) {
                    sceneable.processScene(scene);
                }
                scene.render();
                controller.setShouldReloadScene(false);
            }

            String s = controller.in();
            String[] inputs = s.split(" ");
            String commandBody = inputs[0];

            List<Command> suitableCommands = dataSeedAdapter.getSuitableCommands(commandBody);
            switch (suitableCommands.size()) {
                case 0:
                    controller.out(String.format("Что значит %s?", commandBody));
                    break;
                case 1:
                    Command command = suitableCommands.get(0);
                    command.execute(controller, inputs);
                    break;
                case 2:
                    controller.out("Возможно, вы имели ввиду:");
                    controller.out(suitableCommands.get(0).getAlias());
                    controller.out(suitableCommands.get(1).getAlias());
                    break;
                default:
                    controller.out(String.format("%s допускает много трактований.", commandBody));
            }
            working = controller.isWorking();
        }

        System.out.println("finish");
    }

}
