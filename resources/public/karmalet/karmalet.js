function init(){
    $('#browserid').click(
        function(){
            navigator.id.get(gotAssertion);
            return false;
        });
};

function gotAssertion(assertion){
    if (assertion !== null) {
        $.ajax(
            {type: 'POST',
             url: '/login',
             data: {assertion: assertion},
             success: function(res, status, xhr){
                 console.log(res);
             }
            });
    }
};

$(document).ready(init);
