package ca.morais.everton.todolist.exceptions;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(Long cardId) {
        super("Card not found. cardId: " + cardId);
    }
}