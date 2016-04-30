(function(){
	
	var advertFactory = function($http){
		
		var factory = {};
		
		factory.getAds = function(){
			return $http.get('/api/ads');
		};
		
		factory.getAd = function(id){
			return $http.get('/api/ads/' + id);
		};
		
		factory.save = function(advert) {
			return $http.post('/api/ads', advert);
		 };
		
		return factory;
	}
	
	
	angular.module('carsales').factory('advertFactory', advertFactory);
	
})();