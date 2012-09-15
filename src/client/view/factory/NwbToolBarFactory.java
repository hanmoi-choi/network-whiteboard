package client.view.factory;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 16/09/12
 * Time: 2:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class NwbToolBarFactory extends NwbActionFactory{

    public static JToolBar getToolBar(){
        JToolBar toolBar = new JToolBar();

        JButton btnSketch = new JButton("");
        btnSketch.setAction(actionMap.get("doSketch"));
        toolBar.add(btnSketch);

        JButton btnLine = new JButton("");
        btnLine.setAction(actionMap.get("doLine"));
        toolBar.add(btnLine);

        JButton btnRect = new JButton("");
        btnRect.setAction(actionMap.get("doRect"));
        toolBar.add(btnRect);

        JButton btnRoundedRect = new JButton("");
        btnRoundedRect.setAction(actionMap.get("doRoundedRect"));
        toolBar.add(btnRoundedRect);

        JButton btnErase = new JButton("");
        btnErase.setAction(actionMap.get("doErase"));
        toolBar.add(btnErase);

        JButton btnOval = new JButton("");
        btnOval.setAction(actionMap.get("doOval"));
        toolBar.add(btnOval);

        JButton btnText = new JButton("");
        btnText.setAction(actionMap.get("doText"));
        toolBar.add(btnText);

        return toolBar;
    }
}
