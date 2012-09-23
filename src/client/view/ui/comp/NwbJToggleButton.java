package client.view.ui.comp;

import client.view.ui.controller.NwbUIComponentMediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 17/09/12
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbJToggleButton extends JToggleButton{

    private ImageIcon hoverIcon;
    private ImageIcon icon;
    private NwbUIComponentMediator mediator;

    public NwbJToggleButton() {
        super();
    }

    public void setMediator(NwbUIComponentMediator mediator) {
        this.mediator = mediator;
        if(isFillModeButton()){
            this.mediator.registerFillModeButton(this);
        }
        else{
            this.mediator.registerToolbarButton(this);
        }

        this.mediator.registerToolbarButton(this);
    }

    public NwbJToggleButton(String title) {
        super(title);
    }

    public void notifyToMediator() {
        if(isFillModeButton()){
            this.mediator.fillModeButtonClicked(this);
        }
        else {
            this.mediator.toolbarButtonClicked(this);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
    }

    private boolean isFillModeButton() {
        return this.getActionCommand().equals("doSelectFillOnly")
                || this.getActionCommand().equals("doSelectStrokeOnly")
                || this.getActionCommand().equals("doSelectStrokeFill");
    }

    private void addMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                NwbJToggleButton button = (NwbJToggleButton) e.getSource();
                if (!button.isSelected())
                    button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                NwbJToggleButton button = (NwbJToggleButton) e.getSource();
                if (!button.isSelected())
                    button.setBorder(BorderFactory.createEmptyBorder());
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                NwbJToggleButton button = (NwbJToggleButton) e.getSource();
                button.setBorder(BorderFactory.createEmptyBorder());
            }
        });
    }


}
