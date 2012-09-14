package client.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NwbPaintable {
    public List<JMenuItem>  getFileMenuItems();
    public List<JMenuItem> getEditMenuItems();
    public Canvas getDrawingCanvs();
}
