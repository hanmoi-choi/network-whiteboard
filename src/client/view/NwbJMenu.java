package client.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbJMenu extends JMenu {

    private List<JMenuItem> itemList = new ArrayList<JMenuItem>();

    public NwbJMenu(String name) {
        super(name);

    }

    public NwbJMenu() {
        super();
    }

    public  List<JMenuItem> getMenuItems(){
        return itemList;
    }

    @Override
    public JMenuItem add(JMenuItem menuItem) {
        super.add(menuItem);

        itemList.add(menuItem);
        return menuItem;
    }

    public int jMenuItemCount(){
        return itemList.size();
    }

}
