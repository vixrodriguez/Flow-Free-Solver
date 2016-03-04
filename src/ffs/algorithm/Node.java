/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cristina Barreno, Victor Rodriguez
 * 
 */
public class Node {
    
    private State state;
    private Node parent;
    private int[] action;
    private int path_cost;
    private int depth;
    private State nextState;
    
    public Node(State state, Node parent, int[] action, int path_cost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.path_cost = path_cost!=0? path_cost: 0; 
        this.depth = (parent!=null)? parent.getDepth() + 1 : 0;
        this.nextState = this.state;
    }

    @Override
    public String toString() {
        return  String.format("<Node %s>", this.state);
    }
    
    public List<Node> path(){
        Node x = this;
        List<Node> result = new ArrayList<>();
        result.add(this);
        
        while(x.parent!=null){
            result.add(x.parent);
            x = x.parent;
        }
        return result;
    }
    
    public List<Node> getNextNodes(FlowFree problem){
        List<Node> result = new ArrayList<>();
        
        for(Successor r : problem.getSuccessors(this.nextState)){;
            result.add( new Node(r.getNext(), 
                            this, r.getAction(), 
                            problem.path_cost(this.path_cost, this.state, r.getAction(), r.getNext())));
            this.nextState = r.getNext();
        }
        
        return result;
    }
    
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int[] getAction() {
        return action;
    }

    public void setAction(int[] action) {
        this.action = action;
    }

    public int getPath_cost() {
        return path_cost;
    }

    public void setPath_cost(int path_cost) {
        this.path_cost = path_cost;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public State getNextState() {
        return nextState;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }
}
