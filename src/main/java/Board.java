import java.util.*;

public class Board {

    private Set<Ship> ships = new LinkedHashSet<Ship>();
    private Set<Ship> sunkShips = new LinkedHashSet<Ship>();
    private Map<Ship, List<Boolean>> shipState = new HashMap<Ship, List<Boolean>>();
    private final int X_SIZE = 10;
    private final int Y_SIZE = 10;

    public void addShip(Ship ship) {
        if(isShipPlacedInProperPositionOnBoard(ship)) {
            ships.add(ship);
            System.out.println("Ship added :)");
        }
        else {
            System.out.println("This ship can't be added because is touching another ship! Try again!");
        }

    }

    private boolean isShipPlacedInProperPositionOnBoard(Ship ship) {
        if(isStartedOnBoard(ship) && isEndedOnBoard(ship)) {
            if (ships.size() > 0) {
                for (Ship existedShip : ships) {
                    if (ship.getDirection() == Direction.HORIZONTAL) {
                        return !isHorizontalShipTouchingAnotherShip(ship, existedShip);
                    } else {
                        return !isVerticalShipTouchingAnotherShip(ship, existedShip);
                    }
                }
            }
        }
        else {
            return false;
        }
        return true;
    }

    private boolean isStartedOnBoard(Ship ship) {
        return ship.getHeadPosition().getPosX() > 0 && ship.getHeadPosition().getPosY() > 0;
    }

    private boolean isEndedOnBoard(Ship ship) {
        return ship.getHeadPosition().getPosX() + ship.getSize() - 1 <= X_SIZE
                && ship.getHeadPosition().getPosY() + ship.getSize() -1 <= Y_SIZE;
    }

    private boolean isHorizontalShipTouchingAnotherShip(Ship ship, Ship existedShip) {
        if(existedShip.getDirection() == Direction.HORIZONTAL) {
            for(int n = ship.getHeadPosition().getPosX(); n <= ship.getSize(); n++) {
                for(int e = existedShip.getHeadPosition().getPosX(); e <= existedShip.getSize(); e++) {
                    if((n == e || n == e - 1 || n == e + 1)
                            && (existedShip.getHeadPosition().getPosY() == ship.getHeadPosition().getPosY()
                            || existedShip.getHeadPosition().getPosY() == ship.getHeadPosition().getPosY() - 1
                            || existedShip.getHeadPosition().getPosY() == ship.getHeadPosition().getPosY() + 1)

                    ) {
                        return true;
                    }
                }
            }
        }
        else {
            for(int n = ship.getHeadPosition().getPosX(); n <= ship.getSize(); n++) {
                for(int e = existedShip.getHeadPosition().getPosY(); e <= existedShip.getSize(); e++) {
                    if(n == existedShip.getHeadPosition().getPosX()
                            || (n == existedShip.getHeadPosition().getPosX() - 1 || n == existedShip.getHeadPosition().getPosX() + 1)
                            && (e == ship.getHeadPosition().getPosY()
                            || e == ship.getHeadPosition().getPosY() - 1
                            || e == ship.getHeadPosition().getPosY() + 1)

                    ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isVerticalShipTouchingAnotherShip(Ship ship, Ship existedShip) {
        if(existedShip.getDirection() == Direction.HORIZONTAL) {
            for(int n = ship.getHeadPosition().getPosY(); n <= ship.getSize(); n++) {
                for(int e = existedShip.getHeadPosition().getPosY(); e <= existedShip.getSize(); e++) {
                    if(n == e
                            || (n == e - 1 || n == e + 1)
                            && (existedShip.getHeadPosition().getPosX() == ship.getHeadPosition().getPosX()
                            || existedShip.getHeadPosition().getPosX() == ship.getHeadPosition().getPosX() - 1
                            || existedShip.getHeadPosition().getPosX() == ship.getHeadPosition().getPosX() + 1)

                    ) {
                        return true;
                    }
                }
            }
        }
        else {
            for(int n = ship.getHeadPosition().getPosY(); n <= ship.getSize(); n++) {
                for(int e = existedShip.getHeadPosition().getPosX(); e <= existedShip.getSize(); e++) {
                    if(n == existedShip.getHeadPosition().getPosY()
                            || (n == existedShip.getHeadPosition().getPosY() - 1 || n == existedShip.getHeadPosition().getPosY() + 1)
                            && (e == ship.getHeadPosition().getPosX()
                            || e == ship.getHeadPosition().getPosX() - 1
                            || e == ship.getHeadPosition().getPosX() + 1)

                    ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
