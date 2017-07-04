var lemmein = angular.module('lemmein', []);
lemmein.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
}]);

angular.module('lemmein', [])
    .controller('FlatController', function ($http) {
        var flatList = this;

        $http.get("/results")
            .then(function (response) {
                flatList.flats = response.data.flats;
            })
    });