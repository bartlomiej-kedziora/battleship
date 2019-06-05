package com.akudama.game.battleship.gameplay.model;

import com.akudama.game.battleship.gameplay.model.Board;
import com.akudama.game.battleship.gameplay.model.Ship;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class Player {

    private String name;
    private Board myBoard;
    private Board opponentBoard;
    private List<Ship> myShips;

    public Player(String name, Board myBoard, Board opponentBoard) {
        this.name = name;
        this.myBoard = myBoard;
        this.opponentBoard = opponentBoard;
    }

    public String getName() {
        return name;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public List<Ship> getMyShips() {
        return ImmutableList.copyOf(myShips);
    }

    public void addShip(Ship ship) {
        myShips.add(ship);
    }

}
