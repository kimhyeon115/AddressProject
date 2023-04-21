import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinSearchDoro extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinSearchDoro dialog = new WinSearchDoro();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JTextField tfDoro;
	private JList list;
	private String sAddress;
	
	public String getAddress() {
		return sAddress;
	}

	/**
	 * Create the dialog.
	 */
	public WinSearchDoro() {
		setTitle("도로명 선택");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		tfDoro = new JTextField();
		panel.add(tfDoro);
		tfDoro.setColumns(10);
		
		JButton btnSearch = new JButton("탐색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String doro = tfDoro.getText();
				
				try {					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");
					Statement stmt = con.createStatement();
					
					String sql = "select * from address_table where doro='" + doro + "'";
					ResultSet rs = stmt.executeQuery(sql);
					
					Vector<String> v = new Vector<>();
					// Vector 가변 리스트(값의 개수가 미상일때 사용)
					
					while(rs.next()) {
						String ssi = rs.getString("si");
						String sgu = rs.getString("gu");
						String sdong = rs.getString("dong");
						
						v.add(ssi+"  "+sgu+"  "+sdong);
					}
					list.setListData(v);
				} catch(ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}							
			}
		});
		panel.add(btnSearch);
		
		JPanel panelResult = new JPanel();
		getContentPane().add(panelResult, BorderLayout.CENTER);
		panelResult.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelResult.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sAddress = list.getSelectedValue().toString();
				dispose();				
			}
		});
		scrollPane.setViewportView(list);

	}

}
