/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ffs.algorithm;

import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Class that represent a flow free problem
 * @author Cristina Barreno, Victor Rodriguez
 */
public class FlowFree extends Problem {

    private HashMap<Character, List<int[]>> dotsPaths;
    private HashMap<Character, List<int[]>> copyDotsPats;
    private int exploredNodes;
    private int[][] fourDirections = {{0,-1},{0,1},{1,0},{-1,0}};
    private int[][] allDirections = { {0, -1}, {0, 1}, {1, 0}, {-1, 0}, 
                                    {1, -1}, {1, 1}, {-1, -1}, {-1, 1}};

    public FlowFree(State initial) {
        super(initial);
    }
    
    public FlowFree(char[][] grid, State initial) {
        super(initial);
        State state = build(grid);
        super.setInitial(state);
        this.exploredNodes = 0;
    }

    /**
     * Construct the initial state of the problem
     * @param grid Grid that represent the color with characteres and the
     * empty cell with the '.'
     * @return Initial state of the problem with the pair dots to find
     */
    private State build(char[][] grid){
        this.dotsPaths = constructDotsPathsToHash(grid);
        this.copyDotsPats = (new Cloner()).deepClone(dotsPaths);
        Pair firstPoint = getNextDots(dotsPaths);
        ArrayList<int[]> p = new ArrayList<>();
        p.add(firstPoint.getStar());
        State initialState = new State(grid, 
                firstPoint.getLetter(), 
                new ArrayList(p),
                firstPoint.getEnd(), new HashMap<>(dotsPaths));

        return initialState;
    }
    
    /**
     * Get the all dots on the grid with the initial point and end points.
     * @param grid that represent the color with characteres and the
     * empty cell with the '.'
     * @return Map with key(Character of the color) and value (initial and end point)
     */
    public HashMap constructDotsPathsToHash(char[][] grid){
        HashMap<Character, List<int[]>> dict = new HashMap<>();
        int i =0, j;
        
        for (char[] line : grid) {
            j = 0;
            for (char letter : line) {
                int[] p = new int[2];
                p[0] = i; p[1] = j;
                if(letter!= '.'){
                    if(!dict.containsKey(letter))
                        dict.put(letter, (new ArrayList<int[]>()));
                    dict.get(letter).add(p);        
                }    
                j++;
            }
            i++;
        }
        return dict;
    }
    
    public Pair getNextDots(HashMap<Character, List<int[]>> dots){
        List<Character> key = getKeys(dots);
        int i = 0;
        if(key.isEmpty()) return null;
        Pair result = new Pair( key.get(i), 
                                dots.get(key.get(i)).get(1), 
                                dots.get(key.get(i)).get(0));
        
        i++;
        while(i < key.size()){
            Pair tmp = new Pair(key.get(i), dots.get(key.get(i)).get(1), dots.get(key.get(i)).get(0));
            int a = Math.abs(result.getStar()[0] - result.getEnd()[0]) + Math.abs(result.getStar()[1] - result.getEnd()[1]);
            int b = Math.abs(tmp.getStar()[0] - tmp.getEnd()[0]) + Math.abs(tmp.getStar()[1] - tmp.getEnd()[1]);
            
            if(a > b)
                result = tmp;
            else if(a == b){
                if(tmp.getStar()[1] > result.getStar()[1] || tmp.getStar()[0] > result.getStar()[0])
                    result = tmp;
            }
            i++;   
        }
        return result;
    }
    
    public List getKeys(HashMap<Character, List<int[]>> M){
        ArrayList result = new ArrayList();
        for (Character entry : M.keySet())
            result.add(entry);
        return result;
    }
    
    /**
     * Verifica si como maximo tiene 2 vecinos (mismo color) a los alrededores
     * del punto
     * @param grid
     * @param letter
     * @param pos
     * @return 
     */
    public boolean hasTwoNeighboorMax(char[][] grid,
                                     char letter, int[] pos){
        int[][] sub1 = {{0, -1}, {-1, 0}, {-1,-1}};
        int[][] sub2 = {{0,  1}, {-1, 0}, {-1, 1}};  
        int[][] sub3 = {{0, -1}, {1 , 0}, {1, -1}};  
        int[][] sub4 = {{0,  1}, {1 , 0}, {1,  1}};
        
        List<int[][]> squares = new ArrayList<>();
        squares.add(sub1);
        squares.add(sub2);
        squares.add(sub3);
        squares.add(sub4);

        boolean hasTwoNgb = true;

        int count_letter = 0;
        
        for(int[] dir : allDirections){
            int[] newPos = {pos[0]+dir[0], pos[1]+dir[1]};
            
            if(inGrid(grid, newPos)){
                if(grid[newPos[0]][newPos[1]] == letter)
                    count_letter++;
            }
        }

        hasTwoNgb = (count_letter <= 3); 

        for(int[][] subSquarre : squares){
            count_letter = 0;
            for(int[] direction : subSquarre){
                int[] newPosition = {pos[0]+direction[0], pos[1]+direction[1]};
                if(inGrid(grid, newPosition)){
                    if(grid[newPosition[0]][newPosition[1]] == letter)
                        count_letter++;
                }
            }
            hasTwoNgb = hasTwoNgb && (count_letter <= 2);
        }
        return hasTwoNgb;       
    }

