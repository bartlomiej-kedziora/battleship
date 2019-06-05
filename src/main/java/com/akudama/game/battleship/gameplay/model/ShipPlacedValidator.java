package com.akudama.game.battleship.gameplay.model;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class ShipPlacedValidator {
    private static final Map<CoordinateType, Function<Coordinates, Integer>> COORDINATE_CALCULATION_TYPE
        = ImmutableMap.of(CoordinateType.X, Coordinates::getPosX, CoordinateType.Y, Coordinates::getPosY);

    private Set<Ship> ships;

    private enum ShipParts {
        HEAD, TAIL
    }

    private enum CoordinateType {
        X, Y
    }

    public ShipPlacedValidator(Set<Ship> ships) {
        this.ships = ships;
    }

    public boolean validate(Ship ship) {
        return isShipPlacedInProperPositionOnBoard(ship);
    }

    private boolean isShipPlacedInProperPositionOnBoard(Ship ship) {
        return isSizeOfShipFitToSizeOfBoard(ship) && isShipNotTouchingAnyOfExistedShips(ship);
    }

    private boolean isSizeOfShipFitToSizeOfBoard(Ship ship) {
        return ship.getSize() > 0 && isStartedOnBoard(ship) && isEndedOnBoard(ship);
    }

    private boolean isStartedOnBoard(Ship ship) {
        return ship.getHeadPosition().getPosX() > 0 && ship.getHeadPosition().getPosY() > 0;
    }

    private boolean isEndedOnBoard(Ship ship) {
        return isInHorizontalScopeOfBoard(ship) && isInVerticalScopeOfBoard(ship);
    }

    private boolean isInHorizontalScopeOfBoard(Ship ship) {
        return ship.getHeadPosition().getPosX() + ship.getSize() - 1 <= Board.X_SIZE;
    }

    private boolean isInVerticalScopeOfBoard(Ship ship) {
        return ship.getHeadPosition().getPosY() + ship.getSize() - 1 <= Board.Y_SIZE;
    }

    private boolean isShipNotTouchingAnyOfExistedShips(Ship ship) {
        return ships.stream()
                .noneMatch(existedShip -> isShipTouchingAnotherShip(ship, existedShip));
    }

    private boolean isShipTouchingAnotherShip(Ship ship, Ship existedShip) {
        Map<ShipParts, Coordinates> shipCoordinates = getShipCoordinates(ship);
        Map<ShipParts, Coordinates> existedShipCoordinates = getShipCoordinates(existedShip);

        return isShipCoordinateInScope(shipCoordinates, existedShipCoordinates, CoordinateType.X)
                && isShipCoordinateInScope(shipCoordinates, existedShipCoordinates, CoordinateType.Y);
    }

    private boolean isShipCoordinateInScope(Map<ShipParts, Coordinates> shipCoordinates,
        Map<ShipParts, Coordinates> existedShipCoordinates, CoordinateType coordinateType) {
        Integer headPosX = COORDINATE_CALCULATION_TYPE.get(coordinateType)
            .apply(shipCoordinates.get(ShipParts.HEAD));
        Integer tailPosX = COORDINATE_CALCULATION_TYPE.get(coordinateType)
            .apply(shipCoordinates.get(ShipParts.TAIL));
        Integer extHeadPosX = COORDINATE_CALCULATION_TYPE.get(coordinateType)
            .apply(existedShipCoordinates.get(ShipParts.HEAD));
        Integer extTailPosX = COORDINATE_CALCULATION_TYPE.get(coordinateType)
            .apply(existedShipCoordinates.get(ShipParts.TAIL));

        return isShapeInLineScope(headPosX, tailPosX, extHeadPosX, extTailPosX);
    }

    private Map<ShipParts, Coordinates> getShipCoordinates(Ship ship) {
        return ImmutableMap.<ShipParts, Coordinates>builder()
                .put(ShipParts.HEAD, getHeadShipCoordinate(ship))
                .put(ShipParts.TAIL, getTailShipCoordinate(ship).get(ship.getDirection()))
                .build();
    }

    private Coordinates getHeadShipCoordinate(Ship ship) {
        return new Coordinates(ship.getHeadPosition().getPosX(), ship.getHeadPosition().getPosY());
    }

    private Map<Direction, Coordinates> getTailShipCoordinate(Ship ship) {
        int horizontalPosX = ship.getHeadPosition().getPosX() + ship.getSize() - 1;
        int horizontalPosY = ship.getHeadPosition().getPosY();
        int verticalPosX = ship.getHeadPosition().getPosX();
        int verticalPosY = ship.getHeadPosition().getPosY() + ship.getSize() - 1;

        return ImmutableMap.<Direction, Coordinates>builder()
                .put(Direction.HORIZONTAL, new Coordinates(horizontalPosX, horizontalPosY))
                .put(Direction.VERTICAL, new Coordinates(verticalPosX, verticalPosY))
                .build();
    }

    private boolean isShapeInLineScope(int lineStartPoint, int lineEndPoint,
        int comparedLineStartPoint, int comparedLineEndPoint) {
        return isPointInScope(lineStartPoint, comparedLineStartPoint, comparedLineEndPoint)
                || isPointInScope(lineEndPoint, comparedLineStartPoint, comparedLineEndPoint);
    }

    private boolean isPointInScope(int point, int comparedStartPointLine, int comparedEndPointLine) {
        return isPointInStartedScope(point, comparedStartPointLine)
                && isPointInEndedScope(point, comparedEndPointLine);

    }

    private boolean isPointInStartedScope(int startPoint, int comparedStartPoint) {
        return startPoint >= comparedStartPoint - 1;
    }

    private boolean isPointInEndedScope(int startPoint, int comparedEndPoint) {
        return startPoint <= comparedEndPoint + 1;
    }
}
