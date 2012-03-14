package flashcards

class FlashCardTestService {

    def FlashCardTest generate(int numberOfCards, Set<Category> cats) {
        List<Card> cards = getAllPossibleCards(cats)
        Random random = new Random(new Date().getTime())
        FlashCardTest flashCardTest = new FlashCardTest(start: new Date())
        def pickedCards = new HashSet()
        if (numberOfCards >= cards.size()) {
            pickedCards.addAll(cards)
        }
        else {
            while (pickedCards.size() < numberOfCards) {
                def index = random.nextInt(cards.size())
                pickedCards.add(cards.get(index))
            }
        }
        flashCardTest.cards = pickedCards
        flashCardTest.answers = new ArrayList()
        initScoreCards(flashCardTest)
        flashCardTest.save(true)
        return flashCardTest
    }

    //N = number of cards
    //M = score of the highest card
    //c = random (1 - N)
    //x = random (1 - M)
    //
    //if (x <= (score of card-nr: c)) accept card!
    //else create new c & x and goto if-querry
    public Card nextCard(FlashCardTest flashCardTest) {
        def scoreCards = new ArrayList(flashCardTest.scoreCards)
        def N = scoreCards.size()
        if (N == 0) {
            return null
        }
        def M = getHighestScore(scoreCards)
        def x = 0
        def c = 0
        ScoreCard wrkScoreCard

        def random = new Random(new Date().getTime())
        while (true) {
            c = random.nextInt(N)
            x = random.nextInt(M)
            wrkScoreCard = scoreCards.get(c)
            if (x <= wrkScoreCard.score) {
                if (N == 1 || flashCardTest.lastScoreCard == null) {
                    break;
                }
                if (!wrkScoreCard.equals(flashCardTest.lastScoreCard)) {
                    break
                }
            }
        }
        flashCardTest.lastScoreCard = wrkScoreCard
        return wrkScoreCard.card
    }


    def FlashCardTest submitAnswer(FlashCardTest test, Answer answer) {
        test.answers.add(answer)

        List<ScoreCard> scoreCards = new ArrayList(test.scoreCards)
        def scoreCard = findScoreCardInList(scoreCards, answer.card)
        if (answer.correct) {
            scoreCards.remove(scoreCard)
        } else {
            if (scoreCard == null) {
                scoreCards.add(new ScoreCard(card: answer.card, score: 1))
            }
            else {
                scoreCard.score = scoreCard.score + 1
            }
        }
        test.scoreCards = new HashSet(scoreCards)
        test.numberAnswered++
        test.numberCorrect = test.numberCorrect + (answer.correct ? 1 : 0)
        test.save()
        return test
    }

    private void initScoreCards(FlashCardTest flashCardTest) {
        def potentialCards = flashCardTest.cards
        def scoreCards = new ArrayList<ScoreCard>()
        //Remove all correctly answered cards from potential list of cards
        flashCardTest.answers.each {
            def workCard = it.card
            if (potentialCards.contains(workCard) && it.correct) {
                potentialCards.remove(workCard)
            }
        }
        //Create initial score for all cards still left
        potentialCards.each {
            scoreCards.add(new ScoreCard(card: it, score: 1))
        }
        //Increment score for each card that failed previously
        flashCardTest.answers.each {
            if (!it.correct) {
                def scoreCard = findScoreCardInList(scoreCards, it.card)
                scoreCard.score = scoreCard.score + 1
            }
        }
        flashCardTest.scoreCards = new HashSet(scoreCards)
        flashCardTest.save()
    }

    private int getHighestScore(def scoreCards) {
        if (scoreCards == null | scoreCards.size() == 0) {
            return 0
        }
        List<ScoreCard> wrkCards = new ArrayList(scoreCards)
        Collections.sort(wrkCards)
        return wrkCards.get(0).score
    }

    def findScoreCardInList(List<ScoreCard> scoreCards, Card card) {
        for (ScoreCard scoreCard: scoreCards) {
            if (card.equals(scoreCard.card)) {
                return scoreCard
            }
        }
        return null
    }

    private List<Card> getAllPossibleCards(Set<Category> cats) {
        def cards = new ArrayList()
        //TODO: Fix hql for specific categories selected
        for (String catId: cats) {
            if (Category.ALL.id == new Integer(catId)) {
                return Card.list()
            }
            else {
                def cat = Category.findById(new Integer(catId))
                def tempCards = Card.executeQuery('from Card c where :category in elements(c.categories)', [category: cat])
                cards.addAll(tempCards)
            }
        }
        return cards
    }


}