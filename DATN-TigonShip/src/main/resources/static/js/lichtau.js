const app = angular.module('lichtau-app', []);
app.controller('lichtau-ctrl', function($scope, $http, $sce) {
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
				{ data: 'trangthai' },
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
		$scope.form = {
			trangthai: 'Đang hoạt động',
		};
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
			"giodennoi": $scope.form.giodennoi,
			"trangthai": $scope.form.trangthai
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
		// Kiểm tra số lượng lịch tàu của tàu đó trong danh sách chưa được thêm mới
		var lichTauOfTheTrain = $scope.items.lichtau.filter(function(existingItem) {
			return existingItem.tau.idtau === item.tau.idtau;
		});

		// Kiểm tra nếu số lượng lịch tàu của tàu đó đã đạt đến giới hạn 4
		if (lichTauOfTheTrain.length >= 4) {
			document.getElementById('check11').checked = true; // Đặt một checkbox để hiển thị thông báo lỗi
			return;
		}
		// Kiểm tra xem có lịch tàu nào trùng thời gian xuất phát và thời gian đến nơi không
		var isOverlapping = lichTauOfTheTrain.some(function(existingItem) {
			return (
				compareTimes(existingItem.gioxuatphat, item.giodennoi) &&
				compareTimes(item.gioxuatphat, existingItem.giodennoi)
			);
		});

		if (isOverlapping) {
			document.getElementById('check8').checked = true; // Đặt một checkbox để hiển thị thông báo lỗi
			return;
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
					"thaotac": "Đã thêm mới lịch tàu có ID : " + response.data.idlichtau,
				}
				$http.post('/rest/lichtau/lichsu/save', itemlichsu)
					.then(function(response) {
						$scope.items.lichsu.push(response.data)
					})
					.catch(function(error) {
						console.log("Error creating LichSuLichTau", error);
					});
				$scope.reset();
			}).catch(error => {
				document.getElementById('check2').checked = true;
				console.log("Error", error)
			})
		}
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

	// Hàm kiểm tra số lượng lịch tàu của tàu

	// Hàm kiểm tra sự chồng chép thời gian giữa lịch tàu mới và các lịch tàu đã có
	function checkForTimeOverlap(item) {
		var oldItem = angular.copy(item);
		
		var lichTauOfTheTrain = $scope.items.lichtau.filter(function(existingItem) {
			return existingItem.tau.idtau === oldItem.tau.idtau && existingItem.idlichtau !== oldItem.idlichtau;
		});
		
		var isOverlapping = lichTauOfTheTrain.some(function(existingItem) {
			return (
				compareTimes(existingItem.gioxuatphat, item.giodennoi) &&
				compareTimes(item.gioxuatphat, existingItem.giodennoi)
			);
		});

		return isOverlapping;
	}

	//Cập nhật lịch tàu
	$scope.update = function(item) {
		var item = angular.copy($scope.form);
		var itemold = $scope.originalData;
		var url = `/rest/lichtau/${item.idlichtau}`;
		if (angular.equals(item, $scope.originalData)) {
			document.getElementById('check10').checked = true;
			return;
		}
		// Kiểm tra giờ đến nơi phải sau giờ xuất phát
		if (!compareTimes(item.gioxuatphat, item.giodennoi)) {
			document.getElementById('check9').checked = true;
			return; // Không thực hiện thêm lịch tàu khi có lỗi
		}
		// Tạo một bản sao của dữ liệu cũ trước khi cập nhật
		var oldItem = angular.copy(item);

		var lichTauOfTheTrain = $scope.items.lichtau.filter(function(existingItem) {
			return existingItem.tau.idtau === oldItem.tau.idtau && existingItem.idlichtau !== oldItem.idlichtau;
		});

		// Kiểm tra số lượng lịch tàu của tàu đó đã đạt đến giới hạn 4
		if (lichTauOfTheTrain.length >= 4) {
			document.getElementById('check11').checked = true; // Đặt một checkbox để hiển thị thông báo lỗi
			return;
		}

		// Kiểm tra xem có lịch tàu nào trùng thời gian xuất phát và thời gian đến nơi không
		if (checkForTimeOverlap(item)) {
			document.getElementById('check8').checked = true; // Đặt một checkbox để hiển thị thông báo lỗi
			return;
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
		// Cập nhật thông tin của Tàu + Tuyến trong item tránh việc load lại trang
		var indexTau = $scope.items.tau.findIndex(a => a.idtau === $scope.form.tau.idtau);
		item.tau = $scope.items.tau[indexTau];
		var indexTuyen = $scope.items.tuyen.findIndex(a => a.idtuyen === $scope.form.tuyen.idtuyen);
		item.tuyen = $scope.items.tuyen[indexTuyen];



		var ttupdate = "Cập nhật lịch tàu  có ID: " + itemold.idlichtau;

		if (itemold.tuyen.tentuyen !== item.tuyen.tentuyen) {
			ttupdate += "# Tên tuyến: " + itemold.tuyen.tentuyen + " thành " + item.tuyen.tentuyen;
		}
		if (itemold.tau.tentau !== item.tau.tentau) {
			ttupdate += "# Tên tàu: " + itemold.tau.tentau + " thành " + item.tau.tentau;
		}
		if (!angular.equals(itemold.gioxuatphat, item.gioxuatphat)) {
			ttupdate += "# Giờ xuất phát: " + itemold.gioxuatphat + " thành " + item.gioxuatphat;
		}
		if (!angular.equals(itemold.giodennoi, item.giodennoi)) {
			ttupdate += "# Giờ xuất phát: " + itemold.giodennoi + " thành " + item.giodennoi;
		}
		if (itemold.trangthai !== item.trangthai) {
			ttupdate += "# trạng thái thành " + item.trangthai;
		}

		console.log(ttupdate);
		var itemlichsu = {
			"lichtau": item,
			"thaotac": ttupdate,
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
				$http.post('/rest/lichtau/lichsu/save', itemlichsu)
					.then(function(response) {
						$scope.items.lichsu.push(response.data)
					})
					.catch(function(error) {
						console.log("Error creating LichSuLichTau", error);
					});
			}).catch(error => {
				console.log("Error", error)
				document.getElementById('check2').checked = true;
			})
		}
	}
})