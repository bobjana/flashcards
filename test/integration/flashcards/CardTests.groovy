package flashcards



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Card)
class CardTests {

    void testList() {
        def cats = Category.findAllByName('Animals')
        println cats

        def results = Card.find("from Card where categories ", cats)
//        def c = Card.createCriteria()
//        def results = c.list {
//           like('question','l%')
//        }

//        'in'("categories",[cats])



//        def cards = Card.findAll("from Card as c left join Category as cat on c.category = cat.id where cat.id = ?", cats)
        println results
    }
}
