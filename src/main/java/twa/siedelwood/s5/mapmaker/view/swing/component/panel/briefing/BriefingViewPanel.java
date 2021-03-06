package twa.siedelwood.s5.mapmaker.view.swing.component.panel.briefing;

import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.model.data.briefing.*;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestCollection;
import twa.siedelwood.s5.mapmaker.view.ViewPanel;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.EnterValueDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.ListSelectValueDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.SelectValueDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Main panel for briefings editor tab
 */
public class BriefingViewPanel extends JPanel implements ViewPanel {
    private int baseWidth = 0;
    private int baseHeight = 0;
    private BriefingSelectBriefingPanel briefingGroup;
    private BriefingEditPagePanel pageGroup;
    private BriefingCollection briefingCollection;
    private int currentSelectedBriefing;
    protected int height;
    private int currentSelectedPage;

    /**
     * Constructor
     */
    public BriefingViewPanel() {
        super();
    }

    /**
     * Checks if the project has any biefings.
     * @return Has briefings
     */
    public boolean thereAreBriefings() {
        return briefingCollection.getBriefings().size() > 0;
    }

    /**
     * Checks if the project has a briefing selected and this briefing has pages.
     * @return Pages found
     */
    public boolean thereArePages() {
        int idx = briefingGroup.getSelectedBriefingIndex();
        if (idx == -1) {
            return false;
        }
        Briefing briefing = briefingCollection.get(idx);
        return briefing.getPages().size() > 0;
    }

    /**
     * The user copied a briefing
     */
    private void onBriefingCopiedClicked() {
        Briefing briefing = briefingCollection.get(currentSelectedBriefing);
        if (thereAreBriefings()) {
            EnterValueDialog dialog = new EnterValueDialog(null) {
                @Override
                public void onConfirm() {
                    super.onConfirm();
                    if (!checkBriefingName(getValue())) {
                        return;
                    }
                    Briefing newBriefing = briefing.clone();
                    newBriefing.setName(getValue());
                    briefingCollection.add(newBriefing);
                    briefingCollection.sort();
                    briefingGroup.clearSelection();
                    briefingGroup.setSelectableBriefings(briefingCollection.toNamesVector());
                    briefingGroup.setSelectedBriefing(getValue());
                }
            };
            dialog.initDialog();
            dialog.setValue(briefing.getName() + "_Kopie");
            dialog.showDialog();
        }
    }

    /**
     * The user renamed a briefing
     */
    private void onBriefingRenamedClicked() {
        Briefing briefing = briefingCollection.get(briefingGroup.getSelectedBriefingIndex());
        if (thereAreBriefings()) {
            EnterValueDialog dialog = new EnterValueDialog(null) {
                @Override
                public void onConfirm() {
                    super.onConfirm();
                    if (!checkBriefingName(getValue())) {
                        return;
                    }
                    String oldName = briefing.getName();
                    briefing.setName(getValue());
                    briefingCollection.sort();
                    briefingGroup.clearSelection();
                    briefingGroup.setSelectableBriefings(briefingCollection.toNamesVector());
                    briefingGroup.setSelectedBriefing(getValue());

                    QuestCollection questCollection = ApplicationController.getInstance().getCurrentProject().getQuestCollection();
                    questCollection.replaceBriefingNameInReferencingQuests(oldName, getValue());
                }
            };
            dialog.initDialog();
            dialog.setValue(briefing.getName());
            dialog.showDialog();
        }
    }

    /**
     * The user deleted a briefing
     */
    private void onBriefingDeletedClicked() {
        if (thereAreBriefings()) {
            ApplicationController controller = ApplicationController.getInstance();
            Briefing briefing = briefingCollection.get(briefingGroup.getSelectedBriefingIndex());
            int result = controller.getMessageService().displayConfirmDialog(
                "Briefing l??schen",
                "Soll das Briefing wirklich vollst??ndig gel??scht werden?"
            );
            if (result == 0) {
                QuestCollection questCollection = ApplicationController.getInstance().getCurrentProject().getQuestCollection();
                Vector<String> referencingQuests = questCollection.getQuestsReferencingBriefing(briefing);
                if (referencingQuests.size() > 0) {
                    String briefings = "";
                    for (String s : referencingQuests) {briefings += "<br>" + s;}
                    controller.getMessageService().displayErrorMessage(
                        "Referenzen gefunden",
                        "<html>Briefings k??nnen nur gel??scht werden, wenn sie von keinem Auftrag referenziert" +
                        " werden!<br>Das Briefing wird von diesen Auftr??gen benutzt:<br><code>" +briefings+ "</code>" +
                        "</html>"
                    );
                    return;
                }
                briefingGroup.removeSelectableBriefing(briefing.getName());
                briefingCollection.remove(briefing.getName());
                pageGroup.setPages(new Vector<>());
                briefingGroup.setSelectedBriefing(0);
            }
        }
    }

