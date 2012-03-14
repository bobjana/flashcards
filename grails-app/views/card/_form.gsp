<%@ page import="flashcards.Card" %>



<div class="fieldcontain ${hasErrors(bean: cardInstance, field: 'notes', 'error')} ">
	<label for="notes">
		<g:message code="card.notes.label" default="Notes" />
		
	</label>
	<g:textField name="notes" value="${cardInstance?.notes}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardInstance, field: 'answer', 'error')} ">
	<label for="answer">
		<g:message code="card.answer.label" default="Answer" />
		
	</label>
	<g:textField name="answer" value="${cardInstance?.answer}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardInstance, field: 'categories', 'error')} ">
	<label for="categories">
		<g:message code="card.categories.label" default="Categories" />
		
	</label>
	<g:select name="categories" from="${flashcards.Category.list()}" multiple="multiple" optionKey="id" size="5" value="${cardInstance?.categories*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardInstance, field: 'deck', 'error')} required">
	<label for="deck">
		<g:message code="card.deck.label" default="Deck" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="deck" name="deck.id" from="${flashcards.Deck.list()}" optionKey="id" required="" value="${cardInstance?.deck?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cardInstance, field: 'question', 'error')} ">
	<label for="question">
		<g:message code="card.question.label" default="Question" />
		
	</label>
	<g:textField name="question" value="${cardInstance?.question}"/>
</div>

