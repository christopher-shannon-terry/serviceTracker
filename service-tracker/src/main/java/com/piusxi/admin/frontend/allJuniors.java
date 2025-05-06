package com.piusxi.admin.frontend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.piusxi.admin.backend.showJuniors;
import com.piusxi.student.database.serviceSubmissionDatabase;
import com.piusxi.student.database.studentInformationDatabase;

public class allJuniors extends JFrame {
    private JTextField searchField;
    private JList<String> studentList;
    private DefaultListModel<String> listModel;
    private JTable serviceTable;
    private DefaultTableModel tableModel;
    private JPanel servicePanel;
    private JScrollPane serviceScrollPane;
    private Object[][] juniorData;
    private Object[][] serviceData;
    private JLabel titleLabel;
    private JLabel selectedStudentLabel;
    
    public allJuniors() {
        setTitle("Junior Students");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title at the top
        titleLabel = new JLabel("Junior Students", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Left panel - search and student list
        JPanel leftPanel = new JPanel(new BorderLayout(5, 10));
        leftPanel.setPreferredSize(new Dimension(300, 600));
        
        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search Students:");
        searchField = new JTextField(20);
        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        
        // Student list
        listModel = new DefaultListModel<>();
        studentList = new JList<>(listModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentList.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane listScrollPane = new JScrollPane(studentList);
        leftPanel.add(listScrollPane, BorderLayout.CENTER);
        
        // Right panel - service information
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        
        selectedStudentLabel = new JLabel("Select a student to view their service records", JLabel.CENTER);
        selectedStudentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.add(selectedStudentLabel, BorderLayout.NORTH);
        
        servicePanel = new JPanel(new BorderLayout());
        
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
        serviceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Hide the Submission ID column as it's only used for retrieving the specific record
        serviceTable.getColumnModel().getColumn(4).setMinWidth(0);
        serviceTable.getColumnModel().getColumn(4).setMaxWidth(0);
        serviceTable.getColumnModel().getColumn(4).setWidth(0);
        
        serviceScrollPane = new JScrollPane(serviceTable);
        servicePanel.add(serviceScrollPane, BorderLayout.CENTER);
        rightPanel.add(servicePanel, BorderLayout.CENTER);
        
        // Add left and right panels to the main panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);
        mainPanel.add(splitPane, BorderLayout.CENTER);
       
        add(mainPanel);
        loadJuniorData();
        addEventListeners();
    }
    
    private void loadJuniorData() {
        Connection connection = studentInformationDatabase.connect();
        
        if (connection != null) {
            juniorData = showJuniors.getJuniorInfo(connection);
            serviceData = showJuniors.getJuniorService(connection);
            
            // Sort the data by last name, then first name
            Arrays.sort(juniorData, new Comparator<Object[]>() {
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
            for (Object[] student : juniorData) {
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
        
        for (Object[] student : juniorData) {
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
            for (Object[] student : juniorData) {
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
            for (Object[] student : juniorData) {
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
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, String value, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        
        JTextField field = new JTextField(value, 30);
        field.setEditable(false);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            allJuniors frame = new allJuniors();
            frame.setVisible(true);
        });
    }
}