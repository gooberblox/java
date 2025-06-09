/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package org.bwint.firstjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author overlord
 */
class Board {

    List<String> boardList = new ArrayList<>();

    public Board() {
        // constructor

        // making board
        for (int i = 0; i < 10; i++) {
            boardList.add("-");
        }
    }

    public boolean addCross(int position) {
        // returns false if something is already at that position
        if ((position < 0) || (position > 9)) {
            return false;

        }
        if (boardList.get(position).equals("-")) {
            boardList.set(position, "x");
        } else {
            // position given already has an x or o, return false b/c you cant replace
            return false;

        }
        return true;
    }

    public boolean addCircle(int position) {
        // same as addCross, just for circle

        if (boardList.get(position).equals("-")) {
            boardList.set(position, "o");
        } else {
            return false;
        }
        return true;
    }

    public void newBoard() {
        // resets board
        for (int i = 0; i < 9; i++) {
            boardList.set(i, "-");
        }
    }

    public void PrintBoard() {
        for (int i = 0; i < 9; i++) {
            System.out.print(boardList.get(i) + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }

        }
    }

    public int checkWin() {
        // 1: win
        // 2: lose
        // 3: tie
        // checking horizontally first, so 0-2, 3-5, 6-8
        String firstVal, secondVal, thirdVal;

        for (int i = 0; i < 3; i++) {
            firstVal = boardList.get((i * 3));
            secondVal = boardList.get((i * 3) + 1);
            thirdVal = boardList.get((i * 3) + 2);

            // if a=b and a=c, then b=c
            // removes need for a third check
            if (!firstVal.equals("-") && (firstVal.equals(secondVal) && firstVal.equals(thirdVal))) {
                if (firstVal.equals("x")) {
                    System.out.println("Player 1 wins!");
                } else {
                    System.out.println("Player 2 wins!");
                }
                return 1;
            }
        }

        // checking vertically now
        for (int i = 0; i < 3; i++) {
            firstVal = boardList.get(0 + i);
            secondVal = boardList.get(3 + i);
            thirdVal = boardList.get(6 + i);

            if (!firstVal.equals("-") && (firstVal.equals(secondVal) && firstVal.equals(thirdVal))) {
                if (firstVal.equals("x")) {
                    System.out.println("Player 1 wins !");
                } else {
                    System.out.println("Player 2 wins!");
                }
                return 1;
            }
        }

        // diagonals
        // second will always b 5
        secondVal = boardList.get(4);
        for (int i = 0; i < 2; i++) {
            firstVal = boardList.get(i * 2);
            thirdVal = boardList.get(8 - (i * 2));
            if (!firstVal.equals("-") && (firstVal.equals(secondVal) && firstVal.equals(thirdVal))) {
                if (firstVal.equals("x")) {
                    System.out.println("Player 1 wins!");
                } else {
                    System.out.println("Player 2 wins!");
                }
                return 1;
            }
        }
        
        return 2;

    }
}

public class FirstJava {

    static private int s;
    // ^^ for reading inputs
    static boolean usingComputerOpponent;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Tic Tac Toe game\n\nPress 1 for an AI opponent, or 2 to play with another person.");
        // ^^ for reading inputs
        s = in.nextInt();

        Board game = new Board();

        ComputerOpponent opponent = null;
        int computerDifficulty;
        // ^^ have to call both outside of if statement so
        // it can be called later(scope issue)

        // determining opponent
        if (s == 1) {
            // playing against computer
            usingComputerOpponent = true;

            
            // while loop so i can repeat incase they get it wrong
            while (true) {
                System.out.println("Playing against the computer\nEnter difficulty(1 for easy, 2 for medium, 3 for hard): ");
                s = in.nextInt();
                if (1 <= s && s <= 3) {
                    computerDifficulty = s;
                    break;
                } else {
                    System.out.println("err, number not in range");
                }
            }
            opponent = new ComputerOpponent(computerDifficulty, game.boardList);
        } else if (s == 2) {
            usingComputerOpponent = false;
        }

        while (true) {
            game.PrintBoard();

            // users move
            while (true) {
                System.out.println("Player 1, enter your move: ");
                s = in.nextInt();
                if (!game.addCross(s - 1)) {
                    System.out.println("That spots already taken!");
                } else {
                    break;
                }
            }

            if (game.checkWin() == 1) {
                game.PrintBoard();
                break;
            }

            // computer/other person's move
            if (usingComputerOpponent) {
                // update board and call new turn
                opponent.updateBoard(game.boardList);
                game.addCircle(opponent.opponentTurn());
            } else {
                game.PrintBoard();
                while (true) {
                    System.out.println("Player 2, enter your move: ");
                    s = in.nextInt();
                    if (!game.addCircle(s - 1)) {
                        System.out.println("That spots already taken!");
                    } else {
                        break;
                    }
                }
            }

            if (game.checkWin() == 2) {
                game.PrintBoard();
                break;
            }

        }
    }
}
