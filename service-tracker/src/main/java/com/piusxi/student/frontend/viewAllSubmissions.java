package com.piusxi.student.frontend;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * This class will show all the student's service submissions so far, with the ability to click on them to view the filled out form in its entirety
 * Form im referencing is the serviceReportingForm.java file
 */
public class viewAllSubmissions extends JFrame {

    

    private void showSubmissionForm(ResultSet resultSet) {
        try {
            JFrame submissionFrame = new JFrame("Service Submission Details");
            submissionFrame.setSize(600, 700);
            submissionFrame.setLocationRelativeTo(this);
            submissionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            
            JLabel titleLabel = new JLabel("Service Submission Details", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(titleLabel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            
            JPanel formPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);
            
            // Extract all the data from the ResultSet
            int studentId = resultSet.getInt("student_id");
            String serviceType = resultSet.getString("service_type");
            String serviceDescription = resultSet.getString("service_description");
            double eventLength = resultSet.getDouble("service_event_length");
            String supervisorEmail = resultSet.getString("supervisor_email");
            Timestamp submissionDate = resultSet.getTimestamp("submission_date");
            
            // Get student name from the student database
            String studentName = "Unknown";
            for (Object[] student : sophomoreData) {
                if ((Integer) student[0] == studentId) {
                    studentName = student[1] + " " + student[2];
                    break;
                }
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            addFormField(formPanel, gbc, "Student:", studentName, 0);
            addFormField(formPanel, gbc, "Student ID:", String.valueOf(studentId), 1);
            addFormField(formPanel, gbc, "Service Type:", serviceType, 2);
            
            // Description as text area
            gbc.gridx = 0;
            gbc.gridy = 3;
            formPanel.add(new JLabel("Description:"), gbc);
            
            JTextArea descriptionArea = new JTextArea(serviceDescription, 4, 30);
            descriptionArea.setEditable(false);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            JScrollPane descScrollPane = new JScrollPane(descriptionArea);
            
            gbc.gridx = 1;
            gbc.gridy = 3;
            formPanel.add(descScrollPane, gbc);
            
            addFormField(formPanel, gbc, "Service Hours:", String.valueOf(eventLength), 4);
            addFormField(formPanel, gbc, "Supervisor Email:", supervisorEmail, 6);
            addFormField(formPanel, gbc, "Submission Date:", dateFormat.format(submissionDate), 8);
            
            // Add the form panel to a scroll pane
            JScrollPane formScrollPane = new JScrollPane(formPanel);
            formScrollPane.setBorder(null);
            mainPanel.add(formScrollPane);
            
            // Close button
            JButton closeButton = new JButton("Close");
            closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            closeButton.addActionListener(e -> submissionFrame.dispose());
            
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(closeButton);
            
            submissionFrame.add(mainPanel);
            submissionFrame.setVisible(true);
            
        } 
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                    "Error displaying submission form: " + e.getMessage(), 
                    "Form Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
