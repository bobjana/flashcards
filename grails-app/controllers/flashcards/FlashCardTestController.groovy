package flashcards

import grails.converters.JSON

class FlashCardTestController {

    def flashCardTestService

    def index() {
//        def uncompleted = FlashCardTest.findAllByEndIsNull()
        //        if (uncompleted != null && uncompleted.size() > 0) {
        //            redirect(action: "continue", params: [id: uncompleted.get(0).id])
        //        }
        int numberOfCards = 20
        def numberOfCardsList = [20, 40, 60, 100]
        def categories = Category.list()
        categories.add(0, Category.ALL)
        [numberOfCards: numberOfCards, numberOfCardsList: numberOfCardsList, categoriesList: categories]
    }

    def start() {
        Set<Category> categories = new HashSet(params.list('categories'))
        println(params.list('categories'))
        def numberCards = params.int("numberOfCards")
        def test = flashCardTestService.generate(numberCards, categories)
        redirect(action: "next", params: [testId: test.id])
    }

    def next() {
        def flashCardTest = FlashCardTest.findById(params.testId)
//        if (flashCardTest == null || flashCardTest.completed()){
        //            redirect(action: "index")
        //        }
        def card = flashCardTestService.nextCard(flashCardTest)

        //TODO: Pass proper json headers...
        //        if (StringUtils.contains(request.contentType, "application/json")) {
        if (params.ajax) {
            if (card != null) {
                render card as JSON
            }
            else {
                render """{"redirect":"finish"}"""
            }
        }
        else {
            if (card != null){
                [flashCardTest: flashCardTest, card: card]
            }
            else{
              redirect(action: "finish", params: [id: flashCardTest.id])
            }
        }
    }

    def submitAnswer() {
        def flashCardTest = FlashCardTest.findById(params.testId)
        def card = Card.findById(params.cardId)
        flashCardTestService.submitAnswer(flashCardTest, new Answer(card: card, correct: params.boolean("correct")))
        //TODO: Pass proper json headers...
        if (!params.ajax) {
            redirect(action: "next", params: [testId: flashCardTest.id])
        }
        else{
            def nextCard = flashCardTestService.nextCard(flashCardTest)
            if (nextCard == null){
                render new Card(id:-1) as JSON //"""{"redirect":"finish"}"""
            }else{
                render flashCardTestService.nextCard(flashCardTest) as JSON
            }
        }
    }

    def finish() {
        def flashCardTest = FlashCardTest.findById(params.id)
        flashCardTest.end = new Date()
        flashCardTest.save()
        [flashCardTest: flashCardTest]
    }
}

public class RedirectMessage{

    private boolean redirect = true


    boolean getRedirect() {
        return redirect
    }

    void setRedirect(boolean redirect) {
        this.redirect = redirect
    }
}