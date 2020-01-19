package GUIDesign;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class Operator extends User
{
	private JFrame frame; //��������
	private JFrame fileframe; //�ļ�������
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JTextField textField;
	public Operator(String name,String password,String role) {
		super(name,password,role);
	}
	

    public void showMenu() throws IOException, DataException {
		frame = new JFrame();
		frame.setTitle("����¼��Ա�˵�");
		frame.setBounds((1920 - 1080)/2 ,(1080 - 700)/2,  1080, 700);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane); 
		
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
				FileList();
			}
		});
		
		JMenuItem uploadfile_MenuItem = new JMenuItem("�ϴ��ļ�");
		uploadfile_MenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileList();
			}
		});
		FileMenu.add(uploadfile_MenuItem);
		
		JMenu UserMenu = new JMenu("�û�");
		menuBar.add(UserMenu);
		
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
		
		frame.setVisible(true);
	}
	
	//�û�����
	
	public void UserOperate()
	{
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel Changeinfo_panel = new JPanel();
		Changeinfo_panel.setLayout(null);
		tabbedPane.addTab("�޸�����", Changeinfo_panel);
		
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
	//�ļ��б�
	public void FileList()
	{
		fileframe = new JFrame();
		fileframe.setTitle("�ļ��б�");
		fileframe.setBounds((1920 - 1080)/2 ,(1080 - 700)/2,  1080, 700);
		fileframe.getContentPane().setLayout(new BorderLayout());
		
		String[] columnNames = {"�ļ����","������","���һ�β���ʱ��","�ļ�����","�ļ���"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		
		try {
			DataProcessing.Init();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (DataException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = "select * from doc_info";
		try {
		    ResultSet rs = DataProcessing.stmt.executeQuery(sql);// executeQuery�᷵�ؽ���ļ��ϣ����򷵻ؿ�ֵ
		    DateFormat da = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (rs.next())
			{
				tableModel.addRow(new String[] {rs.getString(1),rs.getString(2),da.format(rs.getTimestamp(3)),rs.getString(4),rs.getString(5)}); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JTable table = new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		JButton button1 = new JButton("�ϴ�");
		JButton button2 = new JButton("����");
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(20));
		panel.add(button1);
		panel.add(button2);
		
		fileframe.getContentPane().add(scrollPane, BorderLayout.CENTER);
		fileframe.getContentPane().add(panel, BorderLayout.SOUTH);
		fileframe.setVisible(true);
		
		fileframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "�Ƿ��˳�", "ѯ��", JOptionPane.OK_CANCEL_OPTION))
					fileframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				else
					fileframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		});
		//�ϴ��ļ�
		button1.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0) {
				Fileupload();
			}
		});
		//�����ļ�
        button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread()
				{
					public void run() {
						JProgressBar jBar = new JProgressBar();
						jBar.setPreferredSize(new Dimension(400, 50));
						
						JFrame barframe = new JFrame("�����ļ�");
						barframe.getContentPane().setLayout(new BorderLayout(5,5));
						barframe.getContentPane().add(jBar, BorderLayout.NORTH);
						barframe.pack();
						barframe.setVisible(true);
						barframe.setLocationRelativeTo(null);
						barframe.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent e) {
								barframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							}
						});
						
						int row = table.getSelectedRow();
						String filename = table.getValueAt(row, 4).toString();
						try {
							
							Socket socket = new Socket(DataProcessing.SERVER_IP, DataProcessing.SEVER_PORT);
							System.out.println("���ӳɹ�");
							
							DataOutputStream fwriter = new DataOutputStream(socket.getOutputStream());
							DataInputStream in = new DataInputStream(socket.getInputStream());
							DataOutputStream out = new DataOutputStream(new FileOutputStream(new File("e:/OOP/downloadFile/"+filename)));
							//���������ļ�ָ��
							fwriter.writeUTF("download"); //�߳̽�������̬
							
						    //����Ҫ���ص��ļ�������������
						    fwriter.writeUTF(filename);
						   
						    //�Ƚ����ļ�����
						    long filelength = Long.parseLong(in.readUTF());
						    
							//Ȼ����ܷ������˴��������ļ� 
						    byte[] buffer = new byte[5000];
						    int len = 0;
						    long curlength = 0;
						    boolean mark = true;
						    while(mark)
						    {
						    	while((len = in.read(buffer, 0, buffer.length)) != -1)
							    {
							    	out.write(buffer, 0, len);
							    	curlength = curlength + len;
							    	long i = curlength*100/filelength;
							    	jBar.setValue((int)i);
							    	jBar.setString("������"+i+"%");	
							    	jBar.setStringPainted(true);
							    	if(i == 100) {
							    		JOptionPane.showMessageDialog(null, "���سɹ�", "֪ͨ", JOptionPane.INFORMATION_MESSAGE);
							    		mark = false;
							    	}		
							    }
						    }
						    //�رո�����
						    out.close();
						    socket.close();
						} catch (UnknownHostException e1) {
							e1.printStackTrace();
						} catch (IOException e11) {
							e11.printStackTrace();
						}
					}
				}.start();	
			}	
		}); 
	}	
	//�ϴ��ļ�
	public  void Fileupload()
	{
		JFrame ufFrame = new JFrame("�ϴ��ļ�");
		ufFrame.getContentPane().setLayout(new BorderLayout());
		ufFrame.setBounds((1920 - 750)/2, (1080 - 550)/2, 750, 550);
		ufFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		ufFrame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("�������ļ�����");
		lblNewLabel_1.setBounds(158, 129, 120, 18);
		panel.add(lblNewLabel_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(309, 140, 228, 133);
		panel.add(textArea);
		
		JLabel lblNewLabel_2 = new JLabel("�������ļ���");
		lblNewLabel_2.setBounds(158, 303, 100, 18);
		panel.add(lblNewLabel_2);
		
		JTextField textField_2 = new JTextField();
		textField_2.setBounds(309, 300, 181, 24);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("�ļ������");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser("d:\\"); //�򿪷������ļ��б�,Ĭ�ϴ�D��
				int result = fileChooser.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();
					textField_2.setText(file.getAbsolutePath());
				}
			}
		});
		btnNewButton_2.setBounds(504, 299, 113, 27);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("ȷ���ϴ�");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread()
				{
					public void run() 
					{ 
						try {
						    Socket socket = new Socket(DataProcessing.SERVER_IP, DataProcessing.SEVER_PORT);
						    System.out.println("���ӳɹ�");
						    
						    File file = new File(textField_2.getText());
						    DataInputStream in = new DataInputStream(new FileInputStream(file));
							DataOutputStream out = new DataOutputStream(socket.getOutputStream());
							
							//�����ϴ�ָ��
							out.writeUTF("upload");
							
							//�ϴ��ļ���
							out.writeUTF(file.getName());
							
							JProgressBar jBar = new JProgressBar();
							jBar.setPreferredSize(new Dimension(400, 50));
							
							JFrame frame = new JFrame("�ϴ��ļ�");
							frame.getContentPane().setLayout(new BorderLayout(5,5));
							frame.getContentPane().add(jBar,BorderLayout.NORTH);
							frame.setLocationRelativeTo(null);
							frame.pack();
							frame.setVisible(true);
							frame.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
									frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
								}
							});
							
							
							//�����ļ�
							byte[] buffer = new byte[5000];
							int length = 0;
							long curlength = 0;
							long filelength = file.length();
							while ((length = in.read(buffer, 0, buffer.length)) != -1) 
							{
								out.write(buffer, 0, length);
								curlength = curlength + length;
								long i = curlength*100/filelength;
								jBar.setValue((int)i);
								jBar.setString("���ϴ�"+i+"%");
								jBar.setStringPainted(true);
								if(i == 100) {
									JOptionPane.showMessageDialog(null, "�ϴ��ɹ�", "֪ͨ", JOptionPane.INFORMATION_MESSAGE);	
								}	
							}
							//�������ݿ��е��ļ���Ϣ
							DataProcessing.insertDoc("0", name, System.currentTimeMillis(), textArea.getText(), file.getName());
							JOptionPane.showMessageDialog(null, "�ϴ��ɹ�", "֪ͨ", JOptionPane.INFORMATION_MESSAGE);
							fileframe.dispose();
							FileList(); //ˢ�½���
							in.close();
							out.close();
							socket.close();
						} catch (UnknownHostException e2) {
							e2.printStackTrace();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
				}.start();	
			}
		});
		btnNewButton_3.setBounds(158, 375, 459, 27);
		panel.add(btnNewButton_3);
		
		ufFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(ufFrame, "�Ƿ��˳�", "��ʾ", JOptionPane.OK_CANCEL_OPTION))
					ufFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				else
					ufFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		});
	}	
}
