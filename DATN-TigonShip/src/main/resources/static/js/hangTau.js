const app = angular.module('hangtau-app', []);
app.controller('hangtau-ctrl', function ($scope, $http) {
    $scope.form = {}
    $scope.initialize = function () {
    $http.get("/rest/hangtau").then(response => {
        $scope.items = response.data;
        $scope.post = true
        $scope.put = false
        $scope.dele = false
    })}
    $scope.initialize()

    $scope.index_of = function (id) {
        return $scope.items.findIndex(a => a.idhangtau == id);
    }

    $scope.reset = function () {
        $scope.form = null;
        $scope.post = true;
        $scope.put = false;
        $scope.dele = false;
    }

    $scope.edit = function (id) {
        var url = `/rest/hangtau/${id}`;
        $http.get(url).then(response => {
            $scope.form = response.data;
            $scope.post = false;
            $scope.put = true;
            $scope.dele = true;
        }).catch(err => {
            console.log("Error", err)
        })
    }
<<<<<<< HEAD
<<<<<<< HEAD
    $scope.save = function () {
        var item = angular.copy($scope.form);
        var url = `/rest/hangtau/save`;
        $http.post(url,item).then(response => {
            $scope.items.push(response.data);
            alert("Thêm loại hãng tàu mới thành công")
            $scope.reset();
        }).catch(error => {
            alert("Thêm loại hãng tàu mới thất bại");
            console.log("Error",error)
        })
=======
    $scope.validate = function () {
        const phoneNumberPattern = /^[0-9]{10}$/;
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if ($scope.form.tenhangtau == null || $scope.form.diachi == null || $scope.form.sdt == null || $scope.form.email == null) {
            var confirmation = alert("Vui không để trống");
           /* var text =  "Vui lòng nhâp tên hãng tàu"
            $scope.form.tenhangtau = text; */
            return false;
        } else if ($scope.items.some(t => t.idhangtau !== $scope.form.idhangtau && t.tenhangtau.toUpperCase() === $scope.form.tenhangtau.toUpperCase())) {
            var confirmation = alert("Tên hãng tàu đã tồn tại");
            return false;
        }
        else if (!phoneNumberPattern.test($scope.form.sdt)) {
            var confirmation = alert("Vui lòng nhập đúng định dạng số đth");
            return false;
        } else if (!emailPattern.test($scope.form.email)) {
            var confirmation = alert("Vui lòng nhập đúng định dạng email");
            return false;
        }
        else {
            return true;
        }

    }
    $scope.save = function () {
        
        if ($scope.validate() == true) {
            var item = angular.copy($scope.form);
            var url = `/rest/hangtau/save`;
            $http.post(url, item).then(response => {
                $scope.items.push(response.data);
                alert("Thêm loại hãng tàu mới thành công")
                $scope.reset();
            }).catch(error => {
                alert("Thêm loại hãng tàu mới thất bại");
                console.log("Error", error)
            })
        }

>>>>>>> parent of 02ee2bf (8/9/2023)
=======
    $scope.save = function () {
        var item = angular.copy($scope.form);
        var url = `/rest/hangtau/save`;
        $http.post(url,item).then(response => {
            $scope.items.push(response.data);
            alert("Thêm loại hãng tàu mới thành công")
            $scope.reset();
        }).catch(error => {
            alert("Thêm loại hãng tàu mới thất bại");
            console.log("Error",error)
        })
>>>>>>> parent of dae6c20 (4/9)
    }
    $scope.update = function () {
        var item = angular.copy($scope.form);
        var url = `/rest/hangtau/${item.idhangtau}`;
        $http.put(url, item).then(response => {
            var index = $scope.items.findIndex(a => a.idhangtau === item.idhangtau);
            $scope.items[index] = item;
            $scope.reset();                                         
            alert("Cập nhật hãng tàu thành công")
        }).catch(error=>{
            console.log("Error",error)
            alert("Cập nhật hãng tàu thất bại")
        })
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> parent of dae6c20 (4/9)
    $scope.delete = function (id){
        $http.delete(`/rest/hangtau/${id}`).then(response => {
            var index = $scope.items.findIndex(a => a.idhangtau === $scope.form.idhangtau);
            $scope.items.splice(index,1);
            alert("Xóa thành công");
        }).catch(error =>{
            alert("Xóa thành công");
            console.log("Error",error)
        })
<<<<<<< HEAD
=======
    $scope.delete = function (id) {
        var confirmation = confirm("Bạn có chắc chắn muốn xóa tuyến này?");
        if (confirmation) {
            $http.delete(`/rest/hangtau/${id}`).then(response => {
                var index = $scope.items.findIndex(a => a.idhangtau === $scope.form.idhangtau);
                $scope.items.splice(index, 1);
                alert("Xóa thành công");
                $scope.reset()
            }).catch(error => {
                alert("Hãng tàu đang được sử dụng.Vui lòng không xóa mà hãy chuyển sang trạng thái ẩn hoạt động");
                console.log("Error", error)
            })
        }

>>>>>>> parent of 02ee2bf (8/9/2023)
=======
>>>>>>> parent of dae6c20 (4/9)
    }
    

    $scope.pager = {
        page: 0,
        size: 5,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.length / this.size);
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