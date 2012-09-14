package test;

import client.view.NwbJMenu;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.swing.*;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbMenuTest {

    private JMenuItem item1;
    private JMenuItem item2;
    private JMenuItem item3;
    private JMenuItem item4;
    private JSeparator spr;
    private NwbJMenu jMenu;

    @BeforeTest
    public void initOnlyOnce(){
        item1 = new JMenuItem("item1");
        item2 = new JMenuItem("item2");
        item3 = new JMenuItem("item3");
        item4 = new JMenuItem("item4");
        spr = new JSeparator();
    }

    @BeforeMethod
    public void initEveryTimeOnEachTest(){
        jMenu = new NwbJMenu();
    }

    @Test
    public void shallReturnOnlyTheNumberOfJMenuItem_WhenMenuItemCountIsRequested(){
        jMenu.add(item1);
        jMenu.add(item2);
        jMenu.add(item3);
        jMenu.add(item4);
        jMenu.add(spr);

        assertThat(jMenu.jMenuItemCount()).isEqualTo(4);
        assertThat(jMenu.getItemCount()).isEqualTo(5);
    }

    @Test
    public void shouldReturnItemListThatIsAdded(){
        jMenu.add(item1);
        jMenu.add(item2);

        assertThat(jMenu.getMenuItems()).isNotEmpty()
                                        .contains(item1, item2);
    }
}
