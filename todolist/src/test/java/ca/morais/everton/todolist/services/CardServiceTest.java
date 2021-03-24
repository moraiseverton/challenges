package ca.morais.everton.todolist.services;

import ca.morais.everton.todolist.domains.Card;
import ca.morais.everton.todolist.domains.CardStatus;
import ca.morais.everton.todolist.exceptions.InvalidCardException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class CardServiceTest {

    @Autowired
    private CardService service;

    @Test
    public void validateCard_whenCardTitleIsNull_throwsException() {
        Card card = Card.create(null,
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                Instant.now()
                                       .plus(5, ChronoUnit.MINUTES));

        Exception exception = assertThrows(InvalidCardException.class, () ->
                service.validateCard(card)
        );

        String expected = "title must not be empty";
        String resulted = exception.getMessage();

        assertThat(resulted, containsString(expected));
        assertThat(resulted, containsString(card.toString()));
    }

    @Test
    public void validateCard_whenCardTitleIsEmpty_throwsException() {
        Card card = Card.create(StringUtils.EMPTY,
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                Instant.now()
                                       .plus(5, ChronoUnit.MINUTES));

        Exception exception = assertThrows(InvalidCardException.class, () ->
                service.validateCard(card)
        );

        String expected = "title must not be empty";
        String resulted = exception.getMessage();

        assertThat(resulted, containsString(expected));
        assertThat(resulted, containsString(card.toString()));
    }

    @Test
    public void validateCard_whenCardTitleIsBlank_throwsException() {
        Card card = Card.create("      ",
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                Instant.now()
                                       .plus(5, ChronoUnit.MINUTES));

        Exception exception = assertThrows(InvalidCardException.class, () ->
                service.validateCard(card)
        );

        String expected = "title must not be empty";
        String resulted = exception.getMessage();

        assertThat(resulted, containsString(expected));
        assertThat(resulted, containsString(card.toString()));
    }

    @Test
    public void validateCard_whenCardTitleHasMoreThan100Chars_throwsException() {
        Card card = Card.create(RandomStringUtils.random(101),
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                Instant.now()
                                       .plus(5, ChronoUnit.MINUTES));

        Exception exception = assertThrows(InvalidCardException.class, () ->
                service.validateCard(card)
        );

        String expected = "title must not have more than 100 characters";
        String resulted = exception.getMessage();

        assertThat(resulted, containsString(expected));
        assertThat(resulted, containsString(card.toString()));
    }

    @Test
    public void validateCard_whenCurrentStatusIsNull_throwsException() {
        Card card = Card.create(RandomStringUtils.random(100),
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                null,
                                Instant.now()
                                       .plus(5, ChronoUnit.MINUTES));

        Exception exception = assertThrows(InvalidCardException.class, () ->
                service.validateCard(card)
        );

        String expected = "currentStatus must be selected";
        String resulted = exception.getMessage();

        assertThat(resulted, containsString(expected));
        assertThat(resulted, containsString(card.toString()));
    }

    @Test
    public void validateCard_whenDueDateIsNull_throwsException() {
        Card card = Card.create(RandomStringUtils.random(100),
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                null);

        Exception exception = assertThrows(InvalidCardException.class, () ->
                service.validateCard(card)
        );

        String expected = "dueDate must be selected";
        String resulted = exception.getMessage();

        assertThat(resulted, containsString(expected));
        assertThat(resulted, containsString(card.toString()));
    }

    @Test
    public void validateCard_whenDueDateIsInThePast_throwsException() {
        Card card = Card.create(RandomStringUtils.random(100),
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                Instant.now()
                                       .minusMillis(1));

        Exception exception = assertThrows(InvalidCardException.class, () ->
                service.validateCard(card)
        );

        String expected = "dueDate must be a future date";
        String resulted = exception.getMessage();

        assertThat(resulted, containsString(expected));
        assertThat(resulted, containsString(card.toString()));
    }

    @Test
    public void validateCard_whenCardHasMultipleErrors_throwsException() {
        Card card = Card.create(null, null, null, null);

        Exception exception = assertThrows(InvalidCardException.class, () ->
                service.validateCard(card)
        );

        String resulted = exception.getMessage();

        assertThat(resulted, containsString("title must not be empty"));
        assertThat(resulted, containsString("currentStatus must be selected"));
        assertThat(resulted, containsString("dueDate must be selected"));
        assertThat(resulted, containsString(CardService.LINE_SEPARATOR));
        assertThat(resulted, containsString(card.toString()));
    }

    @Test
    public void validateCard_whenCardIsValid_returnsTrue() {
        Card card = Card.create(RandomStringUtils.random(100),
                                new Random().nextBoolean() ? UUID.randomUUID()
                                                                 .toString() : null,
                                CardStatus.values()[new Random().nextInt(CardStatus.values().length)],
                                Instant.now()
                                       .plus(1, ChronoUnit.MINUTES));

        assertTrue(service.validateCard(card));
    }
}
