package com.piusxi.student.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.piusxi.student.backend.studentSession;
import com.piusxi.student.database.serviceSubmissionDatabase;

/**
 * This class will show all the student's service submissions so far, with the ability to click on them to view the filled out form in its entirety
 * Form im referencing is the serviceReportingForm.java file
 */
public class viewAllSubmissions extends JFrame {
    // Pius XI school colors
    private static final Color PIUS_NAVY = new Color(0, 32, 91);
    private static final Color PIUS_GOLD = new Color(255, 215, 0);
    private static final Color PIUS_WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY_BG = new Color(245, 245, 250);
    
    private JTable submissionsTable;
    private DefaultTableModel tableModel;
    private String studentId;
    private JLabel titleLabel;

    public viewAllSubmissions() {
        setTitle("My Service Submissions");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(PIUS_WHITE);
        
        // Check if there's an active student session
        if (!studentSession.getInstance().isSessionActive()) {
            JOptionPane.showMessageDialog(this,
                "No active session. Please log in first.",
                "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        
        studentId = studentSession.getInstance().getStudentId();
        String studentName = studentSession.getInstance().getFullName();
        
        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(PIUS_WHITE);
        
        // Title at the top
        titleLabel = new JLabel("Service Submissions for " + studentName, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PIUS_NAVY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Create table model with column names
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };
        
        tableModel.addColumn("Date");
        tableModel.addColumn("Type");
        tableModel.addColumn("Hours");
        tableModel.addColumn("Status");
        tableModel.addColumn("Submission ID"); // Hidden column for reference
        
        // Create the table and set properties
        submissionsTable = new JTable(tableModel);
        submissionsTable.setFillsViewportHeight(true);
        submissionsTable.setRowHeight(25);
        submissionsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Style the table header
        JTableHeader header = submissionsTable.getTableHeader();
        header.setBackground(PIUS_NAVY);
        header.setForeground(PIUS_WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Style the table rows with alternating colors
        submissionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(PIUS_GOLD);
                    c.setForeground(PIUS_NAVY);
                } else {
                    c.setBackground(row % 2 == 0 ? PIUS_WHITE : LIGHT_GRAY_BG);
                    c.setForeground(PIUS_NAVY);
                }
                
                return c;
            }
        });
        
        // Hide the Submission ID column
        submissionsTable.getColumnModel().getColumn(4).setMinWidth(0);
        submissionsTable.getColumnModel().getColumn(4).setMaxWidth(0);
        submissionsTable.getColumnModel().getColumn(4).setWidth(0);
        
