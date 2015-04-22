'use strict';

angular.module('cmsApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
