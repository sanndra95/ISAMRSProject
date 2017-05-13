(function () {
    'use strict';

    angular
        .module('app')
        .factory('RestaurantService', RestaurantService);

    RestaurantService.$inject = ['$http','$q'];
    function RestaurantService($http, $q) {
        var service = {};

        service.GetAllRests = GetAllRests;
        service.GetById = GetById;
        service.CreateRestaurant = CreateRestaurant;
        return service;

        
        function GetAllRests() {
            //return $http.get('/api/users').then(handleSuccess, handleError('Error getting all users'));
        	return $http.get('/api/restaurants')
            .then(function(response) {
                return response;
            }); 
        }
        
        function GetById(id) {
        	alert("service");
            //return $http.get('/api/users/' + email).then(handleSuccess, handleError('Error getting user by email'));
            return $http.get('/api/restaurants/' + id)
            .then(function(response) {
                return response;
            }); 
        }
        
        function CreateRestaurant(restaurant, email) {
        	console.log("create");
            return $http.post('/api/restaurants/' + email,restaurant)
            .then(function (response) {
            	alert("opet milana");
                return response;
            });               

        }
        function handleSuccessTrue(res) {
            return res;
        }

        function handleSuccess(res) {
            return { success: true, message: error };
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }

})();
