(function () {
    angular.module('qudini.QueueApp', [])
        .controller('QueueCtrl', QueueCtrl).directive('addCustomer', AddCustomer).directive('customer', Customer)

    /**
     * Bonus points - manipulating the without waiting for the
     * server request
     */
    function QueueCtrl($scope, $http) {		
	    $scope.customers = [];
        $scope.customersServed = [];
        _getCustomers();
        _getServedCustomers();

		$scope.changedValue = function(item) {
			$scope.selectedProduct.push(item.name);
			console.log('selected item='+item.name);
		} 
  
        $scope.onCustomerAdded = function(){
            _getCustomers();
        }

        $scope.onCustomerRemoved = function(){
            _getCustomers();
        }

        $scope.onCustomerServed = function(){
            _getCustomers();
            _getServedCustomers()
        }

        function _getServedCustomers(){
            return $http.get('/api/customers/served').then(function(res){
                $scope.customersServed = res.data;
            })
        }

        function _getCustomers(){
            return $http.get('/api/customers').then(function(res){
                $scope.customers = res.data;
            })
        }
    }
	
	function AddCustomer($http){
        return {
            restrict: 'E',
            scope:{
                onAdded: '&',			
				customer:'='				
            },
            templateUrl:'/add-customer/add-customer.html',
            link: function(scope){
                scope.products = [
                    {name: 'Grammatical advices'},
                    {name: 'Magnifying glass repairs'},
                    {name: 'Cryptography advices'}
                ];
				
				scope.add = function(){
					var cust = {name: scope.name, product: {name:scope.product.name}, id:null, joinedTime: new Date().toString()};
                    $http({
                        method: 'POST',
                        url: '/api/customer/add',
                        data: cust,
						headers: {'Content-Type': 'application/json'}
                    }).then(function(res){
						console.log('res='+res);
                        scope.onAdded()()
                    })
                };
            }
        }
    }

	function Customer($http){

        return{
            restrict: 'E',
            scope:{
                customer: '=',
                onRemoved: '&',
                onServed: '&'
            },
            templateUrl: '/customer/customer.html',
            link: function(scope,element,attrs){
                // calculate how long the customer has queued for
                scope.queuedTime = new Date() - new Date(scope.customer.joinedTime);

                scope.remove = function(){
                    $http({
                        method: 'DELETE',
                        url: '/api/customer/remove',
                        params: {id: scope.customer.id}
                    }).then(function(res){
                        scope.onRemoved()()
                    })
                };
				scope.serve = function(){
                    $http({
                        method: 'POST',
                        url: '/api/customer/serve',
                        params: {id: scope.customer.id}
                    }).then(function(res){
                        scope.onServed()()
                    })
                };
            }
        }
    }

})()

