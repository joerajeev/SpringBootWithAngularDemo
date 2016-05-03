/**
 * Contains the view logic for advert related pages.
 */
(function() {
	
	var AdvertsController = function($scope, $http, $log, $location, advertFactory) {
		
		advertFactory.getAds()
			.success(function (response) {
				$scope.ads = response;
			}) 
			.error(function (data, status){
				$log.log( "Error loading adverts. Status: "+ status);
			});
		
		 $scope.showAdvert = function(advert) {
			 $location.path("/advert/" + advert.id);
		 }
		 
	};
	
	angular.module('carsales').controller('AdvertsController', AdvertsController);
}());