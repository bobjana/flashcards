package flashcards



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(FlashCardTest)
class FlashCardTestTests {

    void testNextCard() {
        //given
        def cards = createTestCards(10)
        def answers = createAnswers(cards, ["q1", "q5", "q7"], ["q2", "q4", "q9", "q2", "q2"])
        FlashCardTest flashCardTest = new FlashCardTest(cards: cards, answers: answers)
        //when
        def random = new Random()
        while (true){
            def card = flashCardTest.nextCard()
            println card
            if (card == null){
                break;
            }
            def answer = new Answer(card:card, correct: random.nextBoolean())
            println answer
            flashCardTest.submitAnswer(answer)
        }
    }

    def createAnswers(ArrayList<Card> cards, ArrayList<String> correctlyAnswered, ArrayList<String> wronglyAnswered) {
        def result = new ArrayList()
        cards.each {
            if (correctlyAnswered.contains(it.question)) {
                result.add(new Answer(card: it, correct: true))
            }
            else if (wronglyAnswered.contains(it.question)) {
                def card = it
                int c = wronglyAnswered.count(card.question)
                c.times {
                    result.add(new Answer(card: card, correct: false))
                }
            }
        }
        return result
    }

    def createTestCards(int numberOfCards) {
        def result = new ArrayList()
        numberOfCards.times {
            result.add(new Card(question: "q" + (it+1)))
        }
        return result
    }
}
