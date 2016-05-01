/**
 * Contains the view logic for advert related pages.
 */
(function() {
	
	var AdvertsController = function($scope, $http, $log, advertFactory) {
		
		advertFactory.getAds()
			.success(function (response) {
				$scope.ads = response;
			}) 
			.error(function (data, status){
				$log.log( "Error loading adverts. Status: "+ status);
			});
		
		  $scope.showAdvert = function(advert) {
			  window.location.href = "#/advert/" + advert.id; 
		  }
		 
	};
	
	angular.module('carsales').controller('AdvertsController', AdvertsController);
}());