const app = angular.module('tuyen-app', []);
app.controller('tuyen-ctrl', function ($scope, $http) {

    $scope.form = {};
    
    $scope.initialize = function () {
    $http.get("/rest/tuyen").then(response => {
        $scope.items = response.data;
         $scope.post = true
        $scope.put = false
        $scope.delete = false
    })}
    $scope.initialize()
    
	$scope.index_of = function (id) {
        return $scope.items.findIndex(t => t.idtuyen == id);
    }
	//Hiển thị lên form
 $scope.edit = function (id) {
        var url = `/rest/tuyen/${id}`;
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
        $scope.form = null;
        $scope.post = true;
        $scope.put = false;
        $scope.delete = false;
    }
	//Thêm tuyến mới
	  $scope.create = function () {
        var item = angular.copy($scope.form);
        var url = `/rest/tuyen/save`;
        $http.post(url,item).then(response => {
            $scope.items.push(response.data);
            alert("Thêm tuyến mới thành công")
            $scope.reset();
        }).catch(error => {
            alert("Thêm tuyến mới thất bại");
            console.log("Error",error)
        })
    }
	//Cập nhật tuyến
	    $scope.update = function () {
        var item = angular.copy($scope.form);
        var url = `/rest/tuyen/${item.idtuyen}`;
        $http.put(url, item).then(response => {
            var index = $scope.items.findIndex(t => t.idtuyen == item.idtuyen);
            $scope.items[index] = item;
            $scope.reset();                                         
            alert("Cập nhật tuyến thành công")
        }).catch(error=>{
            console.log("Error",error)
            alert("Cập nhật tuyến thất bại")
        })
    }
    
    //Xóa sản phẩm
/*	$scope.delete = function(item){
		
		$http.delete(`/rest/tuyen/${item.idtuyen}`).then(response => {
			var index = $scope.items.findIndex(t => t.idtuyen == item.idtuyen);
			$scope.items.splice(index, 1);
			$scope.reset();
			alert("Xóa sản phẩm thành công!")
		})
		.catch(error => {
			alert("Lỗi xóa sản phẩm!");
			console.log("Error", error);
		});
	}*/
})