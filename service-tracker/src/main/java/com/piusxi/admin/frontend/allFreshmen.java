package com.piusxi.admin.frontend;

import com.piusxi.admin.backend.showFreshmen;
import com.piusxi.student.database.serviceSubmissionDatabase;
import com.piusxi.student.database.studentInformationDatabase;
import com.piusxi.student.frontend.serviceReportingForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.piusxi.admin.backend.generateReport;
import com.piusxi.admin.backend.serviceStatus;

public class allFreshmen extends JFrame {
    // Pius XI school colors - added for consistency with student side
    private static final Color PIUS_NAVY = new Color(0, 32, 91);
    private static final Color PIUS_GOLD = new Color(255, 215, 0);
    private static final Color PIUS_WHITE = Color.WHITE;
    private static final Color LIGHT_GRAY_BG = new Color(245, 245, 250);
    
    private JTextField searchField;
    private JList<String> studentList;
    private DefaultListModel<String> listModel;
    private JTable serviceTable;
    private DefaultTableModel tableModel;
    private JPanel servicePanel;
    private JScrollPane serviceScrollPane;
    private Object[][] freshmenData;
    private Object[][] serviceData;
    private JLabel titleLabel;
    private JLabel selectedStudentLabel;
    
    public allFreshmen() {
        setTitle("Freshman Students");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(PIUS_WHITE);
        
        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(PIUS_WHITE);
        
        // Title at the top
        titleLabel = new JLabel("Freshman Students", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PIUS_NAVY);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Left panel - search and student list
        JPanel leftPanel = new JPanel(new BorderLayout(5, 10));
        leftPanel.setPreferredSize(new Dimension(300, 600));
        leftPanel.setBackground(PIUS_WHITE);
        leftPanel.setBorder(BorderFactory.createLineBorder(PIUS_NAVY, 1));
        
        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(PIUS_WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel searchLabel = new JLabel("Search Students:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchLabel.setForeground(PIUS_NAVY);
        
        searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        
        // Student list
        listModel = new DefaultListModel<>();
        studentList = new JList<>(listModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentList.setFont(new Font("Arial", Font.PLAIN, 14));
        studentList.setBackground(PIUS_WHITE);
        studentList.setSelectionBackground(PIUS_GOLD);
        studentList.setSelectionForeground(PIUS_NAVY);
        
        JScrollPane listScrollPane = new JScrollPane(studentList);
        listScrollPane.setBorder(BorderFactory.createLineBorder(PIUS_NAVY, 1));
        leftPanel.add(listScrollPane, BorderLayout.CENTER);
        
        // Right panel - service information
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(PIUS_WHITE);
        
        selectedStudentLabel = new JLabel("Select a student to view their service records", JLabel.CENTER);
        selectedStudentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectedStudentLabel.setForeground(PIUS_NAVY);
        rightPanel.add(selectedStudentLabel, BorderLayout.NORTH);
        
        servicePanel = new JPanel(new BorderLayout());
        servicePanel.setBackground(PIUS_WHITE);
        
        // Create table model and table for service submissions
        String[] columnNames = {"Type", "Hours", "Supervisor Email", "Submission Date", "Submission ID"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        serviceTable = new JTable(tableModel);
        serviceTable.setFillsViewportHeight(true);
        serviceTable.setRowHeight(25);
        serviceTable.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Style the table header
        JTableHeader header = serviceTable.getTableHeader();
        header.setBackground(PIUS_NAVY);
        header.setForeground(PIUS_WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Style the table rows with alternating colors
        serviceTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        
        // Hide the Submission ID column as it's only used for retrieving the specific record
        serviceTable.getColumnModel().getColumn(4).setMinWidth(0);
        serviceTable.getColumnModel().getColumn(4).setMaxWidth(0);
        serviceTable.getColumnModel().getColumn(4).setWidth(0);
        
        serviceScrollPane = new JScrollPane(serviceTable);
        serviceScrollPane.setBorder(BorderFactory.createLineBorder(PIUS_NAVY, 1));
        servicePanel.add(serviceScrollPane, BorderLayout.CENTER);
        rightPanel.add(servicePanel, BorderLayout.CENTER);
        
        // Add left and right panels to the main panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);
        splitPane.setBorder(null);
        mainPanel.add(splitPane, BorderLayout.CENTER);
       
        add(mainPanel);
        loadFreshmenData();
        addEventListeners();
        createMenuBar();
    }

    public void createMenuBar() {
        JMenuBar navigationBar = new JMenuBar();
        navigationBar.setBackground(PIUS_NAVY);
        navigationBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        JMenu home = new JMenu("Home");
        home.setForeground(PIUS_WHITE);
        home.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem dashboard = new JMenuItem("Dashboard");
        dashboard.setBackground(PIUS_WHITE);
        dashboard.setForeground(PIUS_NAVY);
        dashboard.setFont(new Font("Arial", Font.PLAIN, 14));
        home.add(dashboard);
        dashboard.addActionListener((ActionEvent e) -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                new adminHomepage().setVisible(true);
            });
        });

        JMenuItem exit = new JMenuItem("Exit");
        exit.setBackground(PIUS_WHITE);
        exit.setForeground(PIUS_NAVY);
        exit.setFont(new Font("Arial", Font.PLAIN, 14));
        home.add(exit);
        exit.addActionListener((ActionEvent e) -> {
            dispose();
            
            System.exit(0);
        });

        JMenu studentsMenu = new JMenu("Students");
        studentsMenu.setForeground(PIUS_WHITE);
        studentsMenu.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem allStudents = new JMenuItem("All");
        allStudents.setBackground(PIUS_WHITE);
        allStudents.setForeground(PIUS_NAVY);
        allStudents.setFont(new Font("Arial", Font.PLAIN, 14));
        studentsMenu.add(allStudents);
        allStudents.addActionListener((ActionEvent e) -> {
            dispose();

            SwingUtilities.invokeLater(() -> {
                new allStudents().setVisible(true);
            });
        });

        JMenuItem sophomoreStudents = new JMenuItem("Sophomores");
        sophomoreStudents.setBackground(PIUS_WHITE);
        sophomoreStudents.setForeground(PIUS_NAVY);
        sophomoreStudents.setFont(new Font("Arial", Font.PLAIN, 14));
        studentsMenu.add(sophomoreStudents);
        sophomoreStudents.addActionListener((ActionEvent e) -> {
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                new allSophomores().setVisible(true);
            });
        });
        
        JMenuItem juniorStudents = new JMenuItem("Juniors");
        juniorStudents.setBackground(PIUS_WHITE);
        juniorStudents.setForeground(PIUS_NAVY);
        juniorStudents.setFont(new Font("Arial", Font.PLAIN, 14));
        studentsMenu.add(juniorStudents);
        juniorStudents.addActionListener((ActionEvent e) -> {
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                new allJuniors().setVisible(true);
            });
        });

        JMenuItem seniorStudents = new JMenuItem("Seniors");
        seniorStudents.setBackground(PIUS_WHITE);
        seniorStudents.setForeground(PIUS_NAVY);
        seniorStudents.setFont(new Font("Arial", Font.PLAIN, 14));
        studentsMenu.add(seniorStudents);
        seniorStudents.addActionListener((ActionEvent e) -> {
            dispose();
            
            SwingUtilities.invokeLater(() -> {
                new allSeniors().setVisible(true);
            });
        });

        JMenu reports = new JMenu("Reports");
        reports.setForeground(PIUS_WHITE);
        reports.setFont(new Font("Arial", Font.BOLD, 14));
        
        JMenuItem generateReports = new JMenuItem("Generate Report");
        generateReports.setBackground(PIUS_WHITE);
        generateReports.setForeground(PIUS_NAVY);
        generateReports.setFont(new Font("Arial", Font.PLAIN, 14));
        reports.add(generateReports);
        generateReports.addActionListener((ActionEvent e) -> {
            try {
                String report = generateReport.generateFile(null);
                JOptionPane.showMessageDialog(this, 
                    "Report generated successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SQLException se) {
                se.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Database error: " + se.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (IOException ie) {
                ie.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "File error: " + ie.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        navigationBar.add(home);
        navigationBar.add(studentsMenu);
        navigationBar.add(reports);

        setJMenuBar(navigationBar);
    }
    
    private void loadFreshmenData() {
        Connection connection = studentInformationDatabase.connect();
        if (connection != null) {
            freshmenData = showFreshmen.getFreshmenInfo(connection);
            serviceData = showFreshmen.getFreshmenService(connection);
            
            // Sort the data by last name, then first name
            Arrays.sort(freshmenData, new Comparator<Object[]>() {
                @Override
                public int compare(Object[] o1, Object[] o2) {
                    String lastName1 = (String) o1[2];
                    String lastName2 = (String) o2[2];
                    int lastNameCompare = lastName1.compareTo(lastName2);
                    
                    if (lastNameCompare != 0) {
                        return lastNameCompare;
                    } 
                    else {
                        String firstName1 = (String) o1[1];
                        String firstName2 = (String) o1[1];
                        return firstName1.compareTo(firstName2);
                    }
                }
            });
            
            // Populate the list model
            for (Object[] student : freshmenData) {
                int id = (Integer) student[0];
                String firstName = (String) student[1];
                String lastName = (String) student[2];
                listModel.addElement(lastName + ", " + firstName + " (ID: " + id + ")");
            }
        } 
        else {
            JOptionPane.showMessageDialog(this, 
                    "Could not connect to the database.", 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addEventListeners() {
        // Search field document listener
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterStudents();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterStudents();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterStudents();
            }
        });
        
        // Student list mouse listener for double-click
        studentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    displayStudentServiceInfo();
                }
            }
        });

        studentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayStudentServiceInfo();
            }
        });
        
        // Service table mouse listener for double-click
        serviceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = serviceTable.getSelectedRow();
                    if (row >= 0) {
                        openServiceSubmissionForm(row);
                    }
                }
            }
        });
    }
    
    // Search for students
    private void filterStudents() {
        String searchText = searchField.getText().toLowerCase();
        DefaultListModel<String> filteredModel = new DefaultListModel<>();
        
        for (Object[] student : freshmenData) {
            String firstName = ((String) student[1]).toLowerCase();
            String lastName = ((String) student[2]).toLowerCase();
            int id = (Integer) student[0];
            
            if (firstName.contains(searchText) || lastName.contains(searchText) || 
                    String.valueOf(id).contains(searchText)) {
                filteredModel.addElement(lastName + ", " + firstName + " (ID: " + id + ")");
            }
        }
        
        studentList.setModel(filteredModel);
    }
    
    private void displayStudentServiceInfo() {
        int selectedIndex = studentList.getSelectedIndex();
        if (selectedIndex >= 0) {
            String selectedItem = studentList.getSelectedValue();
            
            // Extract student ID from the selected item
            int startPos = selectedItem.lastIndexOf("(ID: ") + 5;
            int endPos = selectedItem.lastIndexOf(")");
            int studentId = Integer.parseInt(selectedItem.substring(startPos, endPos));
            
            // Find student info
            String firstName = "";
            String lastName = "";
            for (Object[] student : freshmenData) {
                if ((Integer) student[0] == studentId) {
                    firstName = (String) student[1];
                    lastName = (String) student[2];
                    break;
                }
            }
            
            // Update selected student label
            selectedStudentLabel.setText("Service Records for " + firstName + " " + lastName);
            
            // Clear the table
            tableModel.setRowCount(0);
            
            // Query the database directly to get all submission details including ID
            try {
                Connection serviceConnection = serviceSubmissionDatabase.connect();
                if (serviceConnection != null) {
                    String query = "SELECT submission_id, service_type, service_event_length, " +
                                   "supervisor_email, submission_date " +
                                   "FROM service_submissions " +
                                   "WHERE student_id = ? " +
                                   "ORDER BY submission_date DESC";
                    
                    PreparedStatement statement = serviceConnection.prepareStatement(query);
                    statement.setInt(1, studentId);
                    ResultSet resultSet = statement.executeQuery();
                    
                    boolean hasRecords = false;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    
                    while (resultSet.next()) {
                        hasRecords = true;
                        int submissionId = resultSet.getInt("submission_id");
                        String serviceType = resultSet.getString("service_type");
                        double hours = resultSet.getDouble("service_event_length");
                        String supervisorEmail = resultSet.getString("supervisor_email");
                        Timestamp submissionDate = resultSet.getTimestamp("submission_date");
                        
                        tableModel.addRow(new Object[]{
                                serviceType,
                                hours,
                                supervisorEmail,
                                dateFormat.format(submissionDate),
                                submissionId  // Hidden column
                        });
                    }
                    
                    if (!hasRecords) {
                        tableModel.addRow(new Object[]{"No service records found", "", "", "", ""});
                    }
                    
                    resultSet.close();
                    statement.close();
                    serviceConnection.close();
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
                tableModel.addRow(new Object[]{"Error retrieving service records", "", "", "", ""});
            }
        }
    }
    
    private void openServiceSubmissionForm(int selectedRow) {
        if (selectedRow >= 0) {
            // Get the submission ID from the hidden column
            int submissionId = Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString());
            
            try {
                Connection serviceConnection = serviceSubmissionDatabase.connect();
                if (serviceConnection != null) {
                    // Query to get all the details for this submission
                    String query = "SELECT * FROM service_submissions WHERE submission_id = ?";
                    PreparedStatement statement = serviceConnection.prepareStatement(query);
                    statement.setInt(1, submissionId);
                    ResultSet resultSet = statement.executeQuery();
                    
                    if (resultSet.next()) {
                        // Create and show a read-only form pre-filled with the submission data
                        showSubmissionForm(resultSet);
                    } 
                    else {
                        JOptionPane.showMessageDialog(this, 
                                "Could not find submission details.", 
                                "Record Not Found", 
                                JOptionPane.ERROR_MESSAGE);
                    }
                    
                    resultSet.close();
                    statement.close();
                    serviceConnection.close();
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                        "Error retrieving submission details: " + e.getMessage(), 
                        "Database Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showSubmissionForm(ResultSet resultSet) {
        try {
            JFrame submissionFrame = new JFrame("Service Submission Details");
            submissionFrame.setSize(600, 700);
            submissionFrame.setLocationRelativeTo(this);
            submissionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            submissionFrame.getContentPane().setBackground(PIUS_WHITE);
            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(PIUS_WHITE);
            
            JLabel titleLabel = new JLabel("Service Submission Details", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
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
            
            // Get student name from the student database
            String studentName = "Unknown";
            for (Object[] student : freshmenData) {
                if ((Integer) student[0] == studentId) {
                    studentName = student[1] + " " + student[2];
                    break;
                }
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            Font labelFont = new Font("Arial", Font.BOLD, 14);
            
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
            addFormField(formPanel, gbc, "Supervisor Email:", supervisorEmail, 6, labelFont);
            addFormField(formPanel, gbc, "Submission Date:", dateFormat.format(submissionDate), 8, labelFont);
            
            // Add the form panel to a scroll pane
            JScrollPane formScrollPane = new JScrollPane(formPanel);
            formScrollPane.setBorder(BorderFactory.createLineBorder(PIUS_NAVY, 1));
            formScrollPane.setBackground(PIUS_WHITE);
            mainPanel.add(formScrollPane);
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            buttonPanel.setBackground(PIUS_WHITE);
            
            JButton closeButton = new JButton("Close");
            styleButton(closeButton, LIGHT_GRAY_BG);
            
            JButton approveButton = new JButton("Approve");
            styleButton(approveButton, PIUS_GOLD);
            
            JButton rejectButton = new JButton("Reject");
            styleButton(rejectButton, PIUS_GOLD);

            closeButton.addActionListener((ActionEvent e) -> {
                submissionFrame.dispose();

                SwingUtilities.invokeLater(() -> {
                    new adminHomepage().setVisible(true);
                });
            });

            approveButton.addActionListener((ActionEvent e) -> {
                try {
                    int submissionId = resultSet.getInt("submission_id");
                    boolean success = serviceStatus.setApproved(submissionId);

                    if (success) {
                        JOptionPane.showMessageDialog(submissionFrame, 
                            "Submission approved successfully!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else {
                        JOptionPane.showMessageDialog(submissionFrame, 
                            "Failed to approve submission.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    submissionFrame.dispose();
                } 
                catch (SQLException se) {
                    se.printStackTrace();
                    JOptionPane.showMessageDialog(submissionFrame, 
                        "Database error: " + se.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            rejectButton.addActionListener((ActionEvent e) -> {
                try {
                    int submissionId = resultSet.getInt("submission_id");
                    boolean success = serviceStatus.setRejected(submissionId);

                    if (success) {
                        JOptionPane.showMessageDialog(submissionFrame, 
                            "Submission rejected.", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else {
                        JOptionPane.showMessageDialog(submissionFrame, 
                            "Failed to reject submission.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    submissionFrame.dispose();
                } 
                catch (SQLException se) {
                    se.printStackTrace();
                    JOptionPane.showMessageDialog(submissionFrame, 
                        "Database error: " + se.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            buttonPanel.add(rejectButton);
            buttonPanel.add(approveButton);
            buttonPanel.add(closeButton);

            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(buttonPanel);
            
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
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PIUS_NAVY, 1),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
    
    // Overload the addFormField method to maintain backward compatibility
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, String value, int row) {
        addFormField(panel, gbc, labelText, value, row, new Font("Arial", Font.BOLD, 14));
    }
    
    // Helper method to style buttons consistently
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(PIUS_NAVY);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }
}