    /**
     * The user created a briefing
     */
    private void onBriefingCreatedClicked() {
        EnterValueDialog dialog = new EnterValueDialog(null) {
            @Override
            public void onConfirm() {
                super.onConfirm();
                if (!checkBriefingName(getValue())) {
                    return;
                }
                Briefing newBriefing = new Briefing(getValue());
                briefingCollection.add(newBriefing);
                briefingCollection.sort();
                briefingGroup.clearSelection();
                briefingGroup.setSelectableBriefings(briefingCollection.toNamesVector());
                briefingGroup.setSelectedBriefing(newBriefing.getName());
            }
        };
        dialog.initDialog();
        dialog.showDialog();
    }

    /**
     * Checks a briefing name and displays error messages in case of failures.
     * @param name Name of briefing
     * @return Name valid
     */
    private boolean checkBriefingName(String name) {
        ApplicationController controller = ApplicationController.getInstance();
        if (!briefingCollection.validateName(name)) {
            controller.getMessageService().displayErrorMessage(
                    "Ung??ltiger Name",
                    "Der angegebene Name ist ung??ltig oder zu kurz! M??glicher Weise enth??lt er ung??ltige" +
                    " Zeichen. \n\nElaubte Zeichen: A-Z a-z 0-9 _",
                    controller.getWorkbenchWindow()
            );
            return false;
        }

        if (briefingCollection.doesBriefingNameExist(name)) {
            controller.getMessageService().displayErrorMessage(
                    "Duplikat",
                    "Es existiert bereits ein Briefing mit dem angegebenen Namen! Bitte w??hle einen anderen" +
                    " Namen f??r das Briefing aus.",
                    controller.getWorkbenchWindow()
            );
            return false;
        }
        return true;
    }

    /**
     * The user selected another briefing
     */
    private void onSelectedBriefingChanged() {
        if (briefingCollection != null) {
            currentSelectedBriefing = briefingGroup.getSelectedBriefingIndex();
            if (currentSelectedBriefing != -1) {
                Vector<BriefingPage> pageNames = new Vector<>(briefingCollection.getBriefings().get(currentSelectedBriefing).getPages());
                setPages(pageNames);
            }
        }
    }

    /**
     * The user selected a page
     */
    protected void onPageSelectionChanged() {
        int newIndex = pageGroup.getSelectedPageIndex();
        if (newIndex != -1) {
            currentSelectedPage = newIndex;
        }
    }

    /**
     * Opens the edit options if a page is double clicked.
     */
    private void onPageDoubleClicked() {
        if (currentSelectedPage != -1) {
            Briefing briefing = briefingCollection.getBriefings().get(currentSelectedBriefing);
            BriefingPage page = briefing.getPage(currentSelectedPage);
            openPageProperties(page, briefing);
        }
    }

