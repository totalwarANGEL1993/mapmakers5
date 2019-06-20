package twa.siedelwood.s5.mapmaker.service.script;

import twa.siedelwood.s5.mapmaker.model.data.briefing.Briefing;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingChoicePage;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingCollection;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingDialogPage;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPage;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPageTypes;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingSepertorPage;

import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * Service for building briefings
 */
public class BriefingBuilderService {
    private static BriefingBuilderService instance;

    private BriefingBuilderService() {}

    /**
     * Returns the singleton instance
     * @return Instance
     */
    public static BriefingBuilderService getInstance() {
        if (instance == null) {
            instance = new BriefingBuilderService();
        }
        return instance;
    }

    /**
     * Creates a new briefing and adds it to the briefing collection. All pages in the page list will be added.
     * @param name Name of briefing
     * @param briefings Briefings collection
     * @param pages Pages
     * @return Briefing
     */
    public Briefing createBriefing(BriefingCollection briefings, String name, List<BriefingPage> pages) {
        Briefing briefing = new Briefing(name);
        if (pages != null) {
            for (BriefingPage p : pages) {
                briefing.addPage(p);
            }
        }
        briefings.add(briefing);
        return briefing;
    }

    /**
     * Creates a new briefing and adds it to the briefing collection.
     * @param name Name of briefing
     * @param briefings Briefings collection
     * @return Briefing
     */
    public Briefing createBriefing(BriefingCollection briefings, String name) {
        return createBriefing(briefings, name, null);
    }

    /**
     * Creates an dialog page
     * @param briefing Briefing
     * @param name Name of page
     * @param entity Entity to look at
     * @param title Page title
     * @param text Page text
     * @param dialogCamera Dialog camera
     * @param action Action
     * @param showSky Show sky
     * @param hideFow Hide fow
     * @return Page
     */
    public BriefingPage createDialogPage(
        Briefing briefing, String name, String entity, String title, String text, boolean dialogCamera, String action,
        boolean showSky, boolean hideFow
    ) {
        BriefingPage page = new BriefingDialogPage(name, entity, title, text, dialogCamera, action, showSky, hideFow);
        briefing.addPage(page);
        return page;
    }

    /**
     * Creates an choice page
     * @param briefing Briefing
     * @param name Name of page
     * @param entity Entity to look at
     * @param title Page title
     * @param text Page text
     * @param dialogCamera Dialog camera
     * @param action Action
     * @param showSky Show sky
     * @param hideFow Hide fow
     * @param firstText First answer
     * @param firstPage First answer target
     * @param secondTest Second answer
     * @param secondPage Second answer target
     * @return Page
     */
    public BriefingPage createChoicePage(
        Briefing briefing, String name, String entity, String title, String text, boolean dialogCamera, String action,
        boolean showSky, boolean hideFow, String firstText, String firstPage, String secondTest, String secondPage
    ) {
        BriefingPage page = new BriefingChoicePage(
            name, entity, title, text, dialogCamera, action, showSky, hideFow, firstText, firstPage, secondTest, secondPage
        );
        return page;
    }

    /**
     * Creates an seperator page
     * @param briefing Briefing
     * @param name Name of page
     * @return Page
     */
    public BriefingPage createSeperatorPage(Briefing briefing, String name) {
        BriefingPage page = new BriefingSepertorPage(name);
        briefing.addPage(page);
        return page;
    }

    /**
     * Checks if the name is a valid name for a briefing or a page.
     * @param name Name to check
     * @return Name is valid
     */
    public boolean validateName(String name) {
        if (name.length() < 1 && !Pattern.matches("^[a-zA-Z0-9_]+$", name)) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the name is a valid name for a briefing or a page.
     * @param name Name to check
     * @return Name is valid
     */
    public boolean validatePageName(String name) {
        if (name.length() < 1 && !Pattern.matches("^[ a-zA-Z0-9_\\-\\\\(\\\\)]+$", name)) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a briefing with the name exists.
     * @param name Name of Briefing
     * @param briefings List of briefings
     * @return Briefing exists
     */
    public boolean doesBriefingNameExist(String name, Vector<String> briefings) {
        for (String s : briefings) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a page with the name exists in die Briefing.
     * @param name Name of page
     * @param briefing Briefing
     * @return Page exists
     */
    public boolean doesPageNameExist(String name, Briefing briefing) {
        for (BriefingPage p : briefing.getPages()) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a choice page with the name. Other page types are ignored.
     * @param name Name of page
     * @param briefingCollection Briefing collection
     * @return
     */
    public boolean doesChoicePageNameExist(String name, BriefingCollection briefingCollection) {
        for (Briefing b : briefingCollection.getBriefings()) {
            for (BriefingPage p : b.getPages()) {
                if (p.getType() == BriefingPageTypes.TYPE_CHOICE && p.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
}
