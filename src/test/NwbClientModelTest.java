package test;

import client.model.NwbClientModel;
import client.model.NwbDrawingCommand;
import org.testng.annotations.BeforeTest;

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


}
