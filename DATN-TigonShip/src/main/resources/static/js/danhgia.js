const app = angular.module('danhgia-app', []);
app.controller('danhgia-ctrl', function ($scope, $http) {
    $scope.items = [];
    $scope.ghe = {}
    $scope.reset = function () { 
    }
    $scope.initialize = function () {
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