package com.gui;

import com.dao.DepartmentDao;
import com.dao.ProfessorDao;
import com.dao.StudentDao;
import com.dao.imp.DepartmentDaoImp;
import com.dao.imp.ProfessorDaoImp;
import com.dao.imp.StudentDaoImp;
import com.entities.Department;
import com.entities.Professor;
import com.entities.Student;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends JFrame {

    private JLabel lblEmail;
    private JTextField txtEmail;

    private JLabel lblPassword;
    private JTextField txtPassword;

    private JButton btnLogin;
    private JButton btnSignin;

    private ImageIcon image;
    private JLabel jufeLogo;



    public Login() {
        this.setTitle("JUFE App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        //Components Declaration
        image = new ImageIcon("img/jufeLogo.jpeg");
        jufeLogo = new JLabel(image);


        lblEmail = new JLabel("Email");
        txtEmail = new JTextField();

        lblPassword = new JLabel("Password");
        txtPassword = new JPasswordField();

        btnLogin = new JButton("Login");
        btnSignin = new JButton("Signin");


        //Components emplacement
        jufeLogo.setBounds(100, 0, 225, 225);

        lblEmail.setBounds(100, 230, 170, 20);
        txtEmail.setBounds(100, 260, 170, 20);

        lblPassword.setBounds(100, 290, 170, 20);
        txtPassword.setBounds(100, 320, 170, 20);

        btnLogin.setBounds(100,350,80, 20);
        btnSignin.setBounds(190,350,80,20);


        //Components add
        this.add(jufeLogo);

        this.add(lblEmail);
        this.add(txtEmail);

        this.add(lblPassword);
        this.add(txtPassword);

        this.add(btnLogin);
        this.add(btnSignin);

        //Components actions
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login, password;

                login = txtEmail.getText().trim();
                password = txtPassword.getText();

                if (!login.isEmpty() && !password.isEmpty()) {
                    ProfessorDao professorDao = new ProfessorDaoImp();
                    // Check for the existance of the professor in database
                    if (professorDao.isProfessor(login, password) == true) {
                        // Buisness windows
                        getContentPane().removeAll();
                        validate();
                        repaint();

                        JButton addStudent = new JButton("+Add");
                        JButton deleteStudent = new JButton("-Del");

                        addStudent.setBounds(10,10, 100, 20);
                        deleteStudent.setBounds(120,10,100, 20);

                        add(addStudent);
                        add(deleteStudent);

                        JTable jTable;
                        StudentDao studentDao = new StudentDaoImp();
                        List<Student> ls = studentDao.getAll();
                        String[] columnNames = { "id", "fname", "lname" };
                        Object[][] data = new Object[ls.size()][3];

                        for (int i = 0; i < ls.size(); i++) {
                            Student student = ls.get(i);
                            data[i][0] = student.getId();
                            data[i][1] = student.getFname();
                            data[i][2] = student.getLname();
                        }

                        jTable = new JTable(data,columnNames);

                        jTable.setBounds(10, 50, 210, 100 );
                        JScrollPane scrollPane = new JScrollPane(jTable);
                        getContentPane().add(scrollPane, BorderLayout.CENTER);
                        add(jTable);

                        addStudent.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                repaint();
                                JButton add = new JButton("Submit");

                                JLabel lblsfname = new JLabel("First Name");
                                JTextField txtsfname = new JTextField();

                                JLabel lblslname = new JLabel("Last Name");
                                JTextField txtslname = new JTextField();

                                lblsfname.setBounds(10,160,100, 20);
                                txtsfname.setBounds(120,160,100, 20);

                                lblslname.setBounds(10,190,100, 20);
                                txtslname.setBounds(120,190,100, 20);

                                add.setBounds(10,220,100, 20);

                                add(lblsfname);
                                add(txtsfname);

                                add(lblslname);
                                add(txtslname);

                                add(add);

                                add.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        String fname = txtsfname.getText();
                                        String lname = txtslname.getText();

                                        Student s = new Student(fname, lname);
                                        studentDao.create(s);

                                        getContentPane().removeAll();
                                        validate();
                                        repaint();

                                        JButton addStudent = new JButton("+Add");
                                        JButton deleteStudent = new JButton("-Del");

                                        addStudent.setBounds(10,10, 100, 20);
                                        deleteStudent.setBounds(120,10,100, 20);

                                        JTable jTable1;

                                        StudentDao studentDao = new StudentDaoImp();
                                        List<Student> ls = studentDao.getAll();
                                        String[] columnNames = { "id", "fname", "lname" };
                                        Object[][] data = new Object[ls.size()][3];

                                        for (int i = 0; i < ls.size(); i++) {
                                            Student student = ls.get(i);
                                            data[i][0] = student.getId();
                                            data[i][1] = student.getFname();
                                            data[i][2] = student.getLname();
                                        }

                                        jTable1 = new JTable(data,columnNames);

                                        jTable1.setBounds(10, 50, 210, 100 );
                                        JScrollPane scrollPane = new JScrollPane(jTable1);
                                        getContentPane().add(scrollPane, BorderLayout.CENTER);

                                        add(addStudent);
                                        add(deleteStudent);
                                        add(jTable1);
                                    }
                                });
                            }
                        });

                        deleteStudent.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                repaint();
                                JButton del = new JButton("Submit");

                                JLabel lblid = new JLabel("Id");
                                JTextField txtid = new JTextField();

                                lblid.setBounds(10,160,100, 20);
                                txtid.setBounds(120,160,100, 20);
                                del.setBounds(10,190, 100, 20);

                                add(lblid);
                                add(txtid);

                                add(del);

                                del.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        int id = Integer.parseInt(txtid.getText());

                                        Map<String, Integer> keys = new HashMap<>();
                                        keys.put("id", Integer.valueOf(id));
                                        studentDao.delete(keys);

                                        getContentPane().removeAll();
                                        validate();
                                        repaint();

                                        JButton addStudent = new JButton("+Add");
                                        JButton deleteStudent = new JButton("-Del");

                                        addStudent.setBounds(10,10, 100, 20);
                                        deleteStudent.setBounds(120,10,100, 20);

                                        JTable jTable1;

                                        StudentDao studentDao = new StudentDaoImp();
                                        List<Student> ls = studentDao.getAll();
                                        String[] columnNames = { "id", "fname", "lname" };
                                        Object[][] data = new Object[ls.size()][3];

                                        for (int i = 0; i < ls.size(); i++) {
                                            Student student = ls.get(i);
                                            data[i][0] = student.getId();
                                            data[i][1] = student.getFname();
                                            data[i][2] = student.getLname();
                                        }

                                        jTable1 = new JTable(data,columnNames);

                                        jTable1.setBounds(10, 50, 210, 100 );
                                        JScrollPane scrollPane = new JScrollPane(jTable1);
                                        getContentPane().add(scrollPane, BorderLayout.CENTER);

                                        add(addStudent);
                                        add(deleteStudent);
                                        add(jTable1);
                                    }
                                });
                            }
                        });


                    } else {
                        txtEmail.setText("");
                        txtPassword.setText("");
                    }
                }
            }
        });

        btnSignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the frame from old components
                // for painting the new components of the signin windows
                getContentPane().removeAll();
                validate();
                repaint();


                JLabel lblFname;
                JTextField txtFname;

                JLabel lblLname;
                JTextField txtLname;

                JLabel lblpEmail;
                JTextField txtpEmail;

                JLabel lblpPassword;
                JTextField txtpPassword;

                String[] listDepartments = {"A","B", "C", "D"};
                JLabel lblDepartment;
                JComboBox combo_depatments;

                JButton btnpLogin;
                JButton btnSubmit;

                //Components Declaration
                lblFname = new JLabel("First Name");
                txtFname = new JTextField();

                lblLname = new JLabel("Last Name");
                txtLname = new JTextField();

                lblpEmail = new JLabel("Email");
                txtpEmail = new JTextField();

                lblpPassword = new JLabel("Password");
                txtpPassword = new JPasswordField();

                lblDepartment = new JLabel("Department");
                combo_depatments = new JComboBox(listDepartments);

                btnpLogin = new JButton("< Login");
                btnSubmit = new JButton("Submit");


                //Components emplacement
                lblFname.setBounds(10,10,170, 20);
                txtFname.setBounds(10, 40, 170, 20);

                lblLname.setBounds(10,70, 170, 20);
                txtLname.setBounds(10, 100,170,20);

                lblpEmail.setBounds(10,130,170,20);
                txtpEmail.setBounds(10,160,170,20);

                lblpPassword.setBounds(10,190,170,20);
                txtpPassword.setBounds(10,220,170,20);

                lblDepartment.setBounds(10,250,170,20);
                combo_depatments.setBounds(10,280,170,20);

                btnSubmit.setBounds(10,320,80,20);
                btnpLogin.setBounds(100,320,80,20);


                //Components add
                add(lblFname);
                add(txtFname);

                add(lblLname);
                add(txtLname);

                add(lblpEmail);
                add(txtpEmail);

                add(lblpPassword);
                add(txtpPassword);

                add(lblDepartment);
                add(combo_depatments);

                add(btnSubmit);
                add(btnpLogin);

                //Components actions
                btnpLogin.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getContentPane().removeAll();
                        validate();
                        repaint();

                        add(jufeLogo);

                        add(lblEmail);
                        add(txtEmail);

                        add(lblPassword);
                        add(txtPassword);

                        add(btnLogin);
                        add(btnSignin);

                    }
                });
                btnSubmit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DepartmentDao departmentDao = new DepartmentDaoImp();
                        ProfessorDao professorDao = new ProfessorDaoImp();

                        String fname;
                        String lname;
                        String login;
                        String passwrd;
                        String department_name;
                        int department_id;

                        fname = txtFname.getText().trim();
                        lname = txtLname.getText().trim();
                        login = txtpEmail.getText().trim();
                        passwrd = txtpPassword.getText();
                        department_name = combo_depatments.getSelectedItem().toString();


                        // Check if all inputs are not empty ...
                        if (!fname.isEmpty() && !lname.isEmpty() && !login.isEmpty() && !passwrd.isEmpty()) {

                            // Get the departements id from database
                            department_id = departmentDao.getDepartmentId(department_name);

                            Department d = new Department();
                            d.setId(department_id);

                            Professor p = new Professor(fname, lname, login, passwrd, d);

                            // Add that professor to the database ...
                            professorDao.create(p);

                            // Back to the login windows
                            getContentPane().removeAll();
                            validate();
                            repaint();

                            add(jufeLogo);

                            add(lblEmail);
                            add(txtEmail);

                            add(lblPassword);
                            add(txtPassword);

                            add(btnLogin);
                            add(btnSignin);
                        }

                    }
                });


            }
        });

        this.setVisible(true);
    }

}
