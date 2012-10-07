package client.view.ui.comp;

import client.view.CanvasDrawble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 21/09/12
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbCanvasSizeDialog extends JDialog{
    private JFrame frmCanvasSize;
    private JTextField tfWidth;
    private JTextField tfHeight;
    private JComboBox cbPreset;
    private JLabel lblPreset;
    private CanvasDrawble parent;

    public NwbCanvasSizeDialog(CanvasDrawble parent) {
        super((Dialog) null, true);
        this.parent = parent;
        initialize();
    }

    private void initialize() {

        this.setFont(null);
        this.setTitle("Canvas Size");
        this.setBounds(100, 100, 300, 200);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(null);

        cbPreset = new JComboBox();
        cbPreset.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        cbPreset.setModel(new DefaultComboBoxModel(
                new String[]{"640 x 480", "800 x 600", "1024 x 768", "1280 x 1024", "Custom"}));
        cbPreset.setBounds(115, 18, 136, 27);
        cbPreset.addActionListener(new ComboBoxActionListener());
        container.add(cbPreset);


        JLabel lblWidth = new JLabel("Width:");
        lblWidth.setHorizontalAlignment(SwingConstants.CENTER);
        lblWidth.setBounds(44, 57, 61, 28);
        container.add(lblWidth);

        tfWidth = new JTextField("640");
        tfWidth.setBounds(115, 57, 84, 28);
        container.add(tfWidth);
        tfWidth.setColumns(10);
        tfWidth.requestFocus();

        JLabel lblHeight = new JLabel("Height:");
        lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
        lblHeight.setBounds(44, 97, 61, 28);
        container.add(lblHeight);

        tfHeight = new JTextField("480");
        tfHeight.setColumns(10);
        tfHeight.setBounds(115, 97, 84, 28);
        container.add(tfHeight);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(161, 143, 90, 29);
        container.add(btnOk);

        lblPreset = new JLabel("Preset:");
        lblPreset.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreset.setBounds(44, 22, 61, 16);
        container.add(lblPreset);

        JLabel lblPixels = new JLabel("Pixels");
        lblPixels.setBounds(224, 63, 61, 16);
        container.add(lblPixels);

        JLabel label = new JLabel("Pixels");
        label.setBounds(224, 103, 61, 16);
        container.add(label);
	}

    class ComboBoxActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> comboBox = (JComboBox<String>)e.getSource();

            String selectedItem = (String) comboBox.getSelectedItem();
            selectedItem = selectedItem.trim();

            if(selectedItem.toLowerCase().equals("custom")){
            }
            else {
                String[] sizes = selectedItem.split("x");
                tfWidth.setText(sizes[0].trim());
                tfHeight.setText(sizes[1].trim());
            }

            tfWidth.requestFocus();
        }
    }
}