    /**
     * Opens the properties of the page at the index.
     * @param page Page to edit
     * @param briefing Briefing of page
     */
    private void openPageProperties(BriefingPage page, Briefing briefing) {
        // Dialog page
        if (page.getType() == BriefingPageTypes.TYPE_DIALOG) {
            BriefingDialogPageProperties editDialog = new BriefingDialogPageProperties(page, null) {
                @Override
                protected void onEntitySelectClicked() {
                    Vector<String> scriptNames = ApplicationController.getInstance().getSelectableValueService().getScriptNames();
                    ListSelectValueDialog select = new ListSelectValueDialog(null, "Position ausw??hlen") {
                        @Override
                        public void onConfirm() {
                            super.onConfirm();
                            String nameSelected = (getValue() != null) ? getValue() : page.getEntity();
                            page.setEntity(nameSelected);
                            entity.setText(nameSelected);
                        }
                    };
                    select.initDialog();
                    select.setValues(scriptNames);
                    select.showDialog();
                }
            };
            editDialog.initDialog();
            editDialog.showDialog();
        }

        // Choice between 2 Options
        if (page.getType() == BriefingPageTypes.TYPE_CHOICE) {
            BriefingChoicePageProperties editDialog = new BriefingChoicePageProperties(page, null) {
                @Override
                protected void onEntitySelectClicked() {
                    Vector<String> scriptNames = ApplicationController.getInstance().getSelectableValueService().getScriptNames();
                    ListSelectValueDialog select = new ListSelectValueDialog(null, "Position ausw??hlen") {
                        @Override
                        public void onConfirm() {
                            super.onConfirm();
                            String nameSelected = (getValue() != null) ? getValue() : page.getEntity();
                            page.setEntity(nameSelected);
                            entity.setText(nameSelected);
                        }
                    };
                    select.initDialog();
                    select.setValues(scriptNames);
                    select.showDialog();
                }

                @Override
                public void onTargetOption1Clicked() {
                    Vector<String> pageNames = briefing.getPageNamesOfType(BriefingPageTypes.TYPE_DIALOG);
                    ListSelectValueDialog select = new ListSelectValueDialog(null, "Ziel ausw??hlen") {
                        @Override
                        public void onConfirm() {
                            super.onConfirm();
                            String nameSelectedPage = getValue();
                            page.setFirstSelectPage(nameSelectedPage);
                            op1Target.setText(nameSelectedPage);
                        }
                    };
                    select.initDialog();
                    select.setValues(pageNames);
                    select.showDialog();
                }

                @Override
                public void onTargetOption2Clicked() {
                    Vector<String> pageNames = briefing.getPageNamesOfType(BriefingPageTypes.TYPE_DIALOG);
                    ListSelectValueDialog select = new ListSelectValueDialog(null, "Ziel ausw??hlen") {
                        @Override
                        public void onConfirm() {
                            super.onConfirm();
                            String nameSelectedPage = getValue();
                            page.setSecondSelectPage(nameSelectedPage);
                            op2Target.setText(nameSelectedPage);
                        }
                    };
                    select.initDialog();
                    select.setValues(pageNames);
                    select.showDialog();
                }
            };
            editDialog.initDialog();
            editDialog.showDialog();
        }

        // Redirect to other
        if (page.getType() == BriefingPageTypes.TYPE_JUMP) {
            BriefingRedirectPageProperties editDialog = new BriefingRedirectPageProperties(page, null) {
                @Override
                public void onTargetSelectClicked() {
                    Vector<String> pageNames = briefing.getPageNamesOfType(BriefingPageTypes.TYPE_DIALOG);
                    ListSelectValueDialog select = new ListSelectValueDialog(null, "Ziel ausw??hlen") {
                        @Override
                        public void onConfirm() {
                            super.onConfirm();
                            String nameSelectedPage = getValue();
                            page.setSecondSelectPage(nameSelectedPage);
                            targetPage.setText(nameSelectedPage);
                        }
                    };
                    select.initDialog();
                    select.setValues(pageNames);
                    select.showDialog();
                }
            };
            editDialog.initDialog();
            editDialog.showDialog();
        }
    }

    /**
     * The user moved the page up
     */
    private void onPageUpClicked() {
        if (currentSelectedPage > 0) {
            Briefing briefing = briefingCollection.getBriefings().get(currentSelectedBriefing);
            ArrayList<BriefingPage> pageList = (ArrayList<BriefingPage>) briefing.getPages();
            if (pageList.size() >= currentSelectedPage) {
                Collections.swap(pageList, currentSelectedPage, currentSelectedPage-1);
                currentSelectedPage = currentSelectedPage -1;
                Vector<BriefingPage> data = briefing.toPageVector();
                setPages(briefing.toPageVector());
                pageGroup.setSelectedPage(data.get(currentSelectedPage));
            }
        }
    }

    /**
     * The user moved the page down
     */
    private void onPageDownClicked() {
        if (currentSelectedPage < briefingCollection.get(currentSelectedBriefing).getPages().size() -1) {
            Briefing briefing = briefingCollection.getBriefings().get(currentSelectedBriefing);
            ArrayList<BriefingPage> pageList = (ArrayList<BriefingPage>) briefing.getPages();
            if (pageList.size() > currentSelectedPage+1) {
                Collections.swap(pageList, currentSelectedPage, currentSelectedPage +1);
                currentSelectedPage = currentSelectedPage +1;
                Vector<BriefingPage> data = briefing.toPageVector();
                setPages(data);
                pageGroup.setSelectedPage(data.get(currentSelectedPage));
            }
        }
    }

