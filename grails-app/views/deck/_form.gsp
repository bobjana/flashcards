<%@ page import="flashcards.Deck" %>



<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="deck.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${deckInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="deck.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${deckInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: deckInstance, field: 'cards', 'error')} ">
	<label for="cards">
		<g:message code="deck.cards.label" default="Cards" />
		
	</label>
	<g:select name="cards" from="${flashcards.Card.list()}" multiple="multiple" optionKey="id" size="5" value="${deckInstance?.cards*.id}" class="many-to-many"/>
</div>

