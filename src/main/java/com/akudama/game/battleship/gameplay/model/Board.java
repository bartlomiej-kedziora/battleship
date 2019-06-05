package com.akudama.game.battleship.gameplay.model;

import com.google.common.collect.ImmutableSet;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {

    public static final int X_SIZE = 10;
    public static final int Y_SIZE = 10;

    private Set<Ship> ships = new LinkedHashSet<Ship>();
    private Set<Ship> sunkShips = new LinkedHashSet<Ship>();
    private Map<Ship, List<Boolean>> shipState = new HashMap<Ship, List<Boolean>>();

    public void addShip(Ship ship) throws Exception {
        if (new ShipPlacedValidator(getShips()).validate(ship)) {
            ships.add(ship);
        } else {
            throw new Exception("This ship can't be added because is touching another ship! Try again!");
        }
    }

    public Set<Ship> getShips() {
        return ImmutableSet.copyOf(ships);
    }

}
