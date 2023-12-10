const app = angular.module('authority1-app', []);
app.controller('authority1-ctrl', function($scope, $http) {
	$scope.form = {};
	$scope.initialize = function() {
		$http.get("/rest/authority1").then(response => {
			$scope.items = response.data;
			// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
			initDataTable($scope.items);
		})
	}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.taikhoan, // Sử dụng dữ liệu từ biến items
			columns: [
				{ data: 'hovaten' },
				{ data: 'email' },
				{ data: 'vaitro' },
				// Cột mới chứa nút bấm
				{
					data: null,
					defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Cập nhật</button>'
				}
			],
			columnDefs: [{ "targets": -1, "orderable": false, "searchable": false }],
			"pageLength": 5 // Đặt giá trị mặc định là 5 mục hiển thị trên mỗi trang
		});
		// Thêm sự kiện click vào nút "Cập nhật"
		$('#table2 tbody').on('click', 'button', function() {
			var data = table.row($(this).parents('tr')).data();
			$scope.$apply(function() {
				console.log(data)
				$scope.edit(data);
			});
		});
	}

	$scope.initialize();
	$scope.index_of = function(id) {
		return $scope.items.findIndex(q => q.idtaikhoan == id);
	}
	//Hiển thị lên form
	$scope.edit = function(id) {
		$scope.originalData = angular.copy(id); // Lưu trữ dữ liệu ban đầu
		$scope.form = angular.copy(id);
	}

	//Phân quyền
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/authority1/${item.idtaikhoan}`;
		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if (angular.equals(item, $scope.originalData)) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		if ($scope.originalData.vaitro === 'ADMIN') {
			// Nếu là ADMIN, kiểm tra xem có thay đổi vai trò không
			if (item.vaitro !== 'ADMIN') {
				// Hiển thị thông báo hoặc thực hiện các hành động khác tùy thuộc vào yêu cầu
				document.getElementById('check4').checked = true;
				return;
			}
		}
		$http.put(url, item).then(response => {
			var index = $scope.items.taikhoan.findIndex(q => q.idtaikhoan === item.idtaikhoan);
			$scope.items.taikhoan[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			document.getElementById('check3').checked = true;
		}).catch(error => {
			console.log("Error", error)
			document.getElementById('check2').checked = true;
		})
	}

})

/*$(document).ready(function() {
		// Khởi tạo DataTables
		var table = $('#table2').DataTable({
			data: $scope.items, // Sử dụng dữ liệu từ biến items
			columns: [
				{ data: 'idtaikhoan' },
				{ data: 'hovaten' },
				{ data: 'email' },
				{ data: 'vaitro' },
				{ data: 'cccd' },
				{ data: 'sdt' },
				{ data: 'diachi' },
				{ data: 'quoctich' },
				{
					data: 'ngaysinh',
					render: function(data, type, full, meta) {
						if (type === 'display') {
							// Định dạng ngày theo dd/MM/yyyy
							return moment(data).format('DD/MM/YYYY'); // Sử dụng thư viện Moment.js hoặc thư viện khác tương tự nếu cần
						}
						return data; // Trả về dữ liệu gốc cho các loại khác
					}
				},
				{ data: 'matkhau' },
				{ data: 'loaihk.loaihk' },
				// Cột mới chứa nút bấm
				{
					data: null,
					defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Cập nhật</button>'
				}
			],
			columnDefs: [{ "targets": -1, "orderable": false, "searchable": false }],
			"pageLength": 5 // Đặt giá trị mặc định là 5 mục hiển thị trên mỗi trang
		});
		// Thêm sự kiện click vào nút "Cập nhật"
		$('#table2 tbody').on('click', 'button', function() {
			var data = table.row($(this).parents('tr')).data();
			$scope.$apply(function() {
				console.log(data)
				$scope.edit(data);
			});
		});
	});
	$scope.initialize = function() {
		$http.get("/rest/authority1").then(response => {
			$scope.items = response.data;
			$scope.put = false
			// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
			var table = $('#table2').DataTable();
			table.clear().rows.add($scope.items).draw();
		})
	}*/