/**
 * 
 */
(function() {
	
	var AdvertController = function($scope, $http) {
		
		$http.get('/api/ads')
			.then(function (response) {
				$scope.ads = response.data;
			});
		
		$http.get('/api/cars')
		.then(function (response) {
			$scope.vehicles = response.data;
		});
		
		  $scope.searchText = undefined;
		  
		  $scope.advert = new Object();
		  $scope.saveAdvert = function() {
			 $http.post('/api/ads',  $scope.advert)
			 	.then(function() {
			 		$scope.message = "Advert saved successfully";
			 	},
			 	function() {
			 		$scope.error = "Error saving advert";
			 	});
		  }
		  
		  $scope.updateVehicleEditForm = function() {
			  $scope.advert.vehicle = $scope.vehicle;
		  }
		  
		  $scope.clearExistingVehicle = function() {
			  $scope.advert.vehicle = undefined;
		  }
		  
		  $scope.showAdvert = function(advert) {
			  window.location.href = "#/advert/" + advert.id; 
		  }
		 
	};
	
	angular.module('carsales').controller('AdvertController', AdvertController);
}());