/**
 * 
 */
(function() {
	
	var app = angular.module('carsales', ['ngRoute']);
	
	app.config(function($routeProvider){
		
		$routeProvider
			.when('/', {
				controller: 'AdvertController',
				templateUrl: '../views/adverts.html'
			});
	});
}());