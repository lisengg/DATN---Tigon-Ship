const app = angular.module('danhgia-app', []);
app.controller('danhgia-ctrl', function($scope, $http) {
	$scope.items = [];
    $scope.top5=[];
    $scope.avg = [];
    $scope.danhgia=[];
	// Hàm khởi tạo
    $scope.getAll = function() {
        var url = `/rest/danhgia`;
		$http.get(url).then(response => {
			$scope.items = response.data;
		}).catch(err => {
			console.log("Error", err)
		})
    }
    $scope.getAll()

	$scope.chart = function() {
		var url = `/rest/danhgia/tuyen/avg/honson`;
		$http.get(url).then(response => {
			$scope.avg = response.data;
			console.log($scope.avg);
		
			// Lấy dữ liệu từ $scope.items và chuyển định dạng nếu cần
			var labels = $scope.avg.map(function(item) {
				return item[0]; // Tên mục
			});
			var data = $scope.avg.map(function(item) {
				return item[1]; // Số lượng
			});

			// Lấy thẻ canvas để vẽ biểu đồ
			var ctx = document.getElementById('chartHonSon').getContext('2d');

			// Tạo biểu đồ cột
			var chartHonSon = new Chart(ctx, {
				type: 'bar',
				data: {
					labels: labels,
					datasets: [{
						label: 'Điểm TB',
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
								max: 5, 
							}
						}]
					},
					legend: {
						display: false // Ẩn chú thích
					},
					title: {
						display: true,
						text: 'Bắt đầu từ Hòn Sơn',
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
		var url = `/rest/danhgia/tuyen/avg/kiengiang`;
		$http.get(url).then(response => {
			$scope.avg = response.data;
			console.log($scope.avg);
		
			// Lấy dữ liệu từ $scope.items và chuyển định dạng nếu cần
			var labels = $scope.avg.map(function(item) {
				return item[0]; // Tên mục
			});
			var data = $scope.avg.map(function(item) {
				return item[1]; // Số lượng
			});

			// Lấy thẻ canvas để vẽ biểu đồ
			var ctx = document.getElementById('chartKienGiang').getContext('2d');

			// Tạo biểu đồ cột
			var chartKienGiang = new Chart(ctx, {
				type: 'bar',
				data: {
					labels: labels,
					datasets: [{
						label: 'Điểm TB',
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
								max: 5, 
							}
						}]
					},
					legend: {
						display: false // Ẩn chú thích
					},
					title: {
						display: true,
						text: 'Bắt đầu từ Kiên Giang',
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
		var url = `/rest/danhgia/tuyen/avg/namdu`;
		$http.get(url).then(response => {
			$scope.avg = response.data;
			console.log($scope.avg);
		
			// Lấy dữ liệu từ $scope.items và chuyển định dạng nếu cần
			var labels = $scope.avg.map(function(item) {
				return item[0]; // Tên mục
			});
			var data = $scope.avg.map(function(item) {
				return item[1]; // Số lượng
			});

			// Lấy thẻ canvas để vẽ biểu đồ
			var ctx = document.getElementById('chartNamDu').getContext('2d');

			// Tạo biểu đồ cột
			var chartNamDu = new Chart(ctx, {
				type: 'bar',
				data: {
					labels: labels,
					datasets: [{
						label: 'Điểm TB',
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
								max: 5, 
							}
						}]
					},
					legend: {
						display: false // Ẩn chú thích
					},
					title: {
						display: true,
						text: 'Bắt đầu từ Nam Du',
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
		var url = `/rest/danhgia/tuyen/avg/phuquoc`;
		$http.get(url).then(response => {
			$scope.avg = response.data;
			console.log($scope.avg);
		
			// Lấy dữ liệu từ $scope.items và chuyển định dạng nếu cần
			var labels = $scope.avg.map(function(item) {
				return item[0]; // Tên mục
			});
			var data = $scope.avg.map(function(item) {
				return item[1]; // Số lượng
			});

			// Lấy thẻ canvas để vẽ biểu đồ
			var ctx = document.getElementById('chartPhuQuoc').getContext('2d');

			// Tạo biểu đồ cột
			var chartPhuQuoc = new Chart(ctx, {
				type: 'bar',
				data: {
					labels: labels,
					datasets: [{
						label: 'Điểm TB',
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
								max: 5, 
							}
						}]
					},
					legend: {
						display: false // Ẩn chú thích
					},
					title: {
						display: true,
						text: 'Bắt đầu từ Phú Quốc',
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

   /*  $scope.showDanhGia() = function() {
        var url = `/rest/danhgia/tuyen/${id}`;
		$http.get(url).then(response => {
			$scope.showDanhGia = response.data;
		}).catch(err => {
			console.log("Error", err)
		})
    } */

    $scope.showDanhGia=function() {
        var id = $scope.form.tuyen.idtuyen;
        console.log(id); // lấy id tàu để show ra ghế ngồi
        var url = `/rest/danhgia/tuyen/${id}`;
        $http.get(url).then(response => {
            $scope.danhgia = response.data;
            console.log($scope.danhgia)
        }).catch(err => {
            console.log("Error", err)
        })
    }
    
   


	// ĐIỂM TB của tất cả tuyến
	$scope.AVGAll = function() {
		var url = `/rest/danhgia/tuyen`;
		$http.get(url).then(response => {
			$scope.AVGAll = response.data;

		}).catch(err => {
			console.log("Error", err)
		})
	}

    //top5ng đi và đánh giá nhiều nhất
	$scope.top5 = function() {
		var url = `/rest/danhgia/top5`;
		$http.get(url).then(response => {
			$scope.top5 = response.data;
		}).catch(err => {
			console.log("Error", err)
		})
	}
    $scope.top5()




})