    /**
     * The user created a new page
     */
    private void onNewDialogPageClicked() {
        Vector<String> options = new Vector<>();
        options.add("Dialog hinzuf??gen");
        options.add("Entscheidung hinzuf??gen");
        options.add("Seperator hinzuf??gen");
        options.add("Weiterleitung hinzuf??gen");

        SelectValueDialog dialog = new SelectValueDialog(null) {
            @Override
            public void onConfirm() {
                super.onConfirm();
                Briefing briefing = briefingCollection.getBriefings().get(currentSelectedBriefing);
                int newPageIndex = briefing.getPages().size() +1;
                String pageName = "";
                BriefingPage newPage = null;
                switch (getValueIndex()) {
                    case 0:
                        pageName = "DP_" +newPageIndex;
                        newPage = new BriefingDialogPage(pageName);
                        break;
                    case 1:
                        pageName = briefing.getName() + "_CP_" +newPageIndex;
                        newPage = new BriefingChoicePage(pageName);
                        break;
                    case 2:
                        pageName = "SP_" +newPageIndex;
                        newPage = new BriefingSepertorPage(pageName);
                        break;
                    case 3:
                        pageName = "RP_" +newPageIndex;
                        newPage = new BriefingRedirectPage(pageName);
                        break;
                }
                if (briefingCollection.doesPageNameExist(newPage.getName(), briefing)) {
                    newPage.setName(newPage.getName() + "_1");
                }
                briefing.addPage(newPage);
                Vector<BriefingPage> data = briefing.toPageVector();
                setPages(data);
                pageGroup.setSelectedPage(newPage);
                openPageProperties(newPage, briefing);
            }
        };
        dialog.initDialog();
        dialog.setValues(options);
        dialog.showDialog();
    }

    /**
     * The user cloned a page
     */
    private void onCopyPageClicked() {
        Briefing briefing = briefingCollection.get(currentSelectedBriefing);
        if (thereArePages()) {
            BriefingPage page = briefing.getPage(currentSelectedPage);
            EnterValueDialog dialog = new EnterValueDialog(null) {
                @Override
                public void onConfirm() {
                    super.onConfirm();
                    if (!checkPageName(getValue(), briefing, page.getType())) {
                        return;
                    }
                    BriefingPage newPage = page.clone();
                    newPage.setName(getValue());
                    briefing.addPage(newPage);
                    pageGroup.setPages(briefing.toPageVector());
                    pageGroup.setSelectedPage(newPage);
                    openPageProperties(newPage, briefing);
                }
            };
            dialog.initDialog();
            dialog.setValue(page.getName() + "_Kopie");
            dialog.showDialog();
        }
    }

    /**
     * The user renamed a page
     */
    private void onRenamePageClicked() {
        Briefing briefing = briefingCollection.get(currentSelectedBriefing);
        if (thereArePages()) {
            BriefingPage page = briefing.getPage(currentSelectedPage);
            EnterValueDialog dialog = new EnterValueDialog(null) {
                @Override
                public void onConfirm() {
                    super.onConfirm();
                    final String oldName = page.getName();
                    String newName = getValue();

                    if (!checkPageName(newName, briefing, page.getType())) {
                        return;
                    }
                    QuestCollection questCollection = ApplicationController.getInstance().getCurrentProject().getQuestCollection();
                    if (briefingCollection.doesPageNameExist(newName, briefing)) {
                        page.setName(newName + "_1");
                    }
                    briefing.renamePage(oldName, newName);
                    pageGroup.setPages(briefing.toPageVector());
                    pageGroup.setSelectedPage(page);
                    questCollection.replacePageNameInReferencingQuests(oldName, newName);
                }
            };
            dialog.initDialog();
            dialog.setValue(page.getName());
            dialog.showDialog();
        }
    }

