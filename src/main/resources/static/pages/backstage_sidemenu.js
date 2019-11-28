const sidemenuVm = new Vue({
    el: "#bootsidemenu",
    data: {
      sideMenuUrl: {
          menuA: [
              "/manage/comic_info",
              "/manage/comic_count"
          ],
          menuB: [
              "/manage/user_info"
          ]
      }
    },
    mounted: function () {
        that.initSideMenu();
        that.initMetisMenu();
    }
});