(function() {
    var app = angular.module('dcdb', []);

    app.controller('dcdbController', ['$http', function($http){
        var store = this;
        store.iocs = []
        this.updateIOCtable = function() {
            $http.get('http://localhost:8080/wildfly-helloworld/dcdb/iocs').success(function (data) {
                console.log(data);
                store.iocs = data;
            });
        }
        this.updateIOCtable();

    } ]);

    app.controller('addIocController', ['$http', function($http){
        this.ioc = {};
        this.submitIoc = function(controller) {
            $http.post('http://localhost:8080/wildfly-helloworld/dcdb/iocs/add', this.ioc, []).success(function (data) {
                console.log("IOC added!");
                controller.updateIOCtable();
            });
            this.ioc = {};
        };
    } ]);

})();