    /**
     * The user deleted a page
     */
    private void onDeletePageClicked() {
        ApplicationController controller = ApplicationController.getInstance();
        Briefing briefing = briefingCollection.get(currentSelectedBriefing);
        if (currentSelectedPage != -1 && thereArePages()) {
            BriefingPage page = briefing.getPage(currentSelectedPage);
            String refText = "";
            if (isPageReferenced(briefing.getPage(currentSelectedPage).getName())) {
                refText = " Mindestens eine Entscheidung referenziert diese Seite. Die Referenz wird entfernt und" +
                          " Entscheidungen m??ssen ??berarbeitet werden.";
            }

            int result = controller.getMessageService().displayConfirmDialog(
                "Seite l??schen",
                "Soll diese Seite wirklich gel??scht werden?" +refText
            );
            if (result == 0) {
                QuestCollection questCollection = ApplicationController.getInstance().getCurrentProject().getQuestCollection();
                Vector<String> referencingQuests = questCollection.getQuestsReferencingPage(page);
                if (referencingQuests.size() > 0) {
                    StringBuilder quests = new StringBuilder();
                    for (String s : referencingQuests) {
                        quests.append("<br>").append(s);
                    }
                    controller.getMessageService().displayErrorMessage(
                        "Referenzen gefunden",
                        "<html>Entscheidungen k??nnen nur gel??scht werden, wenn sie von keinem Auftragt" +
                        " referenzier werden!<br>Die Entscheidung wird von diesen Auftr??gen" +
                        " benutzt:<br><code>" +quests+ "</code></html>"
                    );
                    return;
                }
                String pageName = briefing.getName();
                briefing.removePage(currentSelectedPage);
                pageGroup.setPages(briefing.toPageVector());
                pageGroup.setSelectedPageIndex(0);
                briefing.renamePage(pageName, "INVALID_PAGE");
                // renamePageReferences(pageName, "INVALID_PAGE");
            }
        }
    }

