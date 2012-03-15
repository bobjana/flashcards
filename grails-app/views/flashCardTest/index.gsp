<%@ page import="flashcards.Card" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'card.label', default: 'Card')}"/>
    <title>FlashCards</title>
</head>

<body>
<div class="content">

    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${testInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${testInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form method="post">
        <fieldset class="form">
            <div class="fieldcontain ${hasErrors(bean: testInstance, field: 'numberOfCards', 'error')} ">
                <label for="numberOfCards">
                    <g:message code="test.numberOfQuestions.label" default="Questions:"/>
                </label>
                <g:select id="numberCards" name='numberOfCards' value="${numberOfCards}" from='${numberOfCardsList}'/>
            </div>

            <div class="fieldcontain ${hasErrors(bean: testInstance, field: 'categories', 'error')} ">
                <label for="categories">
                    <g:message code="test.numberOfQuestions.label" default="Categories:"/>
                </label>
                <g:select id="categories" name='categories' optionValue="name" optionKey="id" multiple="true"  style="height:100px;"
                          from='${categoriesList}'/>
            </div>
        </fieldset>
        <fieldset class="buttons">
            <g:actionSubmit class="save" action="start" value="${message(code: 'test.button.start.label', default: 'Start')}"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
