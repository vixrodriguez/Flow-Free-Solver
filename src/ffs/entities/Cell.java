/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.entities;

import java.awt.Color;

/**
 * Class that represent cell on the table
 * @author Cristina Barreno, Victor Rodriguez
 */
public class Cell {
    
    private char letter;
    private int posX;
    private int posY;
    private Color color;

    public Cell(char letter, int posX, int posY, Color color) {
        this.letter = letter;
        this.posX = posX;
        this.posY = posY;
        this.color = color;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }
    
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return letter + "";
    }
}
