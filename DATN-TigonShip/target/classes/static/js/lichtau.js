const app = angular.module('lichtau-app', []);
app.controller('lichtau-ctrl', function($scope, $http) {
	$scope.form = {},
	$scope.initialize = function() {
			$http.get("/rest/lichtau").then(response => {
				$scope.items = response.data;
				$scope.post = true;
				$scope.put = false;
				$scope.delete = false;
				// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
				initDataTable($scope.items);
			});
		}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.lichtau, // Sử dụng mảng giave từ dữ liệu
			columns: [
				{ data: 'idlichtau' },
				{ data: 'tuyen.tentuyen' },
				{ data: 'tau.tentau' },
				{ data: 'gioxuatphat' },
				{ data: 'giodennoi'},
				// Cột mới chứa nút bấm
				{ data: null,
				  defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Cập nhật</button>'}
			],
			columnDefs: [{"targets": -1, "orderable": false, "searchable": false}],
			"pageLength": 5
		});
		// Thêm sự kiện click vào nút "Cập nhật"
		$('#table2 tbody').on('click', 'button', function() {
			var data = table.row($(this).parents('tr')).data();
			$scope.$apply(function() {
				$scope.edit(data);
			});
		});
	}
	$scope.initialize()
	$scope.index_of = function(id) {
		return $scope.items.findIndex(lt => lt.idlichtau == id);
	}
	//Hiển thị lên form
	$scope.edit = function(id) {
		$scope.form = angular.copy(id);
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
	function compareTimes(startTime, endTime) {
		// Chuyển đổi thời gian thành đối tượng Date
		const startDate = new Date(`01/01/2023 ${startTime}`);
		const endDate = new Date(`01/01/2023 ${endTime}`);
		// So sánh thời gian
		if (startDate >= endDate) {
			return false; // Lỗi: Giờ đến nơi phải sau giờ xuất phát
		}
		return true; // Không có lỗi
	}
	//Thêm lịch tàu mới
	$scope.create = function() {
		var index = $scope.items.tau.findIndex(a => a.idtau == $scope.form.tau.idtau)
		var index1 = $scope.items.tuyen.findIndex(a => a.idtuyen == $scope.form.tuyen.idtuyen)
		var item = {
			"tau": $scope.items.tau[index],
			"tuyen": $scope.items.tuyen[index1],
			"gioxuatphat": $scope.form.gioxuatphat,
			"giodennoi": $scope.form.giodennoi
		}
		var url = `/rest/lichtau/save`;
		// Kiểm tra tên tuyến và tên tàu đã được chọn
		if (!item.tuyen.idtuyen || !item.tau.idtau) {
			alert("Vui lòng chọn tên tuyến và tên tàu!");
			return;
		}
		// Kiểm tra giờ xuất phát không được để trống
		if (!$scope.form.gioxuatphat) {
			alert("Vui lòng nhập giờ xuất phát!");
			return;
		}

		// Kiểm tra giờ đến nơi không được để trống
		if (!$scope.form.giodennoi) {
			alert("Vui lòng nhập giờ đến nơi!");
			return;
		}

		// Kiểm tra giờ đến nơi phải sau giờ xuất phát
		if (!compareTimes(item.gioxuatphat, item.giodennoi)) {
			alert("Giờ đến nơi phải sau giờ xuất phát!");
			return; // Không thực hiện thêm lịch tàu khi có lỗi
		}
		// Kiểm tra trùng lịch tàu
		var isDuplicate = $scope.items.lichtau.some(function(existingItem) {
			// So sánh lịch tàu hiện có với lịch tàu mới dựa trên các yếu tố như tuyến, tàu, giờ xuất phát và giờ đến nơi
			return (
				existingItem.tuyen.idtuyen === item.tuyen.idtuyen &&
				existingItem.tau.idtau === item.tau.idtau &&
				existingItem.gioxuatphat === item.gioxuatphat &&
				existingItem.giodennoi === item.giodennoi
			);
		});
		if (isDuplicate) {
			alert("Lịch tàu trùng lặp. Vui lòng kiểm tra lại.");
		} else {
			$http.post(url, item).then(response => {
				$scope.items.lichtau.push(response.data);
				var table = $('#table2').DataTable();
				table.row.add(response.data).draw();
				alert("Thêm lịch tàu thành công")
				$scope.reset();
			}).catch(error => {
				alert("Thêm lịch tàu thất bại");
				console.log("Error", error)
			})
		}
	}
	//Cập nhật lịch tàu
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/lichtau/${item.idlichtau}`;
		// Kiểm tra tên tuyến và tên tàu đã được chọn
		if (!item.tuyen.idtuyen || !item.tau.idtau) {
			alert("Vui lòng chọn tên tuyến và tên tàu.");
			return;
		}
		// Kiểm tra giờ xuất phát không được để trống
		if (!item.gioxuatphat) {
			alert("Vui lòng nhập giờ xuất phát.");
			return;
		}

		// Kiểm tra giờ đến nơi không được để trống
		if (!item.giodennoi) {
			alert("Vui lòng nhập giờ đến nơi.");
			return;
		}

		// Kiểm tra giờ đến nơi phải sau giờ xuất phát
		if (!compareTimes(item.gioxuatphat, item.giodennoi)) {
			alert("Lỗi: Giờ đến nơi phải sau giờ xuất phát");
			return; // Không thực hiện thêm lịch tàu khi có lỗi
		}

		// Kiểm tra trùng lịch tàu
		var isDuplicate = $scope.items.lichtau.some(function(existingItem) {
			// So sánh lịch tàu hiện có với lịch tàu mới dựa trên các yếu tố như tuyến, tàu, giờ xuất phát và giờ đến nơi
			return (
				existingItem.tuyen.idtuyen === item.tuyen.idtuyen &&
				existingItem.tau.idtau === item.tau.idtau &&
				existingItem.gioxuatphat === item.gioxuatphat &&
				existingItem.giodennoi === item.giodennoi
			);
		});
		if (isDuplicate) {
			alert("Lịch tàu trùng lặp. Vui lòng kiểm tra lại.");
		} else {
			$http.put(url, item).then(response => {
				var index = $scope.items.lichtau.findIndex(lt => lt.idlichtau == item.idlichtau);
				$scope.items.lichtau[index] = item;
				var table = $('#table2').DataTable();
				var row = table.row(index);
				row.data(item).draw();
				$scope.reset();
				alert("Cập nhật lịch tàu thành công")
			}).catch(error => {
				console.log("Error", error)
				alert("Cập nhật lịch tàu thất bại")
			})
		}
	}
	//Xóa lịch tàu
	$scope.deleteItem = function(data) {
		var confirmation = confirm("Bạn có chắc chắn muốn xóa lịch tàu này?");
		if (confirmation) {
			var url = `/rest/lichtau/${data.idlichtau}`;
			$http.delete(url).then(response => {
				var index = $scope.items.lichtau.findIndex(p => p.idlichtau == data.idlichtau);
				if (index !== -1) {
					$scope.items.lichtau.splice(index, 1); // Xóa item khỏi danh sách
					// Cập nhật DataTables
					var table = $('#table2').DataTable();
					table.row(index).remove().draw();
					$scope.reset();
					alert("Xóa lịch tàu thành công!");
				} else {
					alert("Không tìm thấy item để xóa!");
				}
			})
				.catch(error => {
					alert("Lỗi xóa lịch tàu!");
					console.log("Error", error);
				});
		} else {
			// Người dùng đã bấm Cancel, không thực hiện việc xóa
		}
	}

	// Hàm cập nhật dữ liệu đã phân trang khi tìm kiếm thay đổi
	$scope.$watch('searchKeyword', function(newVal, oldVal) {
		// Reset the searchPager to the first page when the searchKeyword changes
		$scope.searchPager.first();

		// Cập nhật dữ liệu đã phân trang
		$scope.updatePagedData();
	});

	// Hàm cập nhật dữ liệu đã phân trang
	$scope.updatePagedData = function() {
		// Tính toán lại dữ liệu đã phân trang dựa trên trang hiện tại và số mục trên mỗi trang
		var startIndex = ($scope.pager.page - 1) * 5;
		var endIndex = startIndex + 5;
		$scope.pager.items.lichtau = $scope.pager.items.lichtau.slice(startIndex, endIndex);
	};
	//Phân trang
	$scope.searchPager = {
		page: 0, // Start with the first page
		size: 5, // Number of items per page
		get items() {
			// Calculate the index range based on the current page and size
			var startIndex = (this.page - 1) * this.size;
			var endIndex = startIndex + this.size;

			// Filter the items based on the searchKeyword
			var filteredItems = $scope.items.lichtau.filter(function(item) {
				return item.tuyen.tentuyen.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
					item.tau.tentau.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
					item.gioxuatphat.toLowerCase().includes($scope.searchKeyword.toLowerCase());
			});
			return filteredItems.slice(startIndex, endIndex);
		},
		get count() {
			// Calculate the total number of pages based on filtered items
			var filteredItems = $scope.items.lichtau.filter(function(item) {
				return item.tuyen.tentuyen.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
					item.tau.tentau.toLowerCase().includes($scope.searchKeyword.toLowerCase()) ||
					item.gioxuatphat.toLowerCase().includes($scope.searchKeyword.toLowerCase());
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