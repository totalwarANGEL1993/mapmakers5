package twa.siedelwood.s5.mapmaker.model.data.briefing;


import twa.lib.typesavejson.models.Json;

public interface BriefingPage {
    BriefingPageTypes getType();
    Json toJson();
    String toLua();
    String toString();
    BriefingPage clone();

    String getName();
    String getEntity();
    String getTitle();
    String getText();
    String getAction();
    String getFirstSelectText();
    String getFirstSelectPage();
    String getSecondSelectText();
    String getSecondSelectPage();
    boolean isDialogCamera();
    boolean isShowSky();
    boolean isHideFoW();
    boolean isNoEscape();

    void setName(String name);
    void setEntity(String pos);
    void setTitle(String title);
    void setText(String text);
    void setAction(String action);
    void setFirstSelectText(String text);
    void setFirstSelectPage(String page);
    void setSecondSelectText(String text);
    void setSecondSelectPage(String page);
    void setDialogCamera(boolean flag);
    void setShowSky(boolean flag);
    void setHideFoW(boolean flag);
    void setNoEscape(boolean flag);
}
