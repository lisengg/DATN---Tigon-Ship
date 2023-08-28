const app = angular.module('lichtau-app', []);
app.controller('lichtau-ctrl', function ($scope, $http) {

    $scope.form = {};
    
    $scope.initialize = function () {
    $http.get("/rest/lichtau").then(resp => {
        $scope.items = resp.data;
         $scope.post = true
        $scope.put = false
        $scope.delete = false
    })}
    
    $scope.initialize()
	$scope.index_of = function (id) {
        return $scope.items.findIndex(lt => lt.idlichtau == id);
    }
	//Hiển thị lên form
$scope.edit = function (id) {
        var url = `/rest/lichtau/${id}`;
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
	$scope.reset = function(){
		 $scope.form = null;
        $scope.post = true;
        $scope.put = false;
        $scope.delete = false;
	}
	
	//Thêm lịch tàu mới
	 $scope.create = function () {
        var item = angular.copy($scope.form);
        var url = `/rest/lichtau/save`;
        $http.post(url,item).then(response => {
            $scope.items.push(response.data);
            alert("Thêm lịch tàu thành công")
            $scope.reset();
        }).catch(error => {
            alert("Thêm lịch tàu thất bại");
            console.log("Error",error)
        })
    }
	//Cập nhật lịch tàu
	    $scope.update = function () {
        var item = angular.copy($scope.form);
        var url = `/rest/lichtau/${item.idlichtau}`;
        $http.put(url, item).then(response => {
            var index = $scope.items.findIndex(lt => lt.idlichtau == item.idlichtau);
            $scope.items[index] = item;
            $scope.reset();                                         
            alert("Cập nhật lịch tàu thành công")
        }).catch(error=>{
            console.log("Error",error)
            alert("Cập nhật lịch tàu thất bại")
        })
    }
        //Xóa lịch tàu
	$scope.delete = function(item){
		alet("dô")
		var url = `/rest/lichtau/${item.idlichtau}`;
		$http.delete(url).then(response => {
			var index = $scope.items.findIndex(lt => lt.idlichtau == item.idlichtau);
			$scope.items.splice(index, 1);
			$scope.reset();
			alert("Xóa lịch tàu thành công!")
		})
		.catch(error => {
			alert("Lỗi xóa lịch tàu!");
			console.log("Error", error);
		});
	}

})