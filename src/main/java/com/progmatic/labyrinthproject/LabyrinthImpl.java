package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private CellType[][] aLabyrinth;
    private Coordinate playerPosition;

    public LabyrinthImpl() {
        playerPosition = new Coordinate(0, 0);

    }

    @Override
    public int getWidth() {
        try {
            return aLabyrinth.length;
        } catch (NullPointerException e) {
            return -1;
        }

    }

    @Override
    public int getHeight() {
        try {
            return aLabyrinth[0].length;
        } catch (NullPointerException e) {
            return -1;
        }
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());
            aLabyrinth = new CellType[height][width];
            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            aLabyrinth[hh][ww] = CellType.WALL;
                            break;
                        case 'E':
                            aLabyrinth[hh][ww] = CellType.END;
                            break;
                        case 'S':
                            aLabyrinth[hh][ww] = CellType.START;
                            playerPosition = new Coordinate(ww, hh);
                            break;
                        default:
                            aLabyrinth[hh][ww] = CellType.EMPTY;
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        int row = c.getRow();
        int col = c.getCol();
        try {
            return aLabyrinth[row][col];
        } catch (IndexOutOfBoundsException e) {
            throw new CellException(row, col, "The cell is out of a labyrinth!");
        }
    }

    @Override
    public void setSize(int width, int height) {
        aLabyrinth = new CellType[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                aLabyrinth[j][i] = CellType.EMPTY;
            }
        }

    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        try {
            aLabyrinth[c.getRow()][c.getCol()] = type;
            if (type.equals(CellType.START)) {
                this.playerPosition = c;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CellException(c.getCol(), c.getRow(), "The cell is out of a labyrinth!");
        }
    }

    @Override
    public Coordinate getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public boolean hasPlayerFinished() {
        for (int i = 0; i < aLabyrinth.length; i++) {
            for (int j = 0; j < aLabyrinth[i].length; j++) {
                if (aLabyrinth[i][j].equals(CellType.END)) {
                    if (playerPosition.getRow() == i && playerPosition.getCol() == j) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<Direction> possibleMoves() {
        int row = playerPosition.getRow();
        int col = playerPosition.getCol();
        ArrayList<Direction> moves = new ArrayList<>();
        if (row + 1 < aLabyrinth.length && !aLabyrinth[row + 1][col].equals(CellType.WALL)) {
            moves.add(Direction.SOUTH);
        }
        if (row - 1 >= 0 && !aLabyrinth[row - 1][col].equals(CellType.WALL)) {
            moves.add(Direction.NORTH);
        }
        if (col + 1 < aLabyrinth.length && !aLabyrinth[row][col + 1].equals(CellType.WALL)) {
            moves.add(Direction.EAST);
        }
        if (col - 1 >= 0 && !aLabyrinth[row][col - 1].equals(CellType.WALL)) {
            moves.add(Direction.WEST);
        }
        return moves;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        int row = playerPosition.getRow();
        int col = playerPosition.getCol();
        switch (direction) {
            case SOUTH:
                if (aLabyrinth[row + 1][col].equals(CellType.WALL)) {
                    throw new InvalidMoveException();
                } else {
                    playerPosition = new Coordinate(col, row + 1);
                }
                break;
            case NORTH:
                if (row - 1 < 0 || aLabyrinth[row - 1][col].equals(CellType.WALL)) {
                    throw new InvalidMoveException();
                } else {
                    playerPosition = new Coordinate(col, row - 1);
                }
                break;
            case EAST:
                if (aLabyrinth[row][col + 1].equals(CellType.WALL)) {
                    throw new InvalidMoveException();
                } else {
                    playerPosition = new Coordinate(col + 1, row);
                }
                break;
            case WEST:
                if (col - 1 < 0 || aLabyrinth[row][col - 1].equals(CellType.WALL)) {
                    throw new InvalidMoveException();
                } else {
                    playerPosition = new Coordinate(col - 1, row);
                }
                break;
        }
    }

}
