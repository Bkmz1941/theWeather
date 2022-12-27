package gui;

import javax.swing.*;
import java.awt.*;

public class DegreesJLabel extends JLabel {
    public DegreesJLabel() {
        this.setText("");
        // 0 °C
        this.setFont(new Font("TimesRoman", Font.BOLD, 48));
        this.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
