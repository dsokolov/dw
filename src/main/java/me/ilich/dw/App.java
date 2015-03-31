package me.ilich.dw;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.ilich.dw.commands.Command;
import me.ilich.dw.commands.Controller;
import me.ilich.dw.commands.ExitCommand;
import me.ilich.dw.commands.JumpCommand;
import me.ilich.dw.commands.LookAroundCommand;
import me.ilich.dw.data.DataSeedAdapter;
import me.ilich.dw.data.JsonDataSource;
import me.ilich.dw.data.Seed;
import me.ilich.dw.data.UuidSeed;
import me.ilich.dw.entities.Door;
import me.ilich.dw.entities.Room;
import me.ilich.dw.entities.Scene;
import me.ilich.dw.entities.Sceneable;
import me.ilich.dw.entities.Setting;
import org.apache.commons.io.IOUtils;


public class App {

    public static void main(String[] params) {

        Controller controller = new Controller();
        List<Command> commandList = new ArrayList<Command>();
        commandList.add(new ExitCommand(controller));
        commandList.add(new JumpCommand(controller));
        commandList.add(new LookAroundCommand(controller));

        System.out.println("begin");

        // https://api.vk.com/method/users.get?user_id=2682551
        // https://api.vk.com/method/friends.get?user_id=2682551

        Seed currentSeed = new UuidSeed(UUID.nameUUIDFromBytes("2682551".getBytes()));
        List<Seed> directionSeeds = new ArrayList<>();
        long[] ids = {
                131835, 345079, 458201, 579028, 641592, 681730, 698298, 765841, 861002, 902700, 952908, 955431, 1019859, 1027662, 1028386, 1211402, 1218581, 1338225, 1459509, 1561959, 1588061, 1657665, 1900304, 1950299, 1968420, 2024070, 2029825, 2111981, 2157951, 2182124, 2229725, 2233507, 2285851, 2342367, 2361901, 2499840, 2527031, 2553090, 2563857, 2583049, 2611526, 2646621, 2669683, 2677522, 2888132, 3005801, 3040767, 3043422, 3064555, 3133258, 3243833, 3288642, 3342988, 3361225, 3361288, 3401887, 3491580, 3560621, 3599384, 3731875, 3809420, 3899662, 3949021, 3961821, 3971259, 3988175, 4017204, 4052302, 4112299, 4233303, 4266758, 4272127, 4335549, 4371934, 4396741, 4397837, 4419260, 4442741, 4474062, 4514949, 4535418, 4564813, 4572827, 4710875, 4722781, 4822858, 4870373, 4923349, 4942866, 5030224, 5085032, 5130221, 5156835, 5159382, 5210193, 5225733, 5281419, 5377860, 5396811, 5456091, 5509518, 5529161, 5558442, 5643789, 5691190, 5722804, 5869504, 5899292, 5926448, 6142342, 6190848, 6219112, 6310425, 6514804, 6642187, 6687764, 6728094, 6789362, 6812717, 6824504, 6826684, 6907668, 6909938, 6961716, 7014502, 7028399, 7116293, 7236176, 7248802, 7276894, 7356214, 7541508, 7563586, 7630223, 7640795, 7886694, 8286485, 8554758, 8608011, 8964953, 9034269, 9164472, 9559049, 9601855, 9836823, 9928017, 10269570, 10379561, 10418253, 10522502, 10534422, 10567292, 10591724, 10624426, 10634558, 10673852, 11044460, 11170833, 11418378, 11803191, 11838667, 11841719, 12005603, 12024298, 12317012, 12546291, 12678316, 12860946, 13041593, 13184491, 13491665, 13493844, 13587144, 13712215, 13772922, 14338607, 14657648, 14860850, 15261752, 15961417, 16315441, 16346364, 16442538, 17103004, 17279703, 17341507, 17342298, 17498902, 17503037, 17960268, 18509295, 19264579, 19615824, 19899068, 20761842, 20952992, 21125273, 21158787, 21290003, 21924817, 22259733, 23171279, 24614633, 25264507, 25336824, 25853368, 26451024, 27317751, 27458860, 28005805, 28342507, 28401679, 28688488, 29141995, 29607244, 29712836, 29742513, 30217021, 30367813, 30526477, 30701023, 33925706, 33955484, 34461734, 34496758, 37247475, 37651565, 37776933, 38073479, 38956566, 40564372, 42019955, 42238594, 42645321, 43124774, 43739887, 45828587, 46810449, 46844936, 49657974, 49781666, 49791465, 49845451, 50538045, 50859919, 51418602, 52370858, 56500772, 58818220, 59018688, 59529795, 61970054, 61975494, 62473428, 68938961, 72939820, 73151232, 73717728, 75092325, 88686221, 89195955, 90339287, 93099041, 96281177, 96691816, 99792585, 102063239, 111636042, 116151671, 120132197, 125513192, 135790792, 136478396, 137278244, 139302650, 141517566, 142318106, 149175077, 152763165, 156458846, 158521681, 159335407, 162358342, 174701805, 176081752, 176544742, 177761877, 181373122, 183033685, 190076879, 190570457, 197233036, 201306563, 202667871, 203964255, 206927783, 211769894, 232208831, 242024213, 261675653, 263712785, 274255716, 277006752, 285427625
        };
        for (long id : ids) {
            String s = Long.toString(id);
            Seed seed = new UuidSeed(UUID.nameUUIDFromBytes(s.getBytes()));
            if (seed.getSettingId().equals(currentSeed.getSettingId())) {
                directionSeeds.add(seed);
            }
        }


        DataSeedAdapter dataSeedAdapter = new DataSeedAdapter(new JsonDataSource());

        List<Sceneable> sceneableList = new ArrayList<>();
        Setting setting = dataSeedAdapter.getSetting(currentSeed);
        sceneableList.add(setting);
        Room room = dataSeedAdapter.getRoom(currentSeed);
        sceneableList.add(room);
        List<Door> doors = dataSeedAdapter.getDoors(currentSeed, directionSeeds);
        sceneableList.addAll(doors);

        Scene scene = new Scene(controller);
        for (Sceneable sceneable : sceneableList) {
            sceneable.processScene(scene);
        }
        scene.render();

        boolean working = true;
        while (working) {
            try {
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String s = bufferRead.readLine();
                System.out.println(s);
                int suitableCommandsCount = 0;
                Command suitableCommand = null;
                for (Command command : commandList) {
                    if (command.isSuitable(s)) {
                        suitableCommandsCount++;
                        suitableCommand = command;
                    }
                }
                if (suitableCommandsCount == 1) {
                    suitableCommand.execute();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            working = controller.isWorking();
        }

        System.out.println("end");
    }

}
