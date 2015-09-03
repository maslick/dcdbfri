(function() {
    var app = angular.module('dcdb', []);

    app.controller('dcdbController', ['$http', '$window', '$scope', function($http, $window, $scope) {
        var thisclass = this;
        this.iocs = [];
        this.newioc = {};
        this.restPrefix = 'http://' + location.host + '/wildfly-helloworld/dcdb/iocs';
        this.remote = false;


        this.getRestPrefix = function()
        {
            if (this.remote) {
                return this.restPrefix + '/remote';
            }
            else {
                return this.restPrefix;
            }
        };

        this.notify = function(title, text, icon) {
            var Snarl = $window.Snarl;
            Snarl.addNotification({
                title: title,
                text: text,
                icon: '<i class="' + icon + '"></i>'
            });
        };

        this.updateIOCtable = function() {
            $http.get(this.getRestPrefix()).success(function (data) {
                console.log(data);
                thisclass.iocs = data;
            });
        };

        this.updateIOCtable();

        this.delete = function(id) {
            $http.delete(this.getRestPrefix() + '/delete/' + id ).success(function (data){
                console.log(id + " was deleted from database!");
                thisclass.notify("DELETE", "IOC #" + id + " was deleted from database.", "glyphicon glyphicon-remove");
                thisclass.updateIOCtable();
            });

        };

        this.submitIoc = function() {
            if ($scope.addIOCform.$valid) {
                $http.post(this.getRestPrefix() + '/add', this.newioc, []).success(function (data) {
                    console.log("IOC added!");
                    thisclass.notify("ADD", "New IOC added", "glyphicon glyphicon-plus");
                    thisclass.updateIOCtable();
                    thisclass.newioc = {};
                });
            }
            else {
                if (!$scope.addIOCform.name.$valid) {
                    this.notify("Validation Error", "IOC name not provided!", "glyphicon glyphicon-thumbs-down");
                }
                if (!$scope.addIOCform.ip.$valid) {
                    this.notify("Validation Error", "IP not provided!", "glyphicon glyphicon-thumbs-down");
                }
            }
        };

    } ]);

})();