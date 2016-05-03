"use strict";

describe("Adverts Controller", function () {

    var $scope, $rootScope, $httpBackend, $controller, $location, $advertFactory;
    var createController, initializeController;
    var ads = [{id: 1,
            	   created: null,
            	   description: "my awesome Civic",
            	   image: null,
            	   modified: null,
            	   vehicle: {
	            	   reg: "REG101",
	            	   colour: null,
	            	   make: "Honda",
	            	   milage: 0,
	            	   model: "Civic",
	            	   year: 2012,
	            	   user: null
	            	   },
            	   price: 20000},
               {id: 38,
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
        		   price: 3000}];
    
    
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

    beforeEach(function () {
        createController = function () {
            return $controller("AdvertsController", {
                "$scope": $scope,
            });
        };
        
        initializeController = function () {
        	$httpBackend.whenGET("/api/ads").respond(function (method, url, data, headers) {
                return [200, ads, {}];
            });

        	var controller = createController();

            $httpBackend.flush();
        }

    });

    it("should load the ads when navigating to the ads page", function () {

    	initializeController();

        expect($scope.ads).toBeDefined();
        expect($scope.ads.length).toBe(2);
        
        expect($scope.ads[0].description).toBe("my awesome Civic");
        expect($scope.ads[0].vehicle.reg).toBe("REG101");
        expect($scope.ads[0].price).toBe(20000);
        
        expect($scope.ads[1].description).toBe("my old red car");
        expect($scope.ads[1].vehicle.reg).toBe("MM100");
        expect($scope.ads[1].price).toBe(3000);
    });
    
    it("should navigate to the advert page when showAdvert() is called", function () {
    	
    	initializeController();

        $scope.showAdvert(ads[1]);
        
        expect($location.path()).toBe('/advert/38');
    });
});