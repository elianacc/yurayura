var bsnsSideMenuThat = this;

const sidemenuVm = new Vue({
    el: "#bootsidemenu",
    data: {
      sideMenuUrl: {
          menuA: [
              "/business/comic_info",
              "/business/comic_user_data"
          ],
          menuB: [
              "/business/user_info"
          ]
      }
    },
    mounted: function () {
        bsnsSideMenuThat.initSideMenu();
        bsnsSideMenuThat.initMetisMenu();
    }
});