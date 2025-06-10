package org.bwint.firstjava;

import java.util.List;

public class ComputerOpponent {
    

    int difficulty;
    List<String> gameBoard;

    public ComputerOpponent(int difficulty, List<String> board) {
        // gameBoard needs to be updated every time it has a turn
        this.difficulty = difficulty;
        this.gameBoard = board;
    }

    public void updateBoard(List<String> newBoard) {
        // updates board from board class thing
        this.gameBoard = newBoard;
    }

    private int randomMovement() {
        // literally just randomly selects a position,
        // checks if its already used, and then returns
        // the position value

        while (true) {
            // (int) is type casting
            int selection = (int) ((Math.random() * 9) + 1);

            if (gameBoard.get(selection).equals("-")) {
                return selection;
            }
        }
    }

    int winPossible(int a, int b, int c, String thing) {
        // checks if a win is possible for three cases based on
        // whether or not two of the three values are equal
        // a=b | b=c | a=c WHILE the unused value is empty AND a and b = thing
        // returns the position

        String pos1 = gameBoard.get(a);
        String pos2 = gameBoard.get(b);
        String pos3 = gameBoard.get(c);

        if (pos1.equals(pos2) && pos3.equals("-") && pos1.equals(thing)) {
            return c;

        } else if (pos2.equals(pos3) && pos1.equals("-") && pos2.equals(thing)) {
            return a;

        } else if (pos1.equals(pos3) && pos2.equals("-") && pos3.equals(thing)) {
            return b;
        }
        return -1;
    }

    private int strategicMovement() {
        // makes decisions in this order:
        // 1: win if it can
        // 2: block the opponent from winning
        // 3: pick center, then corners, then sides

        String[] things = {"x", "o"};
        String currentThing;
        // let me cook here

        for (int j = 0; j < 2; j++) {
            currentThing = things[j];
            // so the first time, it'll look if there is a possibility it can win.
            // then it'll go through the same steps, but looking if theres a possibility where it loses
            // because it takes the same steps to win or lose

            // first looking if there is a possible winner
            // checking horizontal
            int first, second, third, outcome;
            for (int i = 0; i < 3; i++) {
                first = i * 3;
                second = (i * 3) + 1;
                third = (i * 3) + 2;

                outcome = winPossible(first, second, third, currentThing);
                if (outcome > -1) return outcome;
            }

            // checking vertical
            for (int i = 0; i < 3; i++) {
                first = i;
                second = i + 3;
                third = i + 6;

                outcome = winPossible(first, second, third, currentThing);
                if (outcome > -1) return outcome;
            }

            // checking diagonals
            for (int i = 0; i < 2; i++) {
                first = i + i; // at 0 it's 0, and at 1 its 2
                second = 4; // middle square
                third = 8 - (i + i); // reverse of the first two rows lower

                outcome = winPossible(first, second, third, currentThing);
                if (outcome > -1) return outcome;

            }
            // now onto picking center -> corner -> side
            if (gameBoard.get(4).equals("-")) {
                return 4;
            } // an else is redundant here

            int[] corners = {0, 2, 6, 8}; // arrays just make my life easier here
            for (int i = 0; i < 4; i++) {
                int current = corners[i]; // goes top left -> top right -> bottom left -> bottom right
                if (gameBoard.get(current).equals("-")) {
                    return current; // if a corner is empty take it

                            }}

            int[] sides = {1, 3, 5, 7};
            // same stuff as before
            for (int i = 0; i < 4; i++) {
                int current = sides[i];
                if (gameBoard.get(current).equals("-")) {
                    return current;
                }
            }

            
        }
        return 0;
    }

    public int opponentTurn() {
        switch (this.difficulty) {
            case 1 -> {
                return randomMovement();
            }

            case 2 -> {
                return strategicMovement();
            }

            // case 3 -> {
            //     return tenStepsAhead();
            // }
            default ->
                throw new AssertionError();
        }
    }

}
