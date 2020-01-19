package GUIDesign;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.sql.Date;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


class DataException extends Exception{
	private static final long serialVersionUID = 3257749654939520367L;
	public String errorinfo;
	public DataException(String p) {
		errorinfo = p;
	}
}
//˵��:ʵ���ʹ�õ� DataProcessing��
public class DataProcessing extends JFrame
{
	private static final long serialVersionUID = 1L;
	static Hashtable<String, User> users;
	static Hashtable<String, Doc> docs;
	static String UPLOAD_PATH = "E:\\OOP\\uploadFile";
	static String DOWNLOAD_PATH = "E:\\OOP\\downloadFile";
    static Connection conn = null;
    static Statement stmt;
    static Socket socket;
    static String SERVER_IP = "localhost";
    static int SEVER_PORT = 5002;
	public static void connect()
	{
		String url = "jdbc:mysql://localhost:3306/document?" + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");// ��̬����mysql����
			System.out.println("�ɹ�����MySQL��������");
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();	 
		}
		catch (SQLException e)
		{
			System.out.println("MySQL��������");
			e.printStackTrace();
		} catch (Exception e)
		{
			System.out.println("MySQL����");
			e.printStackTrace();
		} 
	}
	
	// ˵��: ʵ����������� ʹ�ô˴�Init()����
	public static void Init() throws IOException, DataException, SQLException
	{
		users = new Hashtable<String, User>();
		String name, password, role;
		String	sql = "select * from user_info";
		ResultSet rs = stmt.executeQuery(sql);// executeQuery�᷵�ؽ���ļ��ϣ����򷵻ؿ�ֵ
		while (rs.next())
		{
			name = rs.getString(1).trim();
			password = rs.getString(2).trim();
			role = rs.getString(3).trim();
			if (role.equalsIgnoreCase("Operator"))
				users.put(name, new Operator(name, password, role));
			else if (role.equalsIgnoreCase("Browser"))
				users.put(name, new Browser(name, password, role));
			else if (role.equalsIgnoreCase("Administrator"))
				users.put(name, new Administrator(name, password, role));
			else
			{
				throw new DataException("�û������Ϣ��ʽ����");
			}
		}			
	}
	
	//�����û���Ϣ
	public static User searchUser(String name)
	{
		if (users.containsKey(name))
		{
			User temp = users.get(name);
			return temp;
		}
		return null;
	}
	
    //�����û���Ϣ����
	public static User searchUser(String name, String password)
	{
		if (users.containsKey(name))
		{
			User temp = users.get(name);
			if ((temp.getPassword()).equals(password))
				return temp;
		}
		return null;
	}
    
	//ȡ��ϣ���е������û���Ϣ
	public static Enumeration<User> getAllUser()
	{
		Enumeration<User> e = users.elements();
		return e;
	}
    
	// ���¹�ϣ������ݿ����û���Ϣ
	public static boolean updateUser(String name, String password, String role) throws IOException, DataException
	{
		User user;
		if (users.containsKey(name))
		{
			//���¹�ϣ�����û���Ϣ
			if (role.equalsIgnoreCase("Administrator"))
				user = new Administrator(name, password, role);
			else if (role.equalsIgnoreCase("Operator"))
				user = new Operator(name, password, role);
			else if(role.equalsIgnoreCase("Browser"))
				user = new Browser(name, password, role);
			else {
				throw new DataException("�û������Ϣ��ʽ����");
			}
			users.put(name, user);
            //�������ݿ����û���Ϣ
			String	sql = "update user_info set password = '"+password+"',role='"+role+"' where username='"+name+"'";
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;	
	    } 
		else
			return false;	
	}
		
	// �������û�
	public static boolean insertUser(String name, String password, String role) throws IOException
	{
		User user;
		if (users.containsKey(name))
			return false;
		else
		{
			//����û���Ϣ����ϣ����
			if (role.equalsIgnoreCase("administrator"))
				user = new Administrator(name, password, role);
			else if (role.equalsIgnoreCase("operator"))
				user = new Operator(name, password, role);
			else
				user = new Browser(name, password, role);
			users.put(name, user);
			//����û���Ϣ�����ݿ���
			String sql = "insert into user_info values('" + name + "', '" + password + "', '" + role + "')";
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}

	// ɾ���û�
	public static boolean deleteUser(String name) throws IOException
	{
		if (users.containsKey(name))
		{
			//ɾ����ϣ�����û���Ϣ
			users.remove(name);
			//ɾ�����ݿ����û���Ϣ
			String	sql = "delete from user_info where username='" + name + "'";	
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		else
			return false;	
	}

	
	// �����µĵ����ļ���Ϣ�����ݿ���
	public static boolean insertDoc(String ID, String creator, long timestamp, String description, String filename) throws IOException
	{
		java.util.Date jdate = new Date(timestamp);
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = dFormat.format(jdate);
		String	sql = " insert into doc_info(creator,timestamp,description,filename) values('" + creator + "', '" + time + "', '" + description + "', '" + filename + "')";	
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}   
		return true;
	}
	
	//�ļ��б�
	public static void FileList()
	{
		JFrame frame = new JFrame();
		frame.setBounds((1920 - 1080)/2 ,(1080 - 700)/2,  1080, 700);
		frame.getContentPane().setLayout(new BorderLayout());
		
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
		    ResultSet rs = stmt.executeQuery(sql);// executeQuery�᷵�ؽ���ļ��ϣ����򷵻ؿ�ֵ
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
		
		JButton button2 = new JButton("����");
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(20));
		panel.add(button2);
		
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "�Ƿ��˳�", "ѯ��", JOptionPane.OK_CANCEL_OPTION))
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				else
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
							
							socket = new Socket(SERVER_IP, SEVER_PORT);
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
 
	
	
	//�����ļ�
	public static void copyFile(File sourse,File target) throws IOException
	 {
		 BufferedReader br = new BufferedReader(new FileReader(sourse));
		 PrintWriter bw = new PrintWriter(new BufferedWriter(new FileWriter(target)));
		 String line; 
		 while((line = br.readLine()) != null)
		 {
			 bw.println(line);
		 }	 
		 br.close();
		 bw.flush();
		 bw.close();
	 }
	  
}