package com.akudama.game.battleship.gameplay.exceptions;

import com.akudama.game.battleship.gameplay.model.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TouchingException {
    private static final Logger LOG = LoggerFactory.getLogger(TouchingException.class);

    public TouchingException(Coordinates position) {
        LOG.error("Ship is touching existed one in point (" + position.getPosX() + ", " + position.getPosY() + ")");
    }
}
