package ui;

import model.Elevator;
import model.Person;
import constants.GlobalParams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ControlPanel extends JPanel {
    private int personCount = 0;
    private JLabel personCountLabel;
    private final ElevatorSystemUI elevatorSystemUI;
    private final Map<Integer, Person> people = new HashMap<>();

    public ControlPanel(ElevatorSystemUI elevatorSystemUI) {
        this.elevatorSystemUI = elevatorSystemUI;
        setPreferredSize(new Dimension(400, 700));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Control Section", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title);

        // App Settings
        add(createAppSettingsPanel());

        // Elevator Settings
        add(createElevatorSettingsPanel());

        // People Settings
        add(createPeopleSettingsPanel());
    }

    private JPanel createAppSettingsPanel() {
        JPanel appSettings = new JPanel();
        appSettings.setBorder(BorderFactory.createTitledBorder("App Settings"));
        JButton pauseBtn = new JButton("pause");
        pauseBtn.setBackground(Color.YELLOW);
        JButton resumeBtn = new JButton("resume");
        resumeBtn.setBackground(Color.CYAN);
        JButton restartBtn = new JButton("restart");
        restartBtn.setBackground(Color.CYAN);

//        // Wire up the buttons to the elevator
//        pauseBtn.addActionListener(e -> elevator.pause());
//        resumeBtn.addActionListener(e -> elevator.resumeElevator());
//        restartBtn.addActionListener(e -> elevator.restart());

        appSettings.add(pauseBtn);
        appSettings.add(resumeBtn);
        appSettings.add(restartBtn);
        return appSettings;
    }

    private JPanel createElevatorSettingsPanel() {
        JPanel elevatorSettings = new JPanel();
        elevatorSettings.setBorder(BorderFactory.createTitledBorder("Elevator Settings"));
        JTextField velocityField = new JTextField("Enter Velocity", 10);
        JTextField capacityField = new JTextField("Enter Maximum Capacity", 15);
        JButton saveElevatorBtn = new JButton("SAVE");
        saveElevatorBtn.setBackground(Color.MAGENTA);
        elevatorSettings.add(velocityField);
        elevatorSettings.add(capacityField);
        elevatorSettings.add(saveElevatorBtn);
        return elevatorSettings;
    }

    private JPanel createPeopleSettingsPanel() {
        JPanel peopleSettings = new JPanel();
        peopleSettings.setBorder(BorderFactory.createTitledBorder("Add Person"));
        peopleSettings.setLayout(new BoxLayout(peopleSettings, BoxLayout.Y_AXIS));

        // Create Add Person Button
        JButton addPersonButton = new JButton("Add Person");
        addPersonButton.setBackground(Color.GREEN);
        addPersonButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize person count label
        personCountLabel = new JLabel("Total People Added: 0");
        personCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add Person Button with action listener
        addPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPersonDialog();
            }
        });

        // Add components to the panel
        peopleSettings.add(Box.createVerticalStrut(10));
        peopleSettings.add(addPersonButton);
        peopleSettings.add(Box.createVerticalStrut(10));
        peopleSettings.add(personCountLabel);
        peopleSettings.add(Box.createVerticalStrut(10));

        return peopleSettings;
    }

    private void showAddPersonDialog() {
        // Create a custom dialog panel
        JPanel dialogPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create input fields
        JTextField startFloorField = new JTextField("0", 5);
        JTextField endFloorField = new JTextField("0", 5);

        // Add components to dialog panel
        dialogPanel.add(new JLabel("Start Floor:"));
        dialogPanel.add(startFloorField);
        dialogPanel.add(new JLabel("End Floor:"));
        dialogPanel.add(endFloorField);

        // Show the dialog
        int result = JOptionPane.showConfirmDialog(
                this,
                dialogPanel,
                "Add Person",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Process the result
        if (result == JOptionPane.OK_OPTION) {
            try {
                int startFloor = Integer.parseInt(startFloorField.getText());
                int endFloor = Integer.parseInt(endFloorField.getText());

                // Validate floor numbers
                if (startFloor < 0 || startFloor >= GlobalParams.TOTAL_FLOORS || endFloor < 0 || endFloor >= GlobalParams.TOTAL_FLOORS) {
                    JOptionPane.showMessageDialog(this,
                            "Floor numbers must be between 0 and 4",
                            "Invalid Floor",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate that start and end floors are different
                if (startFloor == endFloor) {
                    JOptionPane.showMessageDialog(this,
                            "Start and end floors must be different",
                            "Invalid Floors",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create and add the person
                Person person = new Person(startFloor, endFloor);
                people.put(personCount, person);
                StickPersonUI stickperson = new StickPersonUI(startFloor);
                elevatorSystemUI.floors[startFloor].add(stickperson, BorderLayout.CENTER);
                elevatorSystemUI.floorStop[startFloor]++;
                elevatorSystemUI.floors[startFloor].revalidate();
                elevatorSystemUI.floors[startFloor].repaint();

                stickperson.startWalking();

                // Update counter and UI
                personCount++;
                personCountLabel.setText("Total People Added: " + personCount);

                JOptionPane.showMessageDialog(this,
                        "Person " + personCount + " added!\nFrom floor " + startFloor + " to floor " + endFloor,
                        "Person Added",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid floor numbers",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
