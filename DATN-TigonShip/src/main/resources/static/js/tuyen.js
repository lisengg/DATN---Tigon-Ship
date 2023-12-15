const app = angular.module('tuyen-app', ['ngSanitize']);
app.controller('tuyen-ctrl', function($scope, $http, $sce) {
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
				{ data: 'tentuyen' },
				{ data: 'trangthai' },
				// Cột mới chứa nút bấm
				{
					data: null,
					defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="custom-button"><i class="fas fa-pen"></i></button>'
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
		$scope.form = {
			trangthai: 'Đang hoạt động',
		};
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
		var item = {
			"tentuyen": $scope.form.tentuyen,
			"trangthai": $scope.form.trangthai
		}
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
				"thaotac": "Đã thêm mới tuyến có ID : " + response.data.idtuyen,
			}
			$http.post('/rest/tuyen/lichsu/save', itemlichsu)
				.then(function(response) {
					$scope.items.lichsu.push(response.data)
				})
				.catch(function(error) {
					console.log("Error creating LichSuTuyen", error);
				});
			$scope.reset();
		}).catch(error => {
			document.getElementById('check2').checked = true;
			console.log("Error", error)
		})
	}
	$scope.formatThaoTac = function(thaoTac) {
		if (thaoTac.includes('#')) {
			// Nếu chuỗi thao tác chứa dấu phẩy, cắt chuỗi và thêm thẻ xuống dòng
			var separatedLines = thaoTac.split('#').map(line => line.trim());
			return $sce.trustAsHtml(separatedLines.join('<br>'));
		} else {
			// Ngược lại, trả về nguyên bản
			return thaoTac;
		}
	};
	//Cập nhật tuyến
	$scope.update = function() {
		if (!$scope.form.tentuyen) {
			document.getElementById('check6').checked = true;
			return;
		}
		var itemold = $scope.originalData
		var item = angular.copy($scope.form);
		var url = `/rest/tuyen/${item.idtuyen}`;
		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if (angular.equals(item, $scope.originalData)) {
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

		var ttupdate = "Cập nhật tuyến có ID: " + itemold.idtuyen;

		if (itemold.tentuyen !== item.tentuyen) {
			ttupdate += "# Tên tuyến: " + itemold.tentuyen + " thành " + item.tentuyen;
		}

		if (itemold.trangthai.toLowerCase() !== item.trangthai.toLowerCase()) {
			ttupdate += "# trạng thái thành " + item.trangthai;
		}
		console.log(ttupdate);
		var itemlichsu = {
			"tuyen": item,
			"thaotac": ttupdate,
		}
		$http.put(url, item).then(response => {
			var index = $scope.items.tuyen.findIndex(t => t.idtuyen == item.idtuyen);
			$scope.items.tuyen[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			document.getElementById('check3').checked = true;

			$http.post('/rest/tuyen/lichsu/save', itemlichsu)
				.then(function(response) {
					$scope.items.lichsu.push(response.data)
				})
				.catch(function(error) {
					console.log("Error creating LichSuTuyen", error);
				});
		}).catch(error => {
			console.log("Error", error);
			document.getElementById('check2').checked = true;
		});
	}

})