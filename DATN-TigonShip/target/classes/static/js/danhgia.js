/*const app = angular.module('danhgia-app', []);
app.controller('danhgia-ctrl', function ($scope, $http) {
    $scope.items = [];
    $scope.ghe = {}
    $scope.reset = function () { 
    }
    $scope.initialize = function () {
<<<<<<< HEAD
        $http.get("/rest/danhgia").then(response => {
            $scope.items = response.data;
        })
    }
    $scope.initialize()

    $scope.xemDanhGia=function() {
        var idtuyen = $scope.form.tuyen.idtuyen;
        console.log(idtuyen); // lấy id tàu để show ra ghế ngồi
        var url = `/rest/danhgia/${idtuyen}`;
        $http.get(url).then(response => {
            $scope.items.danhgia = response.data;
            console.log($scope.danhgia)
        }).catch(err => {
            console.log("Error", err)
        })
    }
   
})
=======
    $http.get("/rest/danhgia").then(resp => {
        $scope.items = resp.data;
         $scope.post = true
        $scope.put = false
        $scope.delete = false
    })}
    
    $scope.initialize()
	$scope.index_of = function (id) {
        return $scope.items.findIndex(lt => lt.iddanhgiahk == id);
    }
	//Hiển thị lên form
	$scope.edit = function (item) {
		$scope.form = angular.copy(item);
		$(".nav-tabs a:eq(0)").tab('show')
		$scope.post = false;
		$scope.put = true;
		$scope.delete = true;
    }
*/	
	//Xóa form
/*	$scope.reset = function(){
		 $scope.form = null;
        $scope.post = true;
        $scope.put = false;
        $scope.delete = false;
	}*/
	
/*	//Thêm lịch tàu mới
	 $scope.create = function () {
        var index = $scope.items.hanhkhach.findIndex(a => a.idhanhkhach == $scope.form.tau.idtau)
		var index1 = $scope.items.tuyen.findIndex(a => a.idtuyen == $scope.form.tuyen.idtuyen)
		var item = {
			"tau": $scope.items.tau[index],
			"tuyen": $scope.items.tuyen[index1],
			"gioxuatphat": $scope.form.gioxuatphat,
			"giodennoi": $scope.form.giodennoi
		}
		console.log(item)
		var url = `/rest/danhgia/save`;
		$http.post(url, item).then(response => {
			$scope.items.lichtau.push(response.data);
			alert("Thêm lịch tàu thành công")
			$scope.reset();
		}).catch(error => {
			alert("Thêm lịch tàu thất bại");
			console.log("Error", error)
		})
    }
	//Cập nhật lịch tàu
	    $scope.update = function () {
        var item = angular.copy($scope.form);
        var url = `/rest/danhgia/${item.idlichtau}`;
        $http.put(url, item).then(response => {
            var index = $scope.items.lichtau.findIndex(lt => lt.idlichtau == item.idlichtau);
            $scope.items.lichtau[index] = item;
            $scope.reset();                                         
            alert("Cập nhật lịch tàu thành công")
        }).catch(error=>{
            console.log("Error",error)
            alert("Cập nhật lịch tàu thất bại")
        })
    }
  
        //Xóa lịch tàu
	$scope.deleteItem = function(item){
		var url = `/rest/danhgia/${item.idlichtau}`;
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
	}*/
/*	  $scope.pager = {
        page: 0,
        size: 5,
        get items() {
            var start = this.page * this.size;
            return $scope.items.danhgia.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.danhgia.length / this.size);
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

})*/

app.service('BookingService', function($http) {
    this.getBookingsByCustomerId = function(customerId) {
        return $http.get('/rest/datve' + customerId); // Thay đổi đường dẫn API thật của bạn
    };
});
app.controller('BookingController', function($scope, BookingService) {
    $scope.customerId = 1; // Thay đổi customerId thành ID của khách hàng bạn muốn tìm
    $scope.bookings = [];
    
    BookingService.getBookingsByCustomerId($scope.customerId)
        .then(function(response) {
            $scope.bookings = response.data;
        })
        .catch(function(error) {
            console.log('Lỗi khi tải dữ liệu:', error);
        });
});


















>>>>>>> Minh
