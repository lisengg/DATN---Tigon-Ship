const app = angular.module('doanhthu-app', []);
app.controller('doanhthu-ctrl', function($scope, $http) {
	$scope.items = [];
	$scope.doanhthu 
	$scope.doanhthutheongay = [];
    $scope.dtChiTiet = function() { // Doanh thu từng tuyến ngày hôm nay
        var url = `/rest/doanhthu/chitiet`;
		$http.get(url).then(response => {
			$scope.items = response.data;
		}).catch(err => {
			console.log("Error", err)
		})
    }
    $scope.dtChiTiet()
	$scope.dt = function() { // Tổng doanh thu ngày hôm nay
        var url = `/rest/doanhthu/doanhthu`;
		$http.get(url).then(response => {
			$scope.doanhthu = response.data;
			console.log($scope.doanhthu)
		}).catch(err => {
			console.log("Error", err)
		})
    }
    $scope.dt()

	$scope.chart = function() {
    var url = `/rest/doanhthu/bieudo`;
    $http.get(url).then(response => {
        $scope.avg = response.data;
        console.log($scope.avg);

        // Tính giá trị lớn nhất trong mảng data
        var maxDataValue = Math.max(...$scope.avg.map(item => item[1]));

        // Lấy dữ liệu từ $scope.items và chuyển định dạng nếu cần
        var labels = $scope.avg.map(function(item) {
            return item[0]; // Tên mục
        });
        var data = $scope.avg.map(function(item) {
            return item[1]; // Số lượng
        });

        // Lấy thẻ canvas để vẽ biểu đồ
        var ctx = document.getElementById('chartDoanhThu').getContext('2d');

        // Tạo biểu đồ cột
        var chartDoanhThu = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Doanh thu',
                    data: data,
                    backgroundColor: 'rgba(75, 192, 192, 1)', // Màu nền cột
                    borderColor: 'rgba(75, 192, 192, 1)', // Màu viền cột
                    borderWidth: 1,
                }]
            },
            options: {
                scales: {
                    xAxes: [{
                        barPercentage: 0.5, // Đặt độ rộng của cột là 50%
                        categoryPercentage: 0.8, // Đặt khoảng cách giữa các cột là 80%
                    }],
                    yAxes: [{
                        ticks: {
                            beginAtZero: true,
                            max: maxDataValue, // Sử dụng giá trị lớn nhất tính được
                        }
                    }]
                },
                legend: {
                    display: false // Ẩn chú thích
                },
                title: {
                    display: true,
                    text: 'Biểu đồ doanh thu hôm nay',
                    fontFamily: 'Arial',
                    fontSize: 20,
                    fontStyle: 'bold',
                    fontColor: '#333'
                }
            }
        });
    }).catch(err => {
        console.log("Error", err);
    });
}
	$scope.chart()

	$scope.doanhthuall= function(){
		
		var url = `/rest/doanhthu/tatca`;
		$http.get(url).then(function (response) {
			$scope.doanhthutheongay = response.data;
			console.log($scope.doanhthutheongay);
		}).catch(function (err) {
			console.log("Error", err);
		});
	}
	$scope.doanhthuall()
	$scope.searchByDate = function () {
		if (!$scope.selectedDate) {
			document.getElementById('check4').checked = true;
			// Hiển thị modal
			//$('#errorMessageModal').modal('show');
			return; // Ngừng thực hiện hàm nếu có lỗi
		}
		var selectedDate = new Date($scope.selectedDate);
		var currentDate = new Date();
	
		// Kiểm tra xem ngày được chọn có vượt quá ngày hiện tại không
		if (selectedDate > currentDate) {
			document.getElementById('check9').checked = true;
			
			// Hiển thị modal
			//$('#errorMessageModal').modal('show');
			
			return; // Ngừng thực hiện hàm nếu có lỗi
		}
	
		var formattedDate = $scope.selectedDate;
		console.log("Formatted Date:", formattedDate);
		var url = `/rest/doanhthu/theongay/${formattedDate}`;
		console.log("URL:", url);
	
		$http.get(url).then(function (response) {
			$scope.doanhthutheongay = response.data;
			console.log($scope.doanhthutheongay);
		}).catch(function (err) {
			console.log("Error", err);
		});
	};
	
	
	
	
	
   


})