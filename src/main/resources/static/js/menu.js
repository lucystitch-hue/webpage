
$(document).ready(function() {
  var keyword = 'user';
  var keywordNotification = 'notification-template';
  var keywordNotificationManagement= 'notification-management';
  var keywordTemporaryUser= 'temporaryUser';
  var keywordDeleteAllUser= 'deleteAllUser';
  var keywordUrgentNotice= 'urgent-notice';
  var keyworkSystemConstants = 'system-constants';
  var keyworkFeaturedRacesOverseas = 'featured-races-overseas';
  var path = window.location.pathname;
  if (path.indexOf(keyworkFeaturedRacesOverseas) !== -1) {
      $('.nav.flex-column li a[href*="' + keyworkFeaturedRacesOverseas + '"]').addClass('active');
    }
  if (path.indexOf(keyworkSystemConstants) !== -1) {
      $('.nav.flex-column li a[href*="' + keyworkSystemConstants + '"]').addClass('active');
    }
  if (path.indexOf(keywordTemporaryUser) !== -1) {
      $('.nav.flex-column li a[href*="' + keywordTemporaryUser + '"]').addClass('active');
    }
  if (path.indexOf(keywordDeleteAllUser) !== -1) {
      $('.nav.flex-column li a[href*="' + keywordTemporaryUser + '"]').addClass('active');
    }
  if (path.indexOf(keyword) !== -1) {
    $('.nav.flex-column li a[href*="' + keyword + '"]').addClass('active');
  }
  if (path.indexOf(keywordNotificationManagement) !== -1) {
      $('.nav.flex-column li a[href*="' + keywordNotificationManagement + '"]').addClass('active');
    }
  if (path.indexOf(keywordUrgentNotice) !== -1) {
      $('.nav.flex-column li a[href*="' + keywordUrgentNotice + '"]').addClass('active');
    }
  if (path.indexOf(keywordNotification) !== -1) {
      $('.nav.flex-column li a[href*="' + keywordNotification + '"]').addClass('active');
    }else{
  $('.nav.flex-column li a[href="'+path+'"]').addClass('active');
  }
});