package ca.morais.everton.todolist.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Card {
    private Long id;

    private String title;

    private String description;

    private CardStatus currentStatus;

    private Instant dueDate;

    public static Card create(Long id, String title, String description, CardStatus currentStatus, Instant dueDate) {
        return Card.builder()
                   .id(id)
                   .title(title)
                   .description(description)
                   .currentStatus(currentStatus)
                   .dueDate(dueDate)
                   .build();
    }

    public static Card create(String title, String description, CardStatus currentStatus, Instant dueDate) {
        return create(null, title, description, currentStatus, dueDate);
    }

    public Card updateDomain(CardInfoToUpdate cardToUpdate) {
        return this.toBuilder()
                   .title(cardToUpdate.getTitle())
                   .description(cardToUpdate.getDescription()
                                            .orElse(null))
                   .currentStatus(cardToUpdate.getCurrentStatus())
                   .dueDate(cardToUpdate.getDueDate())
                   .build();
    }

}
