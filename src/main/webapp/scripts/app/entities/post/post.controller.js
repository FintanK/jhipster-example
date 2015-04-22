'use strict';

angular.module('cmsApp')
    .controller('PostController', function ($scope, Post, Author, Category, ParseLinks) {
        $scope.posts = [];
        $scope.authors = Author.query();
        $scope.categorys = Category.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Post.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.posts = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Post.update($scope.post,
                function () {
                    $scope.loadAll();
                    $('#savePostModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Post.get({id: id}, function(result) {
                $scope.post = result;
                $('#savePostModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Post.get({id: id}, function(result) {
                $scope.post = result;
                $('#deletePostConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Post.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePostConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.post = {title: null, content: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
