package client.view.ui.factory;

import client.controller.NwbVerToolbarActionController;
import client.view.ui.comp.NwbJToggleButton;
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

    public static void setActionMap(NwbVerToolbarActionController controller){
        actionMap = Application.getInstance()
                               .getContext()
                               .getActionMap(NwbVerToolbarActionController.class, controller);
    }

    public static JToolBar getToolBar(){
//        NewColorMediator

        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.VERTICAL);
//        toolBar.setLayout(null);

        JLabel lblStroke = new JLabel("Stroke");
        lblStroke.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        lblStroke.setBounds(0,0, 30,30);
        toolBar.add(lblStroke);

        JComboBox<Integer> cbStroke = new JComboBox<Integer>();
        cbStroke.setAction(actionMap.get("doAdjustStroke"));
        cbStroke.setModel(new DefaultComboBoxModel(
                new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
        cbStroke.setAlignmentX(JComboBox.RIGHT_ALIGNMENT);
        toolBar.add(cbStroke);
        toolBar.add(new JPopupMenu.Separator());

        JPanel strokeNFill = new JPanel();
        strokeNFill.setLayout(new GridLayout(4,1,2,0));

        JLabel lblStrokeNFill = new JLabel("Stroke&Fill");
        lblStrokeNFill.setAlignmentX(JLabel.RIGHT_ALIGNMENT);

        NwbJToggleButton btnStrokeOnly = new NwbJToggleButton("");
        btnStrokeOnly.setAction(actionMap.get("doSelectStrokeOnly"));
        btnStrokeOnly.setAlignmentX(JToggleButton.RIGHT_ALIGNMENT);


        NwbJToggleButton btnFillOnly = new NwbJToggleButton("");
        btnFillOnly.setAction(actionMap.get("doSelectFillOnly"));
        btnFillOnly.setAlignmentX(JToggleButton.RIGHT_ALIGNMENT);


        NwbJToggleButton btnStrokeFill = new NwbJToggleButton("");
        btnStrokeFill.setAction(actionMap.get("doSelectStrokeFill"));
        btnStrokeFill.setAlignmentX(JToggleButton.RIGHT_ALIGNMENT);


        strokeNFill.add(lblStrokeNFill);
        strokeNFill.add(btnStrokeOnly);
        strokeNFill.add(btnFillOnly);
        strokeNFill.add(btnStrokeFill);
        toolBar.add(strokeNFill);

        toolBar.add(new JPopupMenu.Separator());

        JButton btnFgColor = new JButton("FG Color");
        btnFgColor.setAction(actionMap.get("doChaneFgColor"));
        btnFgColor.setAlignmentX(JButton.RIGHT_ALIGNMENT);
        toolBar.add(btnFgColor);

        JTextField tfBgColor = new JTextField("");
        tfBgColor.setColumns(5);
        tfBgColor.setBackground(Color.WHITE);
        tfBgColor.setEditable(false);

        toolBar.add(tfBgColor);

        JButton btnSwitchColor = new JButton();
        btnSwitchColor.setAction(actionMap.get("doSwitchColor"));
        btnSwitchColor.setAlignmentX(JButton.RIGHT_ALIGNMENT);
        toolBar.add(btnSwitchColor);

        JTextField tfFgColor = new JTextField("");
        tfFgColor.setBackground(Color.BLACK);
        tfFgColor.setEditable(false);
        toolBar.add(tfFgColor);

        JButton btnBgColor = new JButton("BG Color");
        btnBgColor.setAction(actionMap.get("doChaneBgColor"));
        btnBgColor.setAlignmentX(JButton.RIGHT_ALIGNMENT);
        toolBar.add(btnBgColor);

        toolBar.setFloatable(false);
        return toolBar;
    }
}
