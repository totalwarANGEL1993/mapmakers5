package twa.siedelwood.s5.mapmaker.view.swing.component.panel.quest;

import twa.siedelwood.s5.mapmaker.controller.ApplicationConstants;
import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.model.data.quest.Quest;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestBehavior;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestCollection;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationBehaviorModel;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationParamaterModel;
import twa.siedelwood.s5.mapmaker.service.config.BehaviorPrototypeService;
import twa.siedelwood.s5.mapmaker.view.ViewPanel;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.EnterValueDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.input.InputComponent;
import twa.siedelwood.s5.mapmaker.view.swing.component.input.InputComponentMessageField;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * Panel for the quest editor tab.
 */
public class QuestViewPanel extends JPanel implements ViewPanel {
    protected int height;
    private QuestSelectQuestPanel questsGroup;
    private QuestEditBehaviorPanel behaviorGroup;
    private QuestCollection questCollection;
    private int currentSelectedQuest = -1;
    private int currentSelectedBehavior = -1;

    /**
     * Constructor
     */
    public QuestViewPanel() {
        super();
    }

    /**
     * Initalizes the panel
     */
    @Override
    public void initPanel() {
        setLayout(null);

        questsGroup = new QuestSelectQuestPanel() {
            @Override
            protected void onNewQuestClicked() {
                QuestViewPanel.this.onNewQuestClicked();
            }

            @Override
            protected void onCopieQuestClicked() {
                QuestViewPanel.this.onCopyQuestClicked();
            }

            @Override
            protected void onRenameQuestClicked() {
                QuestViewPanel.this.onRenameQuestClicked();
            }

            @Override
            protected void onDeleteQuestClicked() {
                QuestViewPanel.this.onDeleteQuestClicked();
            }

            @Override
            protected void onQuestSelectionChanged() {
                QuestViewPanel.this.onQuestSelectionChanged();
            }

            @Override
            protected void onUpdateQuestDataClicked() {
                QuestViewPanel.this.onUpdateQuestDataClicked();
            }

            @Override
            protected void onFilterChanged(String text) {
                QuestViewPanel.this.onFilterChanged(text, this);
            }
        };
        questsGroup.initPanel();
        add(questsGroup);

        behaviorGroup = new QuestEditBehaviorPanel() {
            @Override
            protected void onBehaviorDeleted() {
                QuestViewPanel.this.onBehaviorDeleted();
            }

            @Override
            protected void onBehaviorCopied() {
                QuestViewPanel.this.onBehaviorCopied();
            }

            @Override
            protected void onNewBehaviorAttached() {
                QuestViewPanel.this.onNewBehaviorAttached();
            }

            @Override
            protected void onBehaviorDoubleClicked() {
                QuestViewPanel.this.onBehaviorDoubleClicked();
            }

            @Override
            protected void onBehaviorSelectionChanged() {
                QuestViewPanel.this.onBehaviorSelectionChanged();
            }
        };
        behaviorGroup.initPanel();
        add(behaviorGroup);
    }

    /**
     * Searches and selects a sutible quest-
     * @param text Search pattern
     * @param panel Panel
     */
    private void onFilterChanged(String text, QuestSelectQuestPanel panel) {
        if (text == null || text.equals("")) {
            panel.questList.setSelectedIndex(-1);
        }
        for (Quest q : questCollection.getQuestList()) {
            if (q.getName().toLowerCase().contains(text.toLowerCase())) {
                panel.questList.setSelectedValue(q.getName(), true);
                break;
            }
        }
    }

    /**
     * The user saved changes to the quest properties
     */
    private void onUpdateQuestDataClicked() {
        if (currentSelectedQuest != -1) {
            Quest quest = questCollection.get(currentSelectedQuest);
            quest.setVisible(questsGroup.questVisibility.getSelectedIndex() == 1);
            quest.setTitle(questsGroup.questTitle.getText());
            quest.setType((String) questsGroup.questType.getSelectedItem());
            quest.setReceiver(questsGroup.questReceiver.getSelectedIndex()+1);
            quest.setTime(Integer.parseInt(questsGroup.questLimit.getText()));
            String text = questsGroup.questDescription.getText();
            text = text.replaceAll("\\n", "{cr}");
            quest.setText(text);
        }
    }

