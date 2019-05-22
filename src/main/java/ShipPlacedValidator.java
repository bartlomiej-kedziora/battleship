import com.google.common.collect.ImmutableMap;

import java.util.LinkedHashSet;
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

    private Set<Ship> ships = new LinkedHashSet<Ship>();

    public boolean validate(Ship ship) {
        if (isShipPlacedInProperPositionOnBoard(ship)) {
            ships.add(ship);
            return true;
        }
        return false;
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
        for (int n = ship.getHeadPosition().getPosX(); n <= ship.getHeadPosition().getPosX() + ship.getSize(); n++) {
            for (int e = existedShip.getHeadPosition().getPosX(); e <= existedShip.getHeadPosition().getPosX() + existedShip.getSize(); e++) {
                if (isShipsCoordinatesTouching(n, e, ship.getHeadPosition().getPosY(), existedShip.getHeadPosition().getPosY())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isHorizontalShipNotTouchingVerticalShip(Ship ship, Ship existedShip) {
        for (int n = ship.getHeadPosition().getPosX(); n <= ship.getHeadPosition().getPosX() + ship.getSize(); n++) {
            for (int e = existedShip.getHeadPosition().getPosY(); e <= existedShip.getHeadPosition().getPosY() + existedShip.getSize(); e++) {
                if (isShipsCoordinatesTouching(n, existedShip.getHeadPosition().getPosX(), e, ship.getHeadPosition().getPosY())) {
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
        for (int n = ship.getHeadPosition().getPosY(); n <= ship.getHeadPosition().getPosY() + ship.getSize(); n++) {
            for (int e = existedShip.getHeadPosition().getPosX(); e <= existedShip.getHeadPosition().getPosX() + existedShip.getSize(); e++) {
                if (isShipsCoordinatesTouching(n, existedShip.getHeadPosition().getPosY(), e, ship.getHeadPosition().getPosX())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isVerticalShipNotTouchingVerticalShip(Ship ship, Ship existedShip) {
        for (int n = ship.getHeadPosition().getPosY(); n <= ship.getHeadPosition().getPosY() + ship.getSize(); n++) {
            for (int e = existedShip.getHeadPosition().getPosY(); e <= existedShip.getHeadPosition().getPosY() + existedShip.getSize(); e++) {
                if (isShipsCoordinatesTouching(n, e, existedShip.getHeadPosition().getPosX(), ship.getHeadPosition().getPosX())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isShipsCoordinatesTouching(int x1, int x2, int y1, int y2) {
        return isCoordinateTouching(x1, x2) && isCoordinateTouching(y1, y2);
    }

    private static boolean isCoordinateTouching(int x1, int x2) {
        return x1 == x2 || x1 == x2 - 1 || x1 == x2 + 1;
    }
}
