package twa.siedelwood.s5.mapmaker.view.swing.frame;

import javax.swing.*;
import java.awt.*;

/**
 * Window type interface
 */
public interface WindowFrame {
    void initComponents();
    void setBaseSize(int w, int h);
    void addCard(String name, Component panel);
    JPanel getCard(int idx);
    String getApplicationName();
    void setTitle(String title);

    void setVisible(boolean b);
}
