package com.akudama;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;

public class ShipPlacedValidator {
    private Set<Ship> ships;
    private enum ShipParts {
        HEAD, TAIL
    }

    public ShipPlacedValidator(Board board) {
        ships = board.getShips();
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
        return !ships.stream()
                .anyMatch(existedShip -> isShipTouchingAnotherShip(ship, existedShip));
    }

    private boolean isShipTouchingAnotherShip(Ship ship, Ship existedShip) {
        Map<ShipParts, Coordinates> shipCoordinates = getShipCoordinates(ship);
        Map<ShipParts, Coordinates> existedShipCoordinates = getShipCoordinates(existedShip);
        return isShipXCoordinateInScope(shipCoordinates, existedShipCoordinates)
                && isShipYCoordinateInScope(shipCoordinates, existedShipCoordinates);
    }

    private boolean isShipXCoordinateInScope(Map<ShipParts, Coordinates> shipCoordinates, Map<ShipParts, Coordinates> existedShipCoordinates) {
        return isShapeInLineScope(shipCoordinates.get(ShipParts.HEAD).getPosX(),
                shipCoordinates.get(ShipParts.TAIL).getPosX(),
                existedShipCoordinates.get(ShipParts.HEAD).getPosX(),
                existedShipCoordinates.get(ShipParts.TAIL).getPosX());
    }

    private boolean isShipYCoordinateInScope(Map<ShipParts, Coordinates> shipCoordinates, Map<ShipParts, Coordinates> existedShipCoordinates) {
        return isShapeInLineScope(shipCoordinates.get(ShipParts.HEAD).getPosY(),
                shipCoordinates.get(ShipParts.TAIL).getPosY(),
                existedShipCoordinates.get(ShipParts.HEAD).getPosY(),
                existedShipCoordinates.get(ShipParts.TAIL).getPosY());
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
        return ImmutableMap.<Direction, Coordinates>builder()
                .put(Direction.HORIZONTAL, new Coordinates(ship.getHeadPosition().getPosX() + ship.getSize() - 1, ship.getHeadPosition().getPosY()))
                .put(Direction.VERTICAL, new Coordinates(ship.getHeadPosition().getPosX(), ship.getHeadPosition().getPosY() + ship.getSize() - 1))
                .build();
    }

    private boolean isShapeInLineScope(int lineStartPoint, int lineEndPoint, int comparedLineStartPoint, int comparedLineEndPoint) {
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
