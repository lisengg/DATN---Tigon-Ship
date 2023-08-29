const app = angular.module('giave-app', []);
app.controller('giave-ctrl', function($scope, $http) {
	$scope.items = [],
		$scope.form = {},

		$scope.initialize = function() {
			$http.get("/rest/giave").then(response => {
				$scope.items = response.data;
				$scope.post = true
				$scope.put = false
				$scope.delete = false
				$scope.items.giave.forEach(item => {// trước khi bỏ vào chuyển đổi kiểu ngày tháng trong sql thành chuỗi
					item.ngaybatdau = new Date(item.ngaybatdau);
					item.ngayketthuc = new Date(item.ngayketthuc);
				})
			})

		}
	$scope.initialize()
	$scope.index_of = function(id) {
		return $scope.items.findIndex(p => p.idgiave == id);
	}

	//Hiển thị lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show')
		$scope.post = false;
		$scope.put = true;
		$scope.delete = true;
	}
	//Xóa form
	$scope.reset = function() {
		$scope.form = null;
		$scope.post = true;
		$scope.put = false;
		$scope.delete = false;
	}
	//Thêm giá vé mới
	$scope.create = function() {
		var index = $scope.items.loaive.findIndex(a => a.idloaive == $scope.form.loaive.idloaive)
		var index1 = $scope.items.tuyen.findIndex(a => a.idtuyen == $scope.form.tuyen.idtuyen)
		var item = {
			"loaive": $scope.items.loaive[index],
			"tuyen": $scope.items.tuyen[index1],
			"gia": $scope.form.gia,
			"ngaybatdau": $scope.form.ngaybatdau = new Date(),
			"ngayketthuc": $scope.form.ngayketthuc = new Date()
		}
		console.log(item)
		var url = `/rest/giave/save`;
		$http.post(url, item).then(response => {
			$scope.items.giave.push(response.data);
			alert("Thêm giá vé thành công")
			$scope.reset();
		}).catch(error => {
			alert("Thêm giá vé thất bại");
			console.log("Error", error)
		})
	}
	//Cập nhật giá vé
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/giave/${item.idgiave}`;
		$http.put(url, item).then(response => {
			var index = $scope.items.giave.findIndex(p => p.idgiave == item.idgiave);
				$scope.items.giave[index] = item;
				$scope.reset();
				alert("Cập nhật giá vé thành công");
				window.location.reload();
		}).catch(error => {
			console.log("Error", error)
			alert("Cập nhật giá vé thất bại")
		})
		
	}
	//Xóa giá vé
	$scope.deleteItem = function(item) {
		var url = `/rest/giave/${item.idgiave}`;
		$http.delete(url).then(response => {
			var index = $scope.items.giave.findIndex(p => p.idgiave == item.idgiave);
			if (index !== -1) {
				$scope.items.giave.splice(index, 1); // Xóa item khỏi danh sách
				$scope.reset();
				alert("Xóa giá vé thành công!");
			} else {
				alert("Không tìm thấy item để xóa!");
			}
		})
			.catch(error => {
				alert("Lỗi xóa giá vé!");
				console.log("Error", error);
			});
	}
	$scope.pager = {
		page: 0,
		size: 4,
		get items() {
			var start = this.page * this.size;
			return $scope.items.giave.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.giave.length / this.size);
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
