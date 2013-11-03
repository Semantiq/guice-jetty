function DemoCtrl($scope, $http) {
  $scope.items = [ "Getting tasks..." ];
  $scope.caption = "My tasks";
  $scope.addNewTaskMessage = "Add new task:"
  
  $scope.addTask = function() {
	  $http.post("/api/tasks", { task: $scope.newTask }).success(function(data) {
		  $scope.items.push($scope.newTask);
		  $scope.newTask = "";
	  });
  };
  
  $http.get("/api/tasks").success(function(data) {
	  $scope.items = data;
  });
}
