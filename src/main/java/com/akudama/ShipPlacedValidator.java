package com.akudama;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;

public class ShipPlacedValidator {
    private Set<Ship> ships;

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
        return ship.getHeadPosition().getPosX() + ship.getSize() - 1 <= Board.X_SIZE
                && ship.getHeadPosition().getPosY() + ship.getSize() - 1 <= Board.Y_SIZE;
    }

    private boolean isShipNotTouchingAnyOfExistedShips(Ship ship) {
        return !ships.stream()
                .filter(existedShip -> isShipTouchingAnotherShip(ship, existedShip))
                .findFirst().isPresent();
    }

    private boolean isShipTouchingAnotherShip(Ship ship, Ship existedShip) {
        Map<String, Coordinates> shipCoordinates = setShipCoordinate(ship);
        Map<String, Coordinates> existedShipCoordinates = setShipCoordinate(existedShip);

        boolean isXinScope = isShapeInLineScope(shipCoordinates.get("head").getPosX(),
                shipCoordinates.get("tail").getPosX(),
                existedShipCoordinates.get("head").getPosX(),
                existedShipCoordinates.get("tail").getPosX());
        boolean isYinScope = isShapeInLineScope(shipCoordinates.get("head").getPosY(),
                shipCoordinates.get("tail").getPosY(),
                existedShipCoordinates.get("head").getPosY(),
                existedShipCoordinates.get("tail").getPosY());

        return isXinScope && isYinScope;
    }

    private Map<String, Coordinates> setShipCoordinate(Ship ship) {
        Coordinates headShip = new Coordinates(ship.getHeadPosition().getPosX(), ship.getHeadPosition().getPosY());
        Map<Direction, Coordinates> tailShip = ImmutableMap.<Direction, Coordinates>builder()
                .put(Direction.HORIZONTAL, new Coordinates(ship.getHeadPosition().getPosX() + ship.getSize() - 1, ship.getHeadPosition().getPosY()))
                .put(Direction.VERTICAL, new Coordinates(ship.getHeadPosition().getPosX(), ship.getHeadPosition().getPosY() + ship.getSize() - 1))
                .build();

        Map<String, Coordinates> shipCoordinate = ImmutableMap.<String, Coordinates>builder()
                .put("head", headShip)
                .put("tail", tailShip.get(ship.getDirection()))
                .build();

        return ImmutableMap.copyOf(shipCoordinate);
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
