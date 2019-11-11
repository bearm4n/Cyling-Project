import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.io.*;

public class TestFrame extends JFrame {

	private JPanel contentPane;
	private JTextField distance;
	private JTextField timeTaken;

	public double D_distance = 0.0;
	public String time = "";
	public int timeInSeconds = 0;
	public double speed = 0.0000;
	public int minute;
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					TestFrame frame = new TestFrame();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public TestFrame() 
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 463);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblITitle = new JLabel("Cycling Calculator");
		lblITitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblITitle.setFont(new Font("Berlin Sans FB", Font.PLAIN, 35));
		lblITitle.setBounds(0, 0, 518, 75);
		contentPane.add(lblITitle);
		
		JLabel lblDistanceCycled = new JLabel("Distance Cycled:");
		lblDistanceCycled.setBounds(12, 68, 104, 30);
		contentPane.add(lblDistanceCycled);
		
		distance = new JTextField();
		distance.setText("2100");
		distance.setBounds(12, 99, 80, 48);
		contentPane.add(distance);
		distance.setColumns(10);
		
		JLabel lblTimeTaken = new JLabel("Time Taken:");
		lblTimeTaken.setBounds(12, 160, 104, 30);
		contentPane.add(lblTimeTaken);
			
		JLabel lblM = new JLabel("m");
		lblM.setBounds(106, 115, 56, 16);
		contentPane.add(lblM);
		
		JLabel lblMmss = new JLabel("min : sec");
		lblMmss.setBounds(104, 219, 56, 16);
		contentPane.add(lblMmss);
		
		JLabel lblCaloriesBurned = new JLabel("Calories Burned:");
		lblCaloriesBurned.setBounds(259, 167, 176, 16);
		contentPane.add(lblCaloriesBurned);
		
		timeTaken = new JTextField();
		timeTaken.setText("");
		timeTaken.setBounds(12, 203, 80, 48);
		contentPane.add(timeTaken);
		timeTaken.setColumns(10);
		
		JLabel lblSpeed = new JLabel("Average Speed:");
		lblSpeed.setBounds(259, 109, 176, 25);
		contentPane.add(lblSpeed);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				
				timeInSeconds = convertTime(timeTaken.getText());
				D_distance = Double.parseDouble(distance.getText());
				speed = D_distance/timeInSeconds;
				
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);				
				
				String mphSpeed = df.format((speed*2.237));
				String dist = distance.getText();
				lblSpeed.setText("Average speed: " + mphSpeed+ " mph");
				lblCaloriesBurned.setText("Calories Burned: " + df.format(((75 *6.8*3.5)/200)*minute)+ " Kcal");

			    try 
			    {
			    	BufferedWriter f = new BufferedWriter(new FileWriter("Data.txt", true));
					f.write(mphSpeed);
					f.newLine();
					f.close();
					BufferedWriter w = new BufferedWriter(new FileWriter("Distance.txt", true));
					w.write(dist);
					w.newLine();
					w.close();
					JOptionPane.showMessageDialog(null,"The data has been saved");
				} 
			    catch (IOException e) { }
	
			}
		});
		btnSubmit.setBounds(12, 306, 97, 25);
		contentPane.add(btnSubmit);
		
		JButton btnViewGraph = new JButton("View Graph");
		btnViewGraph.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
					Graph_Frame GF = new Graph_Frame();
					GF.main(null);
			}
		});
		btnViewGraph.setBounds(12, 367, 114, 25);
		contentPane.add(btnViewGraph);
		
		JLabel lblImage = new JLabel("");
		lblImage.setBackground(Color.WHITE);
		lblImage.setIcon(new ImageIcon("C:\\Users\\User\\Pictures\\bike.jpg"));
		lblImage.setBounds(237, 219, 256, 177);
		contentPane.add(lblImage);
	}
	
	public int convertTime(String timeTaken)
	{
		String[] timeCalc = timeTaken.split(":");
		minute = Integer.parseInt(timeCalc[0]);
		int second = Integer.parseInt(timeCalc[1]);
		return (second + (60*minute));
	}
}
