/**
 * 
 */
(function() {
	
	var AdvertController = function($scope) {
		$scope.name = "Rajeev";
	}
	
	angular.module('carsales').controller('AdvertController', AdvertController);
}());