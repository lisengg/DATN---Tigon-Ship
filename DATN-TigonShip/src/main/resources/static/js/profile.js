const app = angular.module('profile-app', []);

app.controller('profile-ctrl', function($scope, $http) {

    $scope.initialize = function() {

        $http.get("/rest/authority1") 
            .then(response => {
                $scope.items = response.data; 
                $scope.put = true;
            })
            .catch(error => {
                console.error("Error fetching user profile data", error);
            });
    }

    $scope.initialize();
});
