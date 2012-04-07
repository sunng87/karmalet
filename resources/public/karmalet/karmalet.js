(function(){

  var karmalet = {};

  karmalet.init = function() {
    $('#browserid').click(
      function(){
        navigator.id.get(karmalet.gotAssertion);
        return false;
      });
    var services = ['reddit', 'stackoverflow',
                   'github', 'twitter', 'hackernews'];
    jQuery.each(services, function(i, e){
      var idfield = '#'+e+'-id';
      var karmafield = '#'+e+'-k';
      $(idfield).change(function(){
        $(karmafield).addClass('icon-refresh');
        $.ajax({
          type: 'GET',
          url: '/karma',
          data: {service: e, id: $(idfield).val()},
          success: function(res, status, xhr){
            $(karmafield).removeClass('icon-refresh');
            $(karmafield).text(res.karma);
          }
        });
      });
    });
    
  };

  karmalet.gotAssertion = function(assertion){
    if (assertion !== null) {
      $.ajax(
        {type: 'POST',
         url: '/login',
         data: {assertion: assertion},
         success: function(res, status, xhr){
           if (res.success ) {
             $('#browserid').fadeOut(1000, function(){
               $('#user-info').fadeIn();
             });
             karmalet.user = res.id;
             $('#account-settings').show();
             $('#snippet-box').show();
           } else {
             $('#auth-failed').show();
           }
         }
        });
    }
  };
  
  $(document).ready(karmalet.init);
}());