    /**
     * The user has selected a behavior
     */
    private void onBehaviorSelectionChanged() {
        Integer selectedIndex = behaviorGroup.getSelectedBehaviorIndex();
        if (selectedIndex == null) {
            selectedIndex = -1;
        }
        currentSelectedBehavior = selectedIndex;
    }

    /**
     * Opens the properties of this behavior.
     * @param behavior Behavior to open
     */
    private void openBehaviorProperties(QuestBehavior behavior) {
        if (behavior != null && behavior.getParameters().size() > 1) {
            QuestDefaultBehaviorPropertiesDialog dialog = new QuestDefaultBehaviorPropertiesDialog(behavior) {
                @Override
                public void onConfirm() {
                    Vector<InputComponent> inputFields = getComponents();
                    for (InputComponent c : inputFields) {
                        if (c.getValue() == null || c.getValue().equals("")) {
                            if (!(c instanceof InputComponentMessageField)) {
                                ApplicationController.getInstance().getMessageService().displayWarningMessage(
                                    "Pflichtfelder ausf??llen",
                                    "Einige Felder wurden noch nicht mit einem Wert versehen! Alle Felder " +
                                    "sollten mit einem Wert versehen werden!"
                                );
                                break;
                            }
                        }
                    }
                    super.onConfirm();
                    int index = 0;
                    for (InputComponent c : components) {
                        index++;
                        behavior.setParameter(index, c.getValue());
                    }
                }
            };
            dialog.initDialog();
            dialog.showDialog();
        }
    }

    /**
     * The user opens the behavior properties
     */
    private void onBehaviorDoubleClicked() {
        QuestBehavior behavior = questCollection.get(currentSelectedQuest).getBehaviorList().get(currentSelectedBehavior);
        openBehaviorProperties(behavior);
    }

    /**
     * The user created a behaviot
     */
    private void onNewBehaviorAttached() {
        if (currentSelectedQuest != -1) {
            BehaviorPrototypeService behaviorService = ApplicationController.getInstance().getBehaviorPrototypeService();
            QuestSelectBehaviorDialog dialog = new QuestSelectBehaviorDialog(null, "Behavior ausw??hlen") {
                @Override
                public void onConfirm() {
                    super.onConfirm();
                    ConfigurationBehaviorModel behaviorModel = behaviorService.getBehaviorPrototype(getValue());

                    ArrayList<String> parameters = new ArrayList<>();
                    parameters.add(behaviorModel.getType());
                    for (ConfigurationParamaterModel p : behaviorModel.getParameters()) {
                        parameters.add("");
                    }
                    Quest quest = questCollection.get(currentSelectedQuest);
                    QuestBehavior behavior = new QuestBehavior();
                    behavior.setParameters(parameters);
                    quest.getBehaviorList().add(behavior);
                    quest.sort();
                    behaviorGroup.setBehaviors(new Vector<>(quest.getBehaviorList()));
                    behaviorGroup.setSelectedBehavior(behavior);
                    onBehaviorSelectionChanged();

                    if (parameters.size() > 1) {
                        openBehaviorProperties(behavior);
                    }
                }

                @Override
                protected void onSelectionChanged() {
                    ConfigurationBehaviorModel behaviorModel = behaviorService.getBehaviorPrototype(valueSelect.getSelectedValue());
                    setDescription(behaviorModel.getDescription());
                }
            };
            dialog.initDialog();
            dialog.setValues(behaviorService.getBehaviorPrototypeNames());
            dialog.showDialog();
        }
    }

    /**
     * The user copied a behavior
     */
    private void onBehaviorCopied() {
        if (currentSelectedBehavior != -1) {
            Quest quest = questCollection.get(currentSelectedQuest);
            QuestBehavior oldBehavior = quest.getBehaviorList().get(currentSelectedBehavior);
            QuestBehavior newBehavior = oldBehavior.clone();
            quest.getBehaviorList().add(newBehavior.clone());
            quest.sort();
            int idx = currentSelectedBehavior;
            behaviorGroup.setBehaviors(new Vector<>(quest.getBehaviorList()));
            onBehaviorSelectionChanged();
            behaviorGroup.setSelectedBehaviorIndex(idx +1);
            if (newBehavior.getParameters().size() > 1) {
                openBehaviorProperties(quest.getBehaviorList().get(idx +1));
            }
        }
    }

