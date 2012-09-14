package test;

import client.model.NwbClientModel;
import client.model.NwbDrawingCommand;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 10:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbClientModelTest {

    private NwbClientModel model;
    private NwbDrawingCommand command1;
    private NwbDrawingCommand command2;
    private NwbDrawingCommand command3;

    @BeforeTest
    public void initOnce(){
        model = new NwbClientModel();
        command1 = mock(NwbDrawingCommand.class);
        command2 = mock(NwbDrawingCommand.class);
        command3 = mock(NwbDrawingCommand.class);
    }

    @Test
    public void couldPushDrawingCommand(){
        model.pushDrawingCommand(command1);
        model.pushDrawingCommand(command2);
        model.pushDrawingCommand(command3);

        assertThat(model.getActionCommands()).isNotEmpty()
                                             .contains(command1, command2, command3);
    }

    @Test
    public void shallMoveCommandAtTheTopToRedoStack_WhenUndoExecutes(){
        model.pushDrawingCommand(command1);
        model.pushDrawingCommand(command2);
        model.pushDrawingCommand(command3);

        model.undo();
        assertThat(model.getActionCommands()).containsOnly(command1, command2);
    }

    @Test(dependsOnMethods = "shallMoveCommandAtTheTopToRedoStack_WhenUndoExecutes")
    public void shallMoveCommandBackToCommandStack_WhenRedoExecutes(){
        model.redo();
        assertThat(model.getActionCommands()).containsOnly(command1, command2, command3);
    }
}
