/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.entities;

import java.awt.Color;

/**
 * @author Cristina Barreno, Victor Rodriguez
 * Class that represent Table in the Flow Free Game
 */
public class Grid {

    private int n;
    private int n_color; 
    private int percent_full;
    private int moves;
    
    private Cell[][] grid;
    
    /**
     * Method Constructor 
     * @param n Table dimension
     * @param n_color Count of color
     */
    public Grid(int n, int n_color) {
        this.n = n;
        this.n_color = n_color;
        grid = new Cell[n][n];
    }

    public Grid generateRandomTable(){
        int x, y;
        Color c;
        Grid g = new Grid(getN(), getN_color());
        for (int i = 1; i <= g.getN_color(); i++) {
            c = HSBRandom();
            for (int j = 0; j < 2; j++) {
                x = alt();
                y = alt();
                if(g.grid[x][y] == null)
                    g.grid[x][y] = new Cell((char) ('a'+i), x, y, c);
                else
                    j--;
            }
        }
        return g;
    }
    
    public void showGrid(){
        int v;
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                v = (getGrid()[i][j] == null)? 0 : getGrid()[i][j].getLetter();
                System.out.print(v + " ");
            }
            System.out.println();
        }
    }
    
    public int alt(){
        return (int)(Math.random()*getN());
    }
    
    public Color HSBRandom(){
        int h = (int) (Math.random() * 361);
        int s = (int) (Math.random() * 101);
        int b = (int) (Math.random() * 101);
        return  Color.getHSBColor(h, s, b);
    }
    
    // Getters & Setters
    
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getN_color() {
        return n_color;
    }

    public void setN_color(int n_color) {
        this.n_color = n_color;
    }

    public int getPercent_full() {
        return percent_full;
    }

    public void setPercent_full(int percent_full) {
        this.percent_full = percent_full;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }
}
