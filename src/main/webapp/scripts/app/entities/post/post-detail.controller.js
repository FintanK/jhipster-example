'use strict';

angular.module('cmsApp')
    .controller('PostDetailController', function ($scope, $stateParams, Post, Author, Category) {
        $scope.post = {};
        $scope.load = function (id) {
            Post.get({id: id}, function(result) {
              $scope.post = result;
            });
        };
        $scope.load($stateParams.id);
    });
