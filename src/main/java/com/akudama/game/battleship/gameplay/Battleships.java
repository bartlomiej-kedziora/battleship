package com.akudama.game.battleship.gameplay;


import com.akudama.game.battleship.gameplay.model.Board;
import com.akudama.game.battleship.gameplay.model.Coordinates;
import com.akudama.game.battleship.gameplay.model.Direction;
import com.akudama.game.battleship.gameplay.model.Player;
import com.akudama.game.battleship.gameplay.model.Ship;

public class Battleships {

  public void launchGame() {
      Board myBoard = new Board();
      Board opponentBoard = new Board();
      Player player1 = new Player("Ja", myBoard, opponentBoard);
      try {
          myBoard.addShip(new Ship(4, new Coordinates(1, 1), Direction.HORIZONTAL)); //TRUE
          myBoard.addShip(new Ship(4, new Coordinates(1, 2), Direction.HORIZONTAL)); //FALSE
          myBoard.addShip(new Ship(4, new Coordinates(1, 3), Direction.HORIZONTAL)); //TRUE
          myBoard.addShip(new Ship(4, new Coordinates(1, 2), Direction.VERTICAL)); //FALSE
          myBoard.addShip(new Ship(4, new Coordinates(2, 2), Direction.VERTICAL)); //FALSE
          myBoard.addShip(new Ship(4, new Coordinates(3, 3), Direction.VERTICAL)); //FALSE
          myBoard.addShip(new Ship(4, new Coordinates(3, 4), Direction.VERTICAL)); //FALSE
          myBoard.addShip(new Ship(4, new Coordinates(4, 5), Direction.VERTICAL)); //TRUE
          myBoard.addShip(new Ship(2, new Coordinates(5, 4), Direction.VERTICAL)); //FALSE
      } catch(Exception e) {
          System.out.println(e);
      }
  }
}
