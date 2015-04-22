'use strict';

angular.module('cmsApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


