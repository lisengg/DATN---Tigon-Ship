const app = angular.module('doanhthu-app', []);
app.controller('doanhthu-ctrl', function($scope, $http) {
	$scope.items = [];
	$scope.doanhthu 
	$scope.doanhthutheongay = [];
    $scope.dtChiTiet = function() { // Doanh thu từng tuyến ngày hôm nay
        var url = `/rest/doanhthu/chitiet`;
		$http.get(url).then(response => {
			$scope.items = response.data;
		}).catch(err => {
			console.log("Error", err)
		})
    }
    $scope.dtChiTiet()
	$scope.dt = function() { // Tổng doanh thu ngày hôm nay
        var url = `/rest/doanhthu/doanhthu`;
		$http.get(url).then(response => {
			$scope.doanhthu = response.data;
			console.log($scope.doanhthu)
		}).catch(err => {
			console.log("Error", err)
		})
    }
    $scope.dt()

	$scope.dttheongay = function() { //Doanh thu theo từng tuyến theo ngày lựa chọn
		var date = $scope.date;
		var formattedDate = date.toISOString().split('T')[0]; 
        var url = `/rest/doanhthu/theongay/${formattedDate}`;
		$http.get(url).then(response => {
			$scope.doanhthutheongay = response.data;
			console.log($scope.doanhthutheongay)
		}).catch(err => {
			console.log("Error", err)
		})
    }
   


})