package skeletonprogram;

import java.util.*;

public class SkeletonProgram {

    public static class Main {

        char board[][];
        Player playerOne;
        Player playerTwo;
        //PlayerThree;
        Scanner input = new Scanner(System.in);

        int width = 0;

        Console console = new Console();

        public Main() {
            console.printLeaderBoard();
            System.out.println("What size board do you want?");
            width = input.nextInt();
            width = width + 1;

            board = new char[width][width];

            playerOne = new Player(console.readLine("What is the name of player one? "));
            playerTwo = new Player(console.readLine("What is the name of player two? "));
            //playerThree = new Player(console.readLine("What is the name of player three? "));
            playerOne.setScore(0);
            playerTwo.setScore(0);
            //playerThree.setScore(0);

            do {
                playerOne.setSymbol(console.readChar((playerOne.getName()
                        + " what symbol do you wish to use X or O? ")));
                if (playerOne.getSymbol() != 'X' && playerOne.getSymbol() != 'O') {
                    console.println("Symbol to play must be uppercase X or O");
                }
            } while (playerOne.getSymbol() != 'X' && playerOne.getSymbol() != 'O');

            if (playerTwo.getSymbol() == 'X') {
                playerTwo.setSymbol('O');
            } else {
                playerTwo.setSymbol('X');
            }

            char startSymbol = 'X';
            char replay;

            do {
                int noOfMoves = 0;
                boolean gameHasBeenDrawn = false;
                boolean gameHasBeenWon = false;
                clearBoard();
                console.println();
                displayBoard();
                if (startSymbol == playerOne.getSymbol()) {
                    console.println(playerOne.getName() + " starts playing " + startSymbol);
                }
                else {
                    console.println(playerTwo.getName() + " starts playing " + startSymbol);
                }
                console.println();
                char currentSymbol = startSymbol;
                boolean validMove;
                Coordinate coordinate;
                do {

                    do {

                        coordinate = getMoveCoordinates();
                        validMove = checkValidMove(coordinate, board);
                        if (!validMove) {
                            console.println("Coordinates invalid, please try again");
                        }
                    } while (!validMove);

                    board[coordinate.getX()][coordinate.getY()] = currentSymbol;
                    displayBoard();
                    gameHasBeenWon = checkXOrOHasWon();
                    noOfMoves++;

                    if (!gameHasBeenWon) {

                        if (noOfMoves == 9) {
                            gameHasBeenDrawn = true;
                        } else {

                            if (currentSymbol == 'X') {
                                currentSymbol = 'O';
                            } else {
                                currentSymbol = 'X';
                            }

                        }
                    }

                } while (!gameHasBeenWon && !gameHasBeenDrawn);

                if (gameHasBeenWon) {
                    if (playerOne.getSymbol() == currentSymbol) {
                        console.println(playerOne.getName() + " congratulations you win!");
                        playerOne.addScore();
                    } else {
                        console.println(playerTwo.getName() + " congratulations you win!");
                        playerTwo.addScore();
                    }
                } else {
                    console.println("A draw this time!");
                }

                console.println("\n" + playerOne.getName() + " your score is: " + String.valueOf(playerOne.getScore()));
                console.println(playerTwo.getName() + " your score is: " + String.valueOf(playerTwo.getScore()));
                console.println();
                if (startSymbol == playerOne.getSymbol()) {
                    startSymbol = playerTwo.getSymbol();
                } else {
                    startSymbol = playerOne.getSymbol();
                }
                replay = console.readChar("\n Another game Y/N? ");
            } while (replay == 'Y');

            console.writeFile(playerOne.toString());
            console.writeFile(playerTwo.toString());
        }

        void displayBoard() { //can do any size board
            int row = 0;
            int column;
            String xAxis = " | ";
            String Line = "--+";
            for (int i = 1; i <= board.length - 1; i++) {
                xAxis = xAxis + (i + " ");
                Line = Line + "--";
            }
            console.println(xAxis);
            console.println(Line);

            for (row = 1; row <= board.length - 1; row++) {
                console.write(row + " | ");
                for (column = 1; column <= board.length - 1; column++) {
                    console.write(board[column][row] + " ");
                }
                console.println();
            }
        }

        void clearBoard() {
            int row;
            int column;
            for (row = 1; row <= board.length - 1; row++) {
                for (column = 1; column <= board.length - 1; column++) {
                    board[column][row] = ' ';
                }
            }
        }

        Coordinate getMoveCoordinates() {
            Coordinate coordinate = new Coordinate(console.readInteger("Enter x Coordinate: "), console.readInteger("Enter y Coordinate: "));
            return coordinate;
        }

        boolean checkValidMove(Coordinate coordinate, char[][] board) {
            boolean validMove;
            validMove = true;
            if (coordinate.getX() < 1 || coordinate.getX() > board.length-1) {
                validMove = false;
            }
            if (coordinate.getY() < 1 || coordinate.getX() > board.length-1) {
                validMove = false;
            }
            if (coordinate.getX() >= 1 && coordinate.getY() <= board.length-1) {
                if (coordinate.getY() >= 1 && coordinate.getX() <= board.length-1) {
                    if (board[coordinate.getX()][coordinate.getY()] != ' ') { //doesn't allow player to overwrite play
                        validMove = false;
                    }
                }
            }
            return validMove;
        }

        boolean checkXOrOHasWon() {

            int column;

            for (column = 1; column < board.length - 2; column++) {
                for (int i = 1; i < board.length - 2; i++) {
                    if (board[column][i] == board[column][i + 1]
                            && board[column][i + 1] == board[column][i + 2]
                            && board[column][i] != ' ') {

                        return true;

                    } else if (board[i][column] == board[i + 1][column]
                            && board[i + 1][column] == board[i + 2][column]
                            && board[i][column] != ' ') {
                        return true;

                    } else if (board[column][i] == board[column + 1][i + 1]
                            && board[column][i + 1] == board[column + 2][i + 2]
                            && board[column][i] != ' ') {
                        return true;

                    } else if (board[column][board.length - i] == board[column + 1][board.length - i - 1]
                            && board[column][board.length - i - 1] == board[column + 2][board.length - 1 - 2]
                            && board[column][board.length - i] != ' ') {
                        return true;

                    }
                }
            }

            return false;
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
