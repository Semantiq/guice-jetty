function DemoCtrl($scope, $http) {
	$scope.items = [ "Getting tasks..." ];
	$scope.caption = "My tasks";
	$scope.addNewTaskMessage = "Add new task:"

	$scope.addTask = function() {
		$http.post("/api/tasks", {
			task : $scope.newTask
		}).then(function(r) {
			$scope.items = r.data.todos;
			$scope.newTask = "";
		});
	};

	$http.get("/api/tasks").then(function(r) {
		$scope.items = r.data.todos;
	});
}
