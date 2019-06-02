import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class ShipPlacedValidator {
    public static final Map<Direction, BiFunction<Ship, Ship, Boolean>> NEW_SHIP_TOUCHING_CHECKER_WITH_ADDED
            = ImmutableMap.of(Direction.HORIZONTAL, ShipPlacedValidator::isHorizontalShipTouchingAnotherShip,
            Direction.VERTICAL, ShipPlacedValidator::isVerticalShipTouchingAnotherShip);

    private static final Map<Direction, BiFunction<Ship, Ship, Boolean>> HORIZONTAL_SHIP_COORDINATES_TOUCHING_CHECKER
            = ImmutableMap.of(Direction.HORIZONTAL, ShipPlacedValidator::isHorizontalShipTouchingHorizontalShip,
            Direction.VERTICAL, ShipPlacedValidator::isHorizontalShipTouchingVerticalShip);

    private static final Map<Direction, BiFunction<Ship, Ship, Boolean>> VERTICAL_SHIP_COORDINATES_TOUCHING_CHECKER
            = ImmutableMap.of(Direction.HORIZONTAL, ShipPlacedValidator::isVerticalShipTouchingHorizontalShip,
            Direction.VERTICAL, ShipPlacedValidator::isVerticalShipTouchingVerticalShip);

    private Set<Ship> ships;

    public ShipPlacedValidator(Board board) {
        ships = board.getShips();
    }

    public boolean validate(Ship ship) {
        return isShipPlacedInProperPositionOnBoard(ship);
    }

    private boolean isShipPlacedInProperPositionOnBoard(Ship ship) {
        if (isSizeOfShipFitToSizeOfBoard(ship)) {
            for (Ship existedShip : ships) {
                if(NEW_SHIP_TOUCHING_CHECKER_WITH_ADDED.get(ship.getDirection()).apply(ship, existedShip)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean isSizeOfShipFitToSizeOfBoard(Ship ship) {
        return ship.getSize() > 0 && isStartedOnBoard(ship) && isEndedOnBoard(ship);
    }

    private boolean isStartedOnBoard(Ship ship) {
        return ship.getHeadPosition().getPosX() > 0 && ship.getHeadPosition().getPosY() > 0;
    }

    private boolean isEndedOnBoard(Ship ship) {
        return ship.getHeadPosition().getPosX() + ship.getSize() - 1 <= Board.X_SIZE
                && ship.getHeadPosition().getPosY() + ship.getSize() - 1 <= Board.Y_SIZE;
    }

    private static boolean isHorizontalShipTouchingAnotherShip(Ship ship, Ship existedShip) {
        return HORIZONTAL_SHIP_COORDINATES_TOUCHING_CHECKER.get(existedShip.getDirection()).apply(ship, existedShip);
    }

    private static boolean isVerticalShipTouchingAnotherShip(Ship ship, Ship existedShip) {
        return VERTICAL_SHIP_COORDINATES_TOUCHING_CHECKER.get(existedShip.getDirection()).apply(ship, existedShip);
    }

    private static boolean isHorizontalShipTouchingHorizontalShip(Ship ship, Ship existedShip) {
        boolean isXinScope = isShapeInLineScope(ship.getHeadPosition().getPosX(),
                ship.getHeadPosition().getPosX() + ship.getSize() - 1,
                existedShip.getHeadPosition().getPosX(),
                existedShip.getHeadPosition().getPosX() + existedShip.getSize() - 1);
        boolean isYinScope = isShapeInLineScope(ship.getHeadPosition().getPosY(),
                ship.getHeadPosition().getPosY(),
                existedShip.getHeadPosition().getPosY(),
                existedShip.getHeadPosition().getPosY());
        return isXinScope && isYinScope;
    }

    private static boolean isHorizontalShipTouchingVerticalShip(Ship ship, Ship existedShip) {
        boolean isXinScope = isShapeInLineScope(ship.getHeadPosition().getPosX(),
                ship.getHeadPosition().getPosX() + ship.getSize() - 1,
                existedShip.getHeadPosition().getPosX(),
                existedShip.getHeadPosition().getPosX() + existedShip.getSize() - 1);
        boolean isYinScope = isShapeInLineScope(ship.getHeadPosition().getPosY(),
                ship.getHeadPosition().getPosY(),
                existedShip.getHeadPosition().getPosY(),
                existedShip.getHeadPosition().getPosY() + existedShip.getSize() - 1);
        return isXinScope && isYinScope;
    }

    private static boolean isVerticalShipTouchingVerticalShip(Ship ship, Ship existedShip) {
        boolean isXinScope = isShapeInLineScope(ship.getHeadPosition().getPosX(),
                ship.getHeadPosition().getPosX(),
                existedShip.getHeadPosition().getPosX(),
                existedShip.getHeadPosition().getPosX());
        boolean isYinScope = isShapeInLineScope(ship.getHeadPosition().getPosY(),
                ship.getHeadPosition().getPosY() + ship.getSize() - 1,
                existedShip.getHeadPosition().getPosY(),
                existedShip.getHeadPosition().getPosY() + existedShip.getSize() - 1);
        return isXinScope && isYinScope;
    }

    private static boolean isVerticalShipTouchingHorizontalShip(Ship ship, Ship existedShip) {
        boolean isXinScope = isShapeInLineScope(ship.getHeadPosition().getPosX(),
                ship.getHeadPosition().getPosX(),
                existedShip.getHeadPosition().getPosX(),
                existedShip.getHeadPosition().getPosX() + existedShip.getSize() - 1);
        boolean isYinScope = isShapeInLineScope(ship.getHeadPosition().getPosY(),
                ship.getHeadPosition().getPosY() + ship.getSize() - 1,
                existedShip.getHeadPosition().getPosY(),
                existedShip.getHeadPosition().getPosY());
        return isXinScope && isYinScope;
    }

    private static boolean isShapeInLineScope(int lineStartPoint, int lineEndPoint, int comparedLineStartPoint, int comparedLineEndPoint) {
        return isPointInScope(lineStartPoint, comparedLineStartPoint, comparedLineEndPoint)
                || isPointInScope(lineEndPoint, comparedLineStartPoint, comparedLineEndPoint);
    }

    private static boolean isPointInScope(int point, int comparedStartPointLine, int comparedEndPointLine) {
        return isPointInStartedScope(point, comparedStartPointLine)
                && isPointInEndedScope(point, comparedEndPointLine);

    }
    private static boolean isPointInStartedScope(int startPoint, int comparedStartPoint) {
        return startPoint >= comparedStartPoint - 1;
    }

    private static boolean isPointInEndedScope(int startPoint, int comparedEndPoint) {
        return startPoint <= comparedEndPoint + 1;
    }
}
