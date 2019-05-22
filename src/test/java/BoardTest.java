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
        myBoard.addShip(new Ship(0, new Coordinates(1, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(-1, new Coordinates(3, 4), Direction.VERTICAL));

        assertEquals(myBoard.getShips().size(), 0);
    }

    @Test
    public void shouldNotAddShipWhichSizeIsMoreThanMaxBoardSize() {
        myBoard.addShip(new Ship(Board.X_SIZE + 1, new Coordinates(1, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(Board.Y_SIZE + 1, new Coordinates(1, 1), Direction.VERTICAL));

        assertEquals(myBoard.getShips().size(), 0);
    }

    @Test
    public void shouldNotAddShipIfItsHeadIsOutOfBoard() {
        myBoard.addShip(new Ship(2, new Coordinates(0, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(3, new Coordinates(-1, 2), Direction.VERTICAL));

        assertEquals(myBoard.getShips().size(), 0);
    }

    @Test
    public void shouldNotAddShipIfItsTailIsOutOfBoard() {
        myBoard.addShip(new Ship(2, new Coordinates(Board.X_SIZE, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(3, new Coordinates(2, Board.Y_SIZE - 1), Direction.VERTICAL));

        assertEquals(myBoard.getShips().size(), 0);
    }

    @Test
    public void shouldAddOneShipToBoardWhereOneIsTouching() {
        myBoard.addShip(new Ship(2, new Coordinates(1, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(3, new Coordinates(1, 2), Direction.HORIZONTAL));

        assertEquals(myBoard.getShips().size(), 1);
    }

    @Test
    public void shouldAddTwoShipsToBoardWhereOneIsTouching() {
        myBoard.addShip(new Ship(2, new Coordinates(1, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(3, new Coordinates(1, 2), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(4, new Coordinates(1, 3), Direction.HORIZONTAL));

        assertEquals(myBoard.getShips().size(), 2);
    }

    @Test
    public void shouldAddTwoShipsToBoardWhereTwoAreTouching() {
        myBoard.addShip(new Ship(2, new Coordinates(1, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(3, new Coordinates(1, 2), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(4, new Coordinates(1, 3), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(1, new Coordinates(1,2), Direction.VERTICAL));

        assertEquals(myBoard.getShips().size(), 2);
    }

    @Test
    public void shouldAddTwoShipsToBoardWhereThreeAreTouching() {
        myBoard.addShip(new Ship(2, new Coordinates(1, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(3, new Coordinates(1, 2), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(4, new Coordinates(1, 3), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(1, new Coordinates(1,2), Direction.VERTICAL));
        myBoard.addShip(new Ship(1, new Coordinates(2,2), Direction.VERTICAL));

        assertEquals(myBoard.getShips().size(), 2);
    }

    @Test
    public void shouldAddTwoShipsToBoardWhereFourAreTouching() {
        myBoard.addShip(new Ship(2, new Coordinates(1, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(3, new Coordinates(1, 2), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(4, new Coordinates(1, 3), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(1, new Coordinates(1,2), Direction.VERTICAL));
        myBoard.addShip(new Ship(1, new Coordinates(2,2), Direction.VERTICAL));
        myBoard.addShip(new Ship(2, new Coordinates(3,3), Direction.VERTICAL));

        assertEquals(myBoard.getShips().size(), 2);
    }

    @Test
    public void shouldAddTwoShipsToBoardWhereFiveAreTouching() {
        myBoard.addShip(new Ship(2, new Coordinates(1, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(3, new Coordinates(1, 2), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(4, new Coordinates(1, 3), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(1, new Coordinates(1,2), Direction.VERTICAL));
        myBoard.addShip(new Ship(1, new Coordinates(2,2), Direction.VERTICAL));
        myBoard.addShip(new Ship(2, new Coordinates(3,3), Direction.VERTICAL));
        myBoard.addShip(new Ship(2, new Coordinates(3,4), Direction.VERTICAL));

        assertEquals(myBoard.getShips().size(), 2);
    }

    @Test
    public void shouldAddThreeShipsToBoardWhereFiveAreTouching() {
        myBoard.addShip(new Ship(2, new Coordinates(1, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(3, new Coordinates(1, 2), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(4, new Coordinates(1, 3), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(1, new Coordinates(1,2), Direction.VERTICAL));
        myBoard.addShip(new Ship(1, new Coordinates(2,2), Direction.VERTICAL));
        myBoard.addShip(new Ship(2, new Coordinates(3,3), Direction.VERTICAL));
        myBoard.addShip(new Ship(2, new Coordinates(3,4), Direction.VERTICAL));
        myBoard.addShip(new Ship(1, new Coordinates(4,5), Direction.VERTICAL));

        assertEquals(myBoard.getShips().size(), 3);
    }

    @Test
    public void shouldAddThreeShipsToBoardWhereSixAreTouching() {
        myBoard.addShip(new Ship(2, new Coordinates(1, 1), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(3, new Coordinates(1, 2), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(4, new Coordinates(1, 3), Direction.HORIZONTAL));
        myBoard.addShip(new Ship(1, new Coordinates(1,2), Direction.VERTICAL));
        myBoard.addShip(new Ship(1, new Coordinates(2,2), Direction.VERTICAL));
        myBoard.addShip(new Ship(2, new Coordinates(3,3), Direction.VERTICAL));
        myBoard.addShip(new Ship(2, new Coordinates(3,4), Direction.VERTICAL));
        myBoard.addShip(new Ship(1, new Coordinates(4,5), Direction.VERTICAL));
        myBoard.addShip(new Ship(3, new Coordinates(5,4), Direction.VERTICAL));

        assertEquals(myBoard.getShips().size(), 3);
    }
}

