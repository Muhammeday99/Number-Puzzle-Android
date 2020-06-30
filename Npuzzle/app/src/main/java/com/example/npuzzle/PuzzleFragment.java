package com.example.npuzzle;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.*;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.Random;

public class PuzzleFragment extends Fragment{
    PuzzleCell[][] Cells;
    TextView Moves;
    GridLayout grid;
    PuzzleGenerator puzzle;
    Spinner rowSizeList;
    Spinner colSizeList;
    Button Hint;
    private int row,col;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.game_main,container,false);

        rowSizeList = (Spinner) view.findViewById(R.id.ProwSize);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1
                , getResources().getStringArray(R.array.Size));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rowSizeList.setAdapter(myAdapter);


        colSizeList = (Spinner) view.findViewById(R.id.PcolSize);
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1
                , getResources().getStringArray(R.array.colSize));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colSizeList.setAdapter(myAdapter1);
        //////////////////////////////////////////////////////
        Moves = (TextView)view.findViewById(R.id.NumofMoves);
        Moves.setTextColor(Color.DKGRAY);

        Hint = (Button)view.findViewById(R.id.Hint);

        /////////////////////////////////////////////////////////////////////////
        grid = (GridLayout)view.findViewById(R.id.myGrid);

        puzzle = new PuzzleGenerator();
        Moves.setText("Number of Moves: "+puzzle.getNumOfMoves());
        DrawPuzzle();

        grid.getViewTreeObserver().addOnGlobalLayoutListener(Update);


        return view;
    }

    private AdapterView.OnItemSelectedListener rowSizeChanger = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int colSize = grid.getColumnCount();
            grid.removeAllViews();
            String item = parent.getItemAtPosition(position).toString();
            switch (item) {
                case "3":
                    grid.setRowCount(3);
                    break;
                case "4":
                    grid.setRowCount(4);
                    break;
                case "5":
                    grid.setRowCount(5);
                    break;
                case "6":
                    grid.setRowCount(6);
                    break;
                case "7":
                    grid.setRowCount(7);
                    break;
                case "8":
                    grid.setRowCount(8);
                    break;
                case "9":
                    grid.setRowCount(9);
                    break;

            }
            Moves.setText("Number of Moves: "+puzzle.getNumOfMoves());
            DrawPuzzle();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    private AdapterView.OnItemSelectedListener colSizeChanger = new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grid.removeAllViews();
                String item = parent.getItemAtPosition(position).toString();
                switch (item){
                    case "3":
                        grid.setColumnCount(3);
                        break;
                    case "4":
                        grid.setColumnCount(4);
                        break;
                    case "5":
                        grid.setColumnCount(5);
                        break;
                    case "6":
                        grid.setColumnCount(6);
                        break;
                    case "7":
                        grid.setColumnCount(7);
                        break;
                    case "8":
                        grid.setColumnCount(8);
                        break;
                    case "9":
                        grid.setColumnCount(9);
                        break;

                }
                Moves.setText("Number of Moves: "+puzzle.getNumOfMoves());
                DrawPuzzle();
            }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private OnGlobalLayoutListener Update =   new OnGlobalLayoutListener(){

        @Override
        public void onGlobalLayout() {
            final int MARGIN = 5;
            int pWidth = grid.getWidth();
            int pHeight = grid.getHeight();
            int numOfCol = grid.getColumnCount();
            int numOfRow = grid.getRowCount();
            int w = pWidth/numOfCol;
            int h = pHeight/numOfRow;

            for(int i=0; i<numOfRow; i++){
                for(int j=0; j<numOfCol; j++){
                    GridLayout.LayoutParams params =
                            (GridLayout.LayoutParams)Cells[i][j].getLayoutParams();
                    params.width = w - 2*MARGIN;
                    params.height = h - 2*MARGIN;
                    params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                    Cells[i][j].setLayoutParams(params);
                    Cells[i][j].setOnTouchListener(swipeTouchListener);
                }
            }
            rowSizeList.setOnItemSelectedListener(rowSizeChanger);
            colSizeList.setOnItemSelectedListener(colSizeChanger);
            Hint.setOnClickListener(HintButton);

        }
    };

    private void DrawPuzzle(){
        int numOfCol = grid.getColumnCount();
        int numOfRow = grid.getRowCount();
        String a;
        Cells = new PuzzleCell[numOfRow][numOfCol];
        puzzle.setSize(numOfRow,numOfCol);
        System.out.println(numOfRow);
        System.out.println(numOfCol);
        grid.removeAllViews();
        for(int i=0;i<numOfCol;i++){
            for (int j=0;j<numOfRow;j++){
                a = puzzle.getValue(j,i);
                if(a == String.valueOf(numOfCol*numOfRow)){
                    a = "";
                }
                PuzzleCell Tcell = new PuzzleCell(mContext,i,j,a,numOfRow,numOfCol);

                Cells[j][i] = Tcell;
                grid.addView(Tcell);
            }
        }
    }

    private void updatePuzzle(){
        Moves.setText("Number of Moves: "+puzzle.getNumOfMoves());

        int numOfCol = grid.getColumnCount();
        int numOfRow = grid.getRowCount();
        for(int i=0;i<numOfCol;i++){
            for (int j=0;j<numOfRow;j++){
                String a = puzzle.getValue(j,i);
                if(a == String.valueOf(numOfCol*numOfRow)){
                    a = "";
                }
                Cells[j][i].SetValue(a);
            }
        }

    }

    private OnSwipeTouchListener swipeTouchListener = new OnSwipeTouchListener(mContext){
        public void onSwipeTop() {
            puzzle.moveUp();
            updatePuzzle();
            isGameWon();
        }
        public void onSwipeRight() {
            puzzle.moveRight();
            updatePuzzle();
            isGameWon();
        }
        public void onSwipeLeft() {
            puzzle.moveLeft();
            updatePuzzle();
            isGameWon();
        }
        public void onSwipeBottom() {
            puzzle.moveDown();
            updatePuzzle();
            isGameWon();
        }
    };

    private View.OnClickListener HintButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            puzzle.intellegent();
            updatePuzzle();
            isGameWon();
        }
    };

    private void isGameWon(){
        if(puzzle.isSolved()){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainPage,new GameWon());
            fragmentTransaction.commit();
            System.out.println("You Won");
        }
    }
}


