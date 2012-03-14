package flashcards

import au.com.bytecode.opencsv.CSVReader
import org.apache.commons.lang.StringUtils

class CardImporterService {

    def importCards(File csvFile) {
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        def lines = reader.readAll();
        def defaultDeck = Deck.findById(1)
        boolean firstLine = true
        lines.each {
            if (firstLine) {
                firstLine = false
            }
            else {
                def values = (String[]) it
                def cardCategories = new ArrayList()
                final String question = values[0]
                final String answer = values[1]

                def categoryList = StringUtils.split(values[2], ",")
                categoryList.each {
                    def catName = it.trim()
                    Category workCategory = Category.findByName(catName)
                    if (workCategory == null) {
                        workCategory = new Category(name: catName).save()
                    }
                    cardCategories.add(workCategory)
                }
                new Card(question: question, answer: answer, categories: cardCategories, deck: defaultDeck).save(validate: true)
            }
        }
    }
}
