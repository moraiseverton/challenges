package ca.morais.everton.todolist.repositories.entities;

import ca.morais.everton.todolist.domains.Card;
import ca.morais.everton.todolist.domains.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@NoArgsConstructor
@Entity
@Table(name = "CARDS")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    private @NonNull String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private @NonNull CardStatus currentStatus;

    private @NonNull Instant dueDate;

    public static CardEntity fromDomain(Card domain) {
        return new CardEntity(domain.getId(),
                              domain.getTitle(),
                              domain.getDescription(),
                              domain.getCurrentStatus(),
                              domain.getDueDate());
    }

    public Card toDomain() {
        return Card.create(id, title, description, currentStatus, dueDate);
    }
}
