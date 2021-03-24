package ca.morais.everton.todolist.repositories;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
public class CardRepositoryTest {

    @Autowired
    private CardRepository repository;

    @Test
    @Disabled
    public void queryCards_whenAllParamsAreEmpty_returnAllCards() {
    }

    @Test
    @Disabled
    public void queryCards_filterByPartialTitle() {

    }

    @Test
    @Disabled
    public void queryCards_filterByAllFields() {

    }
}