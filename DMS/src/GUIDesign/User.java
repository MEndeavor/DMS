package GUIDesign;
import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;


public abstract class User
{

	protected String name;
	protected String password;
	protected String role;
	
	Scanner input = new Scanner(System.in);
	JPanel panel;
	JLabel l1,l2;
	JTextField t1, t2;
	JButton b1,b2;
	
	User(){}
	//���캯��
	User(String name, String password, String role)
	{
		this.name = name;
		this.password = password;
		this.role = role;
	}
		
    //��ʾ�˵�
	public abstract void showMenu() throws IOException,DataException;
	
    //�˳�ϵͳ
	public void exitSystem()
	{
		System.out.println("ϵͳ�˳�, ллʹ�� ! ");
		System.exit(0);
	}
    //ȡ�û���
	public String getName()
	{
		return name;
	}
    //�����û���
	public void setName(String name)
	{
		this.name = name;
	}
    //ȡ�û�����
	public String getPassword()
	{
		return password;
	}
    //�����û�����
	public void setPassword(String password)
	{
		this.password = password;
	}
    //ȡ�û���ɫ
	public String getRole()
	{
		return role;
	}
    //�����û���ɫ
	public void setRole(String role)
	{
		this.role = role;
	}
    //��ӡ�û���Ϣ
	public void print() {
		System.out.println(getName() + "\t" + getPassword() + "\t" + getRole());
	}
	
}