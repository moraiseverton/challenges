package ca.morais.everton.todolist.repositories;

import ca.morais.everton.todolist.domains.Card;
import ca.morais.everton.todolist.domains.CardStatus;
import ca.morais.everton.todolist.repositories.entities.CardEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface CardRepository extends CrudRepository<CardEntity, Long> {

    @Override
    List<CardEntity> findAll();

    @Override
    Optional<CardEntity> findById(Long cardId);

    @Query(" SELECT card " +
            "  FROM CardEntity card " +
            " WHERE 1 = 1 " +
            "   AND (card.id IN :ids OR :ids IS NULL)" +
            "   AND (LOWER(card.title) LIKE LOWER(CONCAT('%', :partialTitle, '%')) OR :partialTitle IS NULL)" +
            "   AND (LOWER(card.description) LIKE LOWER(CONCAT('%', :partialDescription, '%')) OR :partialDescription IS NULL)" +
            "   AND (card.currentStatus IN :statuses OR :statuses IS NULL)" +
            "   AND (card.dueDate >= :startDueDate OR :startDueDate IS NULL)" +
            "   AND (card.dueDate <= :endDueDate OR :endDueDate IS NULL)" +
            ""
    )
    List<CardEntity> queryCards(@Param("ids") Collection<Long> ids,
                                @Param("partialTitle") Optional<String> partialTitle,
                                @Param("partialDescription") Optional<String> partialDescription,
                                @Param("statuses") Collection<CardStatus> statuses,
                                @Param("startDueDate") Optional<Instant> startDueDate,
                                @Param("endDueDate") Optional<Instant> endDueDate
    );

    default List<Card> findAllCards() {
        return findAll().stream()
                        .map(CardEntity::toDomain)
                        .collect(Collectors.toList());
    }

    default Optional<Card> findCardByCardId(Long cardId) {
        return findById(cardId).map(CardEntity::toDomain);
    }

    default List<Card> queryCards(CardQuery query) {
        return queryCards(query.getIds(),
                          query.getPartialTitle(),
                          query.getPartialDescription(),
                          query.getStatuses(),
                          query.getStartDueDate(),
                          query.getEndDueDate()).stream()
                                                .map(CardEntity::toDomain)
                                                .collect(Collectors.toList());
    }

    default Card saveCard(Card cardToSave) {
        CardEntity entityToSave = CardEntity.fromDomain(cardToSave);
        CardEntity savedCard = save(entityToSave);
        return savedCard.toDomain();
    }

    default void deleteCard(Card cardToDelete) {
        delete(CardEntity.fromDomain(cardToDelete));
    }
}
