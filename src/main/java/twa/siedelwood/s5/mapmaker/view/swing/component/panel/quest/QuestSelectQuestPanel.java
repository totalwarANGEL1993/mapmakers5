package twa.siedelwood.s5.mapmaker.view.swing.component.panel.quest;

import twa.siedelwood.s5.mapmaker.model.data.quest.Quest;
import twa.siedelwood.s5.mapmaker.view.swing.component.swing.JOnChangeTextFieldPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Vector;

/**
 * Panel for the quest selection and quest property change.
 */
public class QuestSelectQuestPanel extends JPanel implements ActionListener, ListSelectionListener, FocusListener, PropertyChangeListener {
    int height = 450;
    protected JLabel questInfo;
    protected JList<String> questList;
    protected JScrollPane questListScroll;
    protected JOnChangeTextFieldPanel filterTextarea;
    protected JButton[] buttons;
    protected JComboBox<String> questVisibility;
    protected JLabel questVisibilityLabel;
    protected JLabel questTypeLabel;
    protected JComboBox<String> questType;
    protected JTextField questTitle;
    protected JTextArea questDescription;
    protected JScrollPane questDescriptionScroll;
    protected JLabel questTitleLabel;
    protected JLabel questDescriptionLabel;
    protected JLabel questReceiverLabel;
    protected JComboBox<String> questReceiver;
    protected JLabel questLimitLabel;
    protected JFormattedTextField questLimit;

    /**
     * Constructor
     */
    public QuestSelectQuestPanel() {
        super();
    }

