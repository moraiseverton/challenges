package ca.morais.everton.todolist.services;

import ca.morais.everton.todolist.domains.Card;
import ca.morais.everton.todolist.domains.CardInfoToUpdate;
import ca.morais.everton.todolist.exceptions.CardNotFoundException;
import ca.morais.everton.todolist.exceptions.InvalidCardException;
import ca.morais.everton.todolist.repositories.CardQuery;
import ca.morais.everton.todolist.repositories.CardRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    static final String LINE_SEPARATOR = System.lineSeparator();

    public Card createCard(Card card) {
        return saveCard(card);
    }

    private Card saveCard(Card card) {
        if (validateCard(card)) {
            return repository.saveCard(card);
        }
        throw new RuntimeException("Invalid card: " + card);
    }

    public Card updateCard(Long cardId, CardInfoToUpdate updateInfo) {
        Card savedCard = getCardById(cardId);
        Card cardToUpdate = savedCard.updateDomain(updateInfo);
        return saveCard(cardToUpdate);
    }

    public void deleteCard(Long cardId) {
        Card cardToDelete = getCardById(cardId);
        repository.deleteCard(cardToDelete);
    }

    public List<Card> listAllCards() {
        return repository.findAllCards();
    }

    public List<Card> searchCards(CardQuery query) {
        return repository.queryCards(query);
    }

    public Card getCardById(Long cardId) {
        return repository.findCardByCardId(cardId)
                         .orElseThrow(() -> new CardNotFoundException(cardId));
    }

    boolean validateCard(Card card) {
        StringBuilder exceptionMessage = new StringBuilder();

        if (StringUtils.isBlank(card.getTitle())) {
            exceptionMessage.append("title must not be empty");
            exceptionMessage.append(LINE_SEPARATOR);
        }
        if (StringUtils.isNotBlank(card.getTitle()) && card.getTitle().length() > 100) {
            exceptionMessage.append("title must not have more than 100 characters");
            exceptionMessage.append(LINE_SEPARATOR);
        }
        if (card.getCurrentStatus() == null) {
            exceptionMessage.append("currentStatus must be selected");
            exceptionMessage.append(LINE_SEPARATOR);
        }
        if (card.getDueDate() == null) {
            exceptionMessage.append("dueDate must be selected");
            exceptionMessage.append(LINE_SEPARATOR);
        }
        if (card.getDueDate() != null && card.getDueDate()
                .isBefore(Instant.now())) {
            exceptionMessage.append("dueDate must be a future date");
            exceptionMessage.append(LINE_SEPARATOR);
        }

        if (exceptionMessage.length() > 0) {
            throw new InvalidCardException(exceptionMessage.toString(), card);
        }

        return true;
    }
}
