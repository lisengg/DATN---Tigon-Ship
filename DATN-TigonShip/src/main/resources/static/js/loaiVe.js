const app = angular.module('loaive-app', []);
app.controller('loaive-ctrl', function ($scope, $http) {
    
    $scope.initialize = function () {
    $http.get("/rest/loaive").then(response => {
        $scope.items = response.data;
        console.log($scope.items);
    })}
    $scope.initialize()

})