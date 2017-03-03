'use strict';
console.log(" start of UserController...")
app
		.controller(
				'UserController',
				[
						'$scope',
						'UserService',
						'$location',
						'$rootScope',
						'$cookieStore',
						'$http',
						function($scope, UserService, $location, $rootScope, $cookieStore, $http) {
							console.log("UserController...")
							var self = this;
							self.ChatbizUser = {
								userid : '',
								username : '',
								password : '',
								contact : '',
								address : '',
								email : '',
								is_online : '',
								role : '',
								errorcode : '',
								errormessage : ''
							};
							self.users = [];

							self.fetchAllUsers = function() {
								console.log("fetchAllUsers...")
								UserService
										.fetchAllUsers()
										.then(
												function(d) {
													self.users = d;
													alert("Registered Successfully")
													$location.path('/login')
												},
												function(errResponse) {
													console
															.error('Error while fetching Users');
												});
							};
							
							//self.fatchAllUsers();

							self.createUser = function(ChatbizUser) {
								console.log("createUser...")
								UserService
										.createUser(ChatbizUser)
										.then(
											
												self.fetchAllUsers,
												
												function(errResponse) {
													console
															.error('Error while creating ChatbizUser.');
												});
							};
							
							self.myProfile = function() {
								console.log("myProfile...")
								UserService
										.myProfile()
										.then(
												function(d) {
													self.ChatbizUser = d;
													$location.path("/myProfile")
												},
												function(errResponse) {
													console
															.error('Error while fetch profile.');
												});
							};

							self.updateUser = function(ChatbizUser, userid) {
								console.log("updateUser...")
								UserService
										.updateUser(ChatbizUser, userid)
										.then(
												self.fetchAllUsers,
												function(errResponse) {
													console
															.error('Error while updating User.');
												});
							};

							self.authenticate = function(ChatbizUser) {
								console.log("authenticate...")
								UserService
										.authenticate(ChatbizUser)
										.then(

												function(d) {

													self.ChatbizUser = d;
													console
															.log("ChatbizUser.errorcode: "
																	+ self.ChatbizUser.errorcode)
																	
													if (self.ChatbizUser.errorcode =="404")

													{
														alert("Invalid user..Enter correct userid and password")

														self.ChatbizUser.userid = "";
														self.ChatbizUser.password = "";
													

													} else {
														console
																.log("Valid credentials. Navigating to home page")
																$rootScope.currentUser = self.ChatbizUser
																$http.defaults.headers.common['Authorization'] = 'Basic '
																	+ $rootScope.currentUser;
															$cookieStore
																	.put(
																			'currentUser',
																			$rootScope.currentUser);
															$location.path('/');

													}

												},
												function(errResponse) {

													console
															.error('Error while authenticate Users');
												});
							};

							self.logout = function() {
								console.log("logout")
								$rootScope.currentUser = {};
								$cookieStore.remove('currentUser');
								UserService.logout()
								$location.path('/');

							}

							self.deleteUser = function(userid) {
								console.log("UserController...")
								UserService
										.deleteUser(userid)
										.then(
												self.fetchAllUsers,
												function(errResponse) {
													console
															.error('Error while deleting User.');
												});
							};

							// self.fetchAllUsers();

							self.login = function() {
								{
									console.log('login validation????????',
											self.ChatbizUser);
									self.authenticate(self.ChatbizUser);
								}

							};

							self.submit = function() {
								{
									console.log('Saving New User', self.ChatbizUser);
									self.createUser(self.ChatbizUser);
								}
								self.reset();
							};

							self.reset = function() {
								self.ChatbizUser = {
									userid : '',
									username : '',
									password : '',
									contact : '',
									address : '',
									email : '',
									isonline : '',
									errorcode : '',
									errormessage : ''
								};
								$scope.myForm.$setPristine(); // reset Form
							};

						} ]);