import junit.framework.TestCase;
import tgn.rkvy.deep.controllers.UserInputController;
import tgn.rkvy.deep.entities.Entity;

import java.util.List;

public class UserInputControllerTestCase extends TestCase {

    private UserInputController userInputController = new UserInputController();

    public void testSimple() {
        List<Entity> entities = userInputController.processInput("afvsfbsdb");
        assertEquals(0, entities.size());
    }

}
