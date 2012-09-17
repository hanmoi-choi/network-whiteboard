package client.view;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 17/09/12
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbJToggleButtonMediator {

    private List<NwbJToggleButton> buttonList;

    public NwbJToggleButtonMediator() {
        buttonList = new ArrayList<NwbJToggleButton>();
    }

    public void register(NwbJToggleButton nwbJToggleButton) {
        this.buttonList.add(nwbJToggleButton);
    }

    public void buttonClicked(NwbJToggleButton buttonPressed){
        for(NwbJToggleButton button : buttonList){
            if(button != buttonPressed) button.setSelected(false);
        }
    }
}