    /**
     * The user deleted a behavior
     */
    private void onBehaviorDeleted() {
        if (currentSelectedBehavior != -1) {
            ApplicationController controller = ApplicationController.getInstance();
            Quest quest = questCollection.get(currentSelectedQuest);
            QuestBehavior behavior = quest.getBehaviorList().get(currentSelectedBehavior);
            int result = controller.getMessageService().displayConfirmDialog(
                "Behavior l??schen",
                "Soll dieses Behavior wirklich aus dem Auftrag entfernt werden?"
            );
            if (result == 0) {
                int selected = currentSelectedBehavior;
                quest.getBehaviorList().remove(selected);
                behaviorGroup.setBehaviors(new Vector<>(quest.getBehaviorList()));
                if (quest.getBehaviorList().size() > 0) {
                    if (selected > 0) {
                        selected--;
                    }
                    behaviorGroup.setSelectedBehaviorIndex(selected);
                }
                onBehaviorSelectionChanged();
            }
        }
    }

    /**
     * The user selected a quest
     */
    private void onQuestSelectionChanged() {
        Integer selected = questsGroup.getSelectedQuestIndex();
        if (selected != null && selected != -1) {
            currentSelectedQuest = selected;
            questsGroup.setSelectedQuestIndex(selected);
            questsGroup.updateQuestProperties(questCollection.get(currentSelectedQuest));
            questsGroup.onQuestVisibilityChanged();
            Quest quest = questCollection.get(currentSelectedQuest);
            behaviorGroup.setBehaviors(new Vector<>(quest.getBehaviorList()));
        }
        else {
            currentSelectedQuest = -1;
            questsGroup.clearSelection();
            questsGroup.updateQuestProperties(questCollection.get(currentSelectedQuest));
            questsGroup.onQuestVisibilityChanged();
        }
        questsGroup.onEditQuestPropertiesChanged(false);
        behaviorGroup.clearSelection();
        onBehaviorSelectionChanged();
    }

    /**
     * The user deleted a quest
     */
    private void onDeleteQuestClicked() {
        if (currentSelectedQuest != -1) {
            ApplicationController controller = ApplicationController.getInstance();
            Quest quest = questCollection.get(currentSelectedQuest);
            int result = controller.getMessageService().displayConfirmDialog(
                "Auftrag l??schen",
                "Soll dieser Auftrag wirklich gel??scht werden?"
            );
            if (result == 0) {
                Vector<String> referencingQuests = questCollection.getQuestsReferencingQuest(quest);
                if (referencingQuests.size() > 0) {
                    String quests = "";
                    for (String s : referencingQuests) {quests += "<br>" + s;}
                    controller.getMessageService().displayErrorMessage(
                        "Referenzen gefunden",
                        "<html>Auftr??ge k??nnen nur gel??scht werden, wenn sie von keinem Auftrag referenziert" +
                        " werden!<br>Der Auftrag wird von diesen Auftr??gen benutzt:<br><code>" +quests+ "</code>" +
                        "</html>"
                    );
                    return;
                }

                questCollection.remove(currentSelectedQuest);
                currentSelectedQuest = (questCollection.getQuestList().size() > 0) ? 0 : -1;
                questsGroup.setQuests(controller.getSelectableValueService().getQuestNames());
                if (currentSelectedQuest > -1) {
                    questsGroup.setSelectedQuestIndex(currentSelectedQuest);
                }
                onQuestSelectionChanged();

                behaviorGroup.setBehaviors(new Vector<>());
                behaviorGroup.clearSelection();
                onBehaviorSelectionChanged();
            }
        }
    }

    /**
     * The user renamed a quest
     */
    private void onRenameQuestClicked() {
        if (currentSelectedQuest != -1) {
            ApplicationController controller = ApplicationController.getInstance();
            Quest quest = questCollection.getQuestList().get(currentSelectedQuest);
            EnterValueDialog dialog = new EnterValueDialog(null) {
                @Override
                public void onConfirm() {
                    super.onConfirm();
                    if (!checkQuestName(getValue())) {
                        return;
                    }
                    QuestCollection questCollection = ApplicationController.getInstance().getCurrentProject().getQuestCollection();
                    String oldName = quest.getName();
                    quest.setName(getValue());
                    questCollection.sort();
                    questsGroup.setQuests(controller.getSelectableValueService().getQuestNames());
                    questsGroup.setSelectedQuest(quest.getName());
                    questCollection.replaceQuestNameInReferencingQuests(oldName, getValue());
                    onQuestSelectionChanged();
                }
            };
            dialog.initDialog();
            dialog.setValue(quest.getName());
            dialog.showDialog();
        }
    }

