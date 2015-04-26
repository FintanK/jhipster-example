'use strict';

angular.module('cmsApp')
    .controller('MainController', function ($scope, Post, Principal, ParseLinks) {

        $scope.posts = [];

        Post.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.posts = result;
        });

        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    });
