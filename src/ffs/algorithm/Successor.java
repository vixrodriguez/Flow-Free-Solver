/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.algorithm;

/**
 *
 * @author Cristina Barreno, Victor Rodriguez
 */
public class Successor {
    private int[] action;
    private State next;

    public Successor(int[] action, State state) {
        this.action = action;
        this.next = state;
    }

    public int[] getAction() {
        return action;
    }

    public void setAction(int[] action) {
        this.action = action;
    }

    public State getNext() {
        return next;
    }

    public void setNext(State next) {
        this.next = next;
    }
}
