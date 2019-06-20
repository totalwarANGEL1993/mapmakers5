package twa.siedelwood.s5.mapmaker.view.swing.panel.quest;

import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.BasicConfirmDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.ListSelectValueDialog;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Dialog for selecting behaviors
 */
public class QuestSelectBehaviorDialog extends ListSelectValueDialog implements ListSelectionListener {
    private JLabel description;

    public QuestSelectBehaviorDialog(JFrame frame) {
        super(frame);
    }

    public QuestSelectBehaviorDialog(JFrame frame, String title) {
        super(frame, title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initDialog() {
        width = 400;
        height = 600;

        dialog = new BasicConfirmDialog(this.frame, this.title) {
            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onConfirm() {
                QuestSelectBehaviorDialog.this.onConfirm();
            }
        };
        setSize(width, height);
        dialog.initDialog();

        JScrollPane scrollPane = new JScrollPane();
        valueSelect = new JList<>();
        valueSelect.setBounds(10, 10, width -35, 390);
        valueSelect.addListSelectionListener(this);
        valueSelect.setListData(values);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(valueSelect);
        scrollPane.setBounds(10, 10, width -35, 390);
        scrollPane.setVisible(true);
        dialog.add(scrollPane);

        description = new JLabel("");
        description.setFont(new Font("Arial", Font.PLAIN, 12));
        description.setVerticalAlignment(JLabel.TOP);
        description.setVerticalTextPosition(JLabel.TOP);
        description.setBounds(10, 410, width -35, 90);
        dialog.add(description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConfirm() {
        super.onConfirm();
    }

    /**
     *
     */
    protected void onSelectionChanged() {

    }

    /**
     *
     * @param text
     */
    public void setDescription(String text) {
        description.setText("<html><b>Beschreibung:</b><br>" +text+ "</html>");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == valueSelect) {
            onSelectionChanged();
        }
    }
}
