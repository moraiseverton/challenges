package ca.morais.everton.todolist.domains;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Optional;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class CardInfoToUpdate {
    private String title;

    private Optional<String> description;

    private CardStatus currentStatus;

    private Instant dueDate;
}
