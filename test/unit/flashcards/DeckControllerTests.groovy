package flashcards



import org.junit.*
import grails.test.mixin.*

@TestFor(DeckController)
@Mock(Deck)
class DeckControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/deck/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.deckInstanceList.size() == 0
        assert model.deckInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.deckInstance != null
    }

    void testSave() {
        controller.save()

        assert model.deckInstance != null
        assert view == '/deck/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/deck/show/1'
        assert controller.flash.message != null
        assert Deck.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/deck/list'


        populateValidParams(params)
        def deck = new Deck(params)

        assert deck.save() != null

        params.id = deck.id

        def model = controller.show()

        assert model.deckInstance == deck
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/deck/list'


        populateValidParams(params)
        def deck = new Deck(params)

        assert deck.save() != null

        params.id = deck.id

        def model = controller.edit()

        assert model.deckInstance == deck
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/deck/list'

        response.reset()


        populateValidParams(params)
        def deck = new Deck(params)

        assert deck.save() != null

        // test invalid parameters in update
        params.id = deck.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/deck/edit"
        assert model.deckInstance != null

        deck.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/deck/show/$deck.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        deck.clearErrors()

        populateValidParams(params)
        params.id = deck.id
        params.version = -1
        controller.update()

        assert view == "/deck/edit"
        assert model.deckInstance != null
        assert model.deckInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/deck/list'

        response.reset()

        populateValidParams(params)
        def deck = new Deck(params)

        assert deck.save() != null
        assert Deck.count() == 1

        params.id = deck.id

        controller.delete()

        assert Deck.count() == 0
        assert Deck.get(deck.id) == null
        assert response.redirectedUrl == '/deck/list'
    }
}
