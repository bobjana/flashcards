package flashcards



import org.junit.*
import grails.test.mixin.*

@TestFor(CardController)
@Mock(Card)
class CardControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/card/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.cardInstanceList.size() == 0
        assert model.cardInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.cardInstance != null
    }

    void testSave() {
        controller.save()

        assert model.cardInstance != null
        assert view == '/card/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/card/show/1'
        assert controller.flash.message != null
        assert Card.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/card/list'


        populateValidParams(params)
        def card = new Card(params)

        assert card.save() != null

        params.id = card.id

        def model = controller.show()

        assert model.cardInstance == card
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/card/list'


        populateValidParams(params)
        def card = new Card(params)

        assert card.save() != null

        params.id = card.id

        def model = controller.edit()

        assert model.cardInstance == card
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/card/list'

        response.reset()


        populateValidParams(params)
        def card = new Card(params)

        assert card.save() != null

        // test invalid parameters in update
        params.id = card.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/card/edit"
        assert model.cardInstance != null

        card.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/card/show/$card.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        card.clearErrors()

        populateValidParams(params)
        params.id = card.id
        params.version = -1
        controller.update()

        assert view == "/card/edit"
        assert model.cardInstance != null
        assert model.cardInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/card/list'

        response.reset()

        populateValidParams(params)
        def card = new Card(params)

        assert card.save() != null
        assert Card.count() == 1

        params.id = card.id

        controller.delete()

        assert Card.count() == 0
        assert Card.get(card.id) == null
        assert response.redirectedUrl == '/card/list'
    }
}
