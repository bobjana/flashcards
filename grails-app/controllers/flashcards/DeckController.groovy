package flashcards

import org.springframework.dao.DataIntegrityViolationException

class DeckController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [deckInstanceList: Deck.list(params), deckInstanceTotal: Deck.count()]
    }

    def create() {
        [deckInstance: new Deck(params)]
    }

    def save() {
        def deckInstance = new Deck(params)
        if (!deckInstance.save(flush: true)) {
            render(view: "create", model: [deckInstance: deckInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'deck.label', default: 'Deck'), deckInstance.id])
        redirect(action: "show", id: deckInstance.id)
    }

    def show() {
        def deckInstance = Deck.get(params.id)
        if (!deckInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'deck.label', default: 'Deck'), params.id])
            redirect(action: "list")
            return
        }

        [deckInstance: deckInstance]
    }

    def edit() {
        def deckInstance = Deck.get(params.id)
        if (!deckInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deck.label', default: 'Deck'), params.id])
            redirect(action: "list")
            return
        }

        [deckInstance: deckInstance]
    }

    def update() {
        def deckInstance = Deck.get(params.id)
        if (!deckInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'deck.label', default: 'Deck'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (deckInstance.version > version) {
                deckInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'deck.label', default: 'Deck')] as Object[],
                          "Another user has updated this Deck while you were editing")
                render(view: "edit", model: [deckInstance: deckInstance])
                return
            }
        }

        deckInstance.properties = params

        if (!deckInstance.save(flush: true)) {
            render(view: "edit", model: [deckInstance: deckInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'deck.label', default: 'Deck'), deckInstance.id])
        redirect(action: "show", id: deckInstance.id)
    }

    def delete() {
        def deckInstance = Deck.get(params.id)
        if (!deckInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'deck.label', default: 'Deck'), params.id])
            redirect(action: "list")
            return
        }

        try {
            deckInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'deck.label', default: 'Deck'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'deck.label', default: 'Deck'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
