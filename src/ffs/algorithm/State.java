/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.algorithm;

import java.util.HashMap;
import java.util.List;

/**
 * Class that represent a instance the flow free problem for a particular letter
 * and it has taken a direction to reach the goal
 * @author Cristina Barreno, Victor Rodriguez
 */
public class State {
    /**
     * Represents the grid as an array of characters when the letters is a dots
     * and the '.' is the empty cell
     */
    private char[][] grid;
    /**
     * Current letter that 
     */
    private char currentLetter; 
    
    private List<int[]> path; 
    /**
     * Final point of the path
     */
    private int[] dotEnd; 
    /**
     * Store the dots with the intial point and the final point.<br/>
     * Ex: <br/>
     * A => [[0,0], [4,0]] <br/>
     * B => [[0,1], [4,4]] <br/>
     * C => [[0,2], [3,3]] <br/>
     * D => [[1,4], [3,4]] <br/>
     */
    private HashMap<Character, List<int[]>> dots;
    
    /**
     * Store all path of each dot. <br/>
     * Ex:<br/>
     * A => [[4,0], [3,0], [2,0], [1,0], [0,0]] <br/>
     * B => [[4,4], [4,3], [4,2], [4,1], [3,1], [2,1], [1,1],[0,1]] <br/>
     * C => [[3,3], [3,2], [2,2], [1,2], [0,2]] <br/>
     * D => [[3,4], [2,4], [2,3], [1,3], [0,3], [0,4], [1,4]] <br/>
     */
    private HashMap<Character, List<int[]>> allPath;
    private int[] nextPoint;
    
    public State(char[][] grid, char currentLetter, List<int[]> path, int[] endPoint, 
            HashMap<Character, List<int[]>> endPoints) {
        this.grid = grid;
        this.currentLetter = currentLetter;
        this.path = path;
        this.dotEnd = endPoint;
        this.dots = endPoints;
        this.allPath = new HashMap<>();
    }
    
    public State(char[][] grid, char currentLetter, List<int[]> path, int[] endPoint, 
            HashMap<Character, List<int[]>> endPoints, HashMap<Character, List<int[]>> allPath) {
        this.grid = grid;
        this.currentLetter = currentLetter;
        this.path = path;
        this.dotEnd = endPoint;
        this.dots = endPoints;
        this.allPath = allPath;
    }

    @Override
    public String toString() {
        String output = "";
        for(char[] line: this.grid){
            for(char letter: line)
                output += letter;
            output += "\n";
        }
        return output;
    }

    /**
     * {@link State#grid} <br/>
     * @return characters grid that represent a instance problem
     */
    public char[][] getGrid() {
        return grid;
    }

    public void setGrid(char[][] grid) {
        this.grid = grid;
    }

    public char getCurrentLetter() {
        return currentLetter;
    }

    public void setCurrentLetter(char currentLetter) {
        this.currentLetter = currentLetter;
    }

    public List<int[]> getPath() {
        return path;
    }

    public void setPath(List<int[]> path) {
        this.path = path;
    }

    public int[] getDotEnd() {
        return dotEnd;
    }

    public void setDotEnd(int[] dotEnd) {
        this.dotEnd = dotEnd;
    }

    public HashMap<Character, List<int[]>> getDots() {
        return dots;
    }

    public void setDots(HashMap<Character, List<int[]>> dots) {
        this.dots = dots;
    }

    public HashMap<Character, List<int[]>> getAllPath() {
        return allPath;
    }

    public void setAllPath(HashMap<Character, List<int[]>> allPath) {
        this.allPath = allPath;
    }

    public int[] getNextPoint() {
        return nextPoint;
    }

    public void setNextPoint(int[] nextPoint) {
        this.nextPoint = nextPoint;
    }
}
