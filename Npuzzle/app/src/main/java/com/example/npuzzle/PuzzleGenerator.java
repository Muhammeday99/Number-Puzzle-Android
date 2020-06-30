package com.example.npuzzle;

import java.util.Random;

public class PuzzleGenerator {
    private int row,col,emptysqrposR,emptysqrposC;
    private int[][] grid;
    private int[][] answer;
    private int numOfMoves=0;
    private char lastMove = 'S';

    private int randGen(){
        Random rand = new Random();
        int r = rand.nextInt(4);
        return r;
    }

    private void fillArr(){
        int num = 1;
        for(int i=0;i<row;i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = num;
                answer[i][j] = num;
                if (grid[i][j] == row * col) {
                    emptysqrposC = j;
                    emptysqrposR = i;
                }
                System.out.println(grid[i][j]);
                num++;
            }
        }
    }

    private int calcDiff(){
        int counter = 0;
        for (int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if(answer[i][j] != grid[i][j] && grid[i][j]!= grid[emptysqrposR][emptysqrposC]){
                    counter += 1;
                }
            }
        }
        return counter;
    }

    public PuzzleGenerator(){
    }

    public void setSize(int row,int col){
        this.row = row;
        this.col = col;
        grid = new int[row][col];
        answer = new int[row][col];
        reset();
    }

    public void Shuffle(){
        int randMove;
        int shufflecount;
        if(row<6) {
            shufflecount = 1000;
        }else {shufflecount = 10000;}

        for (int i = 0; i < shufflecount;i++) {
            randMove = randGen();
            switch (randMove) {
                case 0:
                    moveDown();
                    break;
                case 1:
                    moveLeft();
                    break;
                case 2:
                    moveRight();
                    break;
                case 3:
                    moveUp();
                    break;
            }
        }
    }

    public void reset(){
        fillArr();
        Shuffle();
        numOfMoves = 0;
    }

    public String getValue(int row,int col){
        return String.valueOf(grid[row][col]);
    }

    public String getNumOfMoves(){
        return String.valueOf(numOfMoves);
    }

    public void moveUp(){
        if(emptysqrposR != 0) {
            int temp = grid[emptysqrposR][emptysqrposC];
            grid[emptysqrposR][emptysqrposC] = grid[emptysqrposR-1][emptysqrposC];
            grid[emptysqrposR-1][emptysqrposC] = temp;
            emptysqrposR = emptysqrposR-1;
            numOfMoves++;
        }
    }

    public void moveDown(){
        if(emptysqrposR != row-1) {
            int temp = grid[emptysqrposR][emptysqrposC];
            grid[emptysqrposR][emptysqrposC] = grid[emptysqrposR+1][emptysqrposC];
            grid[emptysqrposR+1][emptysqrposC] = temp;
            emptysqrposR = emptysqrposR+1;
            numOfMoves++;
        }
    }

    public void moveLeft(){
        if(emptysqrposC != 0) {
            int temp = grid[emptysqrposR][emptysqrposC];
            grid[emptysqrposR][emptysqrposC] = grid[emptysqrposR][emptysqrposC-1];
            grid[emptysqrposR][emptysqrposC-1] = temp;
            emptysqrposC = emptysqrposC-1;
            numOfMoves++;
        }
    }

    public void moveRight(){
        if(emptysqrposC != col-1) {
            int temp = grid[emptysqrposR][emptysqrposC];
            grid[emptysqrposR][emptysqrposC] = grid[emptysqrposR][emptysqrposC+1];
            grid[emptysqrposR][emptysqrposC+1] = temp;
            emptysqrposC = emptysqrposC+1;
            numOfMoves++;
        }
    }

    public void intellegent(){
        int[] Cost = new int[4];
        int level = numOfMoves, diftiles;

        if(lastMove !='U' && emptysqrposR != 0){
            moveUp();
            diftiles = calcDiff();
            Cost[0] = level + diftiles;
            moveDown();
            numOfMoves -= 2;
        }
        if(lastMove !='D' && emptysqrposR != row-1){
            moveDown();
            diftiles = calcDiff();
            Cost[1] = level + diftiles;
            moveUp();
            numOfMoves -= 2;
        }
        if(lastMove !='L' && emptysqrposC != 0){
            moveLeft();
            diftiles = calcDiff();
            Cost[2] = level + diftiles;
            moveRight();
            numOfMoves -= 2;
        }
        if(lastMove !='R' && emptysqrposC != col-1){
            moveRight();
            diftiles = calcDiff();
            Cost[3] = level + diftiles;
            moveLeft();
            numOfMoves -= 2;
        }

        int minIndex = 0, min = Cost[0];

        for (int i = 1;i<4;i++){
            if(Cost[i]<min){
                min = Cost[i];
                minIndex = i;
            }
        }

        switch (minIndex){
            case 0:
                moveUp();
                lastMove = 'U';
                break;
            case 1:
                moveDown();
                lastMove = 'D';
                break;
            case 2:
                moveLeft();
                lastMove = 'L';
                break;
            case 3:
                moveRight();
                lastMove = 'R';
                break;
        }
    }

    public boolean isSolved(){
        int[] temp = new int[row*col];
        int k=0;
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                temp[k] = grid[i][j];
                k++;
            }
        }
        for(int i=0;i<row*col-1;i++){
            if(temp[i]>temp[i+1]) return false;
        }
        return true;
    }
}
