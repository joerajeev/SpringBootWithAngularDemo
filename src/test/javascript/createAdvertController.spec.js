"use strict";

describe("Create Advert Controller", function(){
	
	 var $scope, $rootScope, $httpBackend, $controller, $location, $advertFactory;
	 var createTheCreateAdvertController, initializeController;
	 
	 var cars = [
			 {"reg":"TT","colour":null,"make":"audi","milage":0,"model":"TT","year":2012,"user":null},
			 {"reg":"UBM123","colour":null,"make":"Honda","milage":0,"model":"Civic","year":2001,"user":null},
	 ];
	 beforeEach(function () {
	        module("carsales");
	 });

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get("$rootScope");
        $scope = $rootScope.$new();
        $httpBackend = $injector.get("$httpBackend");
        $controller = $injector.get("$controller");
        $advertFactory =  $injector.get("advertFactory");
        $location = $injector.get("$location");
    }));
    
    beforeEach(function(){
    	createTheCreateAdvertController = function(){
    		return $controller("CreateAdvertController", {
    			"$scope" : $scope
    		});
    	}
    	
    	initializeController = function(){
    		$httpBackend.whenGET("/api/cars").respond(function(){
    			return [200, cars, {}];
    		});
    		
    		createTheCreateAdvertController();
    		
    		$httpBackend.flush();
    	}
    });
    
    it("should load the vehicles to be used in search for exsting vehicle", function(){
    	
    	initializeController();
    	
    	expect($scope.vehicles).toBeDefined();
    });
});