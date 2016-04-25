/**
 * 
 */
(function() {
	
	var AdvertController = function($scope, $http) {
		
		$http.get('/api/cars')
			.then(function (response) {
				$scope.vehicles = response.data;
			});
		
		  $scope.searchText = undefined;
		  $scope.advert = new Object();
		  $scope.saveAdvert = function() {
			  console.log("advert: " + $scope.advert);
			 $http.post('/api/ads',  $scope.advert)
			 	.then(function() {
			 		console.log("saved advert");
			 	},
			 	function() {
			 		console.log("error saving advert");
			 	});
		  }
		  
		  $scope.updateVehicleEditForm = function() {
			  $scope.advert.vehicle = $scope.vehicle;
		  }
		  
		  $scope.clearExistingVehicle = function() {
			  $scope.advert.vehicle = undefined;
		  }
		 
	};
	
	angular.module('carsales').controller('AdvertController', AdvertController);
}());