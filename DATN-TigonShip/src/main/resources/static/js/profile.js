const app = angular.module('authority-app', []);
app.controller('authority-ctrl', function($scope, $http) {
	$scope.form = {};
	$scope.initialize = function() {
		$http.get("/rest/authority1").then(response => {
			$scope.items = response.data;
			$scope.put = false
		})
	}
	//Khởi đầu
	$scope.initialize();
	$scope.index_of = function(id) {
		return $scope.items.findIndex(q => q.idhanhkhach == id);
	}
	
	//Hiển thị lên form
	$scope.edit = function(id) {
		$scope.form = angular.copy(id);
		$scope.put = true;
	}
	    // Đợi cho tài liệu tải xong
    $(document).ready(function() {
        // Xử lý khi người dùng nhấp vào "Hồ sơ"
        $('a[href="#modal1"]').on('click', function() {
            // Lấy thông tin người dùng từ dữ liệu đã có sẵn (vd: #request.remoteUser)
            var username = "[[${#request.remoteUser}]]"; // Thay thế bằng cách lấy thông tin người dùng thực tế
            var email = "[[Email của người dùng]]"; // Thay thế bằng cách lấy thông tin email người dùng thực tế
            var role = "[[Quyền của người dùng]]"; // Thay thế bằng cách lấy thông tin quyền người dùng thực tế

            // Điền thông tin người dùng vào modal
            $('#modal1 input[name="form.hovaten"]').val(username);
            $('#modal1 input[name="form.email"]').val(email);
            $('#modal1 select[name="form.quyen"]').val(role); // Điền thông tin quyền vào một trường select (nếu có)

            // Hiển thị modal
            $('#modal1').modal('show');
        });
    });
	//Xóa form
	$scope.reset = function() {
		$scope.form = null;
		$scope.searchKeyword = null;
		$scope.put = false;
	}
	//Phân quyền
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/authority1/${item.idhanhkhach}`;
		// Kiểm tra xem người dùng có đang cố gắng thay đổi vai trò tương tự không
		if (item.quyen === 'USER' && item.quyen === $scope.items[$scope.index_of(item.idhanhkhach)].quyen) {
			alert(`Vai trò của ${item.hovaten} đã là USER. không cần thay đổi thành USER.`);
			return;
		}
		if (item.quyen === 'STAFF' && item.quyen === $scope.items[$scope.index_of(item.idhanhkhach)].quyen) {
			alert(`Vai trò của ${item.hovaten} đã là STAFF. không cần thay đổi thành STAFF.`);
			return;
		}
		if (item.quyen === 'ADMIN' && item.quyen === $scope.items[$scope.index_of(item.idhanhkhach)].quyen) {
			alert(`Vai trò của bạn đã là ADMIN. không cần thay đổi thành ADMIN.`);
			return;
		}
		//Kiểm tra xem người dùng có đang cố thay đổi từ QUẢN TRỊ thành NGƯỜI DÙNG hoặc NHÂN VIÊN không
		if ($scope.items[$scope.index_of(item.idhanhkhach)].quyen === 'ADMIN' &&
			(item.quyen === 'USER' || item.quyen === 'STAFF')) {
			alert(`${item.hovaten} đang là ADMIN và không thể thay đổi vai trò.`);
			return; // Don't proceed with the update
		}
		$http.put(url, item).then(response => {
			var index = $scope.items.findIndex(q => q.idhanhkhach === item.idhanhkhach);
			$scope.items[index] = item;
			var table = $('#table2').DataTable();
			var row = table.row(index);
			row.data(item).draw();
			alert("Phân quyền thành công")
		}).catch(error => {
			console.log("Error", error)
			alert("Phân quyền thất bại")
		})
	}

})

