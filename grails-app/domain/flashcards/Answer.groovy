package flashcards

class Answer {

    Card card
    boolean correct

    static constraints = {
    }

    String toString() {
        return card.question + " - " + correct
    }


}
