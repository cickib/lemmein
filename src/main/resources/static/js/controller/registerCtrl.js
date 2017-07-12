app.controller('RegisterCtrl', function ($scope, $http, $window) {
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
                    $window.location.href = '/#/login';
                })
        }
    };

    $scope.match = function () {
        if ($scope.newUser.password != $scope.newUser.passwordAgain) {
            $scope.isMatching = false;
            return false;
        }
        $scope.isMatching = true;
    }

});
