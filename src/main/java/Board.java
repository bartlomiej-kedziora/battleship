import com.google.common.collect.ImmutableSet;

import java.util.*;

public class Board {

    public static final int X_SIZE = 10;
    public static final int Y_SIZE = 10;

    private Set<Ship> ships = new LinkedHashSet<Ship>();
    private Set<Ship> sunkShips = new LinkedHashSet<Ship>();
    private Map<Ship, List<Boolean>> shipState = new HashMap<Ship, List<Boolean>>();
    private ShipPlacedValidator shipPlacedValidator = new ShipPlacedValidator();

    public void addShip(Ship ship) {
        if (shipPlacedValidator.validate(ship)) {
            ships.add(ship);
            System.out.println("Ship added :)");
        } else {
            System.out.println("This ship can't be added because is touching another ship! Try again!");
        }
    }

    public Set<Ship> getShips() {
        return new LinkedHashSet<>(ships);
    }

}
