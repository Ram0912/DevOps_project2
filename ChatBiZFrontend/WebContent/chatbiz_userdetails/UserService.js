'use strict';
 
app.factory('UserService', ['$http', '$q','$rootScope', function($http, $q,$rootScope){
	
	console.log("UserService...")
	
	var BASE_URL='http://localhost:8001/ChatBiZBackend'
		
    return {
         
            fetchAllUsers: function() {
            	console.log("calling fetchAllUsers ")
                    return $http.get(BASE_URL+'/users')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching ChatbizUser');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
            
            myProfile: function() {
            	console.log("calling fetchAllUsers ")
                    return $http.get(BASE_URL+'/myProfile')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching profile');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            createUser: function(ChatbizUser){
            	console.log("calling create ChatbizUser")
                    return $http.post(BASE_URL+'/createusers/', ChatbizUser)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while creating user');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
            updateUser: function(ChatbizUser, userid){
            	console.log("calling fetchAllUsers ")
                    return $http.put(BASE_URL+'/user/', ChatbizUser)
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while updating user');
                                        return $q.reject(errResponse);
                                    }
                            );
            },
             
              
            logout: function(){
            	console.log('logout....')
                return $http.get(BASE_URL+'/user/logout')
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                              null
                        );
        },
        
        
            
            authenticate: function(ChatbizUser){
            	   console.log("Calling the method authenticate with the user :" +ChatbizUser)
          		 
                return $http.post(BASE_URL+'/user/authenticate',ChatbizUser)
                        .then(
                                function(response){
                                    return response.data;
                                }, 
                               null
                        );
        }
         
    };
 
}]);