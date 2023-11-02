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
				{ data: 'giodennoi' },
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
		return $scope.items.findIndex(lt => lt.idlichtau == id);
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
		// Kiểm tra xem tuyen đã được chọn
		if (!$scope.form.tuyen || !$scope.form.tuyen.idtuyen) {
			document.getElementById('check6').checked = true;
			return;
		}
		// Kiểm tra xem tàu đã được chọn
		if (!$scope.form.tau || !$scope.form.tau.idtau) {
			document.getElementById('check5').checked = true;
			return;
		}
		var index = $scope.items.tuyen.findIndex(a => a.idtuyen == $scope.form.tuyen.idtuyen)
		var index1 = $scope.items.tau.findIndex(a => a.idtau == $scope.form.tau.idtau)
		var item = {
			"tuyen": $scope.items.tuyen[index],
			"tau": $scope.items.tau[index1],
			"gioxuatphat": $scope.form.gioxuatphat,
			"giodennoi": $scope.form.giodennoi
		}
		var url = `/rest/lichtau/save`;
		// Kiểm tra giờ xuất phát không được để trống
		if (!item.gioxuatphat) {
			document.getElementById('check4').checked = true;
			return;
		}

		// Kiểm tra giờ đến nơi không được để trống
		if (!item.giodennoi) {
			document.getElementById('check7').checked = true;
			return;
		}

		// Kiểm tra giờ đến nơi phải sau giờ xuất phát
		if (!compareTimes(item.gioxuatphat, item.giodennoi)) {
			document.getElementById('check9').checked = true;
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
			document.getElementById('check8').checked = true;
		} else {
			$http.post(url, item).then(response => {
				$scope.items.lichtau.push(response.data);
				var table = $('#table2').DataTable();
				table.row.add(response.data).draw();
				document.getElementById('check3').checked = true;
				var itemlichsu = {
					"lichtau": response.data,
					"thaotac" : "Vừa thêm mới tàu có ID : " + response.data.idlichtau ,
				}
				$http.post('/rest/lichtau/lichsu/save', itemlichsu)
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
	//Cập nhật lịch tàu
	$scope.updateSuccess = false;
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/lichtau/${item.idlichtau}`;
		// Kiểm tra xem dữ liệu có bị thay đổi so với dữ liệu ban đầu
		if ($scope.updateSuccess) {
			// Hiển thị thông báo lỗi vì không có sự thay đổi
			document.getElementById('check10').checked = true;
			return;
		}
		if (angular.equals(item, $scope.originalData)) {
			document.getElementById('check10').checked = true;
			return;
		}
		// Kiểm tra giờ đến nơi phải sau giờ xuất phát
		if (!compareTimes(item.gioxuatphat, item.giodennoi)) {
			document.getElementById('check9').checked = true;
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
		var itemlichsu = {
			"lichtauchay": item,
			"thaotac" : "Vừa update tàu có ID : " + item.idlichtau ,
		}
		if (isDuplicate) {
			document.getElementById('check8').checked = true;
		} else {
			$http.put(url, item).then(response => {
				var index = $scope.items.lichtau.findIndex(lt => lt.idlichtau == item.idlichtau);
				$scope.items.lichtau[index] = item;
				var table = $('#table2').DataTable();
				var row = table.row(index);
				row.data(item).draw();
				document.getElementById('check3').checked = true;
				$scope.updateSuccess = true;
				$http.post('/rest/lichtau/lichsu/save', itemlichsu)
                .then(function(response) {   
					$scope.items.lichsu.push(response.data)
                })
				.catch(function(error) {
					console.log("Error creating LichuHangTau", error);
				});
			}).catch(error => {
				console.log("Error", error)
				document.getElementById('check2').checked = true;
			})
		}
	}
	//Xóa lịch tàu
	$scope.deleteItem = function(data) {
		var url = `/rest/lichtau/${data.idlichtau}`;
		$http.delete(url).then(response => {
			var index = $scope.items.lichtau.findIndex(p => p.idlichtau == data.idlichtau);
			if (index !== -1) {
				$scope.items.lichtau.splice(index, 1); // Xóa item khỏi danh sách
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