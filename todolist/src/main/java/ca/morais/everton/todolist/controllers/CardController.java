package ca.morais.everton.todolist.controllers;

import ca.morais.everton.todolist.controllers.dtos.CardDtoIn;
import ca.morais.everton.todolist.controllers.dtos.CardDtoOut;
import ca.morais.everton.todolist.controllers.dtos.ListCardsDtoOut;
import ca.morais.everton.todolist.domains.Card;
import ca.morais.everton.todolist.domains.CardInfoToUpdate;
import ca.morais.everton.todolist.domains.CardStatus;
import ca.morais.everton.todolist.repositories.CardQuery;
import ca.morais.everton.todolist.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
class CardController {

    @Autowired
    private CardService service;

    @GetMapping
    public ListCardsDtoOut listCards() {
        List<Card> cards = service.listAllCards();
        return ListCardsDtoOut.fromDomainList(cards);
    }

    @GetMapping("/{cardId}")
    public CardDtoOut getCard(@PathVariable Long cardId) {
        Card card = service.getCardById(cardId);
        return CardDtoOut.fromDomain(card);
    }

    @GetMapping("/search")
    public ListCardsDtoOut search(@RequestParam(required = false, name = "id") Set<Long> ids,
                                   @RequestParam(required = false) Optional<String> partialTitle,
                                   @RequestParam(required = false) Optional<String> partialDescription,
                                   @RequestParam(required = false) Optional<Instant> startDueDate,
                                   @RequestParam(required = false) Optional<Instant> endDueDate,
                                   @RequestParam(required = false, name = "status") Set<CardStatus> statuses) {
        CardQuery cardQuery = CardQuery.create(ids, partialTitle, partialDescription, startDueDate, endDueDate, statuses);
        List<Card> cards = service.searchCards(cardQuery);
        return ListCardsDtoOut.fromDomainList(cards);
    }

    @GetMapping("/expired")
    public ListCardsDtoOut expired() {
        CardQuery cardQuery = CardQuery.builder()
                                       .endDueDate(Optional.of(Instant.now()))
                                       .build();

        List<Card> cards = service.searchCards(cardQuery);
        return ListCardsDtoOut.fromDomainList(cards);
    }

    @PostMapping
    public CardDtoOut create(@RequestBody CardDtoIn cardDtoIn) {
        Card createdCard = service.createCard(cardDtoIn.toDomain());
        return CardDtoOut.fromDomain(createdCard);
    }

    @PatchMapping("/{cardId}")
    public CardDtoOut patch(@PathVariable Long cardId, @RequestBody CardDtoIn cardDtoIn) {
        return update(cardId, cardDtoIn);
    }

    @PutMapping("/{cardId}")
    public CardDtoOut update(@PathVariable Long cardId, @RequestBody CardDtoIn cardDtoIn) {
        CardInfoToUpdate updateInfo = cardDtoIn.toUpdateDomain();
        Card updatedInfo = service.updateCard(cardId, updateInfo);
        return CardDtoOut.fromDomain(updatedInfo);
    }

    @DeleteMapping("/{cardId}")
    public void delete(@PathVariable Long cardId) {
        service.deleteCard(cardId);
    }
}
