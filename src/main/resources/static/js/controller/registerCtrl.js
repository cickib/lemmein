app.controller('RegisterCtrl', function ($scope, $http) {
    $scope.newUser = {};

    $scope.register = function (isValid) {
        if (isValid) {
            $http({
                method: 'POST',
                url: '/register',
                headers: {'Content-Type': 'application/json; charset=UTF-8'},
                data: JSON.stringify($scope.newUser)
            })
                .then(function () {
                    console.log('ok - register')
                })
        }
    };

    $scope.match = function () {
        if ($scope.password != $scope.passwordAgain) {
            $scope.isMatching = true;
            return false;
        }
        $scope.isMatching = false;
    }

});
