package GUIDesign;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Enumeration;


public class Administrator extends User{

	private JFrame frame;
	private JPanel contentPane;
	private JTextField Modi_Name;
	private JPasswordField Modi_password;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JTextField textField;
	public Administrator(String name,String password,String role) {
		super(name,password,role);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void showMenu() throws IOException, DataException {
		frame = new JFrame();
		frame.setTitle("����¼��Ա�˵�");
		frame.setBounds((1920 - 1080)/2 ,(1080 - 700)/2,  1080, 700);
		frame.setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane); 
		contentPane.setLayout(null);

		//���ֲ˵�ѡ��
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu FileMenu = new JMenu("�ļ�");
		menuBar.add(FileMenu);
		
		JMenuItem FileList_menuItem = new JMenuItem("�ļ��б�");
		FileMenu.add(FileList_menuItem);
		FileList_menuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				DataProcessing.FileList();
			}
		});
		
		
		JMenuItem DownLoadFile_menuItem = new JMenuItem("�����ļ�");
		FileMenu.add(DownLoadFile_menuItem);
		DownLoadFile_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataProcessing.FileList();
			}
		});
		

		JMenu UserMenu = new JMenu("�û�");
		menuBar.add(UserMenu);
		
		JMenuItem AddUser_menuItem = new JMenuItem("����û�");
		AddUser_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					UserList();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		UserMenu.add(AddUser_menuItem);
		
		JMenuItem DelUser_MenuItem = new JMenuItem("ɾ���û�");
		UserMenu.add(DelUser_MenuItem);
		DelUser_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				try {
					UserList();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		JMenuItem ModiUser_menuItem = new JMenuItem("�޸��û���Ϣ");
		UserMenu.add(ModiUser_menuItem);
		ModiUser_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserOperate();
			}
		});
		
		JMenuItem ChangeInfo_MenuItem = new JMenuItem("�޸�����");
		UserMenu.add(ChangeInfo_MenuItem);
		ChangeInfo_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserOperate();
			}			
		});
		
		//��ӹرմ����¼�����
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(frame, "�Ƿ��˳�","��ʾ",JOptionPane.OK_CANCEL_OPTION))
				    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				else
				    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
			}
		});
	}
	
	//���û��Ĳ���
	public void UserOperate() 
	{
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		//���ֹ������
		JPanel panelModi = new JPanel();
		tabbedPane.addTab("�޸��û���Ϣ", null, panelModi, null);
		panelModi.setLayout(null);
		
		JPanel Changeinfo_panel = new JPanel();
		tabbedPane.addTab("�޸�����", null, Changeinfo_panel, null);
		Changeinfo_panel.setLayout(null);						
				
		//�޸��û���Ϣ
		JLabel Modi_label_Name = new JLabel("�û���");
		Modi_label_Name.setBounds(235, 116, 72, 18);
		panelModi.add(Modi_label_Name);
		
		Modi_Name = new JTextField();
		Modi_Name.setText("");
		Modi_Name.setBounds(377, 113, 114, 24);
		panelModi.add(Modi_Name);
		Modi_Name.setColumns(10);
		
		JLabel Modi_label_password = new JLabel("������");
		Modi_label_password.setBounds(235, 173, 72, 18);
		panelModi.add(Modi_label_password);
		
		Modi_password = new JPasswordField();
		Modi_password.setBounds(377, 170, 114, 24);
		panelModi.add(Modi_password);
		
		JLabel Modi_label_role = new JLabel("�����");
		Modi_label_role.setBounds(235, 237, 72, 18);
		panelModi.add(Modi_label_role);
		
		JComboBox<Object> Modi_role = new JComboBox<Object>();
		Modi_role.setModel(new DefaultComboBoxModel<Object>(new String[] {"Operator", "Browser"}));
		Modi_role.setBounds(377, 234, 114, 24);
		panelModi.add(Modi_role);
		
		JButton Modi_button_OK = new JButton("ȷ��");
		Modi_button_OK.setBounds(235, 322, 256, 27);
		panelModi.add(Modi_button_OK);
		Modi_button_OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = DataProcessing.searchUser(Modi_Name.getText());
				if(user == null)
					JOptionPane.showMessageDialog(null, "�����ڸ��û�", "UnSucceed", JOptionPane.ERROR_MESSAGE);
				else {
						try {
							if(DataProcessing.updateUser(Modi_Name.getText(), new String(Modi_password.getPassword()), Modi_role.getSelectedItem().toString())) {
								JOptionPane.showMessageDialog(null, "�����û���Ϣ��" + Modi_Name.getText() + "���ɹ�", "��Ϣ", JOptionPane.PLAIN_MESSAGE);
								frame.dispose();
								showMenu();
							}	
							else
								JOptionPane.showMessageDialog(null, "�޸�ʧ��", "UnSucceed", JOptionPane.INFORMATION_MESSAGE);
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (Exception e1) {
							e1.printStackTrace();
						}		
				}	
			}
		});	
		
		//�޸�����

		JLabel Label1 = new JLabel("��ǰ�û�");
		Label1.setBounds(239, 113, 72, 18);
		Changeinfo_panel.add(Label1);
		
	    textField = new JTextField();
		textField.setBounds(402, 110, 150, 24);
		Changeinfo_panel.add(textField);
		textField.setColumns(10);
		textField.setText(getName());
		textField.setEditable(false);
		
		JLabel Label2 = new JLabel("�����������");
		Label2.setBounds(239, 162, 91, 32);
		Changeinfo_panel.add(Label2);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(20);
		passwordField.setBounds(402, 166, 150, 24);
		Changeinfo_panel.add(passwordField);
		
		JLabel Label3 = new JLabel("������������");
		Label3.setBounds(239, 219, 91, 18);
		Changeinfo_panel.add(Label3);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setText("");
		passwordField_1.setBounds(402, 216, 150, 24);
		Changeinfo_panel.add(passwordField_1);
		
		JLabel Label4 = new JLabel("���ٴ�����������");
		Label4.setBounds(239, 276, 120, 18);
		Changeinfo_panel.add(Label4);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(402, 273, 150, 24);
		Changeinfo_panel.add(passwordField_2);
		
		JButton btnNewButton_1 = new JButton("\u786E\u8BA4");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String opassword = new String(passwordField.getPassword());
				String newpassword1 = new String(passwordField_1.getPassword());
				String newpassword2 = new String(passwordField_2.getPassword());
				User user = DataProcessing.searchUser(getName(),opassword);
				if(user == null)
					JOptionPane.showMessageDialog(null, "����������", "UnSucceed", JOptionPane.ERROR_MESSAGE);
				else
				    try {
					if(newpassword1.equals(newpassword2)) {
						if(newpassword1.equals(""))
							JOptionPane.showMessageDialog(null, "���벻������Ϊ��", "֪ͨ", JOptionPane.ERROR_MESSAGE);
						else {
							if(newpassword1.equals(opassword))
								JOptionPane.showMessageDialog(null, "�������ԭ����һ��,�����޸�", "֪ͨ", JOptionPane.INFORMATION_MESSAGE);
							else {
								DataProcessing.updateUser(getName(), newpassword1, getRole());
								JOptionPane.showMessageDialog(null, "�޸ĳɹ�", "Succeed", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
					else
						JOptionPane.showMessageDialog(null, "�������������벻һ��", "�޸�ʧ��", JOptionPane.ERROR_MESSAGE);		
				} catch (Exception e1) {
						e1.printStackTrace();
				} 
			}
		});
		btnNewButton_1.setBounds(324, 376, 156, 32);
		Changeinfo_panel.add(btnNewButton_1);
	}
	
	
	//�û��б���������û���ɾ���û�����
	public void UserList() throws SQLException
	{
		JFrame uframe = new JFrame();
		uframe.setVisible(true);
		uframe.getContentPane().setLayout(new BorderLayout());
		
		JPanel panelList = new JPanel();
		uframe.setContentPane(panelList);
		
		try {
			DataProcessing.Init();
		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (DataException e1) {
			e1.printStackTrace();
		} //�ȳ�ʼ���ļ��������޸���Ϣ��ʱ����
	
        String[] columnNames = {"�û���","����","���"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
            {
                return false;
            }			
		};
        
		Enumeration<User> e = DataProcessing.getAllUser();
		User user;
		while(e.hasMoreElements())
		{
			user = e.nextElement();
			String[] userinfo = {user.getName(),user.getPassword(),user.getRole()};
			tableModel.addRow(userinfo);
		}
		
		JTable table = new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		panelList.add(scrollPane,BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(20));
		JButton Add_button = new JButton("���");
		JButton Del_Button = new JButton("ɾ��");
        panel.add(Add_button);
        panel.add(Del_Button);
        
		panelList.add(panel,BorderLayout.SOUTH);
		
		Add_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//����û�
				JLabel Add_label_Name = new JLabel("�û���");
				JTextField Add_Name = new JTextField();
				Add_Name.setColumns(20);
				JLabel Add_label_password = new JLabel("����");
				JPasswordField Add_password = new JPasswordField();
				JLabel Add_label_role = new JLabel("���");
				JComboBox<Object> Add_role = new JComboBox<Object>();
				Add_role.setModel(new DefaultComboBoxModel<Object>(new String[] {"Operator", "Browser"}));
				JButton btnNewButton = new JButton("ȷ��");
				
				JPanel Addpanel = new JPanel();
				GroupLayout gLayout = new GroupLayout(Addpanel);
				Addpanel.setLayout(gLayout);
				//����ˮƽ������
				GroupLayout.SequentialGroup hp = gLayout.createSequentialGroup();
				hp.addGap(20);
				hp.addGroup(gLayout.createParallelGroup(Alignment.LEADING).addComponent(Add_label_Name).addComponent(Add_label_password).addComponent(Add_label_role));
				hp.addGap(20);
				hp.addGroup(gLayout.createParallelGroup(Alignment.LEADING).addComponent(Add_Name).addComponent(Add_password).addComponent(Add_role));
				hp.addGap(20);
				hp.addGroup(gLayout.createParallelGroup(Alignment.CENTER).addComponent(btnNewButton));
				
				gLayout.setHorizontalGroup(hp);
				//���ô�ֱ������
				GroupLayout.SequentialGroup hp1 = gLayout.createSequentialGroup();
				hp1.addGap(20);
				hp1.addGroup(gLayout.createParallelGroup(Alignment.CENTER).addComponent(Add_label_Name).addComponent(Add_Name));
				hp1.addGap(20);
				hp1.addGroup(gLayout.createParallelGroup(Alignment.CENTER).addComponent(Add_label_password).addComponent(Add_password));
				hp1.addGap(20);
				hp1.addGroup(gLayout.createParallelGroup(Alignment.CENTER).addComponent(Add_label_role).addComponent(Add_role));
				hp1.addGap(20);
				hp1.addGroup(gLayout.createParallelGroup(Alignment.LEADING).addComponent(btnNewButton));
				
				gLayout.setVerticalGroup(hp1);
				JFrame aframe = new JFrame("����û�");
				aframe.setContentPane(Addpanel);
				aframe.setSize(400,350);
				aframe.setLocationRelativeTo(null);
				aframe.setVisible(true);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String userName = Add_Name.getText();
						String password = new String(Add_password.getPassword());
						String role = (String) Add_role.getSelectedItem();
		                
						try {
							if(DataProcessing.insertUser(userName, password, role)) {
								JOptionPane.showMessageDialog(null, "�����û���" + userName + "���ɹ�", "��Ϣ", JOptionPane.PLAIN_MESSAGE);
								aframe.dispose();
								uframe.dispose();
								try {
									UserList();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
								
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
					}
				});
				aframe.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(frame, "�Ƿ��˳�","��ʾ",JOptionPane.OK_CANCEL_OPTION))
						    aframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						else
						    aframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
					}
				});	
			}
		});
		//ɾ���û�
		Del_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1)
					JOptionPane.showMessageDialog(null, "����ѡ��һ��", "֪ͨ", JOptionPane.ERROR_MESSAGE);
				String name = table.getValueAt(row, 0).toString();
				if(name.equals(getName()))
					JOptionPane.showMessageDialog(null, "�û�����ɾ���Լ�", "֪ͨ", JOptionPane.ERROR_MESSAGE);
				else
				{
					try {
						if(DataProcessing.deleteUser(name)) {
							//�����ļ��б���
							DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
							tableModel.removeRow(row);	// currentRow��Ҫɾ���������
							JOptionPane.showMessageDialog(null, "ɾ���û���" + name + "���ɹ�", "��Ϣ", JOptionPane.PLAIN_MESSAGE);
						}
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}	
				}
			}
		});		
		
		uframe.pack();
		uframe.setLocation((1920 - uframe.getWidth())/2,(1080 - uframe.getHeight())/2);
		//��ӹرմ����¼�����
		uframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(frame, "�Ƿ��˳�","��ʾ",JOptionPane.OK_CANCEL_OPTION))
				    uframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				else
				    uframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
			}
		});		
	}
}