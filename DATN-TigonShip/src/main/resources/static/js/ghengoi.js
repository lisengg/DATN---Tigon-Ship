const app = angular.module('ghengoi-app', []);
app.controller('ghengoi-ctrl', function ($scope, $http) {
    $scope.items = [];
    $scope.ghe = {}
    $scope.reset = function () { 
        $scope.form.tenghe = ''    
        $scope.form.khoang = ''  
        $scope.post = true
        $scope.put = false
        $scope.dele = false
    }

    $scope.initialize = function () {
        $http.get("/rest/ghengoi").then(response => {
            $scope.items = response.data;
            $scope.reset()
        })
    }
    $scope.initialize()

    $scope.save = function () {
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
    }

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

    $scope.delete = function (id){
        $http.delete(`/rest/ghengoi/${id}`).then(response => {
            var index = $scope.items.ghengoi.findIndex(a => a.idghe === $scope.form.idghe);
            $scope.items.ghengoi.splice(index,1);
            alert("Xóa thành công");
            $scope.reset()
        }).catch(error =>{
            alert("Xóa thành công");
            console.log("Error",error)
        })
    }
    $scope.edit = function (id) {
        $scope.post = false;
        $scope.put = true;
        $scope.dele = true;
        var url = `/rest/ghe/${id}`;
        $http.get(url).then(response => {
            $scope.form = response.data;            
        }).catch(err => {
            console.log("Error", err)
        })
    }
    $scope.showGhengoi=function() {
        var idtau = $scope.form.tau.idtau;
        console.log(idtau); // lấy id tàu để show ra ghế ngồi
        var url = `/rest/ghengoi/${idtau}`;
        $http.get(url).then(response => {
            $scope.items.ghengoi = response.data;
            console.log($scope.ghe)
        }).catch(err => {
            console.log("Error", err)
        })
    }
    $scope.pager = {
        page: 0,
        size: 8,
        get items() {
            var start = this.page * this.size;
            return $scope.items.ghengoi.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.ghengoi.length / this.size);
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