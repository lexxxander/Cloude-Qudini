
app.config(['$translateProvider', function ($translateProvider) {
  $translateProvider.translations('en', en_translate);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
$translateProvider.translations('id', id_translate);

$translateProvider.preferredLanguage('en');
}]);

app.controller('translateCtrl', function ($rootScope,$scope, $translate, $window) {
	$rootScope.language = "E";
	if ($rootScope.isLocalStorageAvailable()) {
		$window.sessionStorage.language = "E";
	}
	$scope.changeLanguage = function (key) {
		$translate.use(key);
		if (key == "en") {
			$rootScope.language = "E";
		} else if (key == "id") {
			$rootScope.language = "B";
		}
		if ($rootScope.isLocalStorageAvailable()) {
			$window.sessionStorage.language = $rootScope.language;
		}
  };
});

