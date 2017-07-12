app.controller('AboutCtrl', function ($http, $scope) {
    $scope.about = null;
    $http.get('/about')
        .then(function (response) {
            $scope.about = response.data;
        })
        .then(function () {
            console.log('ok - about')
        });
});