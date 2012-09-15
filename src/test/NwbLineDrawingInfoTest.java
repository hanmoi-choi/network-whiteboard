package test;

import client.model.NwbDrawingCommand;
import client.model.NwbLineDrawingCommand;
import client.view.drawing.NwbDrawingInfo;
import client.view.drawing.strategy.NwbDrawingStrategy;
import client.view.drawing.strategy.NwbLineStrategy;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbLineDrawingInfoTest {

    @Test
    public void shallUseLineStrategy(){
        NwbDrawingCommand drawingCommand = new NwbLineDrawingCommand();
        NwbDrawingInfo drawingInfo = mock(NwbDrawingInfo.class);
        NwbDrawingStrategy strategy = mock(NwbLineStrategy.class);

        drawingCommand.setDrawingInfo(drawingInfo);
        drawingCommand.setDrawingStrategy(strategy);
    }
}
