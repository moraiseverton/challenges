package ca.morais.everton.todolist.repositories.entities;

import ca.morais.everton.todolist.domains.Card;
import ca.morais.everton.todolist.domains.CardStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class CardEntityTest {

    @Test
    public void fromDomain_createsEntityCorrectly() {
        Card card = Card.create(new Random().nextLong(),
                                RandomStringUtils.random(100),
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                Instant.now()
                                       .plus(1, ChronoUnit.MINUTES));

        CardEntity resulted = CardEntity.fromDomain(card);

        assertThat(resulted.getId(), is(card.getId()));
        assertThat(resulted.getTitle(), is(card.getTitle()));
        assertThat(resulted.getDescription(), is(card.getDescription()));
        assertThat(resulted.getCurrentStatus(), is(card.getCurrentStatus()));
        assertThat(resulted.getDueDate(), is(card.getDueDate()));
    }

    @Test
    public void toDomain_createdDomainCorrectly() {
        CardEntity cardEntity = new CardEntity(new Random().nextLong(),
                                               RandomStringUtils.random(100),
                                               new Random().nextBoolean() ? UUID.randomUUID()
                                                                                .toString() : null,
                                               CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                               Instant.now()
                                                      .plus(1, ChronoUnit.MINUTES));

        Card resulted = cardEntity.toDomain();

        assertThat(resulted.getId(), is(cardEntity.getId()));
        assertThat(resulted.getTitle(), is(cardEntity.getTitle()));
        assertThat(resulted.getDescription(), is(cardEntity.getDescription()));
        assertThat(resulted.getCurrentStatus(), is(cardEntity.getCurrentStatus()));
        assertThat(resulted.getDueDate(), is(cardEntity.getDueDate()));
    }
}