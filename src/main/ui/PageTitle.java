package ui;

import javax.swing.*;
import java.awt.*;

//class to style page title component
public class PageTitle {
    public PageTitle(JPanel page, String text, ImageIcon icon) {
        JLabel title = new JLabel(text);
        title.setIcon(icon);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getName(), Font.BOLD, 45));
        title.setForeground(Color.WHITE);
        page.add(title);
        page.add(Box.createRigidArea(new Dimension(400, 15)));
    }
}
