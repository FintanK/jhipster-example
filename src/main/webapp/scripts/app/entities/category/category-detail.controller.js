'use strict';

angular.module('cmsApp')
    .controller('CategoryDetailController', function ($scope, $stateParams, Category) {
        $scope.category = {};
        $scope.load = function (id) {
            Category.get({id: id}, function(result) {
              $scope.category = result;
            });
        };
        $scope.load($stateParams.id);
    });
