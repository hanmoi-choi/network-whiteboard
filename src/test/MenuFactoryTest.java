package test;

import client.view.factory.NwbMenuFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.swing.*;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 8:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class MenuFactoryTest {

    private JMenu fileMenu;
    private JMenu editMenu;

    @BeforeTest
    public void init() {
        fileMenu = NwbMenuFactory.createFileMenu();
        editMenu = NwbMenuFactory.createEditMenu();
    }

    @Test
    public void shouldCreateJMenuProperly(){
        assertThat(fileMenu).isInstanceOf(JMenu.class);
        assertThat(editMenu).isInstanceOf(JMenu.class);
    }
}