    /**
     * The user cloned a quest
     */
    private void onCopyQuestClicked() {
        if (currentSelectedQuest != -1) {
            ApplicationController controller = ApplicationController.getInstance();
            Quest quest = questCollection.getQuestList().get(currentSelectedQuest);
            EnterValueDialog dialog = new EnterValueDialog(null) {
                @Override
                public void onConfirm() {
                    super.onConfirm();
                    if (!checkQuestName(getValue())) {
                        return;
                    }
                    Quest newQuest = quest.clone();
                    newQuest.setName(getValue());
                    questCollection.add(newQuest);
                    questCollection.sort();
                    questsGroup.setQuests(controller.getSelectableValueService().getQuestNames());
                    questsGroup.setSelectedQuest(newQuest.getName());
                    onQuestSelectionChanged();
                }
            };
            dialog.initDialog();
            dialog.setValue(quest.getName() + "_Kopie");
            dialog.showDialog();
        }
    }

    /**
     * The user creates a new quest
     */
    private void onNewQuestClicked() {
        ApplicationController controller = ApplicationController.getInstance();
        EnterValueDialog dialog = new EnterValueDialog(null) {
            @Override
            public void onConfirm() {
                super.onConfirm();
                if (!checkQuestName(getValue())) {
                    return;
                }
                Quest quest = new Quest();
                quest.setName(getValue());
                quest.setVisible(false);
                questCollection.add(quest);
                questCollection.sort();
                questsGroup.setQuests(controller.getSelectableValueService().getQuestNames());
                questsGroup.setSelectedQuest(quest.getName());
                currentSelectedQuest = questsGroup.getSelectedQuestIndex();
                onQuestSelectionChanged();
            }
        };
        dialog.initDialog();
        dialog.showDialog();
    }

    /**
     * Checks the quest name if it is correctly spelled and long enough.
     * @param value Name to check
     * @return Name ok
     */
    private boolean checkQuestName(String value) {
        ApplicationController controller = ApplicationController.getInstance();
        if (value.length() < 1 && !Pattern.matches(ApplicationConstants.DEFAULT_NAME_REGEX, value)) {
            controller.getMessageService().displayErrorMessage(
                    "Ung??ltiger Name",
                    "Der angegebene Name ist ung??ltig oder zu kurz! M??glicher Weise ist enth??lt er verbotene Zeichen.",
                    controller.getWorkbenchWindow()
            );
            return false;
        }

        if (controller.getSelectableValueService().getQuestNames().contains(value)) {
            controller.getMessageService().displayErrorMessage(
                    "Duplikat",
                    "Es existiert bereits ein Auftrag mit dem angegebenen Namen! Bitte w??hle einen anderen Namen f??r den Auftrag aus.",
                    controller.getWorkbenchWindow()
            );
            return false;
        }
        return true;
    }

    /**
     * Loads from the given quest collection.
     * @param questCollection Quest collection
     */
    public void loadQuestData(QuestCollection questCollection) {
        this.questCollection = questCollection;
        ApplicationController controller = ApplicationController.getInstance();
        if (questsGroup != null) {
            questsGroup.setQuests(controller.getSelectableValueService().getQuestNames());
            if (questCollection.getQuestList().size() > 0) {
                questsGroup.setSelectedQuestIndex(0);
            }
            onQuestSelectionChanged();
        }
    }

    /**
     * User resized the window
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth();
        height = reference.getHeight();
        questsGroup.updatePanelSize(reference);
        questsGroup.setLocation(0, 5);
        behaviorGroup.updatePanelSize(reference);
        behaviorGroup.setLocation(0, questsGroup.getContentHeight() + 15);
        setSize(reference.getWidth(), getContentHeight());
    }

    /**
     * Returns the conrent height.
     * @return Height
     */
    public int getContentHeight() {
        return questsGroup.getContentHeight() + 20;
    }

    //// Unused ////

    @Override
    public void setPanelHeight(int height) {}

    @Override
    public void setPanelWidth(int height) {}

    private void panelResizedByCode() {}

    @Override
    public int getPanelHeight() {return 0;}

    @Override
    public int getPanelWidth() {return 0;}

    @Override
    public void setVerticalOffset(int offset) {}

    @Override
    public int getVerticalOffset() {return 0;}
}