    /**
     * Check if the state grid has empty cells
     * @param state State to check the grid
     * @return True - If the grid does not have empty cells <br/>
     * False - If the grid has at least one empty cell.
     */
    @Override
    Boolean reachGoal(State state) {
        for(char[] line : state.getGrid())
        {
            for(char letter: line)
            {
                if(letter == '.')
                    return false;
            }
        }
        return true;    
    }
    
    /**
     * Check if the position is received within the borders of the grid
     * @param grid Grid that represent the dots
     * @param pos Position to verify
     * @return True - if the position is within the border of the grid
     * False - if the position is not within the border of the grid
     */
    public boolean inGrid(char[][] grid, int[] pos)
    {
        int n = grid.length;
        int m = grid[0].length;    
        return (0<=pos[0]) && (pos[0]<n) && (0<=pos[1]) && (pos[1]<m);    
    }

    
    public boolean pathExistsDFS(char[][] grid, int[] start, int[] end, int[][] visited)
    {
        int i,j;
        boolean exists;
        
        for (int[] d : fourDirections)
        {   
            int[] next = new int[2];
            i = start[0] + d[0];
            j = start[1] + d[1];
            next[0] = i; next[1] = j;
            if (i==end[0] && j == end[1])
                return true;
            if (inGrid(grid, next) && (grid[i][j] == '.' ) && (visited[i][j] == 0))
            {
                visited[i][j]=1;
                exists = pathExistsDFS(grid, next, end, visited);
                if (exists)
                    return true;
            }
        }
        return false;
    }
    
    public boolean pathExists(char[][] grid, int[] start, int[] end)
    {
        int i,j;
        int n = grid.length;
        int m = grid[0].length; 
        int[][] visited= new int[n][m];
        for(i=0; i<n ; i++)
        {
            for (j=0; j<m ; j++)
                visited[i][j]=0;
        }
        
        return pathExistsDFS(grid, start, end, visited);
        
    }
    
    public boolean noDeadEnd(char[][] grid , 
                             HashMap<Character, List<int[]>> dots)
    {
        for(Entry<Character, List<int[]>> value: dots.entrySet()){
            if(!pathExists(grid, value.getValue().get(0), value.getValue().get(1)))
                return false;
        }
        return true;
    }
    
    public boolean noDeadEndWithState(char[][] grid , 
                                      HashMap<Character, List<int[]>> dots, State state)
    {
        Character key;
        List<int[]> values;
        // Get initial point of the current letter
        int[] last0 = dots.get(state.getCurrentLetter()).get(0); 
        // Get final point of the current letter 
        int[] last1 = dots.get(state.getCurrentLetter()).get(1);
        //Temporal variable
        int[] backup;
        boolean result;
        
        // Check the if the first element of path is same the initial point
        if(((int[])state.getPath().get(0)).equals(last0)){
            backup = last0;
            values = dots.get(state.getCurrentLetter());
            values.set(0, state.getPath().get(state.getPath().size()-1));
            result = noDeadEnd(grid, dots);
            values = dots.get(state.getCurrentLetter());
            values.set(0, backup);
            return result;
        }
        else
        {
            backup = last1;
            values = dots.get(state.getCurrentLetter());
            values.set(1, state.getPath().get(state.getPath().size()-1));
            result = noDeadEnd(grid, dots);
            values = dots.get(state.getCurrentLetter());
            values.set(1, backup);;
            return result;
        }
        
    }
    
