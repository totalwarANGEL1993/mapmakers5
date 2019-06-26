package twa.siedelwood.s5.mapmaker.view.swing.panel.quest;

import twa.siedelwood.s5.mapmaker.model.data.quest.QuestBehavior;
import twa.siedelwood.s5.mapmaker.view.swing.component.renderer.BehaviorCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class QuestEditBehaviorPanel extends JPanel implements ActionListener, ListSelectionListener, MouseListener {
    private JLabel behaviorInfo;
    private JList<QuestBehavior> behaviorList;
    private JScrollPane scrollPane;
    private JButton[] buttons;
    private int height;

    public QuestEditBehaviorPanel() {
        super();
    }

    public void initPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Behavior"));

        behaviorInfo = new JLabel(
            "<html>In dieser Gruppe werden Behavior verwaltet. Du kannst Behavior erstellen," +
            " kopieren, und löschen. Die Eigenschhaften eines Behavior werden bearbeitet," +
            " in dem das Behavior aus der Liste doppelt geklickt wird.</html>"
        );
        behaviorInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        behaviorInfo.setVerticalAlignment(JLabel.TOP);
        behaviorInfo.setVerticalTextPosition(JLabel.TOP);
        add(behaviorInfo);

        behaviorList = new JList<>();
        behaviorList.setCellRenderer(new BehaviorCellRenderer());
        behaviorList.setFont(new Font("Arial", Font.PLAIN, 12));
        behaviorList.addListSelectionListener(this);
        behaviorList.addMouseListener(this);
        behaviorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(behaviorList);
        scrollPane.setVisible(true);
        add(scrollPane);

        buttons = new JButton[7];

        buttons[0] = new JButton("Neu");
        buttons[0].addActionListener(this);
        add(buttons[0]);
        buttons[1] = new JButton("Kopieren");
        buttons[1].addActionListener(this);
        add(buttons[1]);
        buttons[2] = new JButton("Löschen");
        buttons[2].addActionListener(this);
        add(buttons[2]);
    }

    /**
     *
     */
    protected void onBehaviorDeleted() {

    }

    /**
     *
     */
    protected void onBehaviorCopied() {

    }

    /**
     *
     */
    protected void onNewBehaviorAttached() {

    }

    /**
     *
     */
    protected void onBehaviorDoubleClicked() {

    }

    /**
     *
     */
    protected void onBehaviorSelectionChanged() {

    }

    /**
     *
     */
    public void clearSelection() {
        behaviorList.clearSelection();
    }

    /**
     *
     * @param data
     */
    public void setBehaviors(Vector<QuestBehavior> data) {
        behaviorList.setListData(data);
    }

    /**
     *
     * @param behavior
     */
    public void setSelectedBehavior(QuestBehavior behavior) {
        behaviorList.setSelectedValue(behavior, true);
    }

    /**
     *
     * @param idx
     */
    public void setSelectedBehaviorIndex(int idx) {
        behaviorList.setSelectedIndex(idx);
        behaviorList.ensureIndexIsVisible(idx);
    }

    /**
     *
     * @return
     */
    public QuestBehavior getSelectedBehavior() {
        return behaviorList.getSelectedValue();
    }

    /**
     *
     * @return
     */
    public Integer getSelectedBehaviorIndex() {
        if (behaviorList.isSelectionEmpty()) {
            return null;
        }
        return behaviorList.getSelectedIndex();
    }

    /**
     *
     * @param reference
     */
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth() - 10;
        height = (int) (reference.getHeight() * 0.4) -30;

        setSize(width, height);
        behaviorInfo.setBounds(10, 20, width -20, 45);
        int selectionWidth = (width -165 < 700) ? width -165 : 700;
        scrollPane.setBounds(10, 75, selectionWidth, height -105);
        behaviorList.setSize(selectionWidth, height -105);

        int y = 75;
        for (int i=0; i<3; i++) {
            buttons[i].setBounds(selectionWidth + 20, y, 130, 25);
            y = y + 35;
        }
    }

    public int getContentHeight() {
        return height;
    }

    //// Listener stuff ////

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            onNewBehaviorAttached();
        }
        if (e.getSource() == buttons[1]) {
            onBehaviorCopied();
        }
        if (e.getSource() == buttons[2]) {
            onBehaviorDeleted();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == behaviorList) {
            onBehaviorSelectionChanged();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == behaviorList) {
            if (e.getClickCount() == 2) {
                onBehaviorDoubleClicked();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
