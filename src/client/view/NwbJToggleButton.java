package client.view;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 17/09/12
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbJToggleButton extends JToggleButton {

    private ImageIcon hoverIcon;
    private ImageIcon selectedIcon;
    private ImageIcon unselectedIcon;
    private NwbJToggleButtonMediator mediator;

    public NwbJToggleButton() {
        super();
    }

    public void setMediator(NwbJToggleButtonMediator mediator){
        this.mediator = mediator;
        this.mediator.register(this);
    }
    public NwbJToggleButton(String title) {
        super(title);
        addMouseListener();
    }

    public void setHoverIcon(ImageIcon hoverIcon) {
        this.hoverIcon = hoverIcon;
    }

    public void setIcon(ImageIcon selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public void notifyToToggleMediator(){
        this.mediator.buttonClicked(this);
    }

    private void addMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        NwbJToggleButton.this.setIcon(new ImageIcon("resources/images/airbrush.png"));
                        System.out.println("entered");
                    }
                });
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("Exited");
            }
        });
    }

    @Override
    public void updateUI() {
        super.updateUI();
    }
}
