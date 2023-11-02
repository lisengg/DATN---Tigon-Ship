const app = angular.module('tuyen-app', []);
app.controller('tuyen-ctrl', function($scope, $http) {
	$scope.form = {};

	$scope.initialize = function() {
		$http.get("/rest/tuyen").then(response => {
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
			data: data.tuyen, // Sử dụng mảng giave từ dữ liệu
			columns: [
				{ data: 'idtuyen' },
				{ data: 'tentuyen' },
				// Cột mới chứa nút bấm
				{
					data: null,
					defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Cập nhật</button>'
				}
			],
			columnDefs: [{ "targets": -1, "orderable": false, "searchable": false }],
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
		return $scope.items.findIndex(t => t.idtuyen == id);
	}
	//Hiển thị lên form
	$scope.edit = function(id) {
		$scope.originalData = angular.copy(id); // Lưu trữ dữ liệu ban đầu
		$scope.form = angular.copy(id);
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
	//Thêm tuyến mới
	$scope.create = function() {
		// Kiểm tra xem tuyen đã được chọn
		if (!$scope.form.tentuyen) {
			document.getElementById('check6').checked = true;
			return;
		}
		var item = angular.copy($scope.form);
		var url = `/rest/tuyen/save`;
		// Kiểm tra xem tên tuyến mới có trùng với tên tuyến đã có không
		var isDuplicate = $scope.items.tuyen.some(t => t.idtuyen !== item.idtuyen && t.tentuyen === item.tentuyen);

		if (isDuplicate) {
			document.getElementById('check8').checked = true;
			return; // Ngăn cập nhật nếu tên tuyến trùng
		}

		$http.post(url, item).then(response => {
			$scope.items.tuyen.push(response.data);
			var table = $('#table2').DataTable();
			table.row.add(response.data).draw();
			document.getElementById('check3').checked = true;
			var itemlichsu = {
				"tuyen": response.data,
				"thaotac" : "Vừa thêm mới tuyến có ID : " + response.data.idtuyen ,
			}
			$http.post('/rest/tuyen/lichsu/save', itemlichsu)
                .then(function(response) {   
					$scope.items.lichsu.push(response.data)
                })
				.catch(function(error) {
					console.log("Error creating LichuHangTau", error);
				});
			$scope.reset();
		}).catch(error => {
			document.getElementById('check2').checked = true;
			console.log("Error", error)
		})
	}
	//Cập nhật tuyến

	$scope.updateSuccess = false;
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/tuyen/${item.idtuyen}`;
		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if (angular.equals(item, $scope.originalData)) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		if ($scope.updateSuccess) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		// Kiểm tra xem tên tuyến mới có trùng với tên tuyến đã có không
		var isDuplicate = $scope.items.tuyen.some(t => t.idtuyen !== item.idtuyen && t.tentuyen.toLowerCase() === item.tentuyen.toLowerCase());

		if (isDuplicate) {
			document.getElementById('check8').checked = true;
			return; // Ngăn cập nhật nếu tên tuyến trùng
		}

		var itemlichsu = {
			"tuyen": item,
			"thaotac" : "Vừa update tàu có ID : " + item.idtuyen ,
		}
		$http.put(url, item).then(response => {
			var index = $scope.items.tuyen.findIndex(t => t.idtuyen == item.idtuyen);
			$scope.items.tuyen[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			document.getElementById('check3').checked = true;
			$scope.updateSuccess = true;
			$http.post('/rest/tuyen/lichsu/save', itemlichsu)
                .then(function(response) {   
					$scope.items.lichsu.push(response.data)
                })
				.catch(function(error) {
					console.log("Error creating LichuHangTau", error);
				});
		}).catch(error => {
			console.log("Error", error);
			document.getElementById('check2').checked = true;
		});
	}

	//Xóa sản phẩm
	$scope.deleteItem = function(data) {
		var url = `/rest/tuyen/${data.idtuyen}`;
		// Kiểm tra xem tuyến đó có đang được sử dụng trong lịch tàu hay không
		var isTuyenUsedInLichtau = $scope.items.lichtau.some(function(lichtau) {
			return lichtau.tuyen.idtuyen === data.idtuyen;
		});

		if (isTuyenUsedInLichtau) {
			document.getElementById('check7').checked = true;
			return;
		}
		$http.delete(url).then(response => {
			var index = $scope.items.findIndex(p => p.idtuyen == data.idtuyen);
			if (index !== -1) {
				$scope.items.tuyen.splice(index, 1); // Xóa item khỏi danh sách
				// Cập nhật DataTables
				var table = $('#table2').DataTable();
				table.row(index).remove().draw();
				document.getElementById('check1').checked = true; // Hiển thị form thành công
				document.getElementById('check').checked = false;
			} else {
				alert("Không tìm thấy item để xóa!");
			}
		})
			.catch(error => {
				document.getElementById('check4').checked = true;
				document.getElementById('check').checked = false;
				console.log("Error", error);
			});
	}

})