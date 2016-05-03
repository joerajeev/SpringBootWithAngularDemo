/**
 * 
 */
(function() {
	
	var app = angular.module('carsales', ['ngRoute', 'ui.bootstrap']);
	
	app.config(function($routeProvider){
		
		$routeProvider
			.when('/', {
				controller: 'AdvertsController',
				templateUrl: '../views/adverts.html'
			})
			.when('/new-advert', {
				controller: 'CreateAdvertController',
				templateUrl: '../views/new-advert.html'
			})
			.when('/advert/:id', {
				controller: 'ViewAdvertController',
				templateUrl: '../views/advert.html'
			})
			.otherwise({ redirectTo : '/'});
	});
}());