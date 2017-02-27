var HomeController;
app.controller('HomeController', function ($http,$rootScope,$scope,$filter,$location, Upload, $q,$timeout,$window) {
		
	$scope.$on("$destroy",function() {
	    $( window ).off( "resize.Viewport" );
	 });  
	
	$scope.filenames=[];
	$scope.fileids=[];
	 $scope.files=[];
	 $scope.postedpic=null;
	$scope.postedPicFName=null;
	 $scope.imgUploadNominee = null;
	 $scope.imgProfile = null;
	 
	 $scope.init = function() {
		
	};
	
	//CALLED:
	$scope.uploadFiles = function(files, errFiles) {
       angular.forEach(files, function(file) {  			
           file.upload = Upload.upload({
           	url: endPoint + "attachment/upload",
           	data: {file: file}
           });

           file.upload.then(function (response) {
               $timeout(function () {                                 
                 
               });
           }, function (response) {
               if (response.status > 0)
                   $scope.errorMsg = response.status + ': ' + response.data;
           }, function (evt) {
               file.progress = Math.min(100, parseInt(100.0 * 
                                        evt.loaded / evt.total));
           });
       });
		
	}
	
	$scope.init();
});
