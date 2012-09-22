package client.view.ui.controller;

import client.view.ui.comp.NwbJToggleButton;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 22/09/12
 * Time: 5:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NwbUIMediator {
    void register(NwbJToggleButton nwbJToggleButton);

    void buttonClicked(NwbJToggleButton buttonPressed);
}
