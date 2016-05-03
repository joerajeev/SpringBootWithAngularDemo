/**
 * Not used.
 * Just here to demonstrate the alternative of using a service instead of a factory
 * 
 */
(function(){
	
	var advertService = function($http){
		
		this.getAds = function(){
			return $http.get('/api/ads');
		};
		
		this.getAd = function(id){
			return $http.get('/api/ads/' + id);
		};
		
		this.save = function(advert) {
			return $http.post('/api/ads', advert);
		 };
	}
	
	
	angular.module('carsales').service('advertService', advertService);
	
})();