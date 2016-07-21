 $(document).ready(function(){
 // 回到顶部代码
  $(window).scroll(function(){
    var st = $(document).scrollTop(),
        t = $('#go-top').offset().top,
        h = $(window).height();
    if( st>t ){
      $('.go-top').fadeIn(function(){
        $(this).addClass('dis');
      });
    }else{
      $('.go-top').fadeOut(function(){
        $(this).removeClass('dis');
      });
    }
  });
  $('.go-top a').click(function(){
    $('html,body').animate({'scrollTop':0},"slow");
  });

  });


