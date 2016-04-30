/**
 * 
 */
(function() {
	
	var SingleAdvertController = function($scope, $routeParams, $http, advertFactory) {
		
		advertFactory.getAd($routeParams.id)
			.then(function (response) {
				$scope.ad = response.data;
			});
		 
	};
	
	angular.module('carsales').controller('SingleAdvertController', SingleAdvertController);
}());