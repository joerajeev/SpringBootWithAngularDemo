/**
 * Contains the view logic for creating a new advert.
 */
(function() {
	
	var NewAdvertController = function($scope, $http, $log, advertFactory) {
		
		$scope.searchText = undefined;
		$scope.advert = new Object();
		  
		 $http.get('/api/cars')
			.success(function (response) {
				$scope.vehicles = response;
		    })
		    .error(function (data, status){
		    	$log.log( "Error loading cars. Status: "+ status);
		    });
		
		
		  $scope.saveAdvert = function() {
			  advertFactory.save($scope.advert)
			 	.success(function() {
			 		$scope.message = "Advert saved successfully";
			 	})
			 	.error(function() {
			 		$scope.error = "Error saving advert";
			 	});
		  };
		  
		  $scope.updateVehicleEditForm = function() {
			  $scope.advert.vehicle = $scope.vehicle;
		  }
		  
		  $scope.clearExistingVehicle = function() {
			  $scope.advert.vehicle = undefined;
		  }
		 
	};
	
	angular.module('carsales').controller('NewAdvertController', NewAdvertController);
}());