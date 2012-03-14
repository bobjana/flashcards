package flashcards

class Card {

    String question
    String answer
    String notes

    static hasMany = [categories: Category]
    static belongsTo = [deck: Deck]

    static constraints = {
        notes(nullable: true)
    }


    public String toString() {
        return "Card{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