        // Add the table to a scroll pane with styled border
        JScrollPane tableScrollPane = new JScrollPane(submissionsTable);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(PIUS_NAVY, 1));
        tableScrollPane.getViewport().setBackground(PIUS_WHITE);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Add double-click listener to the table
        submissionsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = submissionsTable.getSelectedRow();
                    if (row >= 0) {
                        openSubmissionDetails(row);
                    }
                }
            }
        });
        
        // Close button at the bottom
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(PIUS_GOLD);
        closeButton.setForeground(PIUS_NAVY);
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(PIUS_WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        createMenuBar();

        // Load the submissions data
        loadSubmissions();
    }
    
    private void loadSubmissions() {
        tableModel.setRowCount(0);
        
        Connection connection = null;
        try {
            connection = serviceSubmissionDatabase.connect();
            
            if (connection == null) {
                throw new SQLException("Failed to establish database connection");
            }
            
            List<Map<String, String>> submissions = serviceSubmissionDatabase.getStudentServiceSubmissions(studentId, connection);
            
            for (Map<String, String> submission : submissions) {
                String submissionDate = submission.get("submission_date");
                String serviceType = submission.get("service_type");
                String hours = submission.get("service_event_length");
                String status = submission.get("status");
                String id = submission.get("id"); // Submission ID
                
                tableModel.addRow(new Object[]{submissionDate, serviceType, hours, status, id});
            }
            
            if (submissions.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No service submissions found.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (SQLException se) {
            JOptionPane.showMessageDialog(this,
                "Error loading submissions: " + se.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            se.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }
    
    private void openSubmissionDetails(int selectedRow) {
        if (selectedRow >= 0) {
            String submissionId = tableModel.getValueAt(selectedRow, 4).toString();
            
            Connection connection = null;
            try {
                connection = serviceSubmissionDatabase.connect();
                if (connection == null) {
                    throw new SQLException("Failed to establish database connection");
                }
                
                String query = "SELECT * FROM service_submissions WHERE submission_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, submissionId);
                ResultSet resultSet = statement.executeQuery();
                
                if (resultSet.next()) {
                    showSubmissionForm(resultSet);
                }
                else {
                    JOptionPane.showMessageDialog(this,
                        "Submission details not found.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                resultSet.close();
                statement.close();
            }
            catch (SQLException se) {
                JOptionPane.showMessageDialog(this,
                    "Error retrieving submission details: " + se.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
                se.printStackTrace();
            }
            finally {
                if (connection != null) {
                    try {
                        connection.close();
                    }
                    catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        }
    }
    
    private void showSubmissionForm(ResultSet resultSet) {
        try {
            JFrame submissionFrame = new JFrame("Service Submission Details");
            submissionFrame.setSize(600, 700);
            submissionFrame.setLocationRelativeTo(this);
            submissionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(PIUS_WHITE);
            
            JLabel titleLabel = new JLabel("Service Submission Details", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(PIUS_NAVY);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(titleLabel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(PIUS_WHITE);
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
            String status = resultSet.getString("status");
            
            // Get student name from session
            String studentName = studentSession.getInstance().getFullName();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            // Style for field labels
            Font labelFont = new Font("Arial", Font.BOLD, 14);
            
            // Add fields with Pius colors
            addFormField(formPanel, gbc, "Student:", studentName, 0, labelFont);
            addFormField(formPanel, gbc, "Student ID:", String.valueOf(studentId), 1, labelFont);
            addFormField(formPanel, gbc, "Service Type:", serviceType, 2, labelFont);
            
            // Description as text area
            gbc.gridx = 0;
            gbc.gridy = 3;
            JLabel descLabel = new JLabel("Description:");
            descLabel.setFont(labelFont);
            descLabel.setForeground(PIUS_NAVY);
            formPanel.add(descLabel, gbc);
            
            JTextArea descriptionArea = new JTextArea(serviceDescription, 4, 30);
            descriptionArea.setEditable(false);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
            descriptionArea.setForeground(PIUS_NAVY);
            JScrollPane descScrollPane = new JScrollPane(descriptionArea);
            descScrollPane.setBorder(BorderFactory.createLineBorder(PIUS_NAVY, 1));
            
            gbc.gridx = 1;
            gbc.gridy = 3;
            formPanel.add(descScrollPane, gbc);
            
            addFormField(formPanel, gbc, "Service Hours:", String.valueOf(eventLength), 4, labelFont);
            addFormField(formPanel, gbc, "Supervisor Email:", supervisorEmail, 5, labelFont);
            addFormField(formPanel, gbc, "Submission Date:", dateFormat.format(submissionDate), 6, labelFont);
            addFormField(formPanel, gbc, "Status:", status, 7, labelFont);
            
            // Add the form panel to a scroll pane
            JScrollPane formScrollPane = new JScrollPane(formPanel);
            formScrollPane.setBorder(BorderFactory.createLineBorder(PIUS_NAVY, 1));
            formScrollPane.setBackground(PIUS_WHITE);
            mainPanel.add(formScrollPane);
            
            // Close button
            JButton closeButton = new JButton("Close");
            closeButton.setBackground(PIUS_GOLD);
            closeButton.setForeground(PIUS_NAVY);
            closeButton.setFont(new Font("Arial", Font.BOLD, 14));
            closeButton.setFocusPainted(false);
            closeButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, String value, int row, Font labelFont) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(PIUS_NAVY);
        panel.add(label, gbc);
        
        JTextField field = new JTextField(value, 30);
        field.setEditable(false);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(PIUS_NAVY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(PIUS_NAVY);
        menuBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
        // File menu
        JMenu fileMenu = new JMenu("Home");
        fileMenu.setForeground(PIUS_WHITE);
        fileMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem dashboard = new JMenuItem("Dashboard");
        dashboard.setBackground(PIUS_WHITE);
        dashboard.setForeground(PIUS_NAVY);
        dashboard.setFont(new Font("Arial", Font.PLAIN, 14));
        fileMenu.add(dashboard);
        dashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                SwingUtilities.invokeLater(() -> {
                    studentHomepage homepage = new studentHomepage();
                    homepage.setVisible(true);
                });
            }
        });

        JMenuItem exit = new JMenuItem("Exit");
        exit.setBackground(PIUS_WHITE);
        exit.setForeground(PIUS_NAVY);
        exit.setFont(new Font("Arial", Font.PLAIN, 14));
        fileMenu.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        
        // Service menu
        JMenu serviceMenu = new JMenu("Service");
        serviceMenu.setForeground(PIUS_WHITE);
        serviceMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem serviceForm = new JMenuItem("Submit Service");
        serviceForm.setBackground(PIUS_WHITE);
        serviceForm.setForeground(PIUS_NAVY);
        serviceForm.setFont(new Font("Arial", Font.PLAIN, 14));
        serviceMenu.add(serviceForm);

        serviceForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceReportingForm serviceForm = new serviceReportingForm();
                serviceForm.setVisible(true);
            }
        });
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(PIUS_WHITE);
        helpMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem instructions = new JMenuItem("Instructions");
        instructions.setBackground(PIUS_WHITE);
        instructions.setForeground(PIUS_NAVY);
        instructions.setFont(new Font("Arial", Font.PLAIN, 14));
        helpMenu.add(instructions);
        
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instructionPage instructionPage = new instructionPage();
                instructionPage.setVisible(true);
            }
        });

        JMenuItem resetPassword = new JMenuItem("Reset Password");
        resetPassword.setBackground(PIUS_WHITE);
        resetPassword.setForeground(PIUS_NAVY);
        resetPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        helpMenu.add(resetPassword);

        resetPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                forgotPasswordForm form = new forgotPasswordForm();
                form.setVisible(true);
            }
        });
        
        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(serviceMenu);
        menuBar.add(helpMenu);
        
        // Set the menu bar to the frame
        setJMenuBar(menuBar);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            viewAllSubmissions frame = new viewAllSubmissions();
            frame.setVisible(true);
        });
    }
}