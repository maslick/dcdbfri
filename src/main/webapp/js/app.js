(function() {
    var app = angular.module('dcdb', []);

    app.controller('dcdbController', ['$http', '$scope', function($http, $scope) {
        var thisclass = this;

        $scope.show_alert = false;
        thisclass.iocs = []

        this.status = "";
        this.newioc = {};

        this.updateIOCtable = function() {
            $http.get('http://localhost:8080/wildfly-helloworld/dcdb/iocs').success(function (data) {
                console.log(data);
                thisclass.iocs = data;
            });
        }
        this.updateIOCtable();

        this.delete = function(id) {
            $http.delete('http://localhost:8080/wildfly-helloworld/dcdb/iocs/delete/' + id ).success(function (data){
                console.log(id + " was deleted from database!");
                $scope.show_alert = true;
                thisclass.status = id + " was deleted from database!";
                thisclass.updateIOCtable();
            });

        };

        this.submitIoc = function() {
            $http.post('http://localhost:8080/wildfly-helloworld/dcdb/iocs/add', this.newioc, []).success(function (data) {
                console.log("IOC added!");
                thisclass.updateIOCtable();
                $scope.show_alert = false;
                thisclass.newioc = {};
            });
        };

    } ]);

})();