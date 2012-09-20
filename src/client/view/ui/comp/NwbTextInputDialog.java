package client.view.ui.comp;

import client.view.CanvasDrawble;
import say.swing.JFontChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NwbTextInputDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private final TextInputDialogActionListener actionListener = new TextInputDialogActionListener();
    private final JTextPane textPane;
    private CanvasDrawble parent;

    public NwbTextInputDialog(CanvasDrawble parent) {
        super((Dialog) null, true);
        this.parent = parent;

        setTitle("Text Input");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        textPane = new JTextPane();
        contentPanel.add(textPane, BorderLayout.CENTER);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnFont = new JButton("Font");
        btnFont.setActionCommand("Font");
        btnFont.addActionListener(actionListener);
        buttonPane.add(btnFont);

        JButton btnOk = new JButton("OK");
        btnOk.setActionCommand("OK");
        btnOk.addActionListener(actionListener);
        buttonPane.add(btnOk);
        getRootPane().setDefaultButton(btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setActionCommand("Cancel");
        btnCancel.addActionListener(actionListener);
        buttonPane.add(btnCancel);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    class TextInputDialogActionListener implements ActionListener{

        private Font font;

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton)e.getSource();

            if(sourceButton.getActionCommand().equals("Font")){
                JFontChooser fontChooser = new JFontChooser();
                int result = fontChooser.showDialog(NwbTextInputDialog.this);
                if (result == JFontChooser.OK_OPTION)
                {
                    font = fontChooser.getSelectedFont();
                    NwbTextInputDialog.this.textPane.setFont(font);
                }
            }

            else if(sourceButton.getActionCommand().equals("OK")){
                String text = NwbTextInputDialog.this.textPane.getText();
                NwbTextInputDialog.this.parent.textDraw(text, font);
                NwbTextInputDialog.this.dispose();
            }
            else if(sourceButton.getActionCommand().equals("Cancel")){
                NwbTextInputDialog.this.dispose();
            }
        }
    }

}
