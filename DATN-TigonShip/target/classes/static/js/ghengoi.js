const app = angular.module('ghengoi-app', []);
app.controller('ghengoi-ctrl', function ($scope, $http) {
    
    $scope.reset = function () { 
        $scope.form.tenghe = ''    
        $scope.form.khoang = ''  
        $scope.post = true
        $scope.put = false
        $scope.dele = false
    }

   $scope.initialize = function() {
		$http.get("/rest/ghengoi").then(response => {
			$scope.items = response.data;
			// Khởi tạo DataTables hoặc cập nhật dữ liệu trong DataTables
			initDataTable($scope.items);
		});
	}
	function initDataTable(data) {
		var table = $('#table2').DataTable({
			data: data.ghengoi, // Sử dụng mảng giave từ dữ liệu
			columns: [
				{ data: 'idghe' },
				{ data: 'tenghe' },
				{ data: 'khoang' },
				// Cột mới chứa nút bấm
				{ data: null,
				  defaultContent: '<button data-bs-toggle="modal" data-bs-target="#modal" class="btn btn-warning">Chi tiết</button>'}
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
	    $scope.edit = function (id) {
		$scope.form = angular.copy(id);
        $scope.post = false;
        $scope.put = true;
        $scope.dele = true;
    }
    //Hiển thị ghế ngồi lên modal
    $scope.showGhengoi=function() {
        var idtau = $scope.form.tau.idtau;
        console.log(idtau); // lấy id tàu để show ra ghế ngồi
        var url = `/rest/hangtau/${idtau}`;
        $http.get(url).then(response => {
            $scope.items.ghengoi = response.data;
            console.log($scope.ghe)
        }).catch(err => {
            console.log("Error", err)
        })
    }
   /* $scope.save = function () {
        var index = $scope.items.tau.findIndex(a => a.idtau === $scope.form.tau.idtau)
        var item = {
            "tenghe": $scope.form.tenghe,
            "khoang": $scope.form.khoang,
            "tau": $scope.items.tau[index]
        }
        console.log(item)
        var url = `/rest/ghengoi/save`;
        $http.post(url,item).then(response => {
            $scope.items.ghengoi.push(response.data);
            alert("Thêm ghế ngồi mới thành công")
            $scope.reset();
        }).catch(error => {
            alert("Thêm ghế ngồi mới thất bại");
            console.log("Error",error)
        })
    }*/

    $scope.update = function () {
        var index = $scope.items.tau.findIndex(a => a.idtau === $scope.form.tau.idtau)
        var item = {
            "tenghe": $scope.form.tenghe,
            "khoang": $scope.form.khoang,
            "tau": $scope.items.tau[index]
        }
        console.log(item)
        var url = `/rest/ghengoi/${$scope.form.idghe}`;
        $http.put(url, item).then(response => {
            var index = $scope.items.ghengoi.findIndex(a => a.idghe === $scope.form.idghe);
            $scope.items.ghengoi[index] = response.data;
            alert("Cập nhật ghế ngồi thành công")
            $scope.reset();
        }).catch(err => {
            alert("Lỗi cập nhật ghế ngồi");
            console.log("Error", error);
        });
    }
/*    $scope.delete = function (id){
        $http.delete(`/rest/ghengoi/${id}`).then(response => {
            var index = $scope.items.ghengoi.findIndex(a => a.idghe === $scope.form.idghe);
            $scope.items.ghengoi.splice(index,1);
            alert("Xóa thành công");
            $scope.reset()
        }).catch(error =>{
            alert("Xóa thành công");
            console.log("Error",error)
        })
    }*/
})