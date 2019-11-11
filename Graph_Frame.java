import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import java.awt.Dimension;

public class Graph_Frame extends JFrame {

	private JPanel contentPane;
	private DrawGraph graph;
	double fastestSpeed= 0.0;

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try {
					Graph_Frame frame = new Graph_Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Graph_Frame()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(650, 100, 916, 504);

		List<Double> scores = new ArrayList<Double>();
		List<Integer> distances = new ArrayList<Integer>();
		
		Scanner s,d;

		try 
		{
			s = new Scanner(new File("Data.txt"));
			d = new Scanner(new File("Distance.txt"));
			
			while (s.hasNext())
			{
			    scores.add(Double.parseDouble(s.next()));		       
			}
			s.close();
			
			while (d.hasNext())
			{
			    distances.add(Integer.parseInt(d.next()));   
			}
			d.close();
		} 
		catch (FileNotFoundException e) {}
		
	    graph = new DrawGraph(scores);				
	    graph.setSize(new Dimension(400, 325));
	    graph.setPreferredSize(new Dimension(400, 325));
        graph.setMaximumSize(new Dimension(400, 325));
		
		getContentPane().add(graph, BorderLayout.NORTH);
		graph.setLayout(new BoxLayout(graph, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblFastestAverageSpeed = new JLabel("Fastest Average Speed: " + Collections.max(scores) +" mph");
		lblFastestAverageSpeed.setBounds(340, 13, 246, 16);
		panel.add(lblFastestAverageSpeed);
		
		int sum = 0;
		for(int i = 0; i < distances.size(); i++)
		{
		    sum += distances.get(i);
		}
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		System.out.println(sum);
		
		JLabel lblTotalDistanceCovered = new JLabel("Total Distance Covered: "+ df.format(sum/1609.344) + " miles");
		lblTotalDistanceCovered.setBounds(354, 58, 246, 16);
		panel.add(lblTotalDistanceCovered);
		
	}
	public Dimension getGraphMaximumSize() 
	{
		return graph.getMaximumSize();
	}
	public void setGraphMaximumSize(Dimension maximumSize) 
	{
		graph.setMaximumSize(maximumSize);
	}
}
