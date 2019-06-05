package com.akudama.game.battleship;

import com.akudama.game.battleship.gameplay.model.Board;
import com.akudama.game.battleship.gameplay.model.Coordinates;
import com.akudama.game.battleship.gameplay.model.Direction;
import com.akudama.game.battleship.gameplay.model.Player;
import com.akudama.game.battleship.gameplay.model.Ship;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    @Rule
    public TestName name = new TestName();

    private Board myBoard;
    private Board enemyBoard;
    private Player me;
    private Player enemy;

    @Before
    public void initialize() {
        System.out.println("Currently we're testing: " + name.getMethodName() + " method");
        myBoard = new Board();
        enemyBoard = new Board();
        me = new Player("ME", myBoard, enemyBoard);
        enemy = new Player("ENEMY", enemyBoard, myBoard);
    }

    @Test
    public void shouldNotAddShipWhichSizeIsLessThanOne() {
        try {
            myBoard.addShip(new Ship(0, new Coordinates(1, 1), Direction.HORIZONTAL));
            myBoard.addShip(new Ship(-1, new Coordinates(3, 4), Direction.VERTICAL));
        } catch(Exception e) {
            System.out.println(e);
        }

        assertEquals(myBoard.getShips().size(), 0);
    }

    @Test
    public void shouldNotAddShipWhichSizeIsMoreThanMaxBoardSize() {
        try {
            myBoard.addShip(new Ship(Board.X_SIZE + 1, new Coordinates(1, 1), Direction.HORIZONTAL));
            myBoard.addShip(new Ship(Board.Y_SIZE + 1, new Coordinates(1, 1), Direction.VERTICAL));
        } catch(Exception e) {
        System.out.println(e);
        }

        assertEquals(myBoard.getShips().size(), 0);
    }

    @Test
    public void shouldNotAddShipIfItsHeadIsOutOfBoard() {
        try {
            myBoard.addShip(new Ship(2, new Coordinates(0, 1), Direction.HORIZONTAL));
            myBoard.addShip(new Ship(3, new Coordinates(-1, 2), Direction.VERTICAL));
        } catch(Exception e) {
            System.out.println(e);

        }

        assertEquals(myBoard.getShips().size(), 0);
    }

    @Test
    public void shouldNotAddShipIfItsTailIsOutOfBoard() {
        try {
            myBoard.addShip(new Ship(2, new Coordinates(Board.X_SIZE, 1), Direction.HORIZONTAL));
            myBoard.addShip(new Ship(3, new Coordinates(2, Board.Y_SIZE - 1), Direction.VERTICAL));
        } catch(Exception e) {
            System.out.println(e);
        }

        assertEquals(myBoard.getShips().size(), 0);
    }

    @Test
    public void shouldAddOneShipToBoardWhereOneIsTouching() {
        try {
            myBoard.addShip(new Ship(2, new Coordinates(1, 1), Direction.HORIZONTAL));
            myBoard.addShip(new Ship(3, new Coordinates(1, 2), Direction.HORIZONTAL));
        } catch(Exception e) {
            System.out.println(e);
        }

        assertEquals(myBoard.getShips().size(), 1);
    }

    @Test
    public void shouldAddTwoShipsToBoardWhereOneIsTouching() {
        try {
            myBoard.addShip(new Ship(2, new Coordinates(1, 1), Direction.HORIZONTAL));
            myBoard.addShip(new Ship(4, new Coordinates(1, 3), Direction.HORIZONTAL));
            myBoard.addShip(new Ship(3, new Coordinates(1, 2), Direction.HORIZONTAL));
        } catch(Exception e) {
            System.out.println(e);
        }

        assertEquals(myBoard.getShips().size(), 2);
    }
}