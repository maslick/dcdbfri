(function() {
    var app = angular.module('dcdb', []);

    app.controller('dcdbController', ['$http', '$scope', '$window', function($http, $scope, $window) {
        var thisclass = this;
        thisclass.iocs = [];
        this.newioc = {};

        this.notify = function(title, text, icon) {
            var Snarl = $window.Snarl;
            Snarl.addNotification({
                title: title,
                text: text,
                icon: '<i class="' + icon + '"></i>'
            });
        };

        this.updateIOCtable = function() {
            $http.get('http://' + location.host + '/wildfly-helloworld/dcdb/iocs').success(function (data) {
                console.log(data);
                thisclass.iocs = data;
            });
        };

        this.updateIOCtable();

        this.delete = function(id) {
            $http.delete('http://' + location.host + '/wildfly-helloworld/dcdb/iocs/delete/' + id ).success(function (data){
                console.log(id + " was deleted from database!");
                thisclass.notify("DELETE", "IOC #" + id + " was deleted from database.", "glyphicon glyphicon-remove");
                thisclass.updateIOCtable();
            });

        };

        this.submitIoc = function() {
            $http.post('http://' + location.host + '/wildfly-helloworld/dcdb/iocs/add', this.newioc, []).success(function (data) {
                console.log("IOC added!");
                thisclass.notify("ADD", "New IOC added", "glyphicon glyphicon-plus");
                thisclass.updateIOCtable();
                thisclass.newioc = {};
            });
        };

    } ]);

})();