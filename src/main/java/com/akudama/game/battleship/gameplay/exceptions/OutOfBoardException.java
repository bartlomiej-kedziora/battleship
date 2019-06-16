package com.akudama.game.battleship.gameplay.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutOfBoardException extends BattleshipException {
    private static final Logger LOG = LoggerFactory.getLogger(OutOfBoardException.class);
    public OutOfBoardException(String message) {
        LOG.error(message);
    }
}
