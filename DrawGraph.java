import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

@SuppressWarnings("serial")
public class DrawGraph extends JPanel 
{
   private static final int MAX_SCORE = 10;
   private static final int PREF_W = 400;
   private static final int PREF_H = 325;
   private static final int BORDER_GAP = 30;
   private static final Color GRAPH_COLOR_GOOD = Color.green;
   private static final Color GRAPH_COLOR_BAD = Color.red;
   private static final Color GRAPH_COLOR_AVERAGE = Color.gray;
   private static final Color GRAPH_POINT_COLOR = Color.black;
   private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
   private static final int GRAPH_POINT_WIDTH = 5;
   private static final int Y_HATCH_CNT = 9;
   private List<Double> scores;

   public DrawGraph(List<Double> scores) 
   {
   	setMaximumSize(new Dimension(400, 325));
      this.scores = scores;
      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
   }

   @Override
   protected void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (scores.size() - 1);
      double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SCORE - 1);

      Scanner s;
      double sum = 0;
		try 
		{
			s = new Scanner(new File("Data.txt"));

			while (s.hasNext())
			{
			   sum = sum + Double.parseDouble(s.next());	       
			}
			s.close();
			
		} 
		catch (FileNotFoundException e) {}
		double average = sum/scores.size(); 
		System.out.println(average);
      
      
      List<Point> graphPoints = new ArrayList<Point>();
      for (int i = 0; i < scores.size(); i++) 
      {
         int x1 = (int) (i * xScale + BORDER_GAP);
         int y1 = (int) ((MAX_SCORE - scores.get(i)) * yScale + BORDER_GAP);
         int x2 = (int) (i * xScale + BORDER_GAP);
         int y2 = (int) ((MAX_SCORE - average) * yScale + BORDER_GAP);
         graphPoints.add(new Point(x1, y1));
         graphPoints.add(new Point(x2, y2));
      }

      // create x and y axes 
      g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
      g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

      
      FontMetrics fm = g2.getFontMetrics();
      // create hatch marks for y axis. 
      for (int i = 0; i < Y_HATCH_CNT; i++)
      {
         int x0 = BORDER_GAP;
         int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
         int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
         int y1 = y0;
         g2.drawLine(x0, y0, x1, y1);
         
         String value = Integer.toString(i +2);
         if((i%2)==0)
         g2.drawString(value, x0 - fm.stringWidth(value), y0 + (fm.getAscent() / 2));
      }

      // and for x axis
      for (int i = 0; i < scores.size() - 1; i++) 
      {
         int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (scores.size() - 1) + BORDER_GAP;
         int x1 = x0;
         int y0 = getHeight() - BORDER_GAP;
         int y1 = y0 - GRAPH_POINT_WIDTH;
         g2.drawLine(x0, y0, x1, y1);
         
         String value = Integer.toString(i +1);
         g2.drawString(value, x0 - (fm.stringWidth(value) / 2), y0 + fm.getAscent());
      }

      Stroke oldStroke = g2.getStroke();;
      g2.setStroke(GRAPH_STROKE);
      
      for (int i = 0; i < graphPoints.size() - 2; i++) 
      {
         int x1 = graphPoints.get(i).x;
         int y1 = graphPoints.get(i).y;
         int x2 = graphPoints.get(i + 2).x;
         int y2 = graphPoints.get(i + 2).y;
         if(y1 > y2)
         {
        	 g2.setColor(GRAPH_COLOR_GOOD); 
        	 g2.drawLine(x1, y1, x2, y2); 
         }
         else if(y1 == y2)
         {
        	 g2.setColor(GRAPH_COLOR_AVERAGE); 
        	 g2.drawLine(x1, y1, x2, y2);  
         }
         else
         {
        	 g2.setColor(GRAPH_COLOR_BAD); 
        	 g2.drawLine(x1, y1, x2, y2);  
         }
                 
      }

      g2.setStroke(oldStroke);      
      g2.setColor(GRAPH_POINT_COLOR);
      for (int i = 0; i < graphPoints.size(); i++) 
      {
         int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
         int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
         int ovalW = GRAPH_POINT_WIDTH;
         int ovalH = GRAPH_POINT_WIDTH;
         g2.fillOval(x, y, ovalW, ovalH);
      }
   }

   @Override
   public Dimension getPreferredSize()
   {
      return new Dimension(PREF_W, PREF_H);
   }

   private static void createAndShowGui() 
   {

   }

   public static void main(String[] args) 
   {
      SwingUtilities.invokeLater(new Runnable() 
      {
         public void run() 
         {
            createAndShowGui();
         }
      });
   }
}