package client.view.ui.factory;

import client.controller.NwbDrawingOptionActionController;
import client.view.ui.comp.NwbColorControllButton;
import client.view.ui.comp.NwbJToggleButton;
import client.view.ui.controller.NwbUIComponentMediator;
import org.jdesktop.application.Application;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 16/09/12
 * Time: 2:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class NwbDrawingOptionFactory {
    private static ActionMap actionMap;
    private static NwbUIComponentMediator nwbUIComponentMediator;

    public static void setActionMap(NwbDrawingOptionActionController controller) {
        actionMap = Application.getInstance()
                .getContext()
                .getActionMap(NwbDrawingOptionActionController.class, controller);
    }

    public static void setUIMediator(NwbUIComponentMediator mediator) {
        nwbUIComponentMediator = mediator;
    }

    public static JPanel getToolBar() {
        JPanel drawingOptionPanel = new JPanel();
        drawingOptionPanel.setPreferredSize(new Dimension(90, 480));
        drawingOptionPanel.setLayout(null);

        final JLabel lblStroke = new JLabel("Stroke Size");
        lblStroke.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        lblStroke.setBounds(12, 10, 78, 29);
        drawingOptionPanel.add(lblStroke);
//
//        JComboBox<Integer> cbStroke = new JComboBox<Integer>();
//        cbStroke.setAction(actionMap.get("doAdjustStroke"));
//        cbStroke.setModel(new DefaultComboBoxModel(
//                new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
//        cbStroke.setAlignmentX(JComboBox.RIGHT_ALIGNMENT);
//        drawingOptionPanel.add(cbStroke);
//
//        drawingOptionPanel.add(new JPopupMenu.Separator());

        final JLabel lblStrokeSize = new JLabel("5");
        lblStrokeSize.setHorizontalAlignment(SwingConstants.CENTER);
        lblStrokeSize.setBounds(6, 62, 78, 16);
        drawingOptionPanel.add(lblStrokeSize);

        JSlider slider = new JSlider(1,10,5);
        slider.setBounds(0, 37, 90, 29);
        slider.addChangeListener(new ChangeListener() {
            Action action = actionMap.get("doAdjustStroke");
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                int value =  slider.getValue();
                lblStrokeSize.setText(Integer.toString(value));

                //Fire Action
                int modifiers = InputEvent.CTRL_DOWN_MASK;
                KeyEvent event = new KeyEvent(slider, 0, 0, modifiers, KeyEvent.VK_HOME, 'a');
                SwingUtilities.notifyAction(action, null, event, e.getSource(), modifiers);
            }
        });
//        slider.setAction(actionMap.get("doAdjustStroke"));
        drawingOptionPanel.add(slider);


        JLabel lblStrokeNFill = new JLabel("Stroke&Fill");
        lblStrokeNFill.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        lblStrokeNFill.setBounds(11, 90, 78, 16);
        drawingOptionPanel.add(lblStrokeNFill);

        JPanel strokeNFill = new JPanel();
        nwbUIComponentMediator.addFillNStrokPanel(strokeNFill);
        strokeNFill.setBounds(6, 111, 78, 155);
        strokeNFill.setLayout(null);

        NwbJToggleButton btnStrokeOnly = createToggleButton("doSelectStrokeOnly");
        NwbJToggleButton btnFillOnly = createToggleButton("doSelectFillOnly");
        NwbJToggleButton btnStrokeFill = createToggleButton("doSelectStrokeFill");
        btnStrokeOnly.setBounds(0, 6, 78, 41);
        btnFillOnly.setBounds(0, 56, 78, 41);
        btnStrokeFill.setBounds(0, 109, 78, 41);
        strokeNFill.add(btnStrokeOnly);
        strokeNFill.add(btnFillOnly);
        strokeNFill.add(btnStrokeFill);
        strokeNFill.setVisible(false);
        drawingOptionPanel.add(strokeNFill);

        NwbColorControllButton btnFgColor = createColorButton("doChaneFgColor");
        btnFgColor.setBounds(6, 286, 78, 29);
        drawingOptionPanel.add(btnFgColor);

        JTextField tfFgColor = new JTextField("");
        tfFgColor.setColumns(10);
        tfFgColor.setBackground(Color.BLACK);
        tfFgColor.setEditable(false);
        tfFgColor.setBounds(6, 315, 78, 37);

        drawingOptionPanel.add(tfFgColor);

        NwbColorControllButton btnSwitchColor = createColorButton("doSwitchColor");
        btnSwitchColor.setBounds(6, 355, 78, 29);
        drawingOptionPanel.add(btnSwitchColor);

        JTextField tfBgColor = new JTextField("");
        tfBgColor.setColumns(10);
        tfBgColor.setBackground(Color.WHITE);
        tfBgColor.setEditable(false);
        tfBgColor.setBounds(6, 385, 78, 37);
        drawingOptionPanel.add(tfBgColor);

        NwbColorControllButton btnBgColor = createColorButton("doChaneBgColor");
        btnBgColor.setBounds(6, 423, 78, 29);
        nwbUIComponentMediator.registerColorTextFiled(btnFgColor.getActionCommand(), tfFgColor);
        nwbUIComponentMediator.registerColorTextFiled(btnBgColor.getActionCommand(), tfBgColor);
        drawingOptionPanel.add(btnBgColor);

        return drawingOptionPanel;
    }


    private static NwbJToggleButton createToggleButton(String actionCommand) {
        NwbJToggleButton toggleButton = new NwbJToggleButton();
        toggleButton.setAction(actionMap.get(actionCommand));
        toggleButton.setActionCommand(actionCommand);
        toggleButton.setMediator(nwbUIComponentMediator);

        return toggleButton;
    }

    private static NwbColorControllButton createColorButton(String actionCommand) {
        NwbColorControllButton toggleButton = new NwbColorControllButton();
        toggleButton.setAction(actionMap.get(actionCommand));
        toggleButton.setActionCommand(actionCommand);
        toggleButton.setMediator(nwbUIComponentMediator);

        return toggleButton;
    }


}
