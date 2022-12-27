import gui.DegreesJLabel;
import gui.MainJFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUITest implements ActionListener {
    int count;
    private JPanel panel;
    private JPanel degreesPanel;
    private JPanel bottomPanel;
    private MainJFrame frame;
    private JButton button;
    private JTextField input;
    private DegreesJLabel degrees;
    private JLabel errorText;
    private boolean wasError;

    public GUITest() {
        frame = new MainJFrame();
        panel = new JPanel();
        bottomPanel = new JPanel();
        degreesPanel = new JPanel();
        degrees = new DegreesJLabel();
        wasError = false;
        errorText = new JLabel("");

        button = new JButton("Определить");
        button.addActionListener(this);

        input = new JTextField();

        this.iniMainPanel();
        this.iniDegreesPanel();
        this.iniBottomPanel();
        this.iniMainFrame();
    }

    private void setError(boolean viewStatus, String errorMessage) {
        if (viewStatus) {
            this.errorText.setText(errorMessage);
            this.bottomPanel.setVisible(true);
            frame.pack();
        } else {
            this.errorText.setText("");
        }
    }

    private void iniMainPanel() {
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Введите город"));
        panel.add(input, this.getInputConstraints());
        panel.add(button, this.getButtonConstraints());
    }

    private void iniBottomPanel() {
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.add(errorText, this.getInputConstraints());
    }

    private void iniDegreesPanel() {
        degreesPanel.setLayout(new GridBagLayout());
        degreesPanel.setBorder(BorderFactory.createTitledBorder("В городе градусов"));
        degreesPanel.add(degrees, this.getDegreesJLabelConstraints());
    }

    private void iniMainFrame() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.setLayout(new GridBagLayout());

        panel.setPreferredSize(new Dimension(400, panel.getMinimumSize().height));
        frame.getContentPane().add(panel, gbc);
        gbc.gridy = 1;
        degreesPanel.setPreferredSize(new Dimension(400, 80));
        frame.getContentPane().add(degreesPanel, gbc);

        gbc.gridy = 2;
//
        bottomPanel.setPreferredSize(new Dimension(400, 16));
        frame.getContentPane().add(bottomPanel, gbc);
        bottomPanel.setVisible(false);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Погода");
        frame.pack();

        MainJFrame.centreWindow(frame);
        frame.setVisible(true);
    }
    private GridBagConstraints getDegreesJLabelConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return constraints;
    }
    private GridBagConstraints getInputConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return constraints;
    }
    private GridBagConstraints getButtonConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;
        return constraints;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setError(false, "");
        String city = this.input.getText();
        try {
            CityGeo cityGeo = Api.getInstance().getGeoByCityName(city);
            int degrees = Api.getInstance().getDegrees(cityGeo);
            this.degrees.setText(degrees + " °C");
        } catch (Throwable error) {
            this.degrees.setText("");
            this.setError(true, "Not found");
            System.out.println("ERROR: " + error.getMessage());
        }
    }
}
