package flashcards

class ScoreCard implements Comparable<ScoreCard>{

    Card card
    int score

    int compareTo(ScoreCard o) {
        return o.score.compareTo(score)
    }

    boolean equals(o) {
        if (this.is(o)) return true;
        if (!(o instanceof ScoreCard)) return false;

        ScoreCard scoreCard = (ScoreCard) o;

        if (card != scoreCard.card) return false;

        return true;
    }

    int hashCode() {
        return (card != null ? card.hashCode() : 0);
    }

    String toString() {
        return card.question + ":" + score
    }

}
