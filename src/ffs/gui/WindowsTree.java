package ffs.gui;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import ffs.algorithm.Node;
import java.util.HashMap;

public class WindowsTree extends JFrame implements Runnable{

    private mxGraph graph;
    private Object parent;
    private mxHierarchicalLayout layout;
    private Object root;
    
    private int width = 150;
    private int height = 150;
    
    private HashMap<Node, Object> nodesTree;
    
    public WindowsTree(Node node) {
        super("Process tree");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(640, 480);
        
        graph = new mxGraph();
        parent = graph.getDefaultParent();
        nodesTree = new HashMap<>();
        
        graph.getModel().beginUpdate();
        try {
            root = graph.insertVertex(parent, null, message(node), 
                    20, 20, width, height);
            nodesTree.put(node, root);
        } finally {
            graph.getModel().endUpdate();
        }
        graph.setCellStyle("fillColor=blue");
        layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
        
        //this.setVisible(true);
    }
    
    public void addNode(Node parent, Node child){      
        if(nodesTree.containsKey(parent)){
            Object a = nodesTree.get(parent);
            String m = message(child);
            //graph.getModel().endUpdate();
            Object b = graph.insertVertex(this.parent, null, 
                    m, 240, 150,
                    width, height, "fillColor=yellow");
            graph.insertEdge(this.parent, null, "", a, b);
            nodesTree.put(child, b);
            //graph.getModel().endUpdate();
            layout.execute(graph.getDefaultParent());
        }
    }

//    private String message(Node n){
//        return n.getState().getCurrentLetter() + ": ["
//                    +n.getState().getDotEnd()[0] + ","
//                    +n.getState().getDotEnd()[1] + "]";
//    }
    
//    private String message(Node n){
//        return n.getState().getCurrentLetter() + ": ["
//                    +n.getState().getNextPoint()[0] + ","
//                    +n.getState().getNextPoint()[1] + "]";
//    }
    
    private String message(Node n){
        return "[" + n.getState().getCurrentLetter() + "]\n"+
                n.getState().toString();
    }
    
    @Override
    public void run() {
        this.repaint();
    }
}