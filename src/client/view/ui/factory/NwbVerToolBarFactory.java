package client.view.ui.factory;

import client.controller.NwbVerToolbarActionController;
import client.view.ui.comp.NwbColorControllButton;
import client.view.ui.comp.NwbJToggleButton;
import client.view.ui.controller.NwbUIComponentMediator;
import org.jdesktop.application.Application;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 16/09/12
 * Time: 2:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class NwbVerToolBarFactory {
    private static ActionMap actionMap;
    private static NwbUIComponentMediator nwbUIComponentMediator;

    public static void setActionMap(NwbVerToolbarActionController controller) {
        actionMap = Application.getInstance()
                .getContext()
                .getActionMap(NwbVerToolbarActionController.class, controller);
    }

    public static void setUIMediator(NwbUIComponentMediator mediator) {
        nwbUIComponentMediator = mediator;
    }

    public static JToolBar getToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.VERTICAL);

        JLabel lblStroke = new JLabel("Stroke");
        lblStroke.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        lblStroke.setBounds(0, 0, 30, 30);

        toolBar.add(lblStroke);

        JComboBox<Integer> cbStroke = new JComboBox<Integer>();
        cbStroke.setAction(actionMap.get("doAdjustStroke"));
        cbStroke.setModel(new DefaultComboBoxModel(
                new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
        cbStroke.setAlignmentX(JComboBox.RIGHT_ALIGNMENT);
        toolBar.add(cbStroke);

        toolBar.add(new JPopupMenu.Separator());

        JPanel strokeNFill = new JPanel();
        strokeNFill.setLayout(new GridLayout(4, 1, 2, 0));
        nwbUIComponentMediator.addFillNStrokPanel(strokeNFill);

        JLabel lblStrokeNFill = new JLabel("Stroke&Fill");
        lblStrokeNFill.setAlignmentX(JLabel.RIGHT_ALIGNMENT);

        NwbJToggleButton btnStrokeOnly = createToggleButton("doSelectStrokeOnly");
        NwbJToggleButton btnFillOnly = createToggleButton("doSelectFillOnly");
        NwbJToggleButton btnStrokeFill = createToggleButton("doSelectStrokeFill");
        strokeNFill.add(lblStrokeNFill);
        strokeNFill.add(btnStrokeOnly);
        strokeNFill.add(btnFillOnly);
        strokeNFill.add(btnStrokeFill);
        strokeNFill.setVisible(false);
        toolBar.add(strokeNFill);

        toolBar.add(new JPopupMenu.Separator());

        NwbColorControllButton btnFgColor = createColorButton("doChaneFgColor");
        toolBar.add(btnFgColor);

        JTextField tfFgColor = new JTextField("");
        tfFgColor.setBackground(Color.BLACK);
        tfFgColor.setEditable(false);

        toolBar.add(tfFgColor);

        NwbColorControllButton btnSwitchColor = createColorButton("doSwitchColor");

        toolBar.add(btnSwitchColor);

        JTextField tfBgColor = new JTextField("");
        tfBgColor.setColumns(5);
        tfBgColor.setBackground(Color.WHITE);
        tfBgColor.setEditable(false);

        toolBar.add(tfBgColor);

        NwbColorControllButton btnBgColor = createColorButton("doChaneBgColor");

        nwbUIComponentMediator.registerColorTextFiled(btnFgColor.getActionCommand(), tfFgColor);
        nwbUIComponentMediator.registerColorTextFiled(btnBgColor.getActionCommand(), tfBgColor);
        toolBar.add(btnBgColor);

        toolBar.setFloatable(false);
        return toolBar;
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
