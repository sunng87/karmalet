(function(){

  var karmalet = {};

  karmalet.init = function() {
    // setup login button
    $('#browserid').click(
      function(){
        navigator.id.get(karmalet.gotAssertion);
        return false;
      });

    // setup id field actions
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

    // setup save button
    $('#save-account-btn').click(function(){
      var data = [];
      jQuery.each(services, function(i, e){
        var idfield = '#'+e+'-id';
        var karmafield = '#'+e+'-k';

        if ($(idfield).val().length > 0) {
          var accountinfo = {};
          accountinfo['service'] = e;
          accountinfo['account'] = $(idfield).val();
          accountinfo['karma'] = parseInt($(karmafield).text());

          data.push(accountinfo);
        }
      });

      if(data.length > 0){
        $.ajax({
          type: 'POST',
          url: 'accounts',
          data: {services: JSON.stringify(data)},
          success: function(res, status, xhr){
            $('#save-info').fadeIn(2000, function(){
              $('#save-info').fadeOut();
            });
          }
        });
      }
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

             jQuery.each(res.services, function(i, e){
               var service = e.service;
               var idfield = '#'+service+'-id';
               var karmafield = '#'+service+'-k';
               $(idfield).val(e.account);
               $(karmafield).text(e.karma);
             });

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



