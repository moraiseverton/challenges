package ca.morais.everton.todolist.controllers.dtos;

import ca.morais.everton.todolist.domains.Card;
import ca.morais.everton.todolist.domains.CardInfoToUpdate;
import ca.morais.everton.todolist.domains.CardStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.h2.util.StringUtils;

import java.time.Instant;
import java.util.Optional;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CardDtoIn {
    protected final String title;
    protected final String description;
    protected final CardStatus currentStatus;
    protected final Instant dueDate;

    public Card toDomain() {
        return Card.create(title, description, currentStatus, dueDate);
    }

    public CardInfoToUpdate toUpdateDomain() {
        return new CardInfoToUpdate(
                title,
                Optional.ofNullable(description)
                        .filter(d -> !StringUtils.isNullOrEmpty(d)),
                currentStatus,
                dueDate
        );
    }
}