    /**
     * Initalizes the panel
     */
    public void initPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Quests"));
        createQuestControlls();
        createQuestProperties();
        onQuestVisibilityChanged();
    }

    /**
     * Creates the controlls to select, create, change and delete quests.
     */
    public void createQuestControlls() {
        questInfo = new JLabel(
                "<html>Hier können Aufträge verwaltet werden. Du kannst Aufträge erstellen, kopieren," +
                        " umbenennen und löschen. Die Eigenschhaften eines Auftrages werden bearbeitet," +
                        " in dem der Auftrag aus der Liste ausgewählt wird. Um Eigenschaften zu aktualisieren," +
                        " klicke auf \"Update\".</html>"
        );
        questInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        questInfo.setVerticalAlignment(JLabel.TOP);
        questInfo.setVerticalTextPosition(JLabel.TOP);
        add(questInfo);

        questList = new JList<>();
        questList.setFont(new Font("Arial", Font.PLAIN, 12));
        questList.addListSelectionListener(this);
        questList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questListScroll = new JScrollPane();
        questListScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        questListScroll.setViewportView(questList);
        questListScroll.setVisible(true);
        add(questListScroll);

        buttons = new JButton[5];

        buttons[0] = new JButton("Neu");
        buttons[0].addActionListener(this);
        add(buttons[0]);
        buttons[1] = new JButton("Kopie");
        buttons[1].addActionListener(this);
        add(buttons[1]);
        buttons[2] = new JButton("Umbenennen");
        buttons[2].addActionListener(this);
        add(buttons[2]);
        buttons[3] = new JButton("Löschen");
        buttons[3].addActionListener(this);
        add(buttons[3]);
        buttons[4] = new JButton("Update");
        buttons[4].addActionListener(this);
        add(buttons[4]);

        filterTextarea = new JOnChangeTextFieldPanel("Suchen...") {
            @Override
            protected void onTextChanged(String text) {
                QuestSelectQuestPanel.this.onFilterChanged(text);
            }
        };
        add(filterTextarea);
    }

    /**
     * Creates the quest properties
     */
    private void createQuestProperties() {
        questVisibilityLabel = new JLabel("Sichtbarkeit");
        add(questVisibilityLabel);
        questVisibility = new JComboBox<String>();
        questVisibility.addItem("versteckt");
        questVisibility.addItem("sichtbar");
        questVisibility.setSelectedIndex(0);
        questVisibility.addActionListener(this);
        add(questVisibility);

        questTypeLabel = new JLabel("Typ");
        add(questTypeLabel);
        questType = new JComboBox<String>();
        questType.addItem("MAINQUEST_OPEN");
        questType.addItem("SUBQUEST_OPEN");
        questType.addItem("FRAGMENTQUEST_OPEN");
        add(questType);

        questReceiverLabel = new JLabel("Empfänger");
        add(questReceiverLabel);
        questReceiver = new JComboBox<String>();
        for (int i=1; i<9; i++) {
            questReceiver.addItem("Spieler " + i);
        }
        add(questReceiver);

        questTitleLabel = new JLabel("Titel");
        add(questTitleLabel);
        questTitle = new JTextField("");
        questTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        add(questTitle);

        questDescriptionLabel = new JLabel("Beschreibung");
        add(questDescriptionLabel);
        questDescriptionScroll = new JScrollPane();
        questDescription = new JTextArea("");
        questDescription.setFont(new Font("Arial", Font.PLAIN, 12));
        questDescription.setLineWrap(true);
        questDescription.setWrapStyleWord(true);
        questDescriptionScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questDescriptionScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        questDescriptionScroll.setViewportView(questDescription);
        questDescriptionScroll.setVisible(true);
        add(questDescriptionScroll);

        questLimitLabel = new JLabel("Zeitlimit");
        add(questLimitLabel);
        NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
        integerFieldFormatter.setGroupingUsed(false);
        questLimit = new JFormattedTextField(integerFieldFormatter);
        questLimit.setFont(new Font("Arial", Font.PLAIN, 12));
        questLimit.setValue(0);
        questLimit.addFocusListener(this);
        questLimit.addPropertyChangeListener(this);
        add(questLimit);
    }

    /**
     * The user has create a new quest.
     */
    protected void onNewQuestClicked() {

    }

    /**
     * The user has copied a quest.
     */
    protected void onCopieQuestClicked() {

    }

    /**
     * The user has renamed a quest.
     */
    protected void onRenameQuestClicked() {

    }

    /**
     * The user has deleted a quest.
     */
    protected void onDeleteQuestClicked() {

    }

    /**
     * The user has selected a quest.
     */
    protected void onQuestSelectionChanged() {

    }

    /**
     * The user saves changes to the quest data.
     */
    protected void onUpdateQuestDataClicked() {

    }

    /**
     * The user changed the visibility of the quest.
     */
    public void onQuestVisibilityChanged() {
        boolean elementsEditable = questVisibility.getSelectedIndex() == 1;
        questType.setVisible(elementsEditable);
        questTypeLabel.setVisible(elementsEditable);
        questTitle.setVisible(elementsEditable);
        questTitleLabel.setVisible(elementsEditable);
        questDescription.setVisible(elementsEditable);
        questDescriptionLabel.setVisible(elementsEditable);
        questDescriptionScroll.setVisible(elementsEditable);
        /*
        questReceiver.setVisible(elementsEditable);
        questReceiverLabel.setVisible(elementsEditable);
        questLimit.setVisible(elementsEditable);
        questLimitLabel.setVisible(elementsEditable);
        */
    }

    /**
     * Sets the list of quest names.
     * @param data List of names
     */
    public void setQuests(Vector<String> data) {
        questList.setListData(data);
    }

    /**
     * Sets the selected item by quest name.
     * @param pageName Name of quest
     */
    public void setSelectedQuest(String pageName) {
        questList.setSelectedValue(pageName, true);
    }

    /**
     *
     * @param idx
     */
    public void setSelectedQuestIndex(int idx) {
        questList.setSelectedIndex(idx);
    }

    /**
     * Returns the selected quest name.
     * @return Name of quest
     */
    public String getSelectedQuest() {
        return questList.getSelectedValue();
    }

    /**
     * Returns the selected quest index.
     * @return Index of quest
     */
    public Integer getSelectedQuestIndex() {
        return questList.getSelectedIndex();
    }

    /**
     *
     * @param quest
     */
    public void updateQuestProperties(Quest quest) {
        if (quest != null) {
            int index;
            switch (quest.getType()) {
                case "SUBQUEST_OPEN": index = 1; break;
                case "FRAGMENTQUEST_OPEN": index = 2; break;
                default: index = 0;
            }
            questType.setSelectedIndex(index);
            questTitle.setText(quest.getTitle());
            questDescription.setText(quest.getText());
            questReceiver.setSelectedIndex(quest.getReceiver() - 1);
            questLimit.setValue(quest.getTime());
            questVisibility.setSelectedIndex(quest.isVisible() ? 1 : 0);
            questVisibility.setEnabled(true);
            buttons[4].setEnabled(true);
            questReceiver.setEnabled(true);
            questLimit.setEnabled(true);
        }
        else {
            questType.setSelectedIndex(0);
            questTitle.setText("");
            questDescription.setText("");
            questReceiver.setSelectedIndex(0);
            questLimit.setValue(0);
            questVisibility.setSelectedIndex(0);
            questVisibility.setEnabled(false);
            buttons[4].setEnabled(false);
            questReceiver.setEnabled(false);
            questLimit.setEnabled(false);
        }
        onQuestVisibilityChanged();
    }

    /**
     *
     */
    public void clearSelection() {
        questList.clearSelection();
    }

    /**
     *
     */
    public void clearFilter() {
        filterTextarea.clear();
    }

    /**
     *
     * @param text
     */
    protected void onFilterChanged(String text) {}

    /**
     * The user has resized the window.
     * @param reference Reference object
     */
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth() - 10;
        height = (int) ((reference.getHeight() * 0.6) -15);
        setSize(width, height);
        questInfo.setBounds(10, 20, width -20, 45);
        int selectionWidth = ((width * 0.5) -10 < 500) ? (int) ((width * 0.5) - 10) : 500;
        questListScroll.setBounds(10, 75, selectionWidth, height -160);
        questList.setSize(selectionWidth, height -160);
        filterTextarea.resizeComponent(10, height -85, selectionWidth, 40);

        for (int i=0; i<4; i++) {
            buttons[i].setBounds((10 + (135 * i)), height -35, 130, 25);
        }

        int propertiesWidth = ((width * 0.5) -20 < 700) ? (int) (width * 0.5) -20 : 700;
        buttons[4].setBounds(selectionWidth +20, 75, 130, 25);
        questVisibilityLabel.setBounds(selectionWidth +20, 105, 200, 20);
        questVisibility.setBounds(selectionWidth +20, 125, 180, 20);

        questReceiverLabel.setBounds(selectionWidth +20, 165, 200, 20);
        questReceiver.setBounds(selectionWidth +20, 185, 180, 20);
        questLimitLabel.setBounds(selectionWidth +20, 210, 200, 15);
        questLimit.setBounds(selectionWidth +20, 225, 180, 20);
        questTypeLabel.setBounds(selectionWidth +20, 250, 200, 15);
        questType.setBounds(selectionWidth +20, 265, 180, 20);
        questTitleLabel.setBounds(selectionWidth +20, 290, 200, 15);
        questTitle.setBounds(selectionWidth +20, 305, propertiesWidth, 20);
        questDescriptionLabel.setBounds(selectionWidth +20, 330, 200, 15);
        int textHeight = height - 387;
        questDescriptionScroll.setBounds(selectionWidth +20, 345, propertiesWidth, textHeight);
        questDescription.setSize(propertiesWidth, textHeight);
    }

    /**
     * Returns the hight of the content of this panel.
     * @return Content height
     */
    public int getContentHeight() {
        return height;
    }

    //// Listener stuff ////

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            onNewQuestClicked();
        }
        if (e.getSource() == buttons[1]) {
            onCopieQuestClicked();
        }
        if (e.getSource() == buttons[2]) {
            onRenameQuestClicked();
        }
        if (e.getSource() == buttons[3]) {
            onDeleteQuestClicked();
        }
        if (e.getSource() == buttons[4]) {
            onUpdateQuestDataClicked();
        }

        if (e.getSource() == questVisibility) {
            onQuestVisibilityChanged();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == questList) {
            onQuestSelectionChanged();
        }
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        try {
            JFormattedTextField src = (JFormattedTextField) focusEvent.getSource();
            SwingUtilities.invokeLater(src::selectAll);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        try {
            JFormattedTextField src = (JFormattedTextField) propertyChangeEvent.getSource();
            if (src.getText().startsWith("0") && src.getText().length() > 1) {
                src.setText(src.getText().substring(1));
            }
        }
        catch (Exception e) {}
    }
}
