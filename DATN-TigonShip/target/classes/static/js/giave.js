const app = angular.module('giave-app', []);
app.controller('giave-ctrl', function($scope, $http) {
	$scope.form = {
		ngaybatdau: new Date(),
		ngayketthuc: new Date(),
	},
		$scope.initialize = function() {
			$http.get("/rest/giave").then(response => {
				$scope.items = response.data;
				$scope.items.giave.forEach(item => {
					item.ngaybatdau = new Date(item.ngaybatdau);
					item.ngayketthuc = new Date(item.ngayketthuc);
				});
				$scope.post = true;
				$scope.put = false;
				$scope.delete = false;
				// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
				initDataTable($scope.items);
			});
		}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.giave, // Sử dụng mảng giave từ dữ liệu
			columns: [
				{ data: 'idgiave' },
				{ data: 'loaive.loaive' },
				{ data: 'tuyen.tentuyen' },
				{ data: 'gia' },
				{
					data: 'ngaybatdau',
					render: function(data, type, full, meta) {
						if (type === 'display') {
							// Định dạng ngày theo dd/MM/yyyy
							return moment(data).format('DD/MM/YYYY'); // Sử dụng thư viện Moment.js hoặc thư viện khác tương tự nếu cần
						}
						return data; // Trả về dữ liệu gốc cho các loại khác
					}
				},
				{
					data: 'ngayketthuc',
					render: function(data, type, full, meta) {
						if (type === 'display') {
							// Định dạng ngày theo dd/MM/yyyy
							return moment(data).format('DD/MM/YYYY'); // Sử dụng thư viện Moment.js hoặc thư viện khác tương tự nếu cần
						}
						return data; // Trả về dữ liệu gốc cho các loại khác
					}
				},
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
			var rowData = table.row($(this).parents('tr')).data();
			$scope.$apply(function() {
				$scope.edit(rowData);
			});
		});
	}
	$scope.initialize()
	$scope.index_of = function(id) {
		return $scope.items.findIndex(p => p.idgiave == id);
	}
	//Hiển thị lên form
	$scope.edit = function(id) {
		$scope.originalData = angular.copy(id); // Lưu trữ dữ liệu ban đầu
		$scope.form = angular.copy(id);
		$scope.post = false;
		$scope.put = true;
		$scope.delete = true;
	}
	//Xóa form
	$scope.reset = function() {
		$scope.form = {
			ngaybatdau: new Date(),
			ngayketthuc: new Date(),
		};
		$scope.post = true;
		$scope.put = false;
		$scope.delete = false;
	}
	//Thêm giá vé mới
	$scope.create = function() {
		// Kiểm tra xem tên loại vé đã được chọn
		if (!$scope.form.loaive || !$scope.form.loaive.idloaive) {
			document.getElementById('check5').checked = true;
			return;
		}
		// Kiểm tra xem tuyen đã được chọn
		if (!$scope.form.tuyen || !$scope.form.tuyen.idtuyen) {
			document.getElementById('check6').checked = true;
			return;
		}
		var index = $scope.items.loaive.findIndex(a => a.idloaive == $scope.form.loaive.idloaive)
		var index1 = $scope.items.tuyen.findIndex(a => a.idtuyen == $scope.form.tuyen.idtuyen)
		var item = {
			"loaive": $scope.items.loaive[index],
			"tuyen": $scope.items.tuyen[index1],
			"gia": $scope.form.gia,
			"ngaybatdau": $scope.form.ngaybatdau = new Date(),
			"ngayketthuc": $scope.form.ngayketthuc = new Date()
		}
		var url = `/rest/giave/save`;
		// Kiểm tra giá vé không được để trống
		if (!item.gia) {
			document.getElementById('check4').checked = true;
			return;
		}
		// Kiểm tra giá vé là số hợp lệ
		if (item.gia <= 100000 || item.gia >= 700000) {
			document.getElementById('check7').checked = true;
			return;
		}
		// Kiểm tra ngày bắt đầu và ngày kết thúc
		if (item.ngaybatdau > item.ngayketthuc) {
			document.getElementById('check9').checked = true;
			return;
		}
		// Kiểm tra xem tên loại vé và tên tuyến đã được chọn có trùng với giá vé khác không
		var isDuplicate = $scope.items.giave.some(function(giave) {
			return giave.idgiave !== item.idgiave &&
				giave.loaive.idloaive === item.loaive.idloaive &&
				giave.tuyen.idtuyen === item.tuyen.idtuyen;
		});
		if (isDuplicate) {
			document.getElementById('check8').checked = true;
		} else {
			$http.post(url, item).then(response => {
				$scope.items.giave.push(response.data);
				var table = $('#table2').DataTable();
				table.row.add(response.data).draw();
				document.getElementById('check3').checked = true; // Hiển thị form thành công
				var itemlichsu = {
					"giave": response.data,
					"thaotac" : "Vừa thêm mới giá vé có ID : " + response.data.idgiave ,
				}
				$http.post('/rest/tau/giave/save', itemlichsu)
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
	}
	//Cập nhật giá vé
	$scope.updateSuccess = false;
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/giave/${item.idgiave}`;
		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if (angular.equals(item, $scope.originalData)) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		if ($scope.updateSuccess) {
			document.getElementById('check10').checked = true;
			return;
		}
		// Kiểm tra giá vé không được để trống
		if (!item.gia) {
			document.getElementById('check4').checked = true;
			return;
		}
		// Kiểm tra giá vé là số hợp lệ
		if (item.gia <= 99999 || item.gia >= 700001) {
			document.getElementById('check7').checked = true;
			return;
		}
		// Kiểm tra ngày bắt đầu và ngày kết thúc
		if (item.ngaybatdau > item.ngayketthuc) {
			document.getElementById('check9').checked = true;
			return;
		}
		// Kiểm tra xem tên loại vé và tên tuyến đã được chọn có trùng với giá vé khác không
		var isDuplicate = $scope.items.giave.some(function(giave) {
			return giave.idgiave !== item.idgiave &&
				giave.loaive.idloaive === item.loaive.idloaive &&
				giave.tuyen.idtuyen === item.tuyen.idtuyen;
		});

		if (isDuplicate) {
			document.getElementById('check8').checked = true;
		} else {
			var itemlichsu = {
				"giave": item,
				"thaotac" : "Vừa update giá vé có ID : " + item.idgiave ,
			}
			$http.put(url, item).then(response => {
				var index = $scope.items.giave.findIndex(p => p.idgiave == item.idgiave);
				$scope.items.giave[index] = item;
				var table = $('#table2').DataTable();
				var row = table.row(index);
				row.data(item).draw();
				document.getElementById('check3').checked = true; // Hiển thị form thành công
				$scope.updateSuccess = true;
				$http.post('/rest/giave/lichsu/save', itemlichsu)
                .then(function(response) {   
					$scope.items.lichsu.push(response.data)
                })
				.catch(function(error) {
					console.log("Error creating LichuHangTau", error);
				});
			}).catch(error => {
				document.getElementById('check2').checked = true;
				console.log("Error", error)

			});
		}
	}
	//Xóa giá vé
	$scope.deleteItem = function(data) {
		var url = `/rest/giave/${data.idgiave}`;
		$http.delete(url).then(response => {
			var index = $scope.items.giave.findIndex(p => p.idgiave == data.idgiave);
			if (index !== -1) {
				$scope.items.giave.splice(index, 1); // Xóa item khỏi danh sách
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
				document.getElementById('check2').checked = true;
				document.getElementById('check').checked = false;
				console.log("Error", error);
			});
	}
})
