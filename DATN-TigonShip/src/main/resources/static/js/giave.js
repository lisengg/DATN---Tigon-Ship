const app = angular.module('giave-app', []);
app.controller('giave-ctrl', function($scope, $http) {
  $scope.form = {};

	$scope.initialize = function() {
		$http.get("/rest/giave").then(response => {
			$scope.items = response.data;
			$scope.items.forEach(item => {// trước khi bỏ vào chuyển đổi kiểu ngày tháng trong sql thành chuỗi
				item.ngaybatdau = new Date(item.ngaybatdau)
				item.ngayketthuc = new Date(item.ngayketthuc)
				})
			$scope.post = true
			$scope.put = false
			$scope.delete = false
		})

	}
	$scope.index_of = function (id) {
        return $scope.items.findIndex(p => p.idgiave == id);
    }
	$scope.initialize()
	
	//Hiển thị lên form
 $scope.edit = function (id) {
        var url = `/rest/giave/${id}`;
        $http.get(url).then(response => {
            $scope.form = response.data;
            $scope.post = false;
            $scope.put = true;
            $scope.delete = true;
        }).catch(err => {
            console.log("Error", err)
        })
    }
	
	//Xóa form
	 $scope.reset = function () {
        $scope.form = {
			ngaybatdau: new Date(),
			ngayketthuc: new Date(),
		};
        $scope.post = true;
        $scope.put = false;
        $scope.delete = false;
    }
})
