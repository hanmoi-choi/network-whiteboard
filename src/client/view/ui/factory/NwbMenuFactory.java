package client.view.ui.factory;

import client.controller.NwbMenuActionController;
import org.jdesktop.application.Application;

import javax.swing.*;

public final class NwbMenuFactory{
    private static ActionMap actionMap;

    public static void setActionMap(NwbMenuActionController controller){
        actionMap = Application.getInstance()
                .getContext()
                .getActionMap(NwbMenuActionController.class, controller);
    }

	public static JMenu createFileMenu() {

        JLabel label = new JLabel();
        JButton button = new JButton();

        JMenu jmFile = new JMenu("File");

		JMenuItem jmiNew = new JMenuItem("New");
        jmiNew.setAction(actionMap.get("doNew"));
		jmFile.add(jmiNew);

		JMenuItem jmiOpen = new JMenuItem("Open");
        jmiOpen.setAction(actionMap.get("doOpen"));
		jmFile.add(jmiOpen);

		JSeparator sprFileFirst = new JSeparator();
		jmFile.add(sprFileFirst);

		JMenuItem jmiSave = new JMenuItem("Save");
        jmiSave.setAction(actionMap.get("doSave"));
		jmFile.add(jmiSave);

		JMenuItem jmiSaveAs = new JMenuItem("Save As");
        jmiSaveAs.setAction(actionMap.get("doSaveAs"));
		jmFile.add(jmiSaveAs);

		JSeparator sprFileSecond = new JSeparator();
		jmFile.add(sprFileSecond);

		JMenuItem jmiQuit = new JMenuItem("Quit");
        jmiQuit.setAction(actionMap.get("doQuit"));
		jmFile.add(jmiQuit);

		gjmiNew=jmiNew;
		gjmiOpen=jmiOpen;
		gjmiSave=jmiSave;
		gjmiSaveAs=jmiSaveAs;

		return jmFile;
	}

	public static JMenu createEditMenu() {
        JMenu jmEdit = new JMenu("Edit");

		JMenuItem jmiUndo = new JMenuItem("Undo");
        jmiUndo.setAction(actionMap.get("doUndo"));
//		jmiUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		jmEdit.add(jmiUndo);

		JMenuItem jmiRedo = new JMenuItem("Redo");
        jmiRedo.setAction(actionMap.get("doRedo"));
//		jmiRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		jmEdit.add(jmiRedo);

		JSeparator sprEdit = new JSeparator();
		jmEdit.add(sprEdit);

		return jmEdit;
	}

	public static JMenu createModeMenu() {
        JMenu jmMode = new JMenu("Mode");

        JMenuItem jmiLocal = new JRadioButtonMenuItem("Local Mode", true);
		jmiLocal.setAction(actionMap.get("doLocal"));
		jmiLocal.setEnabled(false);
        jmMode.add(jmiLocal);

        JMenuItem jmiNetwork = new JRadioButtonMenuItem("Network Mode", false);
		jmiNetwork.setAction(actionMap.get("doNetwork"));
		jmMode.add(jmiNetwork);

		gjmiLocal = jmiLocal;
		gjmiNetwork = jmiNetwork;

        return jmMode;
	}

	private static JMenuItem gjmiLocal;
	private static JMenuItem gjmiNetwork;
	public static void toggleModeMenu(boolean isNetwork)
	{
		if(isNetwork)
		{
			gjmiNetwork.setSelected(true);
			gjmiNetwork.setEnabled(false);

			gjmiLocal.setSelected(false);
			gjmiLocal.setEnabled(true);
		}
		else
		{
			gjmiLocal.setSelected(true);
			gjmiLocal.setEnabled(false);

			gjmiNetwork.setSelected(false);
			gjmiNetwork.setEnabled(true);
		}

	}
	private static JMenuItem gjmiNew;
	private static JMenuItem gjmiOpen;
	private static JMenuItem gjmiSave;
	private static JMenuItem gjmiSaveAs;

	public static void toggleFileMenu(boolean isNetwork, boolean isManager)
	{
		if(isNetwork && !isManager)
		{
			gjmiNew.setEnabled(false);
			gjmiOpen.setEnabled(false);
            gjmiSave.setEnabled(false);
            gjmiSaveAs.setEnabled(false);
		}
		else
		{
			gjmiNew.setEnabled(true);
			gjmiOpen.setEnabled(true);
            gjmiSave.setEnabled(true);
            gjmiSaveAs.setEnabled(true);
		}
	}

	public static void forceChangeLocalMode()
	{
		actionMap.get("doLocal").actionPerformed(null);
	}

}
