package vizgraph;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Visualizer {
    public static void display(Graph g){
//        Layout<Integer, String> layout = new CircleLayout(g);
        Layout<Integer, String> layout = new KKLayout<>(g);
//        Layout<Integer, String> layout = new ISOMLayout<>(g);
//        Layout<Integer, String> layout = new DAGLayout<>(g);
//        Layout<Integer, String> layout = new FRLayout<>(g);
        layout.setSize(new Dimension(700, 700));
        BasicVisualizationServer<Integer, String> vv = 
                new BasicVisualizationServer<>(layout);
        
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}
