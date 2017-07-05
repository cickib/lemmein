app.controller('SearchCtrl', function ($scope, $http) {
    $scope.params = {};

    $scope.search = function () {
        console.log($scope.params)
        $http({
            method: 'POST',
            url: '/search',
            headers: {'Content-Type': 'application/json; charset=UTF-8'},
            data: JSON.stringify($scope.params)
        })
            .then(function () {
                console.log("ok - search")
            })
    };

});
