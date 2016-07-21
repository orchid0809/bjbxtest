/**
 *
 * @authors tuyunkun
 * @date    2016-06-26 11:45:37
 * @version 登录页、注册页、找回密码页 页面交互
 */

/* 页面高度自适应 */
function nrHeight() {
  var login_h,mainH,
      screenH = $(window).height(),
      $topArea_h = $('.top-area').outerHeight(true),
      $logoArea_h = $('.logo-area').outerHeight(true),
      $mainArea = $('.login-area'),
      $mainArea_h =$mainArea.outerHeight(true),
      $bottomArea_h = $('.bottom-area').outerHeight(true),
      $copyright_h = $('.copyright').outerHeight();
  
  mainH = $topArea_h+$logoArea_h+$mainArea_h+$bottomArea_h+$copyright_h;
  
  if(mainH<screenH){
    login_h=screenH-$topArea_h-$logoArea_h-$bottomArea_h-$copyright_h;
    $mainArea.parent().css({
      'height': login_h
    });
  }
  else {
    $mainArea.css({
      'height': 415
    });
  }
}
$(function() {
  nrHeight ();
});
$(window).resize(function() {
 nrHeight ()
});

/* 登录 */
 $(function () {
   wap_val = [];
   $(".textbox input").each(function(i){
     wap_val[i] = $(this).val();
     $(this).focusin(function(){
       if ($(this).val() == wap_val[i]) {
         $(this).val("");
         $(this).css({
           color: '#444'
         });
       }
     }).focusout(function(){
       if ($.trim($(this).val()) == "") {
         $(this).val(wap_val[i]);
         $(this).css({
           color: '#999'
         });
       }
     });
   });
 })