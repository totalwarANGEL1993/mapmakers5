package twa.siedelwood.s5.mapmaker.view.swing.component.panel.briefing;

import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPage;
import twa.siedelwood.s5.mapmaker.view.swing.component.renderer.BriefingPageCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class BriefingEditPagePanel extends JPanel implements ActionListener, ListSelectionListener, MouseListener {
    private JLabel pagesInfo;
    private JList<BriefingPage> pagesList;
    private JScrollPane scrollPane;
    private JButton[] buttons;
    protected int height = 590;

    public BriefingEditPagePanel() {
        super();
    }

    /**
     *
     */
    protected void onPageDoubleClicked() {

    }

    /**
     *
     */
    protected void onPageSelectionChanged() {

    }

    /**
     *
     */
    protected void onPageUpClicked() {

    }

    /**
     *
     */
    protected void onPageDownClicked() {

    }

    /**
     *
     */
    protected void onNewDialogPageClicked() {

    }

    /**
     *
     */
    protected void onCopyPageClicked() {

    }

    /**
     *
     */
    protected void onRenamePageClicked() {

    }

    /**
     *
     */
    protected void onDeletePageClicked() {

    }

    public void initPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Pages"));

        pagesInfo = new JLabel(
            "<html>Hier werden die Seiten des Briefings angezeigt. Du kannst Seiten erstellen, kopieren," +
            " umbenennen, löschen und die Reihenfolge verändern. Die Eigenschhaften einer Seite werden bearbeitet," +
            " in dem doppelt auf die Seite in der Liste geklickt wird.</html>"
        );
        pagesInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        pagesInfo.setVerticalAlignment(JLabel.TOP);
        pagesInfo.setVerticalTextPosition(JLabel.TOP);
        add(pagesInfo);

        pagesList = new JList<>();
        pagesList.setCellRenderer(new BriefingPageCellRenderer());
        pagesList.setFont(new Font("Arial", Font.PLAIN, 12));
        pagesList.addListSelectionListener(this);
        pagesList.addMouseListener(this);
        pagesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(pagesList);
        scrollPane.setVisible(true);
        add(scrollPane);

        buttons = new JButton[7];

        buttons[0] = new JButton("Rauf");
        buttons[0].addActionListener(this);
        add(buttons[0]);
        buttons[1] = new JButton("Runter");
        buttons[1].addActionListener(this);
        add(buttons[1]);
        buttons[2] = new JButton("Neu");
        buttons[2].addActionListener(this);
        add(buttons[2]);
        buttons[3] = new JButton("Kopie");
        buttons[3].addActionListener(this);
        add(buttons[3]);
        buttons[4] = new JButton("Umbenennen");
        buttons[4].addActionListener(this);
        add(buttons[4]);
        buttons[5] = new JButton("Löschen");
        buttons[5].addActionListener(this);
        add(buttons[5]);
    }

    /**
     *
     * @param data
     */
    public void setPages(Vector<BriefingPage> data) {
        pagesList.setListData(data);
    }

    /**
     *
     * @param page
     */
    public void setSelectedPage(BriefingPage page) {
        pagesList.setSelectedValue(page, true);
    }

    /**
     *
     * @param idx
     */
    public void setSelectedPageIndex(int idx) {
        pagesList.setSelectedIndex(idx);
    }

    /**
     *
     * @return
     */
    public BriefingPage getSelectedPage() {
        return pagesList.getSelectedValue();
    }

    /**
     *
     * @return
     */
    public int getSelectedPageIndex() {
        return pagesList.getSelectedIndex();
    }

    /**
     *
     * @param reference
     */
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth() - 10;

        setSize(width, height);
        pagesInfo.setBounds(10, 20, width -20, 45);
        int selectionWidth = (width -165 < 700) ? width -165 : 700;
        scrollPane.setBounds(10, 75, selectionWidth, height -85);
        pagesList.setSize(selectionWidth, height -85);

        int y = (height / 2) - ((35 * 3) + 8);
        for (int i=0; i<6; i++) {
            if (i == 2) {
                y = y + 35;
            }
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
            onPageUpClicked();
        }
        if (e.getSource() == buttons[1]) {
            onPageDownClicked();
        }
        if (e.getSource() == buttons[2]) {
            onNewDialogPageClicked();
        }
        if (e.getSource() == buttons[3]) {
            onCopyPageClicked();
        }
        if (e.getSource() == buttons[4]) {
            onRenamePageClicked();
        }
        if (e.getSource() == buttons[5]) {
            onDeletePageClicked();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == pagesList) {
            onPageSelectionChanged();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == pagesList) {
            if (e.getClickCount() == 2) {
                onPageDoubleClicked();
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
