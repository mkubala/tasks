function TaskController($scope) {
	$scope.tasks = [];

	$scope.taskLabelIsBlank = false;
	
	var connector = new maku.tasks.Connector();

	function refreshTasks() {
		connector.get({
			url : "/tasks",
			callback : function(data, textStatus, jqXHR) {
				$scope.tasks = data.tasks;
				refreshUndoneCount();
			}
		});
	}

	function refreshUndoneCount() {
		var numOfTasks = $scope.tasks.length;
		$scope.undoneCount = numOfTasks;
		for (var i = 0; i < numOfTasks; i++) {
			var task = $scope.tasks[i];
			if (task.done) {
				$scope.undoneCount--;
			}
		}
	}

	function validateLabel() {
		$scope.taskLabelIsBlank = !$scope.taskLabel || ($scope.taskLabel.replace(/^\s+|\s+$/g, '').length == 0);
	}

	$scope.addTask = function() {
		validateLabel();
		if ($scope.taskLabelIsBlank) {
			return;
		}
		connector.post({
			url : "/tasks?label=" + $scope.taskLabel,
			callback : function(data, textStatus, jqXHR) {
				$scope.taskLabel = null;
				refreshTasks();
			}
		});
	}

	$scope.delTask = function(task) {
		connector.post({
			url : "/tasks/" + task.id + "/delete",
			callback : function(data, textStatus, jqXHR) {
				refreshTasks();
			}
		});
	}

	$scope.updateTask = function(task) {
		connector.post({
			url : "/tasks/update",
			data : task,
			callback : function(data, textStatus, jqXHR) {
				refreshTasks();
			}
		});
	}

	$scope.archive = function() {
		connector.post({
			url : "/tasks/archive",
			callback : function(data, textStatus, jqXHR) {
				console.log(data);
				refreshTasks();
			}
		});
	}

	refreshTasks();
}