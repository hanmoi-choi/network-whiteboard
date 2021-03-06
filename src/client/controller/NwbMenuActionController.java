package client.controller;

import client.model.NwbClientModel;
import client.model.factory.NwbClientModelFactory;
import client.model.room.NwbClientRoom;
import client.signin.NwbClientSignIn;
import client.view.CanvasDrawble;
import client.view.ui.controller.NwbUIComponentMediator;
import client.view.ui.factory.NwbMenuFactory;

import org.jdesktop.application.Action;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbMenuActionController implements NwbController {

    private CanvasDrawble drawble;
    private NwbClientModel model;
    private File imageFile;
    private NwbUIComponentMediator mediator;

    public NwbMenuActionController(NwbUIComponentMediator mediator){
    	this.mediator = mediator;
    }

    public void setCanvasDrawble(CanvasDrawble drawble){
        this.drawble = drawble;
    }

    public void setModel(NwbClientModel model){
        this.model = model;
    }

    @Action
    public void doNew(ActionEvent evt){
        model.clearStack();
        drawble.newCanvas();

//        NwbCanvasSizeDialog dialog = new NwbCanvasSizeDialog(drawble);
//        dialog.setVisible(true);
    }

    @Action
    public void doOpen(ActionEvent evt){
        File imageFile = selectImageFile(JFileChooser.OPEN_DIALOG);
        if(imageFile != null){
            model.clearStack();
            drawble.openImage(imageFile);
        }
    }

    @Action
    public void doOpenRecent(ActionEvent evt){
        System.out.println("Quit");
    }

    @Action
    public void doSave(ActionEvent evt){
        if(imageFile == null){
            imageFile = selectImageFile(JFileChooser.SAVE_DIALOG);
        }

        saveScreen(imageFile);
    }

    private void saveScreen(File imageFile) {
        if(imageFile != null){
            BufferedImage bufferedImage = drawble.getBufferedImageOfCanvas();
            try {
                // write the image as a PNG
                ImageIO.write(
                        bufferedImage,
                        "png",
                        imageFile);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Action
    public void doSaveAs(ActionEvent evt){
        File imageFile = selectImageFile(JFileChooser.SAVE_DIALOG);
        saveScreen(imageFile);
    }
    @Action
    public void doQuit(ActionEvent evt){
        System.out.println("Quit");
        System.exit(0);
    }

    @Action
    public void doRedo(ActionEvent evt){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                model.redo();
            }
        });

    }

    @Action
    public void doUndo(ActionEvent evt){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                model.undo();
            }
        });

    }

    private File selectImageFile(int dialogMode) {
        int returnValue = -1;

        final JFileChooser fc = new JFileChooser();

        File file = null;
        FileFilter ff = new FileFilter() {
            @Override
            public boolean accept(File file) {
                String filename = file.getName();
                return filename.endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "*.png";
            }
        };
        fc.addChoosableFileFilter(ff);
        fc.setFileFilter(ff);

        if(dialogMode == JFileChooser.OPEN_DIALOG){
            returnValue = fc.showOpenDialog(null);
        }
        else if(dialogMode == JFileChooser.SAVE_DIALOG){
           returnValue = fc.showSaveDialog(null);
        }

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }

        if(file.getName().lastIndexOf('.') == -1) file = new File(file.getAbsolutePath()+".png");

        return file;
    }

    private NwbClientRoom roomModel = null;
    private NwbClientSignIn signIn = null;
    
    // Network, Local mode
    @Action
    public void doNetwork(ActionEvent evt){
        System.out.println("Change to network mode...");
        if(roomModel != null)
        {
        	roomModel.exitRoom();
        	roomModel = null;
        }
        roomModel = new NwbClientRoom();
        mediator.modeChanged(true);
    	NwbMenuFactory.toggleModeMenu(true);
        
		signIn = new NwbClientSignIn(roomModel);
		signIn.setVisible(true);
    }
    
    @Action
    public void doLocal(ActionEvent evt){
        System.out.println("Change to local mode...");
        if(roomModel != null)
        {
        	roomModel.exitRoom();
        	roomModel = null;
        }
        
        if(signIn != null)
        {
        	signIn.signOut();
        	signIn = null;
        }
        
		NwbMenuFactory.toggleFileMenu(false, false);
        
        NwbControllerFactory.setModel(NwbClientModelFactory.createLocalModel());
        mediator.modeChanged(false);
    	NwbMenuFactory.toggleModeMenu(false);
    }
}
