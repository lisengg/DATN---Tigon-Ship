const app = angular.module('tuyen-app', []);
app.controller('tuyen-ctrl', function($scope, $http) {
	$scope.items = [];
	$scope.form = {};
	$scope.searchKeyword = '';
	$scope.filteredTuyens = [];

	$scope.initialize = function() {
		$http.get("/rest/tuyen").then(response => {
			$scope.items = response.data;
			$scope.post = true
			$scope.put = false
			$scope.delete = false
		})
	}



	$scope.initialize()

	$scope.index_of = function(id) {
		return $scope.items.findIndex(t => t.idtuyen == id);
	}
	//Hiển thị lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		console.log('open');
		$scope.post = false;
		$scope.put = true;
		$scope.delete = true;
		$scope.close = false;

	}

	//Xóa form
	$scope.reset = function() {
		$scope.form = null;
		$scope.post = true;
		$scope.put = false;
		$scope.delete = false;
	}

	//Tìm kiếm
	/*$scope.filterTuyens = function () {
		$scope.filteredTuyens = $scope.items.filter(function (tuyen) {
			return tuyen.tentuyen.toLowerCase().includes($scope.searchKeyword.toLowerCase());
		});
	};

	// Call filterTuyens whenever searchKeyword changes
	$scope.$watch('searchKeyword', $scope.filterTuyens);*/
	// Function to filter Tuyens based on searchKeyword
	$scope.filterTuyens = function() {
		$scope.filteredItems = $filter('filter')($scope.items, $scope.searchKeyword);
	};

	
	$scope.$watch('searchKeyword', $scope.filterTuyens);
	//Thêm tuyến mới
	$scope.create = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/tuyen/save`;

		// Kiểm tra xem tên tuyến mới có trùng với tên tuyến đã có không
		var isDuplicate = $scope.items.some(t => t.idtuyen !== item.idtuyen && t.tentuyen === item.tentuyen);

		if (isDuplicate) {
			alert("Tên tuyến đã tồn tại. Vui lòng chọn tên khác.");
			return; // Ngăn cập nhật nếu tên tuyến trùng
		}

		$http.post(url, item).then(response => {
			$scope.items.push(response.data);
			alert("Thêm tuyến mới thành công")
			$scope.reset();
		}).catch(error => {
			alert("Thêm tuyến mới thất bại");
			console.log("Error", error)
		})
	}
	//Cập nhật tuyến
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/tuyen/${item.idtuyen}`;

		// Kiểm tra xem tên tuyến mới có trùng với tên tuyến đã có không
		var isDuplicate = $scope.items.some(t => t.idtuyen !== item.idtuyen && t.tentuyen === item.tentuyen);

		if (isDuplicate) {
			alert("Tên tuyến đã tồn tại. Vui lòng chọn tên khác.");
			return; // Ngăn cập nhật nếu tên tuyến trùng
		}

		$http.put(url, item).then(response => {
			var index = $scope.items.findIndex(t => t.idtuyen == item.idtuyen);
			$scope.items[index] = item;
			$scope.reset();
			alert("Cập nhật tuyến thành công");
		}).catch(error => {
			console.log("Error", error);
			alert("Cập nhật tuyến thất bại");
		});
	}

	//Xóa sản phẩm
	$scope.deleteItem = function(item) {
		var confirmation = confirm("Bạn có chắc chắn muốn xóa tuyến này?");

		if (confirmation) {
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
					alert("Không thể xóa tuyến đang sử dụng!");
					console.log("Error", error);
				});
		} else {
			// Người dùng đã bấm Cancel, không thực hiện việc xóa
		}
	}
	//Phân trang
	$scope.items = []; // Dữ liệu của bạn ở đây
	$scope.pager = {
		page: 0,
		size: 5,
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