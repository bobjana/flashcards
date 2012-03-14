package flashcards

class Deck {

    String name
    String description
    static hasMany = [cards: Card]

    static constraints = {
        name(unique: true)
        description(nullable: true)
    }

    String toString() {
        return name
    }


}
