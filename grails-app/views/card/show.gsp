
<%@ page import="flashcards.Card" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'card.label', default: 'Card')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-card" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-card" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list card">
			
				<g:if test="${cardInstance?.notes}">
				<li class="fieldcontain">
					<span id="notes-label" class="property-label"><g:message code="card.notes.label" default="Notes" /></span>
					
						<span class="property-value" aria-labelledby="notes-label"><g:fieldValue bean="${cardInstance}" field="notes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cardInstance?.answer}">
				<li class="fieldcontain">
					<span id="answer-label" class="property-label"><g:message code="card.answer.label" default="Answer" /></span>
					
						<span class="property-value" aria-labelledby="answer-label"><g:fieldValue bean="${cardInstance}" field="answer"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cardInstance?.categories}">
				<li class="fieldcontain">
					<span id="categories-label" class="property-label"><g:message code="card.categories.label" default="Categories" /></span>
					
						<g:each in="${cardInstance.categories}" var="c">
						<span class="property-value" aria-labelledby="categories-label"><g:link controller="category" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${cardInstance?.deck}">
				<li class="fieldcontain">
					<span id="deck-label" class="property-label"><g:message code="card.deck.label" default="Deck" /></span>
					
						<span class="property-value" aria-labelledby="deck-label"><g:link controller="deck" action="show" id="${cardInstance?.deck?.id}">${cardInstance?.deck?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cardInstance?.question}">
				<li class="fieldcontain">
					<span id="question-label" class="property-label"><g:message code="card.question.label" default="Question" /></span>
					
						<span class="property-value" aria-labelledby="question-label"><g:fieldValue bean="${cardInstance}" field="question"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${cardInstance?.id}" />
					<g:link class="edit" action="edit" id="${cardInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
