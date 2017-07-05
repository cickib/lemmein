app.controller('DisplayCtrl', function ($http, $scope) {

    var flatList = this;

    $http.get("/results")
        .then(function (response) {
            flatList.flats = response.data.flats;
        })
        .then(function () {
            console.log("ok - display")
        });

    $scope.sort = function (keyname) {
        $scope.sortKey = keyname;
        $scope.reverse = !$scope.reverse;
    }
});