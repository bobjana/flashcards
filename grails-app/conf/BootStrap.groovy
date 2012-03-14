import flashcards.Deck
import flashcards.Card
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

class BootStrap {

    def cardImporterService

    def init = { servletContext ->
        if (Card.count() == 0){
            def inputStream = servletContext.getResourceAsStream("tswana.csv")
            File f = File.createTempFile("tswana",".csv")
            FileWriter fileWriter = new FileWriter(f)
            IOUtils.copy(inputStream, fileWriter)

            fileWriter.flush()
            fileWriter.close()
            IOUtils.closeQuietly(inputStream)

            new Deck(name: "Tswana", description: "Tswana 101").save(validate: true)
            cardImporterService.importCards(f)
        }
    }

    def destroy = {
    }
}
