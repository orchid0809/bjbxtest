/**
 *
 * @authors tuyunkun
 * @date    2016-06-24 09:00:00
 * @version common.js  整合公用的js
 */

$(document).ready(function(){

  /* drop-down 顶部下拉菜单 */
  var $dropDown = $(".drop-down");
  $dropDown.hover(function(){},function(){
    var $menu = $(this).find('.menu');
    var $option = $(this).find('.option');
    $menu.slideUp('450');
    $option.removeClass('hover').removeClass('on');
  });
  $dropDown.find('.option').mouseenter(function(){
    var $menu = $(this).next('.menu');
    if($menu.is(':hidden')){
      $(this).addClass('hover');
    }else{
      $(this).removeClass('hover');
    }
    //判断 浏览器是否是IE9以下 ！+[1,]
    if($menu.is(':hidden')&& !+[1,]){
      $(this).addClass('on');
    }else{
      $(this).removeClass('on');
    }
    $menu.slideToggle('450');
  });

  /* shop-car 购物车 列表 */
  var $shopCar = $(".shop-car");
  $shopCar.hover(function(){},function(){
    var $car = $(this).find('.car');
    var $shopList = $(this).find('.shop-list');
    //$shopList.slideUp('100');
    $shopList.hide();
    $car.removeClass('hover').removeClass('on');
  });
  $shopCar.find('.car').mouseenter(function(){
    var $list = $(this).next('.shop-list');
    if($list.is(':hidden')){
      $(this).addClass('hover');
    }else{
      $(this).removeClass('hover');
    }
    //判断 浏览器是否是IE9以下 ！+[1,]
    if($list.is(':hidden')&& !+[1,]){
      $(this).addClass('on');
    }else{
      $(this).removeClass('on');
    }
    $list.show();
  // $list.slideToggle('100');
  });

}); // end ready