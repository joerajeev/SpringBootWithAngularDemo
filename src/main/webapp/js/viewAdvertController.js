/**
 * 
 */
(function() {
	
	var ViewAdvertController = function($scope, $routeParams, $http, advertFactory) {
		
		advertFactory.getAd($routeParams.id)
			.then(function (response) {
				$scope.ad = response.data;
			});
		 
	};
	
	angular.module('carsales').controller('ViewAdvertController', ViewAdvertController);
}());