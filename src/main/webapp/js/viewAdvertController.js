/**
 * Controller with the view logic for the view advert page.
 */
(function() {
	
	var ViewAdvertController = function($scope, $routeParams, advertFactory) {
		
		advertFactory.getAd($routeParams.id)
			.then(function (response) {
				$scope.ad = response.data;
			});
		 
	};
	
	angular.module('carsales').controller('ViewAdvertController', ViewAdvertController);
}());