'use strict';

var lemmein = angular.module('lemmein', ['ngResource', 'gm']);
lemmein.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
}]);

lemmein.controller('searchController', function ($scope, $http) {
    $scope.param = {};

    $scope.search = function () {
        console.log($scope.param)
        $http({
            method: 'POST',
            url: '/search',
            headers: {'Content-Type': 'application/json; charset=UTF-8'},
            data: JSON.stringify($scope.param)
        })
            .then(function (response) {
            })
    };

});
