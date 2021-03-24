package ca.morais.everton.todolist.controllers.dtos;

import ca.morais.everton.todolist.domains.Card;
import ca.morais.everton.todolist.domains.CardStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.Instant;

@JsonInclude(Include.NON_NULL)
public class CardDtoOut {
    public final Long cardId;
    public final String title;
    public final String description;
    public final CardStatus currentStatus;
    public final Instant dueDate;

    CardDtoOut(Long cardId, String title, String description, CardStatus currentStatus, Instant dueDate) {
        this.cardId = cardId;
        this.title = title;
        this.description = description;
        this.currentStatus = currentStatus;
        this.dueDate = dueDate;
    }

    public static CardDtoOut fromDomain(Card card) {
        return new CardDtoOut(card.getId(), card.getTitle(), card.getDescription(), card.getCurrentStatus(), card.getDueDate());
    }
}
