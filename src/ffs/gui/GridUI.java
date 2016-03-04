/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.gui;

import ffs.entities.Grid;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.JPanel;

/**
 *
 * @author Cristina Barreno, Victor Rodriguez
 */
public class GridUI extends JPanel{
    
    private Dimension dimension;
    private Grid grid;
    final static char cell_empty = '.';
    final static char initialLetter = 'A';
    private Color actual_color;
    private Color empty_color = Color.BLACK;
    private CellUI[][] cells;
    
    public GridUI(){
        super();
    }
    public GridUI(int n, int n_colors){
        grid = new Grid(n, n_colors);
        actual_color = empty_color;
        this.setLayout(null);
        this.dimension = new Dimension(CellUI.getDim() * n, CellUI.getDim() * n);
        this.setPreferredSize(dimension);
        initComponents();
    }
    
    private void initComponents(){
        int ini = 0;
        int fin = 0;
        CellUI cell = null;
        cells = new CellUI[grid.getN()][grid.getN()];
        for (int i = 0; i < grid.getN(); i++) {
            ini = 0;
            for (int j = 0; j < grid.getN(); j++) {
                cell = new CellUI(cell_empty, i, j,empty_color, this); 
                cell.setBounds(ini, fin, cell.getDim(), cell.getDim());
                cells[i][j] = cell;
                this.add(cell);
                ini += cell.getDim();
            }
            fin += cell.getDim();
        }
    }
    
    public int getN(){
        return grid.getN();
    }
    
    public int getN_colors(){
        return grid.getN_color();
    }
    
    public Dimension getDimension(){
        return dimension;
    }
    
    /**
     * Allow do zoom in or zoom out of the grid
     * @param n - New size of the cells
     */
    public void zoomChange(int n){
        int ini, fin = 0;
        CellUI.dim = n;
        CellUI cell;
        for (int i = 0; i < grid.getN(); i++) {
            ini = 0;
            for (int j = 0; j < grid.getN(); j++) {
                cell = cells[i][j];
                cell.setBounds(ini, fin, CellUI.dim, CellUI.dim);
                cells[i][j] = cell;
                this.add(cell);
                ini += CellUI.dim;
            }
            fin += CellUI.dim;
        }
        this.validate();
        this.repaint();
    }
    
    public Color getActualColor(){
        return this.actual_color;
    }
    
    public void setActualColor(Color c){
        this.actual_color = c;
    }
     
    public boolean isGrid_correct(){
        return namesCells();
    }
    
    public char[][] toArrayChar(){
        int n = grid.getN();
        char[][] result = new char[n][n];
        for(Component cmp: this.getComponents()){
            CellUI cell = (CellUI) cmp;
            result[cell.getCell().getPosX()][cell.getCell().getPosY()] = cell.getCell().getLetter();
        }
        return result;
    }
    
    private boolean namesCells(){
        HashMap<Color, List<CellUI>> result = new HashMap();
        for(Component cmp: this.getComponents()){
            CellUI c = (CellUI) cmp;
            Color color = c.getCell().getColor();
            if(color != empty_color){
                if(!result.containsKey(color))
                    result.put(color, new ArrayList<CellUI>());
                result.get(color).add(c);
            }
        }
        char a = initialLetter;
        for(Entry<Color, List<CellUI>> entry : result.entrySet()){
            int cnt = 0;
            for(CellUI c : entry.getValue()){
                c.getCell().setLetter(a);
                cnt++;
            }
            if(cnt != 2) return false;
            a++;
        }
        return true && !result.isEmpty();
    }
    
    public void printPaths(HashMap<Character, List<int[]>> dots){
        int[] a, b;
        int x, y, x1, y1, x2, y2;
        float stroke = CellUI.dim / 2;
        Color c;
        Graphics2D g2 = (Graphics2D) this.getGraphics();
       
        for (Entry<Character, List<int[]>> path : dots.entrySet()) {
            // Determine points
            a = path.getValue().get(0);         
            g2.setPaint(cells[a[0]][a[1]].getCell().getColor());
            
            // Set Stroke
            g2.setStroke(new BasicStroke(stroke,                     // Line width
                            BasicStroke.CAP_ROUND,    // End-cap style
                            BasicStroke.JOIN_ROUND)); // Vertex join style
            
            for (int i = 1; i < path.getValue().size(); i++) {
                // Initial point
                x = a[0]; y = a[1];
                x1 = CellUI.dim / 2 + CellUI.dim * y;
                y1 = CellUI.dim / 2 + CellUI.dim * x;
                
                // End point
                b = path.getValue().get(i);
                x = b[0]; y = b[1];
                x2 = CellUI.dim / 2 + CellUI.dim * y;
                y2 = CellUI.dim / 2 + CellUI.dim * x;
                
                g2.drawLine(x1, y1, x2, y2);
                a = b;
            }
        }
        this.setEnabled(false);
    }
    
    public void printPaths(char[][] grid){
        HashMap<Character, Color> dots = new HashMap<>();
        for (int i=0; i < grid[0].length; i++) {
            for (int j=0; j<grid.length; j++) {
                if(!dots.containsKey(grid[i][j]) && 
                    cells[i][j].getCell().getColor() != empty_color)
                    dots.put(grid[i][j], cells[i][j].getCell().getColor());
                cells[i][j].getCell().setLetter(grid[i][j]);
            }
        }
        printCells(dots);
    }
    
    
    private void printCells(HashMap<Character, Color> dots){
        for(Component cell: getComponents()){
            CellUI c = (CellUI) cell;
            c.setColor(dots.get(c.getCell().getLetter()));
        }
    }
}