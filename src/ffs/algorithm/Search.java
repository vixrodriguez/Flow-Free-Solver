/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.algorithm;

import com.rits.cloning.Cloner;
import ffs.gui.GridUI;
import ffs.gui.WindowsTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristina Barreno, Victor Rodriguez
 */
public class Search implements Runnable {

    private FlowFree ff;
    private GridUI grid;
    
    public Search(FlowFree ff, GridUI grid){
        this.ff = ff;
        this.grid = grid;
    }
    
    public List<Node> TreeDFS(){
        Problem problem = ff;
        Stack<Node> fringe = new Stack<>();
        List<Node> nodes = new ArrayList<>();
        Node node = null;
        fringe.add(new Node(problem.getInitial(), null, null, 0));
//        WindowsTree wt = new WindowsTree(fringe.get(0));
//        Thread t = new Thread(wt);
//        //t.run();
        while(!fringe.isEmpty()){
            node = fringe.pop();
            if(problem.reachGoal(node.getState()) || node.getState().getDots().isEmpty())
                nodes.add(node);
            if(!node.getState().getDots().isEmpty()){
                fringe.addAll(node.getNextNodes((FlowFree) problem));
//                for (Node n : fringe){
//                    wt.addNode(node, n);
//                }
            }
        }
//        wt.setVisible(true);
        return nodes;
    }    

    @Override
    public void run() {
        Date start, end;
        start = new Date();
        HashMap<Character, List<int[]>> allPath;
        Runtime r = Runtime.getRuntime();
        List<Node> nodes = TreeDFS();
        ArrayList <int[]> all=new ArrayList <int[]>();
        int i;
        if(!nodes.isEmpty()){
            allPath = new HashMap<>();
            List<Node> path = nodes.get(0).path();
            Collections.reverse(path);
//            WindowsTree wt = new WindowsTree(path.get(0));
//            Node parent = path.get(0);
//            for (Node p : path) {
//                wt.addNode(parent, p);
//                parent = p;
//            }
//            wt.setVisible(true);
            
//            for (Node n : path) {
//                char c = n.getState().getCurrentLetter();
//                if(!allPath.containsKey(c))
//                    allPath.put(c, new ArrayList<int[]>());
//                if(n.getState().getPath().size() > allPath.get(c).size()){
//                    allPath.get(c).clear();
//                    allPath.get(c).addAll(n.getState().getPath());
//                }
//            }
            
            for (Node n : path) {
                char c = n.getState().getCurrentLetter();
                if(!allPath.containsKey(c))
                    allPath.put(c, new ArrayList<int[]>());
                if(n.getState().getNextPoint() != null)
                    allPath.get(c).add(n.getState().getNextPoint());
            }
            
//            i=0;
//            for (Node otronombre : path) {
//                System.out.print(otronombre.getState().getCurrentLetter()+": ");
//                for(int[] o: otronombre.getState().getPath())
//                    System.out.print(o[0]+","+o[1]+" @ ");
//                System.out.println("");
//            }
//            for (Node otronombre : path) {
//                System.out.print(otronombre.getState());
//                
//            }
//            
//            for(Entry<Character, List<int []>> k: path.get(path.size()-1).getState().getAllPath().entrySet()){
//                System.out.print(k.getKey()+": ");
//                for (int[] v: k.getValue()) 
//                    System.out.print(v[0]+","+v[1]+" ");
//                System.out.println("");
//            }
//            for (Node n : path) {
//                if(!allPath.containsKey(n.getState().getCurrentLetter()))
//                    allPath.put(n.getState().getCurrentLetter(), new ArrayList<int[]>());
//                allPath.get(n.getState().getCurrentLetter()).add(n.getState().getNextPoint());
//            }
            for(Entry<Character, List<int []>> k: allPath.entrySet()){
                System.out.print(k.getKey()+": ");
                for (int[] v: k.getValue()) 
                    System.out.print(v[0]+","+v[1]+" ");
                System.out.println("");
            }
            //System.out.println("Solution:\n" + path.get(path.size() -1).getState());
            //grid.printPaths(path.get(path.size() - 1).getState().getGrid());
            //grid.printPaths(path.get(path.size() - 1).getState().getAllPath());
            grid.printPaths(path.get(path.size() - 1).getState().getGrid());
            
            end = new Date();
            showResults(end.getTime() - start.getTime(), 
                        ff.getExploredNodes(), path.size()
                        , (r.totalMemory() - r.freeMemory())/ (1024.0*1024.0));
        }
        else
            JOptionPane.showMessageDialog(null, "Don't have solution");
        
    }
    
    private void showResults(long time, int nodeExplored, int steps, double memory){
        String message = "Time: "+ time + " miliseconds\n" +
                         "Memory usage: " + String.format("%.3f", memory) + " MB \n" +
                         "Explored nodes: "+ nodeExplored + "\n"+
                         "Steps: " + steps;
        JOptionPane.showMessageDialog(null, message);
    }
    
//    public HashMap<Character, List<int[]>> getAllPaths(char[][] grid, HashMap<Character, List<int[]>> dots){
//        HashMap<Character, List<int[]>> allPath = (new Cloner()).deepClone(dots);
//        int[] a, b;
//        for (Entry <Character, List<int[]>>  entry: allPath.entrySet()) {
//            a = entry.getValue().get(1);
//            b = entry.getValue().get(0);
//            
//            while(a[0] != b[0] && a[1] != b[1]){
//                for(int[] d : ff.getFourDirections()){
//                    int[] p = { a[0] + d[0],  a[1] + b[1]};                        
//                    if(grid[p[0]][p[1]] == entry.getKey()){
//                        allPath.get(entry.getKey()).add(1, p);
//                        a = p;
//                        break;
//                    } 
//                }
//            }
//        }
//        
//        return allPath;
//    }
}