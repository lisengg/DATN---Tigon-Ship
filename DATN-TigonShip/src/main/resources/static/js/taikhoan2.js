const app = angular.module('taikhoan1-app', []);
app.controller('taikhoan1-ctrl', function ($scope, $http) {
	$scope.form = {};
	$scope.items.taikhoan = [];
	 $scope.totalAccounts = 0;
	$scope.initialize = function() {
			$http.get("/rest/taikhoan").then(response => {
				$scope.items.taikhoan = response.data.taikhoan;
				// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
				  $scope.totalAccounts = $scope.items.taikhoan.length;
				
			});
	}
    	//Khởi đầu
	$scope.initialize();
	console.log($scope.items.taikhoan)
	console.log('Total Accounts:', $scope.totalAccounts);
})