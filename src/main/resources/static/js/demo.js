function DemoCtrl($scope, $http) {
  $scope.items = [ "default item 1", "default item 2" ];
  $scope.caption = "Hello world!";
  $scope.refresh = function() {
	  $http.get("/api/demo.json").success(function(data) {
		  $scope.items = data;
	  });
  };
}
