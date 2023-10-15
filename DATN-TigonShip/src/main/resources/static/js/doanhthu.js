const app = angular.module('doanhthu-app', []);
app.controller('doanhthu-ctrl', function($scope, $http) {
	$scope.items = [];
	$scope.doanhthu 
    $scope.dtChiTiet = function() {
        var url = `/rest/doanhthu/chitiet`;
		$http.get(url).then(response => {
			$scope.items = response.data;
		}).catch(err => {
			console.log("Error", err)
		})
    }
    $scope.dtChiTiet()
	$scope.dt = function() {
        var url = `/rest/doanhthu/doanhthu`;
		$http.get(url).then(response => {
			$scope.doanhthu = response.data;
			console.log($scope.doanhthu)
		}).catch(err => {
			console.log("Error", err)
		})
    }
    $scope.dt()
})