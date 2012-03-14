<%@ page import="flashcards.Card" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'card.label', default: 'Card')}"/>
    <title>FlashCards</title>

</head>

<body>

<div id="cardDiv" style="min-height: 220px; position: relative;">
    %{--<div id="busy" class="spinner" style="position: absolute; bottom: 50%; right: 50%; display: none"/>--}%
    <div id="questionDiv">
        <h2 id="question">${card.question}</h2>
    </div>

    <div id="answerDiv">
        <h1 id="answer">${card.answer}</h1>
    </div>

    <form id="myForm" method="post" action="submitAnswer" controller="flashCardTest">
        <g:hiddenField name="testId" value="${flashCardTest.id}"/>
        <g:hiddenField name="cardId" value="${card.id}"/>
        <g:hiddenField name="correct" id="correct"/>

        <div id="submissionDiv" style="position: absolute; bottom: 0; right:20px; width: 150px;">
            <div id="correctDiv" style="float:left;"><img class="submitBtns"
                                                          src="${resource(dir: 'images', file: 'green_tick.png')}"/>
            </div>

            <div id="failedDiv" style="float:right;"><img class="submitBtns"
                                                          src="${resource(dir: 'images', file: 'red_tick.png')}"/></div>
        </div>
    </form>

</div>


<script>
    var answerShown = false;

    $(document).ready(function() {
        $("#answerDiv").hide();
        $("#submissionDiv").hide();
    });

    $('#cardDiv').click(function() {
        if (!answerShown) {
            $("#answerDiv").show();
            $("#submissionDiv").show();
            answerShown = true;
        }
    });

    $('#correctDiv').click(function() {
        $("#correct").val("true");
        submitAnswerAndGetNext();
    });

    $('#failedDiv').click(function() {
        $("#correct").val("false");
        submitAnswerAndGetNext();
    });

    function submitAnswerAndGetNext() {
        $("#questionDiv").hide();
        $("#answerDiv").hide();
        $("#submissionDiv").hide();
//        $("#busy").css('display', 'block');
        $("#spinner").css('display', 'block');

        event.preventDefault();

        var $form = $("#myForm")
        var url = $form.attr('action')

        var $testId = $form.find('input[name="testId"]').val();
        var $cardId = $form.find('input[name="cardId"]').val();
        var $correct = $form.find('input[name="correct"]').val();


        //submitAnswer
        $.get(url, { ajax:true, testId:$testId, cardId:$cardId, correct:$correct},
                function(data) {
                    $.get("next", {ajax:true, testId:$testId},
                            function(data) {
                                if (data.redirect) {
                                    window.location.href = data.redirect + "?id=" + $testId;
                                }
                                else {
                                    $("#question").html(data.question);
                                    $("#answer").html(data.answer);
                                    $("#cardId").val(data.id);

                                    $("#questionDiv").show()
                                    answerShown = false;
                                    $("#spinner").css('display', 'none');
                                }
                            }
                            , "json");
                }
        );


    }


</script>

</body>
</html>