    /**
     * Check if the path is completed. 
     * Check around if the last element in the path + new position = final point
     * @param state
     * @return 
     */
    public boolean isPathCompleted(State state)
    {
        for(int[] dir : fourDirections)
        {
            int[] pos = new int[2];
            int[] last = new int[2];
            last = state.getPath().get(state.getPath().size() - 1);
            pos[0] = last[0] + dir[0];
            pos[1] = last[1] + dir[1];
            
            if(pos[0] == state.getDotEnd()[0] && pos[1] == state.getDotEnd()[1])
                return true;
        }
        return false;
    }
    
    
    public boolean hasNoLonelyPoint(char[][] grid, Character ltr, HashMap<Character, List<int[]>> dots)
    {
        boolean noLonelyPoints = true;
        boolean lonelyPoint;
        int[] point, pos , posCheck;
        int i = 0 ,j;
        int count_neighboors = 0;
        for(char[] line : grid)
        {
           j=0;
           for(char letter : line)
           {
               if(letter == '.')
               {
                   count_neighboors += 1;
                   lonelyPoint = true;
                   point = new int[2];
                   point[0] = i; point[1] = j;
                   for (int[] direction : allDirections)
                   {
                       pos= new int[2];
                       pos[0] = point[0] + direction[0]; 
                       pos[1] = point[1] + direction[1];
                       if(inGrid(grid, pos) && 
                         (grid[pos[0]][pos[1]]) == '.')
                       {    
                           lonelyPoint = false;
                           break;
                       }
                   }
                   if (lonelyPoint)
                   {
                       for(int[] direction : fourDirections)
                       {
                           posCheck = new int[2];
                           posCheck[0] = point[0] + direction[0];
                           posCheck[1] = point[1] + direction[1];
                           if(inGrid(grid, posCheck))
                           {
                               for(int[] value: dots.get(ltr))
                               {
                                    if (posCheck[0] == value[0] && posCheck[1] == value[1])
                                        lonelyPoint = false;
                                }
                           }
                       }
                   }
                   noLonelyPoints = (noLonelyPoints && !lonelyPoint);
                   if (!noLonelyPoints) break;
               }
               j++;
           }
           i++;           
        }
        if (!noLonelyPoints && count_neighboors == 1) 
            return true;
        else
            return noLonelyPoints;
    }
    
    @Override
    public List<Successor> getSuccessors(State state){
        char[][] grid = state.getGrid();
        Cloner cloner = new Cloner();
        boolean pathCompleted = false;       
        List<int[]> newPath;
        State nextState;
        List<Successor> result = new ArrayList<>();
        List<int[]> path = state.getPath();
        for(int[] dir : fourDirections){
            if(!pathCompleted){
                int[] newPos = new int[2];
                newPos[0] = path.get(path.size()-1)[0] + dir[0];
                newPos[1] = path.get(path.size()-1)[1] + dir[1];

                if(inGrid(grid, newPos)){
                    if(state.getGrid()[newPos[0]][newPos[1]] == '.'){
                        if(hasTwoNeighboorMax(state.getGrid(), state.getCurrentLetter(), newPos)){
                            char[][] newGrid = cloner.deepClone(grid);
                            newGrid[newPos[0]][newPos[1]] = state.getCurrentLetter();
                            newPath = new ArrayList<>(state.getPath());
                            newPath.add(newPos);
                            nextState = new State(newGrid, state.getCurrentLetter(), 
                                                 newPath, state.getDotEnd(), new HashMap<>(state.getDots()),
                                                  state.getAllPath());
                            
                            if(noDeadEndWithState(newGrid, state.getDots(), nextState) &&
                               hasNoLonelyPoint(newGrid, state.getCurrentLetter(), state.getDots())){
                                
                                if(isPathCompleted(nextState)){
                                    pathCompleted = true;
                                    this.exploredNodes++;
                                    result.add(new Successor(dir, startNewPath(nextState)));
                                }
                                else{
                                    this.exploredNodes++;
                                    result.add(new Successor(dir, nextState));
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    
    public State startNewPath(State state){
        State result = null;
        HashMap<Character, List<int[]>> dots = new HashMap<>(state.getDots());
        state.getDots().remove(state.getCurrentLetter());
        dots.remove(state.getCurrentLetter());
        Pair nextPoint = getNextDots(dots);
        List<int[]> listNextPoint;
        if(nextPoint == null){ 
            state.getAllPath().put(state.getCurrentLetter(), 
                state.getPath());
            state.getAllPath().get(state.getCurrentLetter()).add(state.getDotEnd());
            state.setDots(dots);
            return state;
        }
        listNextPoint = new ArrayList<>();
        listNextPoint.add(nextPoint.getStar());
        result = new State(state.getGrid(), nextPoint.getLetter(), 
                        listNextPoint, nextPoint.getEnd(), dots);
        
        result.setAllPath(state.getAllPath());
        result.getAllPath().put(state.getCurrentLetter(), 
                state.getPath());
        result.getAllPath().get(state.getCurrentLetter()).add(state.getDotEnd());
        
        
        return result;
    }

    @Override
    int path_cost(int c, State state1, int[] action, State state2) {
        return c + 1;
    }

    public int getExploredNodes() {
        return exploredNodes;
    }

    public void setExploredNodes(int exploredNodes) {
        this.exploredNodes = exploredNodes;
    }

    public int[][] getFourDirections() {
        return fourDirections;
    }

    public void setFourDirections(int[][] fourDirections) {
        this.fourDirections = fourDirections;
    }

}
