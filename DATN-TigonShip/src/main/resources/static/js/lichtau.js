const app = angular.module('lichtau-app', []);
app.controller('lichtau-ctrl', function($scope, $http) {
	$scope.items = [],
		$scope.form = {},

		$scope.initialize = function() {
			$http.get("/rest/lichtau").then(resp => {
				$scope.items = resp.data;
				$scope.post = true
				$scope.put = false
				$scope.delete = false
				updateCurrentTimeInput()
			})
		}

	$scope.initialize()
	$scope.index_of = function(id) {
		return $scope.items.findIndex(lt => lt.idlichtau == id);
	}
	//Hiển thị lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show')
		$scope.post = false;
		$scope.put = true;
		$scope.delete = true;

	}

	function updateCurrentTimeInput() {
		var currentTime = new Date();
		var hours = currentTime.getHours();
		var minutes = currentTime.getMinutes();

		// Định dạng giờ và phút để có 2 chữ số, ví dụ: 09:05
		var formattedHours = hours < 10 ? '0' + hours : hours;
		var formattedMinutes = minutes < 10 ? '0' + minutes : minutes;

		// Gán giá trị vào trường nhập
		$("#selected-time").val(formattedHours + ':' + formattedMinutes);
		$("#selected-time1").val(formattedHours + ':' + formattedMinutes); // Đối với trường nhập thứ hai (nếu có)
	}

	//Xóa form
	$scope.reset = function() {
		$scope.form = null;
		$scope.post = true;
		$scope.put = false;
		$scope.delete = false;
		window.location.reload(); // Tải lại trang web
		// Gọi hàm để cập nhật giờ phút hiện tại
		updateCurrentTimeInput();
	}

	$scope.form = {
		gioxuatphat: "",
		giodennoi: ""
	};
	// Hiển thị dropdown thời gian khi bấm vào trường nhập
	$("#selected-time").click(function() {
		$("#time-picker-dropdown").toggle();
	});
	// Xử lý khi chọn một mục từ dropdown thời gian
	$("#time-picker-dropdown ul li").click(function() {
		var selectedTime = $(this).text();
		$("#selected-time").val(selectedTime);
		$("#time-picker-dropdown").hide();
		// Cập nhật giờ xuất phát trong $scope.form
		$scope.form.gioxuatphat = selectedTime;
	});
	// Hiển thị dropdown thời gian khi bấm vào trường nhập
	$("#selected-time1").click(function() {
		$("#time-picker-dropdown1").toggle();
	});
	// Xử lý khi chọn một mục từ dropdown thời gian
	$("#time-picker-dropdown1 ul li").click(function() {
		var selectedTime = $(this).text();
		$("#selected-time1").val(selectedTime);
		$("#time-picker-dropdown1").hide();
		// Cập nhật giờ xuất phát trong $scope.form
		$scope.form.giodennoi = selectedTime;
	});
	
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
		console.log(item)
		var url = `/rest/lichtau/save`;
		// Kiểm tra tên tuyến và tên tàu đã được chọn
		if (!item.tuyen.idtuyen || !item.tau.idtau) {
			alert("Vui lòng chọn tên tuyến và tên tàu.");
			return;
		}
		// Kiểm tra giờ xuất phát không được để trống
		if (!$scope.form.gioxuatphat) {
			alert("Vui lòng nhập giờ xuất phát.");
			return;
		}

		// Kiểm tra giờ đến nơi không được để trống
		if (!$scope.form.giodennoi) {
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
			$http.post(url, item).then(response => {
				$scope.items.lichtau.push(response.data);
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
				$scope.reset();
				alert("Cập nhật lịch tàu thành công")
			}).catch(error => {
				console.log("Error", error)
				alert("Cập nhật lịch tàu thất bại")
			})
		}
	}

	//Xóa lịch tàu
	$scope.deleteItem = function(item) {
		var confirmation = confirm("Bạn có chắc chắn muốn xóa lịch tàu này?");

		if (confirmation) {
			var url = `/rest/lichtau/${item.idlichtau}`;
			$http.delete(url).then(response => {
				var index = $scope.items.lichtau.findIndex(p => p.idlichtau == item.idlichtau);
				if (index !== -1) {
					$scope.items.lichtau.splice(index, 1); // Xóa item khỏi danh sách
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
	$scope.pager = {
		page: 0,
		size: 5,
		get items() {
			var start = this.page * this.size;
			return $scope.items.lichtau.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.lichtau.length / this.size);
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