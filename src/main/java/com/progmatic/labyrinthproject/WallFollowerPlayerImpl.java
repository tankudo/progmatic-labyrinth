package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

import java.util.List;

public class WallFollowerPlayerImpl implements Player {

    private Direction thisWay = Direction.EAST;

    @Override
    public Direction nextMove(Labyrinth l) {
        List<Direction> possibleMoves = l.possibleMoves();

        if (possibleMoves.size() == 1) {
            thisWay = possibleMoves.get(0);
            return thisWay;
        } else if (possibleMoves.size() == 2) {
            if (possibleMoves.contains(thisWay)) {
                return thisWay;
            } else {

                Direction next = turn(thisWay);

                if (possibleMoves.contains(next)) {
                    thisWay = next;
                    return thisWay;
                } else {

                    thisWay = turnBack(next);
                    return thisWay;
                }
            }
        } else if (possibleMoves.size() == 3) {

            Direction next = turn(thisWay);
            if (possibleMoves.contains(next)) {
                thisWay = next;
                return thisWay;
            } else {

                return thisWay;
            }
        } else {

            thisWay = turn(thisWay);
            return thisWay;
        }

    }

    private Direction turn(Direction thisWay) {

        Direction next = Direction.EAST;
        switch (thisWay) {
            case EAST:
                next = Direction.SOUTH;
                break;
            case SOUTH:
                next = Direction.WEST;
                break;
            case WEST:
                next = Direction.NORTH;
                break;
            case NORTH:
                next = Direction.EAST;
                break;
        }
        return next;
    }

    private Direction turnBack(Direction thisWay) {

        Direction next = Direction.EAST;
        switch (thisWay) {
            case EAST:
                next = Direction.WEST;
                break;
            case SOUTH:
                next = Direction.NORTH;
                break;
            case WEST:
                next = Direction.EAST;
                break;
            case NORTH:
                next = Direction.SOUTH;
                break;
        }
        return next;
    }
}
