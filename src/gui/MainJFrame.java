package gui;

import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {
    @Override
    public Insets getInsets() {
        int m = 8;
        return new Insets(28 + m, m, m, m);
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
