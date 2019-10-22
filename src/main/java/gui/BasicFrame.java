package gui;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class BasicFrame extends JFrame implements LayoutManager {
    JSplitPane splitPaneVertical;

    public BasicFrame() {
        log.info("Start instantiating gui");
        this.setLayout(new BorderLayout());
        this.setTitle("Chat");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initSplitPanel();
    }

    private void initSplitPanel(){
        splitPaneVertical = new JSplitPane();
        splitPaneVertical.setDividerLocation(150);
        splitPaneVertical.setOneTouchExpandable(true);
        splitPaneVertical.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        this.add(splitPaneVertical, BorderLayout.CENTER);
        this.setVisible(false);
    }

    public void addLoginToolbar(JToolBar login){
        this.add(login, BorderLayout.NORTH);
    }

    public void addChatComponentToSplit(Container container, String resides){
        splitPaneVertical.add(container, resides);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {}

    @Override
    public void removeLayoutComponent(Component comp) {}

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {}

}