    /**
     * Checks if the page is referenced in any page of the current briefing.
     * @param name Page name
     * @return Page is referenced
     */
    private boolean isPageReferenced(String name) {
        for (BriefingPage p : briefingCollection.get(currentSelectedBriefing).getPages()) {
            if (p.getType() == BriefingPageTypes.TYPE_CHOICE) {
                if (p.getFirstSelectPage().equals(name) || p.getSecondSelectPage().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks a page name and displays error messages in case of failures.
     * @param name Name of briefing
     * @param briefing Briefing
     * @param type Page type
     * @return Name valid
     */
    private boolean checkPageName(String name, Briefing briefing, BriefingPageTypes type) {
        ApplicationController controller = ApplicationController.getInstance();
        if (!briefingCollection.validateName(name)) {
            controller.getMessageService().displayErrorMessage(
                "Ung??ltiger Name",
                "Der angegebene Name ist ung??ltig oder zu kurz! M??glicher Weise enth??lt er verbotene Zeichen.",
                controller.getWorkbenchWindow()
            );
            return false;
        }

        if (type == BriefingPageTypes.TYPE_CHOICE) {
            if (briefingCollection.doesChoicePageNameExist(name, briefing)) {
                controller.getMessageService().displayErrorMessage(
                        "Duplikat",
                        "Der Name einer Entscheidung kann im Projekt nur einmalig vergeben werden!",
                        controller.getWorkbenchWindow()
                );
                return false;
            }
        }
        else {
            if (briefingCollection.doesPageNameExist(name, briefing)) {
                controller.getMessageService().displayErrorMessage(
                        "Duplikat",
                        "Es existiert bereits eine Seite mit dem angegebenen Namen! Bitte w??hle einen anderen Namen f??r die Seite aus.",
                        controller.getWorkbenchWindow()
                );
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the selectable briefings.
     * @param briefings Briefing name vector
     */
    public void setSelectableBriefings(Vector<String> briefings) {
        briefingGroup.setSelectableBriefings(briefings);
        if (briefings.size() == 0) {
            currentSelectedBriefing = -1;
            return;
        }
        setSelectedBriefing(briefings.get(0));
        currentSelectedBriefing = 0;
    }

    /**
     * Sets the selected briefing by briefing name
     * @param name Briefing name
     */
    public void setSelectedBriefing(String name) {
        briefingGroup.setSelectedBriefing(name);
    }

    /**
     * Returns the name of the selected briefing
     * @return Briefing name
     */
    public String getSelectedBriefing() {
        return briefingGroup.getSelectedBriefing();
    }

    /**
     * Sets the list of selectable pages
     * @param data Page names
     */
    public void setPages(Vector<BriefingPage> data) {
        pageGroup.setPages(data);
    }

    /**
     * Sets the selected page by name
     * @param page Page name
     */
    public void setSelectedPage(BriefingPage page) {
        pageGroup.setSelectedPage(page);
    }

    /**
     * Returns the selected page name
     * @return Page name
     */
    public BriefingPage getSelectedPage() {
        return pageGroup.getSelectedPage();
    }

    /**
     * Initalizes the panel
     */
    @Override
    public void initPanel() {
        setLayout(null);

        briefingGroup = new BriefingSelectBriefingPanel() {
            @Override
            protected void onBriefingCreatedClicked() {
                BriefingViewPanel.this.onBriefingCreatedClicked();
            }

            @Override
            protected void onBriefingDeletedClicked() {
                BriefingViewPanel.this.onBriefingDeletedClicked();
            }

            @Override
            protected void onBriefingRenamedClicked() {
                BriefingViewPanel.this.onBriefingRenamedClicked();
            }

            @Override
            protected void onBriefingCopiedClicked() {
                BriefingViewPanel.this.onBriefingCopiedClicked();
            }

            @Override
            protected void onSelectedBriefingChanged() {
                BriefingViewPanel.this.onSelectedBriefingChanged();
            }
        };
        briefingGroup.initPanel();
        add(briefingGroup);

        pageGroup = new BriefingEditPagePanel() {
            @Override
            protected void onPageDoubleClicked() {
                BriefingViewPanel.this.onPageDoubleClicked();
            }

            @Override
            protected void onPageUpClicked() {
                BriefingViewPanel.this.onPageUpClicked();
            }

            @Override
            protected void onPageDownClicked() {
                BriefingViewPanel.this.onPageDownClicked();
            }

            @Override
            protected void onNewDialogPageClicked() {
                BriefingViewPanel.this.onNewDialogPageClicked();
            }

            @Override
            protected void onCopyPageClicked() {
                BriefingViewPanel.this.onCopyPageClicked();
            }

            @Override
            protected void onRenamePageClicked() {
                BriefingViewPanel.this.onRenamePageClicked();
            }

            @Override
            protected void onDeletePageClicked() {
                BriefingViewPanel.this.onDeletePageClicked();
            }

            @Override
            protected void onPageSelectionChanged() {
                BriefingViewPanel.this.onPageSelectionChanged();
            }

            @Override
            protected void onTextChanged(String text) {
                BriefingViewPanel.this.onTextChanged(text, this);
            }
        };
        pageGroup.initPanel();
        add(pageGroup);
    }

    /**
     *
     * @param text
     * @param briefingEditPagePanel
     */
    private void onTextChanged(String text, BriefingEditPagePanel briefingEditPagePanel) {
        if (currentSelectedBriefing == -1 || text == null || text.equals("")) {
            return;
        }
        Briefing briefing = briefingCollection.get(currentSelectedBriefing);
        if (briefing == null) {
            return;
        }
        for (BriefingPage p : briefing.getPages()) {
            if (p.getName().toLowerCase().contains(text.toLowerCase())) {
                briefingEditPagePanel.setSelectedPage(p);
                break;
            }
        }
    }

    /**
     * Takes a map data object and fills in all fields.
     * @param briefingCollection
     */
    public void loadBriefingData(BriefingCollection briefingCollection) {
        this.briefingCollection = briefingCollection;
        ApplicationController controller = ApplicationController.getInstance();
        Vector<String> briefings = controller.getSelectableValueService().getBriefingNames();
        if (briefingGroup != null) {
            briefingGroup.setSelectableBriefings(briefings);
            if (briefings.size() == 0) {
                currentSelectedBriefing = -1;
                return;
            }
            setSelectedBriefing(briefings.get(0));
            currentSelectedBriefing = 0;
        }
    }

    //// Resizing ////

    @Override
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth();
        height = reference.getHeight();
        briefingGroup.updatePanelSize(reference);
        briefingGroup.setLocation(0, 5);
        pageGroup.updatePanelSize(reference);
        pageGroup.setLocation(0, briefingGroup.getContentHeight() + 15);
        setSize(reference.getWidth(), getContentHeight());
    }

    @Override
    public void setPanelHeight(int height) {
        baseHeight = height;
        panelResizedByCode();
    }

    @Override
    public void setPanelWidth(int width) {
        baseWidth = width;
        panelResizedByCode();
    }

    private void panelResizedByCode() {
        setSize(baseWidth + 20, baseHeight);
        updatePanelSize(this);
    }

    @Override
    public int getPanelHeight() {
        return baseHeight;
    }

    @Override
    public int getPanelWidth() {
        return baseWidth;
    }

    /**
     * Returns the conrent height.
     * @return Height
     */
    public int getContentHeight() {
        return briefingGroup.getContentHeight() + pageGroup.getContentHeight() + 20;
    }

    //// Unused ////

    @Override
    public void setVerticalOffset(int offset) {}

    @Override
    public int getVerticalOffset() {return 0;}
}
