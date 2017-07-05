var app = angular.module('BpFF', ['ngRoute']);

app.config(function ($routeProvider) {
    $routeProvider

        .when('/', {
            templateUrl: 'views/main.html',
            controller: 'MainCtrl'
        })
        .when('/login', {
            templateUrl: 'views/login.html'
        })
        .when('/about', {
            templateUrl: 'views/about.html',
            controller: 'AboutCtrl'
        })
        .when('/search', {
            templateUrl: 'views/search_bar.html',
            controller: 'SearchCtrl'
        })
        .when('/display', {
            templateUrl: 'views/display_flats.html',
            controller: 'DisplayCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });
});
