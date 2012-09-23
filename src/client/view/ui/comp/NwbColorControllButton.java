package client.view.ui.comp;

import client.view.ui.controller.NwbUIComponentMediator;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 23/09/12
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbColorControllButton extends JButton {
    private NwbUIComponentMediator mediator;

    public NwbColorControllButton() {
        super();
    }


    public void setMediator(NwbUIComponentMediator mediator) {
        this.mediator = mediator;

        this.mediator.registerColorButton(this);
    }

    public NwbColorControllButton(String title) {
        super(title);
    }

    public void notifyToMediatorWithNewColor(Color newColor) {
        if(this.getActionCommand().equals("doSwitchColor")){
            mediator.switchBgNFg();
        }
        else{
            mediator.handleNewColorChange(this, newColor);
        }
    }


}
