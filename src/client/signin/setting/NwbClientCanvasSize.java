package client.signin.setting;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class NwbClientCanvasSize extends AbstractListModel implements
		ComboBoxModel {
	
	String selectedItem = null;
	String[] size = { "300*168","819*460", "1024*768",};

	public Object getElementAt(int index) {
		return size[index];
	}

	public int getSize() {
		return size.length;
	}

	public void setSelectedItem(Object item) {
		selectedItem = (String) item;
	}

	public Object getSelectedItem() {
		return selectedItem;
	}
}