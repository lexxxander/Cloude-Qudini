var endPoint = "../";
var version = '4.0.0';


var app = angular.module('eEB', ['ngRoute','ngCookies','ngFileUpload','ui.grid', 'ui.grid.edit','ui.grid.rowEdit', 'ui.bootstrap',
                                 'ui.grid.cellNav', 'ui.grid.pagination','pascalprecht.translate','smart-table', 'ngAnimate','ngFileUpload']);

String.prototype.zp = function(n) { return '0'.times(n - this.length) + this; }
String.prototype.times = function(n){
 var s = '';
 for (var i = 0; i < n; i++)
  s += this;

 return s;
}

// ajax error handling, interceptor - vincent wong
var responseErrHandler;
app.factory('responseErrHandler', ['$q', '$injector', '$location', '$window', '$rootScope', function($q, $injector, $location, $window, $rootScope) {	
    var responseErrHandler = {
        responseError: function(response) 
        {
        	console.log('re: response.status ' + response.status);
        	console.log('re: go to home');
        	
        	$location.path("/login");
        	
            return $q.reject(response);
        }
    };
    return responseErrHandler;
}]);

var loadingPage = 0;
app.factory('loadingPage', ['$q', '$injector', '$location', '$window', function($q, $injector, $location, $window) {	
	  return {
		    'request': function(config) {
		    	loadingPage += 1;
		      $('#loadingImage').show();
		      return config;
		    },

		   'requestError': function(rejection) {
		    	loadingPage -= 1;
		    	if (loadingPage <= 0) {
		    		$('#loadingImage').hide();
		    	}
		      
		      return $q.reject(rejection);
		    },

		    'response': function(response) {
		    	loadingPage -= 1;
		    	if (loadingPage <= 0) {
		    		$('#loadingImage').hide();
		    	}
		      return response;
		    },

		   'responseError': function(rejection) {
		    	loadingPage -= 1;
		    	if (loadingPage <= 0) {
		    		$('#loadingImage').hide();
		    	}
//		      if (canRecover(rejection)) {
//		        return responseOrNewPromise
//		      }
		      return $q.reject(rejection);
		    }
		  };
}]);

//ajax error handling, interceptor
var appConfig;
app.config(['$httpProvider', function($httpProvider) {  
    $httpProvider.interceptors.push('responseErrHandler');
    //$httpProvider.interceptors.push('timestampMarker'); 
    $httpProvider.interceptors.push('loadingPage');
}]);
// ajax error handling, interceptor



app.config(function ($routeProvider) {
		$routeProvider		
		.when('/home', {
			controller: 'HomeController',
			templateUrl: 'home.html'
		})
		.otherwise({ redirectTo: '/home'});
});
