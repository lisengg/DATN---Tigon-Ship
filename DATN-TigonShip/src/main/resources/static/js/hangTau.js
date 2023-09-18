const app = angular.module('hangtau-app', []);
app.controller('hangtau-ctrl', function($scope, $http) {
	$scope.form = {}
	$scope.searchKeyword = '';

	$scope.initialize = function() {
		$http.get("/rest/hangtau").then(response => {
			$scope.items = response.data;
			$scope.post = true
			$scope.put = false
			$scope.dele = false
		})
	}
	$scope.initialize()

	$scope.index_of = function(id) {
		return $scope.items.findIndex(a => a.idhangtau == id);
	}

	$scope.reset = function() {
		$scope.form = null;
		$scope.post = true;
		$scope.put = false;
		$scope.dele = false;
	}

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$scope.post = false;
		$scope.put = true;
		$scope.dele = true;
	}
	$scope.save = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/hangtau/save`;
		// Kiểm tra tên hãng tàu không được để trống
		if (!$scope.form.tenhangtau) {
			alert("Vui lòng nhập tên hãng tàu!");
			return;
		}

		// Kiểm tra địa chỉ không được để trống
		if (!$scope.form.diachi) {
			alert("Vui lòng nhập địa chỉ!");
			return;
		}

		// Kiểm tra số điện thoại không được để trống
		if (!$scope.form.sdt) {
			alert("Vui lòng nhập số điện thoại!");
			return;
		}

		// Kiểm tra số điện thoại có đúng định dạng hay không
		var phonePattern = /^(0[1-9][0-9]{8})$/; // Định dạng số điện thoại ở Việt Nam
		if (!phonePattern.test($scope.form.sdt)) {
			alert("Số điện thoại không hợp lệ! Vui lòng nhập số điện thoại ở Việt Nam.");
			$scope.form.sdt = '';
			return;
		}
		
		// Kiểm tra email có đúng định dạng hay không
		var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		if (!emailPattern.test($scope.form.email)) {
			alert("Vui lòng nhập đúng định dạng email!");
			return;
		}

		var tenHangTau = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.tenhangtau.toLowerCase() === item.tenhangtau.toLowerCase());
		var diaChi = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.diachi.toLowerCase() === item.diachi.toLowerCase());
		var email = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.email.toLowerCase() === item.email.toLowerCase());
		var sdt = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.sdt === item.sdt);

		if (tenHangTau) {
			alert("Tên hãng tàu đã tồn tại! Vui lòng nhập tên khác.");
			return; // Ngăn cập nhật nếu tên hãng tàu trùng
		}

		if (diaChi) {
			alert("Địa chỉ đã tồn tại! Vui lòng nhập khác.");
			return; // Ngăn cập nhật nếu địa chỉ trùng
		}

		if (email) {
			alert("Email đã tồn tại! Vui lòng nhập email khác.");
			return; // Ngăn cập nhật nếu email trùng
		}

		if (sdt) {
			alert("Số điện thoại đã tồn tại! Vui lòng nhập số điện thoại khác.");
			return; // Ngăn cập nhật nếu số điện thoại trùng
		}
		$http.post(url, item).then(response => {
			$scope.items.push(response.data);
			alert("Thêm loại hãng tàu mới thành công")
			$scope.reset();
		}).catch(error => {
			alert("Thêm loại hãng tàu mới thất bại");
			console.log("Error", error)
		})
	}
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/hangtau/${item.idhangtau}`;

		// Kiểm tra tên hãng tàu không được để trống
		if (!$scope.form.tenhangtau) {
			alert("Vui lòng nhập tên hãng tàu!");
			return;
		}

		// Kiểm tra địa chỉ không được để trống
		if (!$scope.form.diachi) {
			alert("Vui lòng nhập địa chỉ!");
			return;
		}

		// Kiểm tra số điện thoại không được để trống
		if (!$scope.form.sdt) {
			alert("Vui lòng nhập số điện thoại!");
			return;
		}

		// Kiểm tra số điện thoại có đúng định dạng hay không
		var phonePattern = /^(0[1-9][0-9]{8})$/; // Định dạng số điện thoại ở Việt Nam
		if (!phonePattern.test($scope.form.sdt)) {
			alert("Số điện thoại không hợp lệ! Vui lòng nhập số điện thoại ở Việt Nam.");
			$scope.form.sdt = '';
			return;
		}

		// Kiểm tra email có đúng định dạng hay không
		var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		if (!emailPattern.test($scope.form.email)) {
			alert("Vui lòng nhập đúng định dạng email!");
			return;
		}

		var tenHangTau = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.tenhangtau.toLowerCase() === item.tenhangtau.toLowerCase());
		var diaChi = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.diachi.toLowerCase() === item.diachi.toLowerCase());
		var email = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.email.toLowerCase() === item.email.toLowerCase());
		var sdt = $scope.items.some(a => a.idhangtau !== item.idhangtau && a.sdt === item.sdt);

		if (tenHangTau) {
			alert("Tên hãng tàu đã tồn tại! Vui lòng nhập tên khác.");
			return; // Ngăn cập nhật nếu tên hãng tàu trùng
		}

		if (diaChi) {
			alert("Địa chỉ đã tồn tại! Vui lòng nhập khác.");
			return; // Ngăn cập nhật nếu địa chỉ trùng
		}

		if (email) {
			alert("Email đã tồn tại! Vui lòng nhập email khác.");
			return; // Ngăn cập nhật nếu email trùng
		}

		if (sdt) {
			alert("Số điện thoại đã tồn tại! Vui lòng nhập số điện thoại khác.");
			return; // Ngăn cập nhật nếu số điện thoại trùng
		}

		$http.put(url, item).then(response => {
			var index = $scope.items.findIndex(a => a.idhangtau === item.idhangtau);
			$scope.items[index] = item;
			$scope.reset();
			alert("Cập nhật hãng tàu thành công")
		}).catch(error => {
			console.log("Error", error)
			alert("Cập nhật hãng tàu thất bại")
		});

	}

	$scope.delete = function(id) {
		var confirmation = confirm("Bạn có chắc chắn muốn xóa tuyến này?");
		if (confirmation) {
			$http.delete(`/rest/hangtau/${id}`).then(response => {
				var index = $scope.items.findIndex(a => a.idhangtau === $scope.form.idhangtau);
				$scope.items.splice(index, 1);
				alert("Xóa thành công");
				$scope.reset()
			}).catch(error => {
				alert("Hãng tàu đang được sử dụng.Vui lòng không xóa mà hãy chuyển sang trạng thái ẩn hoạt động");
				console.log("Error", error)
			})
		}

	}


	//Close search form
	$scope.reset1 = function() {
		$scope.searchKeyword = '';
	}

	// Hàm cập nhật dữ liệu đã phân trang khi tìm kiếm thay đổi
	$scope.$watch('searchKeyword', function(newVal, oldVal) {
		$scope.searchPager.first();

		// Cập nhật dữ liệu đã phân trang
		$scope.updatePagedData();
	});

	// Hàm cập nhật dữ liệu đã phân trang
	$scope.updatePagedData = function() {
		// Tính toán lại dữ liệu đã phân trang dựa trên trang hiện tại và số mục trên mỗi trang
		var startIndex = ($scope.pager.page - 1) * 5;
		var endIndex = startIndex + 5;
		$scope.pager.items = $scope.pager.items.slice(startIndex, endIndex);
	};
	//Phân trang
	$scope.searchPager = {
		page: 0, // Bắt đầu từ trang đầu tiên
		size: 3, // Số mục trên mỗi trang
		get items() {
			// Tính toán phạm vi chỉ mục dựa trên trang và kích thước hiện tại
			var startIndex = (this.page - 1) * this.size;
			var endIndex = startIndex + this.size;

			// Lọc các mục dựa trên từ khóa tìm kiếm
			var filteredItems = $scope.items.filter(function(item) {
				return item.tenhangtau.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
					item.diachi.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
					item.email.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
					item.sdt.toLowerCase().includes($scope.searchKeyword.toLowerCase());
			});
			return filteredItems.slice(startIndex, endIndex);
		},
		get count() {
			// Tính tổng số trang dựa trên các mục được lọc
			var filteredItems = $scope.items.filter(function(item) {
				return item.tenhangtau.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
					item.diachi.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
					item.email.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
					item.sdt.toLowerCase().includes($scope.searchKeyword.toLowerCase());
			});
			return Math.ceil(filteredItems.length / this.size);
		},
		first() {
			this.page = 1;
		},
		prev() {
			this.page--;
			if (this.page < 1) {
				this.page = 1;
			}
		},
		next() {
			this.page++;


			if (this.page > this.count) {
				this.page = this.count;
			}
		},
		last() {
			this.page = this.count;
		}
	}

})