"use strict"

describe("Adverts factory", function(){
	
	var $http, $httpBackend, $advertFactory;
	
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
	 
	beforeEach(function(){
		module("carsales");
	});
	
	beforeEach(inject(function($injector){
		$http = $injector.get("$http");
		$httpBackend = $injector.get("$httpBackend");
		$advertFactory = $injector.get("advertFactory");
	}));
	
	it("should get all ads", function(){
		
		$httpBackend.whenGET("/api/ads").respond(function (method, url, data, headers) {
            return [200, ads, {}];
        });

		$advertFactory.getAds().then(function(response){
	        
	        expect(response.data).toBeDefined();
	        expect(response.data.length).toBe(2);
	        
	        expect(response.data[0].description).toBe("my awesome Civic");
	        expect(response.data[0].vehicle.reg).toBe("REG101");
	        expect(response.data[0].price).toBe(20000);
	        
	        expect(response.data[1].description).toBe("my old red car");
	        expect(response.data[1].vehicle.reg).toBe("MM100");
	        expect(response.data[1].price).toBe(3000);

			
		});
		
		$httpBackend.flush();

        
	});
});