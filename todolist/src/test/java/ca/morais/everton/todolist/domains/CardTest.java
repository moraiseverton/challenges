package ca.morais.everton.todolist.domains;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

class CardTest {

    @Test
    void create_withoutCardId_createsDomainCorrectly() {
        String title = RandomStringUtils.random(100);
        String description = new Random().nextBoolean() ? UUID.randomUUID()
                                                              .toString() : null;
        CardStatus currentStatus = CardStatus.values()[new Random().nextInt(CardStatus.values().length)];
        Instant dueDate = Instant.now()
                                 .plus(1, ChronoUnit.DAYS);

        Card resulted = Card.create(title, description, currentStatus, dueDate);

        assertThat(resulted.getId(), nullValue());
        assertThat(resulted.getTitle(), is(title));
        assertThat(resulted.getDescription(), is(description));
        assertThat(resulted.getCurrentStatus(), is(currentStatus));
        assertThat(resulted.getDueDate(), is(dueDate));
    }

    @Test
    void create_withCardId_createsDomainCorrectly() {
        Long cardId = new Random().nextLong();
        String title = RandomStringUtils.random(100);
        String description = new Random().nextBoolean() ? UUID.randomUUID()
                                                              .toString() : null;
        CardStatus currentStatus = CardStatus.values()[new Random().nextInt(CardStatus.values().length)];
        Instant dueDate = Instant.now()
                                 .plus(1, ChronoUnit.DAYS);

        Card resulted = Card.create(cardId, title, description, currentStatus, dueDate);

        assertThat(resulted.getId(), is(cardId));
        assertThat(resulted.getTitle(), is(title));
        assertThat(resulted.getDescription(), is(description));
        assertThat(resulted.getCurrentStatus(), is(currentStatus));
        assertThat(resulted.getDueDate(), is(dueDate));
    }

    @Test
    void updateDomain_withNewDescription_updatesDomainCorrectly() {
        Card card = Card.create(RandomStringUtils.random(100),
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                Instant.now()
                                       .plus(1, ChronoUnit.MINUTES));

        String newTitle = RandomStringUtils.random(100);
        Optional<String> newDescription = Optional.of(UUID.randomUUID()
                                                          .toString());
        CardStatus newCurrentStatus = CardStatus.values()[new Random().nextInt(CardStatus.values().length)];
        Instant newDueDate = Instant.now()
                                    .plus(1, ChronoUnit.DAYS);

        CardInfoToUpdate updateInfo = new CardInfoToUpdate(
                newTitle, newDescription, newCurrentStatus, newDueDate
        );

        Card resultedCard = card.updateDomain(updateInfo);

        assertThat(resultedCard.getId(), is(card.getId()));
        assertThat(resultedCard.getTitle(), is(newTitle));
        assertThat(resultedCard.getDescription(), is(newDescription.get()));
        assertThat(resultedCard.getCurrentStatus(), is(newCurrentStatus));
        assertThat(resultedCard.getDueDate(), is(newDueDate));
    }

    @Test
    void updateDomain_withEmptyDescription_updatesDomainCorrectly() {
        Card card = Card.create(RandomStringUtils.random(100),
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                Instant.now()
                                       .plus(1, ChronoUnit.MINUTES));

        String newTitle = RandomStringUtils.random(100);
        Optional<String> newDescription = Optional.empty();
        CardStatus newCurrentStatus = CardStatus.values()[new Random().nextInt(CardStatus.values().length)];
        Instant newDueDate = Instant.now()
                                    .plus(1, ChronoUnit.DAYS);

        CardInfoToUpdate updateInfo = new CardInfoToUpdate(
                newTitle, newDescription, newCurrentStatus, newDueDate
        );

        Card resultedCard = card.updateDomain(updateInfo);

        assertThat(resultedCard.getId(), is(card.getId()));
        assertThat(resultedCard.getTitle(), is(newTitle));
        assertThat(resultedCard.getDescription(), nullValue());
        assertThat(resultedCard.getCurrentStatus(), is(newCurrentStatus));
        assertThat(resultedCard.getDueDate(), is(newDueDate));
    }
}