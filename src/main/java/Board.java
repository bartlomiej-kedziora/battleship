import com.google.common.collect.ImmutableSet;

import java.util.*;

public class Board {

    public static final int X_SIZE = 10;
    public static final int Y_SIZE = 10;

    private Set<Ship> ships = new LinkedHashSet<Ship>();
    private Set<Ship> sunkShips = new LinkedHashSet<Ship>();
    private Map<Ship, List<Boolean>> shipState = new HashMap<Ship, List<Boolean>>();

    public void addShip(Ship ship) {
        if (new ShipPlacedValidator(this).validate(ship)) {
            ships.add(ship);
            System.out.println("Ship added :)");
        } else {
            System.out.println("This ship can't be added because is touching another ship! Try again!");
        }
    }

    public Set<Ship> getShips() {
        return ImmutableSet.copyOf(ships);
    }

}
