import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class ShipPlacedValidator {
    public static final Map<Direction, BiFunction<Ship, Ship, Boolean>> NEW_SHIP_TOUCHING_CHECKER_WITH_ADDED
            = ImmutableMap.of(Direction.HORIZONTAL, ShipPlacedValidator::isHorizontalShipNotTouchingAnotherShip,
            Direction.VERTICAL, ShipPlacedValidator::isVerticalShipNotTouchingAnotherShip);

    private static final Map<Direction, BiFunction<Ship, Ship, Boolean>> HORIZONTAL_SHIP_COORDINATES_TOUCHING_CHECKER
            = ImmutableMap.of(Direction.HORIZONTAL, ShipPlacedValidator::isHorizontalShipNotTouchingHorizontalShip,
            Direction.VERTICAL, ShipPlacedValidator::isHorizontalShipNotTouchingVerticalShip);

    private static final Map<Direction, BiFunction<Ship, Ship, Boolean>> VERTICAL_SHIP_COORDINATES_TOUCHING_CHECKER
            = ImmutableMap.of(Direction.HORIZONTAL, ShipPlacedValidator::isVerticalShipNotTouchingHorizontalShip,
            Direction.VERTICAL, ShipPlacedValidator::isVerticalShipNotTouchingVerticalShip);

    private Set<Ship> ships;

    public ShipPlacedValidator(Board board) {
        ships = board.getShips();
    }

    public boolean validate(Ship ship) {
        return isShipPlacedInProperPositionOnBoard(ship);
    }

    private boolean isShipPlacedInProperPositionOnBoard(Ship ship) {
        boolean result = true;
        if (isSizeOfShipFitToSizeOfBoard(ship)) {
            for (Ship existedShip : ships) {
                result = NEW_SHIP_TOUCHING_CHECKER_WITH_ADDED.get(ship.getDirection()).apply(ship, existedShip);
            }
        } else {
            result = false;
        }
        return result;
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

    private static boolean isHorizontalShipNotTouchingAnotherShip(Ship ship, Ship existedShip) {
        return HORIZONTAL_SHIP_COORDINATES_TOUCHING_CHECKER.get(existedShip.getDirection()).apply(ship, existedShip);
    }

    private static boolean isHorizontalShipNotTouchingHorizontalShip(Ship ship, Ship existedShip) {
        int iStartLoop = ship.getHeadPosition().getPosX();
        int iStopLoop = ship.getHeadPosition().getPosX() + ship.getSize();
        int jStartLoop = existedShip.getHeadPosition().getPosX();
        int jStopLoop = existedShip.getHeadPosition().getPosX() + existedShip.getSize();
        int y1 = ship.getHeadPosition().getPosY();
        int y2 = existedShip.getHeadPosition().getPosY();
        for (int i = iStartLoop; i <= iStopLoop; i++) {
            for (int j = jStartLoop; j <= jStopLoop; j++) {
                int x1 = i;
                int x2 = j;
                if (isTwoDimensionCoordinateTouching(x1, x2, y1, y2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isHorizontalShipNotTouchingVerticalShip(Ship ship, Ship existedShip) {
        int iStartLoop = ship.getHeadPosition().getPosX();
        int iStopLoop = ship.getHeadPosition().getPosX() + ship.getSize();
        int jStartLoop = existedShip.getHeadPosition().getPosY();
        int jStopLoop = existedShip.getHeadPosition().getPosY() + existedShip.getSize();
        int x2 = existedShip.getHeadPosition().getPosX();
        int y1 = ship.getHeadPosition().getPosY();
        for (int i = iStartLoop; i <= iStopLoop; i++) {
            for (int j = jStartLoop; j <= jStopLoop; j++) {
                int x1 = i;
                int y2 = j;
                if (isTwoDimensionCoordinateTouching(x1, x2, y1, y2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isVerticalShipNotTouchingAnotherShip(Ship ship, Ship existedShip) {
        return VERTICAL_SHIP_COORDINATES_TOUCHING_CHECKER.get(existedShip.getDirection()).apply(ship, existedShip);
    }

    private static boolean isVerticalShipNotTouchingHorizontalShip(Ship ship, Ship existedShip) {
        int iStartLoop = ship.getHeadPosition().getPosY();
        int iStopLoop = ship.getHeadPosition().getPosY() + ship.getSize();
        int jStartLoop = existedShip.getHeadPosition().getPosX();
        int jStopLoop = existedShip.getHeadPosition().getPosX() + existedShip.getSize();
        int x1 = ship.getHeadPosition().getPosX();
        int y2 = existedShip.getHeadPosition().getPosY();
        for (int i = iStartLoop; i <= iStopLoop; i++) {
            for (int j = jStartLoop; j <= jStopLoop; j++) {
                int y1 = i;
                int x2 = j;
                if (isTwoDimensionCoordinateTouching(x1, x2, y1, y2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isVerticalShipNotTouchingVerticalShip(Ship ship, Ship existedShip) {
        int iStartLoop = ship.getHeadPosition().getPosY();
        int iStopLoop = ship.getHeadPosition().getPosY() + ship.getSize();
        int jStartLoop = existedShip.getHeadPosition().getPosY();
        int jStopLoop = existedShip.getHeadPosition().getPosY() + existedShip.getSize();
        int x1 = ship.getHeadPosition().getPosX();
        int x2 = existedShip.getHeadPosition().getPosX();

        for (int i = iStartLoop; i <= iStopLoop; i++) {
            for (int j = jStartLoop; j <= jStopLoop; j++) {
                int y1 = i;
                int y2 = j;
                if (isTwoDimensionCoordinateTouching(x1, x2, y1, y2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isTwoDimensionCoordinateTouching(int x1, int x2, int y1, int y2) {
        return isOneDimensionCoordinateTouching(x1, x2) && isOneDimensionCoordinateTouching(y1, y2);
    }

    private static boolean isOneDimensionCoordinateTouching(int x1, int x2) {
        return x1 == x2 || x1 == x2 - 1 || x1 == x2 + 1;
    }
}
