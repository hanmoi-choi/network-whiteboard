package client.view.ui.controller;

import client.view.ui.comp.NwbColorControllButton;
import client.view.ui.comp.NwbJToggleButton;

import javax.swing.*;
<<<<<<< HEAD
import javax.swing.table.DefaultTableModel;
=======
>>>>>>> baa76583014c8408abc750c72fd7440f47c0e83d

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 17/09/12
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbUIComponentMediator {

    private List<NwbJToggleButton> toolbarToggleButtonList;
    private List<NwbJToggleButton> fillModeToggleButtonList;
    private JPanel fillNStrokePanel;
    private JScrollPane scrollPanel;
<<<<<<< HEAD
    private JTable memberListTable;
=======
>>>>>>> baa76583014c8408abc750c72fd7440f47c0e83d
    private List<NwbColorControllButton> colorControlButtonList;
    private Map<String, JTextField> textFieldMap;

    public NwbUIComponentMediator() {
        toolbarToggleButtonList = new ArrayList<NwbJToggleButton>();
        colorControlButtonList = new ArrayList<NwbColorControllButton>();
        fillModeToggleButtonList = new ArrayList<NwbJToggleButton>();
        textFieldMap = new HashMap<String, JTextField>();
    }

    public void registerToolbarButton(NwbJToggleButton nwbJToggleButton) {
        this.toolbarToggleButtonList.add(nwbJToggleButton);
    }

    public void registerFillModeButton(NwbJToggleButton nwbJToggleButton) {
        this.fillModeToggleButtonList.add(nwbJToggleButton);
    }

    public void toolbarButtonClicked(NwbJToggleButton buttonPressed){
        controlFillNStrokePanel(buttonPressed);

        for(NwbJToggleButton button : toolbarToggleButtonList){
            if(button != buttonPressed) button.setSelected(false);
            if(button == buttonPressed) button.setSelected(true);
        }
    }

    public void fillModeButtonClicked(NwbJToggleButton buttonPressed){

        for(NwbJToggleButton button : fillModeToggleButtonList){
            if(button != buttonPressed) button.setSelected(false);
            if(button == buttonPressed) button.setSelected(true);
        }
    }

    private void controlFillNStrokePanel(NwbJToggleButton buttonPressed) {
        if(isStrategyWithFillingMode(buttonPressed)){
            fillNStrokePanel.setVisible(true);
        }
        else {
            fillNStrokePanel.setVisible(false);
        }
    }
    
    public void modeChanged(boolean isNetwork) {
    	if(isNetwork){
    		scrollPanel.setVisible(true);
    	}
    	else {
    		scrollPanel.setVisible(false);
<<<<<<< HEAD
    		DefaultTableModel tableModel = (DefaultTableModel) memberListTable.getModel();
    		int rowCount = tableModel.getRowCount();
    		for(int i = 0; i < rowCount; i++)
    		{
    			tableModel.removeRow(0);
    		}
=======
>>>>>>> baa76583014c8408abc750c72fd7440f47c0e83d
    	}
    }
    
    private boolean isStrategyWithFillingMode(NwbJToggleButton buttonPressed) {
        return buttonPressed.getActionCommand().equals("doRect")
               || buttonPressed.getActionCommand().equals("doRoundedRect")
               || buttonPressed.getActionCommand().equals("doOval");
    }

    public void addFillNStrokPanel(JPanel fillNStrokePanel) {
        this.fillNStrokePanel = fillNStrokePanel;
    }

    public void addMemberScrollPanel(JScrollPane scrollPanel) {
        this.scrollPanel = scrollPanel;
    }
    
<<<<<<< HEAD
    public void addMemberListTable(JTable table)
    {
    	this.memberListTable = table;
    }
    
=======
>>>>>>> baa76583014c8408abc750c72fd7440f47c0e83d
    public void registerColorButton(NwbColorControllButton nwbColorControllButton) {
        colorControlButtonList.add(nwbColorControllButton);
    }

    public void registerColorTextFiled(String actionCommand, JTextField textField){
        textFieldMap.put(actionCommand, textField);
    }

    public void handleNewColorChange(NwbColorControllButton nwbColorControllButton, Color newColor) {
        textFieldMap.get(nwbColorControllButton.getActionCommand()).setBackground(newColor);
    }

    public void switchBgNFg() {
        JTextField[] list = textFieldMap.values().toArray(new JTextField[]{});

        Color fgColor = list[0].getBackground();
        Color bgColor = list[1].getBackground();

        list[0].setBackground(bgColor);
        list[1].setBackground(fgColor);
    }
}
