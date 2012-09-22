package client.view.ui.controller;

import client.view.ui.comp.NwbJToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 17/09/12
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbJToggleButtonMediator implements NwbUIMediator {

    private List<NwbJToggleButton> buttonList;

    public NwbJToggleButtonMediator() {
        buttonList = new ArrayList<NwbJToggleButton>();
    }

    @Override
    public void register(NwbJToggleButton nwbJToggleButton) {
        this.buttonList.add(nwbJToggleButton);
    }

    @Override
    public void buttonClicked(NwbJToggleButton buttonPressed){
        for(NwbJToggleButton button : buttonList){
            if(button != buttonPressed) button.setSelected(false);
        }
    }
}
