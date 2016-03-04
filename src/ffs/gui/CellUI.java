/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.gui;

import ffs.entities.Cell;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Cristina Barreno, Victor Rodriguez
 */
public class CellUI extends JPanel{
    private Cell cell;
    static int dim = 50;
    final static Color color_bkg = Color.black;
    final static Color color_border = Color.LIGHT_GRAY;
    private GridUI parent;
    
    public CellUI(char l, int i, int j, Color c, final GridUI parent){
        cell = new Cell(l, i, j , c);
        cell.setColor(c);
        this.setBackground(color_bkg);
        this.setBorder(BorderFactory.createLineBorder(color_border));
        this.setSize(dim,dim);
        this.setPreferredSize(new Dimension(dim, dim));
        this.parent = parent;
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me); //To change body of generated methods, choose Tools | Templates.
                //CellUI c = (CellUI) me.getSource();
                setColor(parent.getActualColor());
            }
            
        });
        this.updateUI();
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public static int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
        //this.revalidate();
        this.repaint();
    }
   
    public void setColor(Color c){
        cell.setColor(c);
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(cell.getColor());
	g2d.fillOval(5, 5, dim-12, dim-12);
    }
}   
