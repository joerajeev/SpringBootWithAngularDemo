/**
 * 
 */
(function() {
	
	var AdvertController = function($scope, $http) {
		$http.get('/api/cars')
			.then(function (response) {
				$scope.vehicles = response.data;
			});
	};
	
	angular.module('carsales').controller('AdvertController', AdvertController);
}());