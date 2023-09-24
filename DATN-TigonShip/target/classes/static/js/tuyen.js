const app = angular.module('tuyen-app', []);
app.controller('tuyen-ctrl', function($scope, $http) {
	$scope.form = {};
	/*	$scope.searchKeyword = '';*/

	$(document).ready(function() {
		// Khởi tạo DataTables
		var table = $('#table2').DataTable({
			data: $scope.items, // Sử dụng dữ liệu từ biến items
			columns: [
				{ data: 'idtuyen' },
				{ data: 'tentuyen' },
				// Cột mới chứa nút bấm
				{
					data: null,
					defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Cập nhật</button>'
				}
			],
			columnDefs: [
				{
					"targets": -1, // Chỉ định cột mới được thêm
					"orderable": false, // Không sắp xếp theo cột này	
					"searchable": false
				}
			],
			"pageLength": 5
		});
		// Thêm sự kiện click vào nút "Cập nhật"
		$('#table2 tbody').on('click', 'button', function() {
			var data = table.row($(this).parents('tr')).data();

			$scope.$apply(function() {
				console.log(data)
				$scope.edit(data);
			});
			// data chứa thông tin của hàng được chọn, bạn có thể sử dụng nó ở đây
			// Ví dụ: alert(data.idtuyen);
			// Hoặc gọi hàm edit(data) để hiển thị dữ liệu trong modal
			// edit(data);
		});
		// Để thực hiện phân trang, bạn có thể sử dụng các tuỳ chọn khác của DataTables.
	});

	$scope.initialize = function() {
		$http.get("/rest/tuyen").then(response => {
			$scope.items = response.data;
			$scope.post = true
			$scope.put = false
			$scope.delete = false

			// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
			var table = $('#table2').DataTable();
			table.clear().rows.add($scope.items).draw();
		})
	}
	$scope.initialize()

	$scope.index_of = function(id) {
		return $scope.items.findIndex(t => t.idtuyen == id);
	}
	//Hiển thị lên form
	$scope.edit = function(data) {
		$scope.form = angular.copy(data);
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

	//Close search form
	$scope.reset1 = function() {
		$scope.searchKeyword = '';
	}

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
			var table = $('#table2').DataTable();
			table.row.add(response.data).draw();
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

		// Kiểm tra tên tuyến không được để trống
		if (!$scope.form.tentuyen) {
			alert("Vui lòng nhập tên tuyến.");
			return;
		}

		// Kiểm tra xem tên tuyến mới có trùng với tên tuyến đã có không
		var isDuplicate = $scope.items.some(t => t.idtuyen !== item.idtuyen && t.tentuyen.toLowerCase() === item.tentuyen.toLowerCase());

		if (isDuplicate) {
			alert("Tên tuyến đã tồn tại. Vui lòng chọn tên khác.");
			return; // Ngăn cập nhật nếu tên tuyến trùng
		}

		$http.put(url, item).then(response => {
			var index = $scope.items.findIndex(t => t.idtuyen == item.idtuyen);
			$scope.items[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			$scope.reset();
			alert("Cập nhật tuyến thành công");
		}).catch(error => {
			console.log("Error", error);
			alert("Cập nhật tuyến thất bại");
		});
	}

	//Xóa sản phẩm
	$scope.deleteItem = function(data) {
		var confirmation = confirm("Bạn có chắc chắn muốn xóa tuyến này?");

		if (confirmation) {
			var url = `/rest/tuyen/${data.idtuyen}`;
			$http.delete(url).then(response => {
				var index = $scope.items.findIndex(p => p.idtuyen == data.idtuyen);
				if (index !== -1) {
					$scope.items.splice(index, 1); // Xóa item khỏi danh sách
					// Cập nhật DataTables
					var table = $('#table2').DataTable();
					table.row(index).remove().draw();
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

})