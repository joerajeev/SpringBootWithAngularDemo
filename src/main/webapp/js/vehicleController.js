/**
 * 
 */
(function() {
	
	var VehicleController = function($scope, $http) {
		
		$http.get('/api/cars')
			.then(function (response) {
				$scope.vehicles = response.data;
			});
		
		  $scope.searchText = undefined;
		  
		  $scope.updateVehicleEditForm = function() {
			  $scope.advert.vehicle = $scope.vehicle;
		  }
		  
		  $scope.clearExistingVehicle = function() {
			  $scope.advert.vehicle = undefined;
		  }
		 
	};
	
	angular.module('carsales').controller('Vehicle', VehicleController);
}());