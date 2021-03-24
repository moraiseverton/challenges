package ca.morais.everton.todolist.exceptions;

import ca.morais.everton.todolist.domains.Card;

public class InvalidCardException extends RuntimeException {

    public InvalidCardException(String message, Card card) {
        super(message + " card: " + card);
    }
}