/**
 * 
 */
(function() {
	
	var SingleAdvertController = function($scope, $routeParams, $http) {
		
		$http.get('/api/ads/' + $routeParams.id)
			.then(function (response) {
				console.log("retrived ad "+ response.data);
				$scope.ad = response.data;
			});
		
		 
		 
	};
	
	angular.module('carsales').controller('SingleAdvertController', SingleAdvertController);
}());