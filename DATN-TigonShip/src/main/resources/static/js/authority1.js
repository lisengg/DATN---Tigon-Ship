const app = angular.module('authority1-app', []);
app.controller('authority1-ctrl', function($scope, $http) {
	$scope.items = [];
	$scope.form = {};

	$scope.initialize = function() {
		//load all authority
		$http.get("/rest/authority1").then(resp => {
			$scope.items = resp.data;
			$scope.put = false;
		})

	}
	//Khởi đầu
	$scope.initialize();

	$scope.index_of = function(id) {
		return $scope.items.findIndex(q => q.idhanhkhach == id);
	}
	$scope.form = {
		quyen: "",
	};
	// Hiển thị dropdown thời gian khi bấm vào trường nhập
	$("#phan-quyen").click(function() {
		$("#dropdown").toggle();
	});
	// Xử lý khi chọn một mục từ dropdown thời gian
	$("#dropdown ul li").click(function() {
		var quyenn = $(this).text();
		$("#phan-quyen").val(quyenn);
		$("#dropdown").hide();
		// Cập nhật giờ xuất phát trong $scope.form
		$scope.form.quyen = quyenn;
	});
	//Hiển thị lên form
	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show')
		$scope.put = true;
	}

	//Xóa form
	$scope.reset = function() {
		$scope.form = null;
		$scope.put = false;
	}
	
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/authority1/${item.idhanhkhach}`;
				// Kiểm tra quyền không được để trống
		if (!item.quyen) {
			alert("Vui lòng nhập quyền.");
			return;
		}
		$http.put(url, item).then(response => {
				var index = $scope.items.findIndex(q => q.idhanhkhach == item.idhanhkhach);
				$scope.items[index] = item;
				$scope.reset();
				alert("Phân quyền thành công")
			}).catch(error => {
				console.log("Error", error)
				alert("Phân quyền thất bại")
			})
		}


})

