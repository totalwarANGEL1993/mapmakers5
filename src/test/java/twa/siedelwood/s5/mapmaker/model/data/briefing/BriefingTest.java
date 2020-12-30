package twa.siedelwood.s5.mapmaker.model.data.briefing;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BriefingTest {
    private Briefing briefing;

    @Before
    public void initTest() {
        briefing = new Briefing("XYZ");

        BriefingDialogPage dialogPage = new BriefingDialogPage(
            "Page1",
            "HansWurst",
            "Title",
            "This is the page text.",
            true,
            "nil",
            true,
            true,
            false
        );
        BriefingChoicePage choicePage = new BriefingChoicePage(
            "Page2",
            "HansWurst",
            "Title",
            "Text",
            true,
            "nil",
            true,
            true,
            true,
            "Option 1 text",
            "OptionTarget1",
            "Option 2 text",
            "OptionTarget2"
        );
        BriefingSepertorPage sepertorPage = new BriefingSepertorPage();
        BriefingRedirectPage redirectPage = new BriefingRedirectPage("Page4", "Page2");

        briefing.addPage(dialogPage);
        briefing.addPage(choicePage);
        briefing.addPage(sepertorPage);
        briefing.addPage(redirectPage);
    }

    @Test
    public void displayBriefingToLuaResult() {
        System.out.println(briefing.toLua());
        assertTrue(true);
    }
}
