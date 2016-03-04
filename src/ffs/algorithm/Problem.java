/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.algorithm;

import java.util.List;

/**
 *
 * @author Cristina Barreno, Victor Rodriguez
 */
public abstract class Problem {
    private State initial;
    private State goal;

    public Problem(State initial) {
        this.initial = initial;
        this.goal = null;
    }
    
    public Problem(State initial, State goal) {
        this.initial = initial;
        this.goal = goal;
    }
    
    abstract List<Successor> getSuccessors(State state);
    abstract Boolean reachGoal(State state);
    abstract int path_cost(int c, State state1,int[] action,State state2);
    
    public State getInitial() {
        return initial;
    }

    public void setInitial(State initial) {
        this.initial = initial;
    }

    public State getGoal() {
        return goal;
    }

    public void setGoal(State goal) {
        this.goal = goal;
    }
}
