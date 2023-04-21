import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WinAddMember extends JDialog {
	private JTextField tfName;
	private JTextField tfMobile2;
	private JTextField tfEailId;
	private JTextField tfBirth;
	private JTextField tfAddress;
	private JTextField tfMobile3;
	private JComboBox cbMobile1;
	private JLabel lblMail;
	private JComboBox cbEamilCompany;
	private JComboBox cbgradYear;
	protected String filePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinAddMember dialog = new WinAddMember();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinAddMember() {
		setTitle("회원등록");
		setBounds(100, 100, 490, 353);
		getContentPane().setLayout(null);
		
		JLabel lblPic = new JLabel("");
		lblPic.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JFileChooser chooser = new JFileChooser("c:\\rlagus\\");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지 파일", "jpg", "png", "pig");
					chooser.setFileFilter(filter);
					if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						filePath = chooser.getSelectedFile().getPath();
						ImageIcon icon = new ImageIcon(filePath);
						Image image = icon.getImage();
						image = image.getScaledInstance(120, 140, Image.SCALE_SMOOTH);
						icon = new ImageIcon(image);
						lblPic.setIcon(icon);
					}
				}
			}
		});
		lblPic.setToolTipText("더블클릭해서 사진 선택하시오.");
		lblPic.setOpaque(true);
		lblPic.setBackground(new Color(255, 250, 205));
		lblPic.setBounds(24, 27, 120, 140);
		getContentPane().add(lblPic);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(156, 27, 57, 15);
		getContentPane().add(lblName);
		
		tfName = new JTextField();
		tfName.setHorizontalAlignment(SwingConstants.LEFT);
		tfName.setBounds(225, 24, 136, 21);
		getContentPane().add(tfName);
		tfName.setColumns(10);
		
		tfMobile2 = new JTextField();
		tfMobile2.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMobile2.setColumns(10);
		tfMobile2.setBounds(294, 66, 74, 21);
		getContentPane().add(tfMobile2);
		
		JLabel lblMobile = new JLabel("전화번호:");
		lblMobile.setBounds(156, 69, 57, 15);
		getContentPane().add(lblMobile);
		
		tfEailId = new JTextField();
		tfEailId.setColumns(10);
		tfEailId.setBounds(223, 110, 103, 21);
		getContentPane().add(tfEailId);
		
		JLabel lblEmail = new JLabel("이메일:");
		lblEmail.setBounds(156, 113, 57, 15);
		getContentPane().add(lblEmail);
		
		tfBirth = new JTextField();
		tfBirth.setHorizontalAlignment(SwingConstants.RIGHT);
		tfBirth.setColumns(10);
		tfBirth.setBounds(225, 146, 136, 21);
		getContentPane().add(tfBirth);
		
		JLabel lblBirth = new JLabel("생년월일:");
		lblBirth.setBounds(156, 145, 57, 15);
		getContentPane().add(lblBirth);
		
		JLabel lblGradYear = new JLabel("졸업년도:");
		lblGradYear.setBounds(156, 185, 57, 15);
		getContentPane().add(lblGradYear);
		
		tfAddress = new JTextField();
		tfAddress.setHorizontalAlignment(SwingConstants.LEFT);
		tfAddress.setColumns(10);
		tfAddress.setBounds(57, 220, 285, 21);
		getContentPane().add(tfAddress);
		
		JLabel lblAddress = new JLabel("주소:");
		lblAddress.setBounds(12, 223, 57, 15);
		getContentPane().add(lblAddress);
		
		JButton btnSearch = new JButton("도로명 찾기...");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinSearchDoro winSearchDoro = new WinSearchDoro();
				winSearchDoro.setModal(true);
				winSearchDoro.setVisible(true);
				tfAddress.setText(winSearchDoro.getAddress() + " ");
				tfAddress.requestFocus();
			}
		});
		btnSearch.setBounds(354, 219, 109, 23);
		getContentPane().add(btnSearch);
		
		JButton btnCalendar = new JButton("달력...");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfBirth.setText(winCalendar.getDate());				
			}
		});
		btnCalendar.setBounds(373, 145, 74, 23);
		getContentPane().add(btnCalendar);
		
		JButton btnInsert = new JButton("회원 가입");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertRecord();
			}
		});
		btnInsert.setBounds(181, 251, 135, 45);
		getContentPane().add(btnInsert);
		
		cbgradYear = new JComboBox();
		cbgradYear.setBounds(225, 185, 136, 23);
		getContentPane().add(cbgradYear);		
				
		cbMobile1 = new JComboBox();
		cbMobile1.setModel(new DefaultComboBoxModel(new String[] {"010", "02", "031", "032", "033", "041", "042", "043", "044", "051", "052", "053", "054", "055", "061", "062", "063", "064"}));
		cbMobile1.setBounds(225, 65, 57, 23);
		getContentPane().add(cbMobile1);
		
		tfMobile3 = new JTextField();
		tfMobile3.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMobile3.setColumns(10);
		tfMobile3.setBounds(380, 66, 74, 21);
		getContentPane().add(tfMobile3);
		
		lblMail = new JLabel("@");
		lblMail.setFont(new Font("굴림", Font.BOLD, 16));
		lblMail.setBounds(338, 112, 23, 15);
		getContentPane().add(lblMail);
		
		cbEamilCompany = new JComboBox();
		cbEamilCompany.setEditable(true);
		cbEamilCompany.setModel(new DefaultComboBoxModel(new String[] {"naver.com", "daum.net", "gmail.com", "nate.com", "ici.or.kr", "직접입력"}));
		cbEamilCompany.setBounds(370, 109, 93, 23);
		getContentPane().add(cbEamilCompany);
		
		// ---------------------------------------------------------------------------------
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		for(int i=2000; i<=year; i++) {
			cbgradYear.addItem(i);
		cbgradYear.setSelectedItem(year);
		}
	}

	protected void insertRecord() {
		String sName = tfName.getText();
		String sMobile = cbMobile1.getSelectedItem() + tfMobile2.getText() + tfMobile3.getText();
		String sEmail = tfEailId.getText() + lblMail.getText() + cbEamilCompany.getSelectedItem();
		String sBirth = tfBirth.getText();
		String sGradYear = cbgradYear.getSelectedItem().toString();
		String sAddress = tfAddress.getText();
		String sPath = filePath;
		
		String sql = sName+","+sMobile+","+sEmail+","+sBirth+","+sGradYear+",";
		sql = sql+sAddress+","+sPath;
		
		try {					
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
			Statement stmt = con.prepareStatement(sql);
			// 여기서부터

		} catch(ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}
}
