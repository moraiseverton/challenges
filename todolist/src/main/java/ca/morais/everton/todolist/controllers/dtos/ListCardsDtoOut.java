package ca.morais.everton.todolist.controllers.dtos;

import ca.morais.everton.todolist.domains.Card;

import java.util.List;
import java.util.stream.Collectors;

public class ListCardsDtoOut {
    public final List<CardDtoOut> cards;
    public final Integer size;

    ListCardsDtoOut(List<CardDtoOut> cards, Integer size) {
        this.cards = cards;
        this.size = size;
    }

    public static ListCardsDtoOut fromDomainList(List<Card> cards) {
        return new ListCardsDtoOut(cards.stream()
                                        .map(CardDtoOut::fromDomain)
                                        .collect(Collectors.toList()), cards.size());
    }
}
