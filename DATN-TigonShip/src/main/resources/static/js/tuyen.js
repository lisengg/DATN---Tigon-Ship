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
 $scope.edit = function (item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show')
		$scope.post = false;
		$scope.put = true;
		$scope.delete = true;
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
$scope.deleteItem = function(item) {
		var url = `/rest/tuyen/${item.idtuyen}`;
		$http.delete(url).then(response => {
			var index = $scope.items.findIndex(p => p.idtuyen == item.idtuyen);
			if (index !== -1) {
				$scope.items.splice(index, 1); // Xóa item khỏi danh sách
				$scope.reset();
				alert("Xóa tuyến thành công!");
			} else {
				alert("Không tìm thấy item để xóa!");
			}
		})
			.catch(error => {
				alert("Lỗi xóa tuyến!");
				console.log("Error", error);
			});
	}
	$scope.pager = {
		page: 0,
		size: 4,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		last() {
			this.page = this.count - 1;
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		}
	}
})