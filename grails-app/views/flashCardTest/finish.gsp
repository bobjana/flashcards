<%@ page import="flashcards.Card" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <title>FlashCards</title>
</head>

<body>
<div>
    <div class="fieldcontain">
        <label for="score">
            <g:message code="test.result.score.label" default="Score"/>
        </label>
        ${flashCardTest.result()}%
    </div>
        <div class="fieldcontain">
        <label for="score">
            <g:message code="test.result.answered.label" default="Answered:"/>
        </label>
       ${flashCardTest.numberAnswered}
    </div>

    <div class="fieldcontain">
        <label for="correct">
            <g:message code="test.result.correct.label" default="Correct:"/>
        </label>
        ${flashCardTest.numberCorrect}
    </div>

    <g:form method="post">
        <fieldset class="buttons">
            <g:actionSubmit action="index" value="${message(code: 'test.result.button.done.label', default: 'Done')}"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
