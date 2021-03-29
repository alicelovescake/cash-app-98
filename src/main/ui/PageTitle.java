package ui;

import javax.swing.*;
import java.awt.*;

//class to style page title component
public class PageTitle {
    public PageTitle(JPanel page, String text) {
        JLabel title = new JLabel(text);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getName(), Font.BOLD, 45));
        page.add(title);
        page.add(Box.createRigidArea(new Dimension(400, 15)));
    }
}
