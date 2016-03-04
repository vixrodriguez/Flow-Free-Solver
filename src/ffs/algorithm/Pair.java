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
public class Pair {
    private char letter;
    private int[] star;
    private int[] end;

    public Pair(char letter, int[] star, int[] end) {
        this.letter = letter;
        this.star = star;
        this.end = end;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int[] getStar() {
        return star;
    }

    public void setStar(int[] star) {
        this.star = star;
    }

    public int[] getEnd() {
        return end;
    }

    public void setEnd(int[] end) {
        this.end = end;
    }
    
    
}
