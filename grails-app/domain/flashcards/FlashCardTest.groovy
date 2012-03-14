package flashcards


class FlashCardTest {

    Date start
    Date end
    Integer numberAnswered = 0
    Integer numberCorrect = 0
    ScoreCard lastScoreCard

    static hasMany = [cards: Card, answers: Answer, scoreCards: ScoreCard]
    static transients = ['result','completed']

    static constraints = {
        end(nullable: true)
        lastScoreCard(nullable: true)
    }

    public int result() {
        if (numberAnswered == 0 || numberCorrect == null) {
            return 0
        }
        return java.lang.Math.round(numberCorrect / numberAnswered * 100);
    }

    public boolean completed(){
        return end != null
    }

}
