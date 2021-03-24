package ca.morais.everton.todolist.repositories;

import ca.morais.everton.todolist.domains.CardStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class CardQuery {

    private Set<Long> ids;
    private Optional<String> partialTitle;
    private Optional<String> partialDescription;
    private Optional<Instant> startDueDate;
    private Optional<Instant> endDueDate;
    private Set<CardStatus> statuses;

    public static CardQuery create(Set<Long> ids, Optional<String> partialTitle, Optional<String> partialDescription, Optional<Instant> startDueDate,
                                   Optional<Instant> endDueDate, Set<CardStatus> statuses) {
        return CardQuery.builder()
                        .ids(ids)
                        .partialTitle(partialTitle)
                        .partialDescription(partialDescription)
                        .startDueDate(startDueDate)
                        .endDueDate(endDueDate)
                        .statuses(statuses)
                        .build();
    }
}
