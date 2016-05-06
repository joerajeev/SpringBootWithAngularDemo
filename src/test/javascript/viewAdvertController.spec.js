"use strict"

describe("View Advert Controller", function() {
	
	var $rootScope, $controller, $scope, $routeParams, $advertFactory, $q;
	var createController;
	
	var advert = {id: 38,
 		   created: null,
		   description: "my old red car",
		   image: null,
		   modified: null,
    		   vehicle: {
    		   reg: "MM100",
    		   colour: "red",
    		   make: "holden",
    		   milage: 300000,
    		   model: "nova",
    		   year: 1980,
    		   user: null
    		   },
		   price: 3000};
	
	beforeEach(function() {
		module("carsales");
	});
	
	beforeEach(inject(function($injector){
		$rootScope = $injector.get('$rootScope');
		$scope = $rootScope.$new();
		$controller = $injector.get('$controller');
		$routeParams = $injector.get('$routeParams');
		$advertFactory = $injector.get('advertFactory');
		$q = $injector.get('$q');
	}));
	
	beforeEach(function() {
		createController = function() {
			return $controller('ViewAdvertController', {
				'$scope' : $scope
			});
		};
	});
	
	it("should retrieve a specified advert", function() {
		
		$routeParams.id = 38;
		var deferred = $q.defer();
		spyOn($advertFactory, 'getAd').and.returnValue(deferred.promise);

		$controller('ViewAdvertController', {
			'$scope' : $scope,
			'$routeParams' : $routeParams,
			'advertFactory' : $advertFactory
		});
		
		//Resolving the promise
		deferred.resolve({ data: advert });
		
		//Simulating the async call has not completed yet
		expect($scope.ad).toBe(undefined);
		
		// Propagate promise resolution to 'then' functions
		$rootScope.$apply();
		expect($scope.ad).toBe(advert);
		
		
	});
});